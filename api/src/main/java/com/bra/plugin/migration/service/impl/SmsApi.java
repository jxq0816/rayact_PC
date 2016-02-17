package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 短信验证码
 * Created by xiaobin on 16/2/17.
 */
public class SmsApi implements TransmitsService {

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String mobile = MapUtils.getString(request, "loginCode");
        //发送短信验证码
        json.put("code","8765");
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Sms_Api";
    }
}
