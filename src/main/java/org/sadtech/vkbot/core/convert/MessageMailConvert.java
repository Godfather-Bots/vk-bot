package org.sadtech.vkbot.core.convert;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import org.sadtech.bot.core.domain.content.Mail;
import org.sadtech.bot.core.domain.content.attachment.Attachment;
import org.sadtech.bot.core.domain.content.attachment.AudioMessage;
import org.sadtech.bot.core.domain.content.attachment.Geo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class MessageMailConvert implements Convert<Message, Mail> {

    @Override
    public Mail converting(Message message) {
        Mail mail = new Mail();
        mail.setMessage(message.getText());
        mail.setCreateDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(message.getDate()), TimeZone.getDefault().toZoneId()));
        mail.setId(message.getId());
        mail.setPersonId(message.getPeerId());
        mail.setAttachments(message.getAttachments()
                .stream()
                .map(this::convertAttachment)
                .collect(Collectors.toList()));
        if (message.getGeo()!=null) {
            mail.getAttachments().add(convertGeo(message.getGeo()));
        }
        return mail;
    }

    private Geo convertGeo(com.vk.api.sdk.objects.base.Geo geoVk) {
        return Geo.builder()
                .coordinate(geoVk.getCoordinates().getLatitude(),
                        geoVk.getCoordinates().getLongitude())
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
