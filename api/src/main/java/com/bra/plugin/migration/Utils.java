package com.bra.plugin.migration;

import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.plugin.migration.entity.MobileHead;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by xiaobin on 16/2/17.
 */
public class Utils {

    public static Map<String, Object> headMap(MobileHead mobileHead) {
        Map<String, Object> json = Maps.newConcurrentMap();
        json.put("status_code", "200");
        ReserveMember user = null;
       /* if (StringUtils.isNotBlank(mobileHead.getUserId())) {
            ReserveMember userService = SpringContextHolder.getBean("userService");
            user = userService.get(mobileHead.getUserId());
        }
        if (user == null) {
            json.put("name", "立即登录");
            json.put("nick_name", "");
            json.put("login_status", "0");
            //json.put("head_img", Global.getConfig("system.url") + "mechanism/file/imageMobile/default/" + User.MODEL_NAME + "/_userPhoto?random=" + RandomUtils.nextInt(1, 100));
        } else {
            json.put("name", user.getName() == null ? "您好!" : user.getName());
            json.put("nick_name", user.getNickName() == null ? "" : user.getNickName());
            json.put("login_status", "1");
            //json.put("head_img", Global.getConfig("system.url") + "mechanism/file/imageMobile/" + mobileHead.getUserId() + "/" + User.MODEL_NAME + "/_userPhoto?random=" + RandomUtils.nextInt(1, 100));
            json.put("sex", user.getSex() == null ? "" : user.getSex());
            json.put("mobile", user.getMobile());
        }*/
        json.put("userId", mobileHead.getUserId());
        json.put("equipment", mobileHead.getEquipment());
        json.put("requestType", mobileHead.getRequestType());
        return json;
    }
}
