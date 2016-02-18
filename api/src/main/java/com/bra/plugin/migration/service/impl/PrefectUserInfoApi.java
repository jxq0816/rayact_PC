package com.bra.plugin.migration.service.impl;

import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.MemberService;
import com.bra.plugin.migration.entity.MobileHead;
import com.bra.plugin.migration.entity.StatusField;
import com.bra.plugin.migration.service.TransmitsService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 完善用户信息接口
 * Created by xiaobin on 16/2/18.
 */
public class PrefectUserInfoApi implements TransmitsService {

    private static final Logger logger = LoggerFactory.getLogger(PrefectUserInfoApi.class);

    private MemberService getMemberService(){
        return SpringContextHolder.getBean("memberService");
    }

    @Override
    public String executeTodo(MobileHead mobileHead, Map<String, Object> request) {
        logger.debug(".....................................PrefectUserInfo begining.................................................................");
        Map<String, String> map = new HashMap<>();
        String userToken = MapUtils.getString(request, "token");
        String uuid = MapUtils.getString(request, "uuid");
        String userNickName = MapUtils.getString(request, "nickName");
        String qq = MapUtils.getString(request, "qq");
        String province = MapUtils.getString(request, "province");
        String city = MapUtils.getString(request, "city");
        String userSex = MapUtils.getString(request, "sex");
        String birthday = MapUtils.getString(request, "birthday");

        if (StringUtils.isNotBlank(userToken)) {
            ReserveMember dbUser = getMemberService().get(uuid);
            if (dbUser != null) {
                logger.info("dbToken=" + dbUser.getMemberExtend().getToken()+ "-，userToken=" + userToken);
                if (dbUser.getMemberExtend().getToken().equals(userToken)) {
                    dbUser.getMemberExtend().setNickname(userNickName);
                    dbUser.getMemberExtend().setQq(qq);
                    dbUser.getMemberExtend().setBirthday(birthday);
                    //dbUser.setPhoto(userPhoto);
                    dbUser.setProvince(province);
                    dbUser.setCity(city);
                    dbUser.setDelFlag(ReserveMember.DEL_FLAG_NORMAL);
                    dbUser.setSex(userSex);
                    getMemberService().prefectUserInfo(dbUser);
                    try {
                       // SmsUtils.sendSms(SmsService.apiKey, "尊敬的" + dbUser.getName() + "用户,您已成功注册股权汇", dbUser.getMobile());
                    } catch (Exception e) {
                        logger.error("发送失败",e.getCause());
                    }
                    map.put(StatusField.TOKEN,userToken);
                    map.put(StatusField.UUID,uuid);
                    map.put(StatusField.STATUS_CODE, "200");
                    map.put(StatusField.STATUS_DEC, "提交成功");
                } else {
                    map.put(StatusField.STATUS_CODE, "201");
                    map.put(StatusField.STATUS_DEC, "Token 无效，请重新登录！");
                }
            } else {
                map.put(StatusField.STATUS_CODE, "202");
                map.put(StatusField.STATUS_DEC, "用户不存在！");
            }
        } else {
            map.put(StatusField.STATUS_CODE, "205");
            map.put(StatusField.STATUS_DEC, "提交失败，请先登录！");
        }
        logger.debug(".....................................PrefectUserInfo end.................................................................");
        return JsonUtils.writeObjectToJson(map);
    }

    @Override
    public String getName() {
        return "PrefectUserInfo_Api";
    }
}
