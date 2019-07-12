package org.sadtech.vkbot.core.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import org.sadtech.social.core.domain.content.Mail;
import org.sadtech.social.core.service.MailService;
import org.sadtech.vkbot.core.convert.Convert;
import org.sadtech.vkbot.core.convert.MessageMailConvert;

public class MailSubscriber extends AbstractBasketSubscribe<JsonObject, Message> {

    private final MailService mailService;
    private final Convert<Message, Mail> mailConvert = new MessageMailConvert();

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
        mailService.add(mailConvert.converting(object));
    }

}
