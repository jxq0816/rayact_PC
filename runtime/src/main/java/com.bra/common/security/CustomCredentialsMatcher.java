package com.bra.common.security;

import com.bra.common.utils.MD5Util;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by xiaobin on 15/12/29.
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        Object accountCredentials = getCredentials(info);
        Object hashPassword = null;
        boolean rtnFlag = true;
        try {
            rtnFlag = equals(token.getCredentials(), accountCredentials);
            if(!rtnFlag){
                hashPassword = MD5Util.getMD5String(new ByteArrayInputStream(toBytes(token.getCredentials())));
                rtnFlag = equals(hashPassword, accountCredentials);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rtnFlag;
    }

}