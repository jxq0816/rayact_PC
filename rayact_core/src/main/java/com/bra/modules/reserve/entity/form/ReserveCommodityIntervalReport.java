package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.PayTypeEntity;
import com.bra.modules.reserve.entity.ReserveCommodity;
import com.bra.modules.reserve.entity.ReserveCommodityType;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;
import java.util.List;

/**
 * 商品收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveCommodityIntervalReport extends PayTypeEntity<ReserveCommodityIntervalReport> {

    private static final long serialVersionUID = 1L;

    private ReserveVenue reserveVenue;//场馆

    private ReserveCommodity reserveCommodity;//商品

    private ReserveCommodityType reserveCommodityType;//商品类型


    private Date startDate;//开始日期

    private Date endDate;//结束日期

    private List<ReserveCommodityDayReport> dayReportList;//日报表


    public ReserveCommodity getReserveCommodity() {
        return reserveCommodity;
    }

    public void setReserveCommodity(ReserveCommodity reserveCommodity) {
        this.reserveCommodity = reserveCommodity;
    }

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

    public List<ReserveCommodityDayReport> getDayReportList() {
        return dayReportList;
    }

    public void setDayReportList(List<ReserveCommodityDayReport> dayReportList) {
        this.dayReportList = dayReportList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}