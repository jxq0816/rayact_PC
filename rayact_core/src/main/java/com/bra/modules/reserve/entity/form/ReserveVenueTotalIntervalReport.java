package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.SaasEntity;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;

/**
 * 场馆收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveVenueTotalIntervalReport extends SaasEntity<ReserveVenueTotalIntervalReport> {

    private static final long serialVersionUID = 1L;

    private ReserveVenue reserveVenue;//场馆

    private String queryType;//查询类型 0:流水统计 1:收益统计

    private String transactionType;//交易类型

    private String payType;//支付类型

    private Double bill;//消费金额

    private Double  storedCardBill;// 储值卡

    private Double  cashBill;//现金

    private Double  bankCardBill;//银行卡

    private Double  weiXinBill;//微信

    private Double  personalWeiXinBill;//微信

    private Double  personalAliPayBill;//支付宝个人

    private Double  aliPayBill;//支付宝

    private Double  otherBill;// 优惠券

    private Double  dueBill;// 欠账

    private Double  transferBill;// 转账

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


    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Double getStoredCardBill() {
        return storedCardBill;
    }

    public void setStoredCardBill(Double storedCardBill) {
        this.storedCardBill = storedCardBill;
    }

    public Double getCashBill() {
        return cashBill;
    }

    public void setCashBill(Double cashBill) {
        this.cashBill = cashBill;
    }

    public Double getBankCardBill() {
        return bankCardBill;
    }

    public void setBankCardBill(Double bankCardBill) {
        this.bankCardBill = bankCardBill;
    }

    public Double getWeiXinBill() {
        return weiXinBill;
    }

    public void setWeiXinBill(Double weiXinBill) {
        this.weiXinBill = weiXinBill;
    }

    public Double getAliPayBill() {
        return aliPayBill;
    }

    public void setAliPayBill(Double aliPayBill) {
        this.aliPayBill = aliPayBill;
    }

    public Double getDueBill() {
        return dueBill;
    }

    public void setDueBill(Double dueBill) {
        this.dueBill = dueBill;
    }

    public Double getOtherBill() {
        return otherBill;
    }

    public void setOtherBill(Double otherBill) {
        this.otherBill = otherBill;
    }

    public ReserveVenue getReserveVenue() {
        return reserveVenue;
    }

    public void setReserveVenue(ReserveVenue reserveVenue) {
        this.reserveVenue = reserveVenue;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Double getPersonalWeiXinBill() {
        return personalWeiXinBill;
    }

    public void setPersonalWeiXinBill(Double personalWeiXinBill) {
        this.personalWeiXinBill = personalWeiXinBill;
    }

    public Double getPersonalAliPayBill() {
        return personalAliPayBill;
    }

    public void setPersonalAliPayBill(Double personalAliPayBill) {
        this.personalAliPayBill = personalAliPayBill;
    }

    public Double getTransferBill() {
        return transferBill;
    }

    public void setTransferBill(Double transferBill) {
        this.transferBill = transferBill;
    }

}