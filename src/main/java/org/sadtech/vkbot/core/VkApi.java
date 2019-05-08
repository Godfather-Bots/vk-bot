package org.sadtech.vkbot.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserMin;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import org.apache.log4j.Logger;

import java.util.List;

public class VkApi {

    private static final Logger log = Logger.getLogger(String.valueOf(VkApi.class));

    private final VkApiClient vk;
    private final GroupActor actor;

    public VkApi(VkConnect vkConnect) {
        vk = vkConnect.getVkApiClient();
        actor = vkConnect.getGroupActor();
    }

    public UserMin getUserMini(Integer id) {
        UserMin userMin = null;
        Gson gson = new Gson();
        try {
            List<UserXtrCounters> temp = vk.users().get(actor).userIds(String.valueOf(id)).execute();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(temp.get(0).toString().substring(15)).getAsJsonObject();
            object.add("last_name", object.get("lastName"));
            object.add("first_name", object.get("firstName"));
            userMin = gson.fromJson(object, UserMin.class);
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return userMin;
    }

    public String getUserUniver(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(UserField.UNIVERSITIES).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return temp != null ? temp.get(0).getUniversities().get(0).getName() : null;
    }

    public String getUserCity(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(UserField.CITY).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
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
