package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.modules.reserve.service.SmsService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 手机号验证码校验
 * Created by xiaobin on 16/2/17.
 */
public class CheckMobileCodeApi implements TransmitsService {
    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        Map<String, Object> json = Utils.headMap(mobileHead);
        SmsService smsService = SpringContextHolder.getBean("smsService");
        String mobile = MapUtils.getString(map, "mobile");
        String mobileCode = MapUtils.getString(map, "mobileCode");
        int code = smsService.checkSmsCode(mobile, mobileCode, "MOBILE_APP");
        if (code != 1) {
            json.put("status_code","201");
            json.put("message","验证码有误");
            return JsonUtils.writeObjectToJson(json);
        }
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "CheckMobileCode_Api";
    }
}
