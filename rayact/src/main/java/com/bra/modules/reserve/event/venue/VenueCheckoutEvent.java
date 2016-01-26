package com.bra.modules.reserve.event.venue;

import com.bra.modules.reserve.entity.ReserveVenueCons;

/**
 * 场馆预定结账事件
 * Created by xiaobin on 16/1/22.
 */
public class VenueCheckoutEvent extends VenueEvent {


    public VenueCheckoutEvent(ReserveVenueCons venueCons) {
        super(venueCons);
    }
}
