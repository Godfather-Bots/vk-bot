package org.sadtech.vk.bot.sdk.utils;

import com.vk.api.sdk.objects.users.UserMin;
import org.sadtech.vk.bot.sdk.config.VkConnect;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VkInsertData {

    private final VkApi vkApi;

    public VkInsertData(VkConnect vkConnect) {
        this.vkApi = new VkApi(vkConnect);
    }

    public String insertWords(String message, Long personId) {
        Pattern pattern = Pattern.compile("%(\\w+)%");
        Matcher m = pattern.matcher(message);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            insert(m.group(0), personId.intValue()).ifPresent(s -> m.appendReplacement(result, s));
        }
        m.appendTail(result);
        return result.toString();
    }

    private Optional<String> insert(String key, Integer userId) {
        switch (key) {
            case "%firstname%":
                return vkApi.getUserMini(userId).map(UserMin::getFirstName);
            case "%lastname%":
                return vkApi.getUserMini(userId).map(UserMin::getLastName);
        }
        return Optional.empty();
    }

}
