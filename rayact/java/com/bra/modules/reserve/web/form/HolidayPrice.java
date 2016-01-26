package com.bra.modules.reserve.web.form;

import com.bra.modules.reserve.entity.ReserveFieldHolidayPriceSet;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiaobin on 16/1/8.
 */
public class HolidayPrice {

    private List<ReserveFieldHolidayPriceSet> fieldHolidayPriceSetList = Lists.newArrayList();

    public List<ReserveFieldHolidayPriceSet> getFieldHolidayPriceSetList() {
        return fieldHolidayPriceSetList;
    }

    public void setFieldHolidayPriceSetList(List<ReserveFieldHolidayPriceSet> fieldHolidayPriceSetList) {
        this.fieldHolidayPriceSetList = fieldHolidayPriceSetList;
    }
}
