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
public class VkConfigGroup {

    private String groupToken;
    private Integer groupId;

}
