package org.sadtech.vk.bot.sdk.service.convert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JsonObjectToMessageConvert implements Converter<JsonObject, Message> {

    private final Gson gson = new Gson();

    @Override
    public Message convert(JsonObject source) {
        return gson.fromJson(source.get("object"), Message.class);
    }

}
