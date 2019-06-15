package org.sadtech.vkbot.core.config;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class VkConnect {

    private final VkConfig vkConfig;

    private VkApiClient vkApiClient;
    private GroupActor groupActor;
    private ServiceActor serviceActor;
    private UserActor userActor;

    public VkConnect(VkConfig vkConfig) {
        this.vkConfig = vkConfig;
    }

    public VkApiClient getVkApiClient() {
        if (vkApiClient != null) {
            return vkApiClient;
        } else {
            initVkApiClient();
            return vkApiClient;
        }
    }

    public GroupActor getGroupActor() {
        if (groupActor != null) {
            return groupActor;
        } else {
            initGroupActor();
            return groupActor;
        }
    }

    public UserActor getUserActor() {
        if (userActor != null) {
            return userActor;
        } else {
            initUserActor();
            return userActor;
        }
    }

    private void initUserActor() {
        userActor = new UserActor(vkConfig.getConfigUser().getUserId(), vkConfig.getConfigUser().getToken());
    }

    public ServiceActor getServiceActor() {
        if (serviceActor != null) {
            return serviceActor;
        } else {
            initServiceActor();
            return serviceActor;
        }
    }

    private void initVkApiClient() {
        vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
    }

    private void initGroupActor() {
        groupActor = new GroupActor(vkConfig.getConfigGroup().getGroupId(), vkConfig.getConfigGroup().getGroupToken());
    }

    private void initServiceActor() {
        serviceActor = new ServiceActor(vkConfig.getConfigService().getAppId(), vkConfig.getConfigService().getServiceToken());
    }
}
