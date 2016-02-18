package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;

import java.util.Map;

/**
 * 用户登录
 * Created by xiaobin on 16/2/17.
 */
public class LoginApi implements TransmitsService {

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        MemberService memberService = SpringContextHolder.getBean("memberService");

        return null;
    }

    @Override
    public String getName() {
        return "Login_Api";
    }
}
