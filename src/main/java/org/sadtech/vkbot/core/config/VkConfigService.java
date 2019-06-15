package org.sadtech.vkbot.core.config;

import java.util.Objects;

public class VkConfigService {

    private String serviceToken;
    private Integer appId;

    public String getServiceToken() {
        return serviceToken;
    }

    public Integer getAppId() {
        return appId;
    }

    public static Builder builder() {
        return new VkConfigService().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder serviceToken(String serviceToken) {
            VkConfigService.this.serviceToken = serviceToken;
            return this;
        }

        public Builder appId(Integer appId) {
            VkConfigService.this.appId = appId;
            return this;
        }

        public VkConfigService build() {
            return VkConfigService.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfigService)) return false;
        VkConfigService that = (VkConfigService) o;
        return Objects.equals(serviceToken, that.serviceToken) &&
                Objects.equals(appId, that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceToken, appId);
    }
}
