package com.bra.modules.reserve.event.venue;

import com.bra.modules.reserve.entity.ReserveVenueCons;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 预定事件
 * Created by xiaobin on 16/1/22.
 */
public class VenueReserveEvent extends VenueEvent {

    private List<String> timeList = Lists.newArrayList();

    public VenueReserveEvent(ReserveVenueCons venueCons,List<String> timeList) {
        super(venueCons);
        this.timeList = timeList;
    }
    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }
}
