package com.bra.modules.cms.json;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaobin on 16/2/19.
 */
public class HomeArticle {
    private String id;

    private String title;

    private List<Map<String, Object>> value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, Object>> getValue() {
        return value;
    }

    public void setValue(List<Map<String, Object>> value) {
        this.value = value;
    }
}
