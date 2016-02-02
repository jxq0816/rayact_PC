package com.bra.modules.reserve.listener.member;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
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
public class MemberRechargeListener {

    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveRoleService reserveRoleService;

    @EventListener
    public void onEvent(MainControlerEvent event) {
        ReserveCardStatements reserveCardStatements = new ReserveCardStatements();
        BigDecimal rechargeOfMonth = reserveCardStatementsService.rechargeOfMonth(reserveCardStatements);
        BigDecimal rechargeOfDay = reserveCardStatementsService.rechargeOfToday(reserveCardStatements);

        Map<String, Object> data = event.getMainBean().getMap();
        if (rechargeOfMonth == null) {
            rechargeOfMonth = BigDecimal.ZERO;
        }
        rechargeOfMonth = rechargeOfMonth.setScale(1, BigDecimal.ROUND_HALF_UP);
        if (rechargeOfDay == null) {
            rechargeOfDay = BigDecimal.ZERO;
        }
        rechargeOfDay = rechargeOfDay.setScale(1, BigDecimal.ROUND_HALF_UP);
        data.put("rechargeOfMonth", rechargeOfMonth);
        data.put("rechargeOfDay", rechargeOfDay);

        List<Map<String, Object>> cardStatementsList = reserveCardStatementsService.rechargeOfChart(reserveCardStatements);
        List<String> dateList = Lists.newArrayList();
        List<String> dateListJson = Lists.newArrayList();
        List<String> priceList = Lists.newArrayList();
        for (Map<String, Object> map : cardStatementsList) {
            dateList.add("'" + map.get("updateDate") + "'");
            dateListJson.add(map.get("updateDate").toString());
            priceList.add(map.get("volume") == null ? "0" : map.get("volume").toString());
        }

        data.put("memberListJson", dateListJson);
        data.put("memberChartMapX", StringUtils.join(dateList, ","));
        data.put("memberChartMapY", StringUtils.join(priceList, ","));
    }

}
