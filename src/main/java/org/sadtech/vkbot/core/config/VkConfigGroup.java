package org.sadtech.vkbot.core.config;

import java.util.Objects;

public class VkConfigGroup {

    private String groupToken;
    private Integer groupId;

    public String getGroupToken() {
        return groupToken;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public static Builder builder() {
        return new VkConfigGroup().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder groupId(Integer groupId) {
            VkConfigGroup.this.groupId = groupId;
            return this;
        }

        public Builder groupToken(String groupToken) {
            VkConfigGroup.this.groupToken = groupToken;
            return this;
        }

        public VkConfigGroup build() {
            return VkConfigGroup.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfigGroup)) return false;
        VkConfigGroup that = (VkConfigGroup) o;
        return Objects.equals(groupToken, that.groupToken) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupToken, groupId);
    }
}
