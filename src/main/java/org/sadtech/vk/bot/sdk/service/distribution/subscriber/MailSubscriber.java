package org.sadtech.vk.bot.sdk.service.distribution.subscriber;

import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import org.sadtech.social.core.domain.content.Mail;
import org.sadtech.social.core.service.MailService;
import org.springframework.core.convert.ConversionService;

public class MailSubscriber extends AbstractBasketSubscribe<JsonObject, Message> {

    private final MailService mailService;
    private final ConversionService conversionService;

    public MailSubscriber(MailService mailService, ConversionService conversionService) {
        this.mailService = mailService;
        this.conversionService = conversionService;
    }

    @Override
    public boolean check(JsonObject object) {
        String type = object.get("type").getAsString();
        return "message_new".equals(type);
    }

    @Override
    public Message convert(JsonObject object) {
        return conversionService.convert(object, Message.class);
    }

    @Override
    public void processing(Message message) {
        mailService.add(conversionService.convert(message, Mail.class));
    }

}
