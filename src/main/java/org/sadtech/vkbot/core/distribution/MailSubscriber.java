package org.sadtech.vkbot.core.distribution;

import com.google.cloud.speech.v1.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.messages.MessageAttachmentType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.Mail;
import org.sadtech.vkbot.core.service.distribution.MailService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MailSubscriber implements EventSubscribe<JsonObject> {

    private static final Logger log = Logger.getLogger(MailSubscriber.class);

    private final MailService mailService;
    private Set<Integer> admins = new HashSet<>();
    private final Map<String, EventSubscribe<Message>> eventDistributionMap = new HashMap<>();

    public MailSubscriber(MailService mailService) {
        this.mailService = mailService;
    }

    public void setAdmins(Set<Integer> admins) {
        this.admins = admins;
    }

    public Set<Integer> getAdmins() {
        return admins;
    }

    @Override
    public void update(JsonObject object) {
        log.info("Дистрибьютор получил событие - сообщение");
        Gson gson = new Gson();
        Message userMessage = gson.fromJson(object, Message.class);
        log.info(userMessage);

        if (userMessage.getAttachments()!=null) {
            for (MessageAttachment attachment : userMessage.getAttachments()) {
                if (MessageAttachmentType.AUDIO_MESSAGE.equals(attachment.getType())) {
                    try (SpeechClient speechClient = SpeechClient.create()) {

                        byte[] data = IOUtils.toByteArray(userMessage.getAttachments().get(0).getAudioMessage().getLinkOgg().openStream());
                        ByteString audioBytes = ByteString.copyFrom(data);

                        // Builds the sync recognize request
                        RecognitionConfig config = RecognitionConfig.newBuilder()
                                .setEncoding(RecognitionConfig.AudioEncoding.OGG_OPUS)
                                .setSampleRateHertz(16000)
                                .setLanguageCode("ru-RU")
                                .build();
                        RecognitionAudio audio = RecognitionAudio.newBuilder()
                                .setContent(audioBytes)
                                .build();

                        // Performs speech recognition on the audio file
                        RecognizeResponse response = speechClient.recognize(config, audio);
                        List<SpeechRecognitionResult> results = response.getResultsList();

                        for (SpeechRecognitionResult result : results) {
                            // There can be several alternative transcripts for a given chunk of speech. Just use the
                            // first (most likely) one here.
                            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                            userMessage.setText(alternative.getTranscript());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }




        if (userMessage.getPeerId() > 2000000000) {
            if (eventDistributionMap.containsKey("chat")) {
                eventDistributionMap.get("chat").update(userMessage);
            }
        } else {
            if (admins.contains(userMessage.getPeerId()) && eventDistributionMap.containsKey("terminal")) {
                log.info("Сообщение отправлено в репозиторий команд");
                eventDistributionMap.get("terminal").update(userMessage);
            } else {
                log.info("Сообщение отправленно на добавление в репозиторий");
                mailService.add(createMaail(userMessage));
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private Mail createMaail(Message message) {
        Mail mail = new Mail();
        mail.setMessage(message.getText());
        mail.setDate(message.getDate());
        mail.setId(message.getId());
        mail.setPersonId(message.getPeerId());
        return mail;
    }

}
