package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.PayTypeEntity;
import com.bra.modules.reserve.entity.ReserveCommodity;
import com.bra.modules.reserve.entity.ReserveCommodityType;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;

/**
 * 商品收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveCommodityDayReport extends PayTypeEntity<ReserveCommodityDayReport> {

    private static final long serialVersionUID = 1L;

    private ReserveVenue reserveVenue;//场馆

    private ReserveCommodity reserveCommodity;//商品

    private ReserveCommodityType reserveCommodityType;//商品类型

    private Date day;//日期

    public ReserveCommodityType getReserveCommodityType() {
        return reserveCommodityType;
    }

    public void setReserveCommodityType(ReserveCommodityType reserveCommodityType) {
        this.reserveCommodityType = reserveCommodityType;
    }


    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ReserveCommodity getReserveCommodity() {
        return reserveCommodity;
    }

    public void setReserveCommodity(ReserveCommodity reserveCommodity) {
        this.reserveCommodity = reserveCommodity;
    }

}