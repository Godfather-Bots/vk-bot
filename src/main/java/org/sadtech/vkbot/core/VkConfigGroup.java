package org.sadtech.vkbot.core;

import java.util.Objects;

public class VkConfigGroup {

    private String groupToken;
    private Integer groupId;
    private String serviceToken;
    private Integer appId;
    private String groupSecretKey;
    private String groupPublicKey;

    public String getGroupToken() {
        return groupToken;
    }

    public void setGroupToken(String groupToken) {
        this.groupToken = groupToken;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getGroupSecretKey() {
        return groupSecretKey;
    }

    public void setGroupSecretKey(String groupSecretKey) {
        this.groupSecretKey = groupSecretKey;
    }

    public String getGroupPublicKey() {
        return groupPublicKey;
    }

    public void setGroupPublicKey(String groupPublicKey) {
        this.groupPublicKey = groupPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkConfigGroup)) return false;
        VkConfigGroup that = (VkConfigGroup) o;
        return Objects.equals(groupToken, that.groupToken) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(serviceToken, that.serviceToken) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(groupSecretKey, that.groupSecretKey) &&
                Objects.equals(groupPublicKey, that.groupPublicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupToken, groupId, serviceToken, appId, groupSecretKey, groupPublicKey);
    }
}
