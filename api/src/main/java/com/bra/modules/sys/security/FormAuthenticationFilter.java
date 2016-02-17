/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.bra.common.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 表单验证（包含验证码）过滤类
 *
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends AccessControlFilter {

    public static final String DEFAULT_DIGEST_PARAM = "digest";
    public static final String DEFAULT_USERNAME_PARAM = "username";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //1、客户端生成的消息摘要
        String clientDigest = request.getParameter(DEFAULT_DIGEST_PARAM);
        //2、客户端传入的用户身份
        String username = request.getParameter(DEFAULT_USERNAME_PARAM);
        //3、客户端请求的参数列表
        Map<String,String[]> values = request.getParameterMap();
        Map<String, String[]> params = new LinkedHashMap<>(values);
        params.remove(DEFAULT_DIGEST_PARAM);
        //4、生成无状态Token
        StatelessToken token = new StatelessToken(username, params, clientDigest);
        try {
            //5、委托给Realm进行登录
            getSubject(request, response).login(token);
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(response); //6、登录失败
            return false;
        }
        return true;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}