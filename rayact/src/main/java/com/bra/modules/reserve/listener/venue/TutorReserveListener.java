package com.bra.modules.reserve.listener.venue;

import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveTutor;
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.bra.modules.reserve.event.venue.VenueReserveEvent;
import com.bra.modules.reserve.service.ReserveTutorOrderService;
import com.bra.modules.reserve.service.ReserveTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xiaobin on 16/1/22.
 */
@Component
public class TutorReserveListener{

    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveTutorOrderService reserveTutorOrderService;

    protected int getOrder() {
        return 0;
    }

    @EventListener
    protected void onEvent(VenueReserveEvent event) {
        List<String> timeList = event.getTimeList();
        ReserveVenueCons reserveVenueCons = event.getReserveVenueCons();
        Integer length = timeList.size();//时间以半小时单位.统计预订了多长时间
        if (reserveVenueCons.getTutor() != null && StringUtils.isNotBlank(reserveVenueCons.getTutor().getId())) {
            ReserveTutor tutor = reserveTutorService.get(new ReserveTutor(reserveVenueCons.getTutor().getId()));
            ReserveTutorOrder tutorOrder = new ReserveTutorOrder();
            tutorOrder.setModelId(reserveVenueCons.getId());
            tutorOrder.setTutor(tutor);
            tutorOrder.setModelKey(ReserveVenueCons.MODEL_KEY);
            tutorOrder.setOrderPrice(tutor.getPrice()/2);
            tutorOrder.setOrderCount(length);
            tutorOrder.setTotalPrice(tutor.getPrice()/2 * length);//时间以半小时单位，而教练的费用以一小时为单位，所以需要除2
            tutorOrder.setReserveType("1");
            reserveTutorOrderService.save(tutorOrder);
        }
    }
}
