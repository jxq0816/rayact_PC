package com.bra.plugin.migration.service.impl;

import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;

import java.util.Map;

/**
 * 忘记密码
 * Created by xiaobin on 16/2/18.
 */
public class ForgetPasswordApi implements TransmitsService{
    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        return null;
    }

    @Override
    public String getName() {
        return "ForgetPassword_Api";
    }
}
