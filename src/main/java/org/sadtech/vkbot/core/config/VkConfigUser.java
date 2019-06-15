package org.sadtech.vkbot.core.config;

import java.util.Objects;

public class VkConfigUser {

    private Integer userId;
    private String token;

    private VkConfigUser() {

    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public static Builder builder() {
        return new VkConfigUser().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder userId(Integer userId) {
            VkConfigUser.this.userId = userId;
            return this;
        }

        public Builder token(String token) {
            VkConfigUser.this.token = token;
            return this;
        }

        public VkConfigUser build() {
            return VkConfigUser.this;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfigUser)) return false;
        VkConfigUser that = (VkConfigUser) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, token);
    }
}
