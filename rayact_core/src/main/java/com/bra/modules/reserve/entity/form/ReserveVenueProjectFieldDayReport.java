package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.BaseEntity;
import com.bra.common.persistence.DataEntity;
import com.bra.common.persistence.SaasEntity;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.io.Serializable;
import java.util.Date;

/**
 * 场馆收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveVenueProjectFieldDayReport extends SaasEntity<ReserveVenueProjectFieldDayReport> {

    private static final long serialVersionUID = 1L;

    private Double bill;

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

    private Date day;

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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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


    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }
}