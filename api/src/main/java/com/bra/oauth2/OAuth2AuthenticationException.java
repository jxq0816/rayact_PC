package com.bra.oauth2;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by xiaobin on 16/2/15.
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }
}