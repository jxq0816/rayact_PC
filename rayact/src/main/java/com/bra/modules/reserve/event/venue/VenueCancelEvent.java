package com.bra.modules.reserve.event.venue;

import com.bra.modules.reserve.entity.ReserveVenueCons;

/**
 * 取消预定事件
 * Created by xiaobin on 16/1/22.
 */
public class VenueCancelEvent extends VenueEvent{

    public VenueCancelEvent(ReserveVenueCons venueCons,String tutorOrderId) {
        super(venueCons);
        this.tutorOrderId = tutorOrderId;
    }

    private String tutorOrderId;

    public String getTutorOrderId() {
        return tutorOrderId;
    }

    public void setTutorOrderId(String tutorOrderId) {
        this.tutorOrderId = tutorOrderId;
    }
}
