package com.bra.modules.reserve.entity.form;

import com.bra.modules.reserve.entity.ReserveVenueGift;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiaobin on 16/1/27.
 */
public class VenueGiftForm {

    private List<ReserveVenueGift> giftList = Lists.newArrayList();

    public List<ReserveVenueGift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<ReserveVenueGift> giftList) {
        this.giftList = giftList;
    }
}
