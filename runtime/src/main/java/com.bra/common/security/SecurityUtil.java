package com.bra.common.security;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

/**
 * Created by xiaobin on 16/1/26.
 */
public class SecurityUtil {

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException | InvalidSessionException ignored) {

        }
        return null;
    }
}
