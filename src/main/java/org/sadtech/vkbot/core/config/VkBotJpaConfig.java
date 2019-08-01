package org.sadtech.vkbot.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [28/07/2019]
 */
@EnableJpaRepositories(basePackages = "org.sadtech.vkbot.core.repository.jpa")
@EntityScan(basePackages = "org.sadtech.vkbot.core.domain.jpa")
public class VkBotJpaConfig {

}
