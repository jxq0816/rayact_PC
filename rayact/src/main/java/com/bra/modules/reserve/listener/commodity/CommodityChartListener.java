package com.bra.modules.reserve.listener.commodity;

import com.bra.modules.reserve.event.main.MainControlerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * Created by lenovo on 2016/1/25.
 */
@Component
public class CommodityChartListener {

    @EventListener
    public void onEvent(MainControlerEvent event){

    }
}
