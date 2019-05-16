package org.sadtech.vkbot.core.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.messages.MessageAttachmentType;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.Mail;
import org.sadtech.bot.core.domain.attachment.Attachment;
import org.sadtech.bot.core.domain.attachment.AudioMessage;
import org.sadtech.vkbot.core.service.distribution.MailService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
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
        mail.setDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(message.getDate()), TimeZone.getDefault().toZoneId()));
        mail.setId(message.getId());
        mail.setPersonId(message.getPeerId());

        List<Attachment> attachments = new ArrayList<>();
        for (MessageAttachment attachment : message.getAttachments()) {
            if (MessageAttachmentType.AUDIO_MESSAGE.equals(attachment.getType())) {
                AudioMessage audioMessage = new AudioMessage();
                audioMessage.setLinkOdd(attachment.getAudioMessage().getLinkOgg());
                attachments.add(audioMessage);
            }
        }
        mail.setAttachments(attachments);
        return mail;
    }

}
