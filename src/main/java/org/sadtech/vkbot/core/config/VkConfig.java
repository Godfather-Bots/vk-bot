package org.sadtech.vkbot.core.config;

import org.sadtech.vkbot.core.exception.ConfigException;

import java.util.Objects;
import java.util.Optional;

public class VkConfig {

    private VkConfigGroup configGroup;
    private VkConfigUser configUser;
    private VkConfigService configService;

    public VkConfigGroup getConfigGroup() {
        return Optional.ofNullable(configGroup)
                .orElseThrow(() -> new ConfigException("Конфигурация сервиса для группы найдена"));

    }

    public VkConfigUser getConfigUser() {
        return Optional.ofNullable(configUser)
                .orElseThrow(() -> new ConfigException("Конфигурация для пользователя не найдена"));

    }

    public VkConfigService getConfigService() {
        return Optional.ofNullable(configService)
                .orElseThrow(() -> new ConfigException("Конфигурация сервиса не найдена"));
    }

    public static Builder builder() {
        return new VkConfig().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder configGroup(VkConfigGroup configGroup) {
            VkConfig.this.configGroup = configGroup;
            return this;
        }

        public Builder configUser(VkConfigUser configUser) {
            VkConfig.this.configUser = configUser;
            return this;
        }

        public Builder configService(VkConfigService configService) {
            VkConfig.this.configService = configService;
            return this;
        }

        public VkConfig build() {
            return VkConfig.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfig)) return false;
        VkConfig vkConfig = (VkConfig) o;
        return Objects.equals(configGroup, vkConfig.configGroup) &&
                Objects.equals(configUser, vkConfig.configUser) &&
                Objects.equals(configService, vkConfig.configService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configGroup, configUser, configService);
    }
}
