package com.bra.modules.reserve.listener.member;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveRoleService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员充值事件
 * Created by jiangxingqi on 16/1/25.
 */
@Component
public class MemberRegisterListener {

    @Autowired
    private ReserveMemberService reserveMemberService;

    @EventListener
    public void onEvent(MainControlerEvent event) {

        ReserveMember reserveMember=new ReserveMember();

        Integer registerNum=reserveMemberService.memberRegisterOfMonth(reserveMember);

        Integer memberNum=reserveMemberService.memberRegisterOfAll(reserveMember);

        if(registerNum == null){
            registerNum=0;
        }

        Map<String, Object> data = event.getMainBean().getMap();

        data.put("registerNum", registerNum);

        data.put("memberNum", memberNum);
    }

}
