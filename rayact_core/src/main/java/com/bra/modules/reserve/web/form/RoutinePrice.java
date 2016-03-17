package com.bra.modules.reserve.web.form;

import com.bra.modules.reserve.entity.ReserveFieldPriceSet;
import com.bra.modules.reserve.entity.ReserveTimeInterval;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 常规价格
 * Created by xiaobin on 16/1/8.
 */
public class RoutinePrice {

    private ReserveTimeInterval reserveTimeInterval;

    public ReserveTimeInterval getReserveTimeInterval() {
        return reserveTimeInterval;
    }

    public void setReserveTimeInterval(ReserveTimeInterval reserveTimeInterval) {
        this.reserveTimeInterval = reserveTimeInterval;
    }

    private List<ReserveFieldPriceSet> fieldPriceSetList = Lists.newArrayList();

    public List<ReserveFieldPriceSet> getFieldPriceSetList() {
        return fieldPriceSetList;
    }

    public void setFieldPriceSetList(List<ReserveFieldPriceSet> fieldPriceSetList) {
        this.fieldPriceSetList = fieldPriceSetList;
    }
}
