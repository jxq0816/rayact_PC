package com.bra.plugin.migration.entity;

/**
 * Created by xiaobin on 16/2/16.
 */
public class MobileHead {

    private String userId;

    private String equipment;

    private String token;

    private String requestType;

    public MobileHead(String userId, String equipment, String requestType, String token) {
        this.userId = userId;
        this.equipment = equipment;
        this.requestType = requestType;
        this.token = token;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
