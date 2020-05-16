package org.sadtech.vk.bot.sdk.utils;

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
import org.sadtech.vk.bot.sdk.config.VkConnect;

import java.util.List;
import java.util.Optional;

/**
 *
 * Надстройка над API VK, для упрощеного доступа.
 *
 * @author upagge
 */
@Slf4j
public class VkApi {

    private final VkApiClient vk;
    private final ServiceActor actor;

    public VkApi(VkConnect vkConnect) {
        this.vk = vkConnect.getVkApiClient();
        this.actor = vkConnect.getServiceActor();
    }

    public Optional<UserMin> getUserMini(Integer id) {
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
        return Optional.ofNullable(userMin);
    }

    public Optional<String> getUserUniver(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(Fields.UNIVERSITIES).execute();
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
        }
        return temp != null ? Optional.of(temp.get(0).getUniversities().get(0).getName()) : Optional.empty();
    }

    public Optional<String> getUserCity(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).fields(Fields.CITY).execute();
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
        }
        if (temp != null && checkCity(temp)) {
            return Optional.of(temp.get(0).getCity().getTitle());
        }
        return Optional.empty();
    }

    private boolean checkCity(List<UserXtrCounters> temp) {
        return temp.get(0).getCity() != null;
    }

}
