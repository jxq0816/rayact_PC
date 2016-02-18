package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.MemberExtend;
import com.bra.modules.reserve.service.MemberExtendService;
import com.bra.plugin.migration.Utils;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.service.TransmitsService;

import java.util.Map;

/**
 * 退出
 * Created by xiaobin on 16/2/18.
 */
public class LogoutApi implements TransmitsService {

    private MemberExtendService getMemberExtendService(){
        return SpringContextHolder.getBean("memberExtendService");
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> map) {
        if (StringUtils.isNotBlank(mobileHead.getToken())){
            //清除用户表的token
            MemberExtend memberExtend  = new MemberExtend();
            memberExtend.setId(mobileHead.getUserId());
            getMemberExtendService().logout(memberExtend);
            mobileHead.setUserId("");
            mobileHead.setToken("");
        }
        Map<String,Object> json = Utils.headMap(mobileHead);
        return JsonUtils.writeObjectToJson(json);
    }

    @Override
    public String getName() {
        return "Logout_Api";
    }
}
