package com.bra.modules.reserve.listener.venue;

import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.event.venue.VenueCheckoutEvent;
import com.bra.modules.reserve.service.ReserveTutorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaobin on 16/1/22.
 */
@Component
public class TutorCheckoutListener{

    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;

    protected int getOrder() {
        return 2;
    }

    @EventListener
    protected void onEvent(VenueCheckoutEvent event) {
        ReserveVenueCons cons = event.getReserveVenueCons();
        //教练费用
        ReserveTutorOrder tutorOrder = cons.getTutorOrder();
        if (tutorOrder != null) {
            tutorOrder.setModelId(cons.getId());
            tutorOrder.setModelKey(ReserveVenueCons.MODEL_KEY);
            tutorOrder.setReserveType("2");
            reserveTutorOrderService.save(tutorOrder);

        }
    }
}
