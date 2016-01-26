package com.bra.modules.reserve.event.main.bean;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by xiaobin on 16/1/25.
 */
public class MainBean {

    private Map<String,Object> map = Maps.newConcurrentMap();

    public MainBean(){
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
