package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.DataEntity;
import com.bra.common.persistence.SaasEntity;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 场馆收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveVenueProjectIntervalReport extends SaasEntity<ReserveVenueProjectDayReport> {

    private static final long serialVersionUID = 1L;

    private Double  fieldBillStoredCard;// 储值卡

    private Double  fieldBillCash;//现金

    private Double  fieldBillBankCard;//银行卡

    private Double  fieldBillWeiXin;//微信

    private Double  fieldBillAliPay;//支付宝

    private Double  fieldBillDue;// 欠账

    private Double  fieldBillOther;// 其它

    private ReserveField reserveField;//场地

    private ReserveVenue reserveVenue;//场馆

    private ReserveProject reserveProject;//项目

    private List<ReserveVenueProjectDayReport> dayReportList;//日报表

    private Date startDate;//开始日期

    private Date endDate;//结束日期

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

    public List<ReserveVenueProjectDayReport> getDayReportList() {
        return dayReportList;
    }

    public void setDayReportList(List<ReserveVenueProjectDayReport> dayReportList) {
        this.dayReportList = dayReportList;
    }

    public Double getFieldBillStoredCard() {
        return fieldBillStoredCard;
    }

    public void setFieldBillStoredCard(Double fieldBillStoredCard) {
        this.fieldBillStoredCard = fieldBillStoredCard;
    }

    public Double getFieldBillCash() {
        return fieldBillCash;
    }

    public void setFieldBillCash(Double fieldBillCash) {
        this.fieldBillCash = fieldBillCash;
    }

    public Double getFieldBillBankCard() {
        return fieldBillBankCard;
    }

    public void setFieldBillBankCard(Double fieldBillBankCard) {
        this.fieldBillBankCard = fieldBillBankCard;
    }

    public Double getFieldBillDue() {
        return fieldBillDue;
    }

    public Double getFieldBillWeiXin() {
        return fieldBillWeiXin;
    }

    public void setFieldBillWeiXin(Double fieldBillWeiXin) {
        this.fieldBillWeiXin = fieldBillWeiXin;
    }

    public Double getFieldBillAliPay() {
        return fieldBillAliPay;
    }

    public void setFieldBillAliPay(Double fieldBillAliPay) {
        this.fieldBillAliPay = fieldBillAliPay;
    }

    public void setFieldBillDue(Double fieldBillDue) {
        this.fieldBillDue = fieldBillDue;
    }

    public Double getFieldBillOther() {
        return fieldBillOther;
    }

    public void setFieldBillOther(Double fieldBillOther) {
        this.fieldBillOther = fieldBillOther;
    }



    public ReserveField getReserveField() {
        return reserveField;
    }

    public void setReserveField(ReserveField reserveField) {
        this.reserveField = reserveField;
    }

    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }

    public ReserveProject getReserveProject() {
        return reserveProject;
    }

    public void setReserveProject(ReserveProject reserveProject) {
        this.reserveProject = reserveProject;
    }


}