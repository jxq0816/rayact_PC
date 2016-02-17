package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;

import java.util.Map;

/**
 * 会员注册
 * Created by xiaobin on 16/2/17.
 */
public class RegisterApi implements TransmitsService {

    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        Map<String,Object> json = Utils.headMap(mobileHead);

        return JsonUtils.writeObjectToJson(json);
    }

    public String getName() {
        return "Register_Api";
    }
}
