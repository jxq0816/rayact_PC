package com.bra.api.entity;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
public class BackMsg {

    private String status;

    private String message;

    private Map<String, Object> value;

    public BackMsg() {
    }

    public BackMsg(String status, String message, Map<String, Object> value) {
        this.status = status;
        this.message = message;
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }
}
