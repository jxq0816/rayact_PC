package com.bra.modules.reserve.listener.visitors;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveTutor;
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.event.visitors.VenueOrderEvent;
import com.bra.modules.reserve.service.ReserveTutorOrderService;
import com.bra.modules.reserve.service.ReserveTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaobin on 16/1/22.
 */
@Component
public class VenueOrderTutorListener{

    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;

    protected int getOrder() {
        return 0;
    }

    @EventListener
    protected void onEvent(VenueOrderEvent event) {
        ReserveVenueOrder reserveVenueOrder = event.getVenueOrder();
        if (reserveVenueOrder.getTutor() != null && StringUtils.isNotBlank(reserveVenueOrder.getTutor().getId())) {
            ReserveTutor tutor = reserveTutorService.get(new ReserveTutor(reserveVenueOrder.getTutor().getId()));
            ReserveTutorOrder tutorOrder = new ReserveTutorOrder();
            tutorOrder.setModelId(reserveVenueOrder.getId());
            tutorOrder.setTutor(tutor);
            tutorOrder.setModelKey(ReserveVenueOrder.MODEL_KEY);
            tutorOrder.setOrderPrice(tutor.getPrice());
            tutorOrder.setOrderCount(reserveVenueOrder.getCollectCount());
            tutorOrder.setTotalPrice(tutor.getPrice() * reserveVenueOrder.getCollectCount());
            tutorOrder.setReserveType("2");//结账
            reserveTutorOrderService.save(tutorOrder);
        }
    }
}
