package com.bra.modules.reserve.entity.json;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 场馆用户权限
 * Created by xiaobin on 16/1/20.
 */
public class Authority {

    public Authority() {
    }

    public Authority(String code, String name,String href) {
        this.code = code;
        this.name = name;
        this.href = href;
    }

    /*权限代码*/
    private String code;

    /*权限名称*/
    private String name;

    private String href ;

    public String getCode() {
        if(this.code!=null)
             return code.split(",")[0];
        else
            return null;
    }

    public void setCode(String code) {
        if(code!=null)
            this.code = code.split(",")[0];
        else
            this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    private List<Authority> authorityList = Lists.newArrayList();

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }
}
