package org.sadtech.vkbot.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VkInsertData {

    private VkApi vkApi;

    public VkInsertData(VkConnect vkConnect) {
        this.vkApi = new VkApi(vkConnect);
    }

    public String insertWords(String message, Integer idUser) {
        Pattern pattern = Pattern.compile("%(\\w+)%");
        Matcher m = pattern.matcher(message);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(result, insert(m.group(0), idUser));
        }
        m.appendTail(result);
        return result.toString();
    }

    private String insert(String key, Integer userId) {
        String string = "";
        switch (key) {
            case "%firstname%":
                string = vkApi.getUserMini(userId).getFirstName();
                break;
            case "%lastname%":
                string = vkApi.getUserMini(userId).getLastName();
                break;
            default:
                break;
        }
        return string;
    }

}
