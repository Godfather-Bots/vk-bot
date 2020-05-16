package org.sadtech.vk.bot.sdk.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VkConfigService {

    private Integer appId;
    private String serviceToken;

}
