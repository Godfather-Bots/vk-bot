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
import org.sadtech.bot.core.service.MailService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MailSubscriber extends AbstractBasketSubscribe<JsonObject, Message> {

    private static final Logger log = Logger.getLogger(MailSubscriber.class);

    private final MailService mailService;

    public MailSubscriber(MailService mailService) {
        this.mailService = mailService;
        this.convert = (object) -> {
            Gson gson = new Gson();
            return gson.fromJson(object.getAsJsonObject("object"), Message.class);
        };
    }

    @Override
    protected boolean check(JsonObject object) {
        String type = object.get("type").getAsString();
        return "message_new".equals(type);
    }

    @Override
    public void processing(Message object) {
        mailService.add(createMail(object));
    }

    private Mail createMail(Message message) {
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
