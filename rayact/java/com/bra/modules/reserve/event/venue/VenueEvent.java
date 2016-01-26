package com.bra.modules.reserve.event.venue;

import com.bra.common.event.IEvent;
import com.bra.modules.reserve.entity.ReserveVenueCons;

/**
 * 场地交易事件
 * Created by xiaobin on 16/1/22.
 */
public abstract class VenueEvent extends IEvent{

    private ReserveVenueCons reserveVenueCons;

    public VenueEvent(ReserveVenueCons venueCons) {
        this.reserveVenueCons = venueCons;
    }

    public ReserveVenueCons getReserveVenueCons() {
        return reserveVenueCons;
    }

    public void setReserveVenueCons(ReserveVenueCons reserveVenueCons) {
        this.reserveVenueCons = reserveVenueCons;
    }
}
