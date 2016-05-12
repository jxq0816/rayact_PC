package com.bra.common.web;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by xiaobin on 15/11/20.
 */
public class ViewResult {

    private Boolean status;

    private String msg;

    private Map<String, Object> map = Maps.newConcurrentMap();

    public ViewResult() {
    }

    public ViewResult(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ViewResult(Boolean status, String msg, Map<String, Object> map) {
        this.status = status;
        this.msg = msg;
        this.map = map;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
