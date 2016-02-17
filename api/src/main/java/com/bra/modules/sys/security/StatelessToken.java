package com.bra.modules.sys.security;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
public class StatelessToken implements AuthenticationToken {

    private String userId;
    private Map<String, ?> params;
    private String clientDigest;

    public Object getPrincipal() {
        return userId;
    }

    public Object getCredentials() {
        return clientDigest;
    }

    public StatelessToken(String userId, Map<String, ?> params, String clientDigest) {
        this.userId = userId;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }
}