package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.PayTypeEntity;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;

/**

 * 商品收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveMemberDayReport extends PayTypeEntity<ReserveMemberDayReport> {

    private static final long serialVersionUID = 1L;

    private ReserveVenue reserveVenue;//场馆

    private ReserveProject reserveProject;//项目

    private ReserveStoredcardMemberSet storedcardMemberSet;//会员类型

    private Date day;//日期


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

    public ReserveProject getReserveProject() {
        return reserveProject;
    }

    public void setReserveProject(ReserveProject reserveProject) {
        this.reserveProject = reserveProject;
    }

    public ReserveStoredcardMemberSet getStoredcardMemberSet() {
        return storedcardMemberSet;
    }

    public void setStoredcardMemberSet(ReserveStoredcardMemberSet storedcardMemberSet) {
        this.storedcardMemberSet = storedcardMemberSet;
    }
}