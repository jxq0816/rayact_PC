package com.bra.modules.sys.security;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/16.
 */
public class StatelessToken implements AuthenticationToken {

    private String username;
    private Map<String, ?> params;
    private String clientDigest;

    public Object getPrincipal() {
        return username;
    }

    public Object getCredentials() {
        return clientDigest;
    }

    public StatelessToken(String username, Map<String, ?> params, String clientDigest) {
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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