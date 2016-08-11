package com.bra.common.persistence;


/**
 * Created by xiaobin on 16/1/21.
 */
public abstract class PayTypeEntity<T> extends SaasEntity<T> {


    private Double bill;//消费金额

    private Double storedCardBill;// 储值卡

    private Double cashBill;//现金

    private Double bankCardBill;//银行卡

    private Double transferBill;//转账

    private Double weiXinBill;//微信

    private Double personalWeiXinBill;//（个人）微信

    private Double aliPayBill;//支付宝

    private Double personalAliPayBill;//（个人）支付宝

    private Double dueBill;// 欠账

    private Double otherBill;// 其它

    private Double  multiplePaymentBill;// 多方式付款

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

    public Double getTransferBill() {
        return transferBill;
    }

    public void setTransferBill(Double transferBill) {
        this.transferBill = transferBill;
    }

    public Double getWeiXinBill() {
        return weiXinBill;
    }

    public void setWeiXinBill(Double weiXinBill) {
        this.weiXinBill = weiXinBill;
    }

    public Double getPersonalWeiXinBill() {
        return personalWeiXinBill;
    }

    public void setPersonalWeiXinBill(Double personalWeiXinBill) {
        this.personalWeiXinBill = personalWeiXinBill;
    }

    public Double getAliPayBill() {
        return aliPayBill;
    }

    public void setAliPayBill(Double aliPayBill) {
        this.aliPayBill = aliPayBill;
    }

    public Double getPersonalAliPayBill() {
        return personalAliPayBill;
    }

    public void setPersonalAliPayBill(Double personalAliPayBill) {
        this.personalAliPayBill = personalAliPayBill;
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

    public Double getMultiplePaymentBill() {
        return multiplePaymentBill;
    }

    public void setMultiplePaymentBill(Double multiplePaymentBill) {
        this.multiplePaymentBill = multiplePaymentBill;
    }
}
