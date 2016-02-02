package com.bra.modules.reserve.listener.commodity;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by lenovo on 2016/1/25.
 */
@Component
public class CommodityChartListener {

    @Autowired
    private ReserveCommoditySellService reserveCommoditySellService;

    @EventListener
    public void onEvent(MainControlerEvent event){
        ReserveCardStatements reserveCardStatements = new ReserveCardStatements();
        BigDecimal sellOfMonth = reserveCommoditySellService.sellOfMonth(reserveCardStatements);
        BigDecimal sellOfToday = reserveCommoditySellService.sellOfToday(reserveCardStatements);

        Map<String, Object> data = event.getMainBean().getMap();
        if (sellOfMonth == null) {
            sellOfMonth = BigDecimal.ZERO;
        }
        if (sellOfToday == null) {
            sellOfToday = BigDecimal.ZERO;
        }
        sellOfToday= sellOfToday.setScale(1, BigDecimal.ROUND_HALF_UP);
        sellOfMonth=sellOfMonth.setScale(1, BigDecimal.ROUND_HALF_UP);
        data.put("sellOfToday", sellOfToday);
        data.put("sellOfMonth", sellOfMonth);


        List<Map<String, Object>> cardStatementsList = reserveCommoditySellService.sellOfChart(reserveCardStatements);
        List<String> dateList = Lists.newArrayList();
        List<String> volumeList = Lists.newArrayList();
        List<String> commodityJson = Lists.newArrayList();
        for (Map<String, Object> map : cardStatementsList) {
            dateList.add("'" + map.get("updateDate") + "'");
            if(map.get("updateDate")==null){
                commodityJson.add("");
            }else{
                commodityJson.add(map.get("updateDate").toString());
            }
            if(map.get("volume")==null){
                volumeList.add("");
            }else{
                volumeList.add(map.get("volume").toString());
            }


        }

        data.put("commodityJson",commodityJson);
        data.put("sellChartMapX", StringUtils.join(dateList, ","));
        data.put("sellChartMapY", StringUtils.join(volumeList, ","));

    }
}
