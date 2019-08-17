package org.sadtech.vkbot.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserMin;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vkbot.core.config.VkConnect;

import java.util.List;

@Slf4j
public class VkApi {

    private final VkApiClient vk;
    private final ServiceActor actor;

    public VkApi(VkConnect vkConnect) {
        vk = vkConnect.getVkApiClient();
        actor = vkConnect.getServiceActor();
    }

    public UserMin getUserMini(Integer id) {
        UserMin userMin = null;
        Gson gson = new Gson();
        try {
            List<UserXtrCounters> temp = vk.users().get(actor).userIds(String.valueOf(id)).execute();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(temp.get(0).toString()).getAsJsonObject();
            userMin = gson.fromJson(object, UserMin.class);
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
        }
        return userMin;
    }

    public String getUserUniver(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(Fields.UNIVERSITIES).execute();
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
        }
        return temp != null ? temp.get(0).getUniversities().get(0).getName() : null;
    }

    public String getUserCity(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(Fields.CITY).execute();
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
        }
        if (temp != null && checkCity(temp)) {
            return temp.get(0).getCity().getTitle();
        }
        return null;
    }

    private boolean checkCity(List<UserXtrCounters> temp) {
        return temp.get(0).getCity() != null;
    }

}
