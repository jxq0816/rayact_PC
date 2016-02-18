package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberService;
import com.bra.modules.reserve.service.SmsService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 会员注册
 * Created by xiaobin on 16/2/17.
 * 手机号  mobile
 * 密码   password
 * 验证码   mobileCode
 */
public class RegisterApi implements TransmitsService {

    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        Map<String,Object> json = Utils.headMap(mobileHead);
        String mobile = MapUtils.getString(map, "mobile");
        String password=MapUtils.getString(map, "password");
        if(StringUtils.isBlank(mobile)){
            json.put("status_code","201");
            json.put("message","手机号不能为空");
            return JsonUtils.writeObjectToJson(json);
        }
        if(StringUtils.isBlank(password)){
            json.put("status_code","201");
            json.put("message","密码不能为空");
            return JsonUtils.writeObjectToJson(json);
        }

        MemberService memberService = SpringContextHolder.getBean("memberService");
        ReserveMember reserveMember=new ReserveMember();
        reserveMember.setMobile(mobile);
        reserveMember.setPassword(password);
        //保存新注册的会员的手机号和密码
        memberService.register(reserveMember);
        json.put("status_code", "200");
        json.put("message", "注册成功");
        return JsonUtils.writeObjectToJson(json);
    }

    public String getName() {
        return "Register_Api";
    }
}
