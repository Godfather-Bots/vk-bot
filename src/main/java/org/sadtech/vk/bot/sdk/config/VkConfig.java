package org.sadtech.vk.bot.sdk.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sadtech.vk.bot.sdk.exception.ConfigException;

import java.util.Optional;

/**
 *
 * Класс для сохранения информации о конфигурации, необходимых для создания {@link VkConnect}.
 *
 * @author upagge
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                .orElseThrow(() -> new ConfigException("Конфигурация сервиса не найдена, создайте класс VkConfigService!"));
    }

}
