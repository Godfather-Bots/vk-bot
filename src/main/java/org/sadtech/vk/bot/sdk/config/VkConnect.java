package org.sadtech.vk.bot.sdk.config;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 *
 * Используется для сохранения объектов конфигурации, необходимых для работы SDK VK.
 *
 * @author upagge
 */
@RequiredArgsConstructor
public class VkConnect {

    private final VkConfig vkConfig;

    private VkApiClient vkApiClient;
    private GroupActor groupActor;
    private ServiceActor serviceActor;
    private UserActor userActor;

    public VkApiClient getVkApiClient() {
        return Optional.ofNullable(vkApiClient).orElseGet(this::initVkApiClient);
    }

    public GroupActor getGroupActor() {
        return Optional.ofNullable(groupActor).orElseGet(this::initGroupActor);
    }

    public UserActor getUserActor() {
        return Optional.ofNullable(userActor).orElseGet(this::initUserActor);
    }

    public ServiceActor getServiceActor() {
        return Optional.ofNullable(serviceActor).orElseGet(this::initServiceActor);
    }

    private UserActor initUserActor() {
        userActor = new UserActor(vkConfig.getConfigUser().getUserId(), vkConfig.getConfigUser().getToken());
        return userActor;
    }

    private VkApiClient initVkApiClient() {
        vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
        return vkApiClient;
    }

    private GroupActor initGroupActor() {
        groupActor = new GroupActor(vkConfig.getConfigGroup().getGroupId(), vkConfig.getConfigGroup().getGroupToken());
        return groupActor;
    }

    private ServiceActor initServiceActor() {
        serviceActor = new ServiceActor(vkConfig.getConfigService().getAppId(), vkConfig.getConfigService().getServiceToken());
        return serviceActor;
    }

}
