package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.MD5Util;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.entity.MemberExtend;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberExtendService;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.entity.StatusField;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录
 * Created by xiaobin on 16/2/17.
 */
public class LoginApi implements TransmitsService {

    private static final Logger logger = LoggerFactory.getLogger(LoginApi.class);

    private MemberService getMemberService(){
        return SpringContextHolder.getBean("memberService");
    }

    private MemberExtendService getMemberExtendService(){
        return SpringContextHolder.getBean("memberExtendService");
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {

        Map<String, String> map = new HashMap<>();

        logger.debug("。。。。。。。。。。。。LoginApi beginning。。。。。。。。。。。。。。。");
        String mobile = MapUtils.getString(request, "mobile");
        String userPassword = MapUtils.getString(request, "password");


        String token = MD5Util.getMD5String(mobile + userPassword + String.valueOf(new Date()));
        // 校验用户名密码
        if (!"".equals(userPassword) && null != userPassword) {
            ReserveMember member = new ReserveMember();
            member.setMobile(mobile);
            ReserveMember user = getMemberService().findRegisterMobile(member);
            MemberExtend memberExtend = new MemberExtend();
            if (null != user) {
                memberExtend.setId(user.getId());
                // 通过用户名登录
                // String password = MD5Util.getMD5String(userPassword);
                logger.info("dbUserPassword=" + user.getPassword() + "-，password=" + userPassword);
                if (user.getPassword().equals(userPassword)) {
                    memberExtend.setToken(token);
                    // 更新用户Token
                    getMemberExtendService().updateToken(memberExtend);

                    map.put(StatusField.STATUS_CODE, "200");
                    map.put(StatusField.UUID, user.getId());
                    map.put(StatusField.TOKEN, token);
                    map.put(StatusField.STATUS_DEC, "登录成功，船票已发送!");
                } else {
                    map.put(StatusField.STATUS_CODE, "201");
                    map.put(StatusField.STATUS_DEC, "登录名和密码不匹配，请重新登录!");
                }
            } else {
                map.put(StatusField.STATUS_CODE, "202");
                map.put(StatusField.STATUS_DEC, "杳无此人，请注册先!");
            }
        } else {
            map.put(StatusField.STATUS_CODE, "201");
        }

        logger.debug("。。。。。。。。。。。。LoginApi end。。。。。。。。。。。。。。。");
        logger.debug("set ::::::::::::::::::::::::" + JsonUtils.writeObjectToJson(map));
        return JsonUtils.writeObjectToJson(map);
    }

    @Override
    public String getName() {
        return "Login_Api";
    }
}
