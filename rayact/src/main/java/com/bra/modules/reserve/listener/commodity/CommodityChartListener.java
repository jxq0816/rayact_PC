package com.bra.modules.reserve.listener.commodity;

import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.event.main.MainControlerEvent;
import com.bra.modules.reserve.service.ReserveCommoditySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    }
}
