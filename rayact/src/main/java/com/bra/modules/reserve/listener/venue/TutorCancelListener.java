package com.bra.modules.reserve.listener.venue;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.event.venue.VenueCancelEvent;
import com.bra.modules.reserve.service.ReserveTutorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by jiangxingqi on 16/1/22.
 */
@Component
public class TutorCancelListener{

    @Autowired
    private ReserveTutorOrderService tutorOrderService;

    protected int getOrder() {
        return 0;
    }

    @EventListener
    protected void onEvent(VenueCancelEvent event) {
        if(StringUtils.isBlank(event.getTutorOrderId()))
            return;
        ReserveTutorOrder tutorOrder = new ReserveTutorOrder(event.getTutorOrderId());
        tutorOrderService.delete(tutorOrder);
    }
}
