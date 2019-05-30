package org.sadtech.vkbot.core;

import java.util.Objects;

public class VkConfig {

    private VkConfigGroup configGroup;
    private VkConfigUser configUser;

    public VkConfigGroup getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(VkConfigGroup configGroup) {
        this.configGroup = configGroup;
    }

    public VkConfigUser getConfigUser() {
        return configUser;
    }

    public void setConfigUser(VkConfigUser configUser) {
        this.configUser = configUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfig)) return false;
        VkConfig vkConfig = (VkConfig) o;
        return Objects.equals(configGroup, vkConfig.configGroup) &&
                Objects.equals(configUser, vkConfig.configUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configGroup, configUser);
    }
}
