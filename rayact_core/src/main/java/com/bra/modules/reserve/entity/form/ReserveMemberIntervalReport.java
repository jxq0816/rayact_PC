package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.PayTypeEntity;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;
import java.util.List;

/**

 * 商品收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveMemberIntervalReport extends PayTypeEntity<ReserveMemberIntervalReport> {

    private static final long serialVersionUID = 1L;

    private ReserveVenue reserveVenue;//场馆

    private ReserveProject reserveProject;//项目

    private ReserveStoredcardMemberSet storedcardMemberSet;//会员类型

    private List<ReserveMemberDayReport> dayReports;//日报表

    private Date startDate;//开始日期

    private Date endDate;//结束日期

    public List<ReserveMemberDayReport> getDayReports() {
        return dayReports;
    }

    public void setDayReports(List<ReserveMemberDayReport> dayReports) {
        this.dayReports = dayReports;
    }


    public ReserveProject getReserveProject() {
        return reserveProject;
    }

    public void setReserveProject(ReserveProject reserveProject) {
        this.reserveProject = reserveProject;
    }


    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }


    public ReserveStoredcardMemberSet getStoredcardMemberSet() {
        return storedcardMemberSet;
    }

    public void setStoredcardMemberSet(ReserveStoredcardMemberSet storedcardMemberSet) {
        this.storedcardMemberSet = storedcardMemberSet;
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