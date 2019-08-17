package org.sadtech.vkbot.core.convert;

import com.vk.api.sdk.objects.messages.ForeignMessage;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import org.sadtech.social.core.domain.content.Mail;
import org.sadtech.social.core.domain.content.attachment.Attachment;
import org.sadtech.social.core.domain.content.attachment.AudioMessage;
import org.sadtech.social.core.domain.content.attachment.Geo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class MessageMailConvert implements Convert<Message, Mail> {

    @Override
    public Mail converting(Message message) {
        Mail mail = new Mail();
        mail.setText(message.getText());
        mail.setCreateDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(message.getDate()), TimeZone.getDefault().toZoneId()));
        mail.setId(message.getId());
        mail.setPersonId(message.getPeerId());
        List<MessageAttachment> attachments = message.getAttachments();

        if (attachments != null && !attachments.isEmpty()) {
            mail.setAttachments(
                    attachments.stream()
                            .map(this::convertAttachment)
                            .collect(Collectors.toList())
            );
        }

        com.vk.api.sdk.objects.base.Geo messageGeo = message.getGeo();
        if (messageGeo != null) {
            mail.getAttachments().add(convertGeo(messageGeo));
        }

        List<ForeignMessage> fwdMessages = message.getFwdMessages();
        if (fwdMessages != null && !fwdMessages.isEmpty()) {
            mail.setForwardMail(
                    fwdMessages.stream()
                            .map(this::convertFwdMessage)
                            .collect(Collectors.toList())
            );
        }
        mail.setAddDate(LocalDateTime.now());
        return mail;
    }

    private Mail convertFwdMessage(ForeignMessage foreignMessage) {
        Mail mail = new Mail();
        mail.setText(foreignMessage.getText());
        mail.setCreateDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(foreignMessage.getDate()), TimeZone.getDefault().toZoneId()));
        mail.setId(foreignMessage.getId());
        mail.setPersonId(foreignMessage.getPeerId());

        List<MessageAttachment> attachments = foreignMessage.getAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            mail.setAttachments(
                    attachments.stream()
                            .map(this::convertAttachment)
                            .collect(Collectors.toList())
            );
        }

        com.vk.api.sdk.objects.base.Geo messageGeo = foreignMessage.getGeo();
        if (messageGeo != null) {
            mail.getAttachments().add(convertGeo(messageGeo));
        }

        List<ForeignMessage> fwdMessages = foreignMessage.getFwdMessages();
        if (fwdMessages != null && !fwdMessages.isEmpty())
            mail.setForwardMail(
                    fwdMessages.stream()
                            .map(this::convertFwdMessage)
                            .collect(Collectors.toList())
            );
        mail.setAddDate(LocalDateTime.now());
        return mail;
    }

    private Geo convertGeo(com.vk.api.sdk.objects.base.Geo geoVk) {
        return Geo.builder()
                .coordinate(geoVk.getCoordinates().getLatitude(),
                        geoVk.getCoordinates().getLongitude())
                .city(geoVk.getPlace().getCity())
                .build();
    }

    private Attachment convertAttachment(MessageAttachment vkAttachment) {
        Attachment attachment = null;
        switch (vkAttachment.getType()) {
            case AUDIO_MESSAGE:
                AudioMessage audioMessage = new AudioMessage();
                audioMessage.setLinkOdd(vkAttachment.getAudioMessage().getLinkOgg());
                attachment = audioMessage;
                break;
        }
        return attachment;
    }
}
