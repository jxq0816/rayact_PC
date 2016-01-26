package com.bra.modules.reserve.event.visitors;

import com.bra.modules.reserve.entity.ReserveVenueOrder;

/**
 * Created by xiaobin on 16/1/22.
 */
public class VenueOrderEvent {

    public VenueOrderEvent(ReserveVenueOrder venueOrder) {
        this.venueOrder = venueOrder;
    }

    private ReserveVenueOrder venueOrder;

    public ReserveVenueOrder getVenueOrder() {
        return venueOrder;
    }

    public void setVenueOrder(ReserveVenueOrder venueOrder) {
        this.venueOrder = venueOrder;
    }
}
