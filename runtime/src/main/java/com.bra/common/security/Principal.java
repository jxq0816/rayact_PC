package com.bra.common.security;

/**
 * Created by xiaobin on 16/1/26.
 */

import java.io.Serializable;

/**
 * 授权用户信息
 */
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 编号
    private String loginName; // 登录名
    private String name; // 姓名
    private boolean mobileLogin; // 是否手机登录
    private String tenantId;//路由标识


//		private Map<String, Object> cacheMap;

    public Principal(String id, String loginName, String name,String tenantId, boolean mobileLogin) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.mobileLogin = mobileLogin;
        this.tenantId = tenantId;
    }

    public Principal(String id, String loginName, String name, boolean mobileLogin) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.mobileLogin = mobileLogin;
    }

    public String getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取SESSIONID
     */
    public String getSessionid() {
        try{
            return (String) SecurityUtil.getSession().getId();
        }catch (Exception e) {
            return "";
        }
    }


    @Override
    public String toString() {
        return id;
    }

}