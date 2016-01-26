package com.bra.modules.reserve.web.form;

/**
 * 场地订单
 * Created by xiaobin on 16/1/25.
 */
public class ReserveVenueConsOrder {

    private String venueName;

    private Double orderPrice;

    private int memberCount;//会员卡

    private int moneyCount;//现金

    private int bankCartCount;//银行卡

    private int weixinCount;//微信

    private int zfbCount;//支付宝

    private int otherCount;//其它

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(int moneyCount) {
        this.moneyCount = moneyCount;
    }

    public int getBankCartCount() {
        return bankCartCount;
    }

    public void setBankCartCount(int bankCartCount) {
        this.bankCartCount = bankCartCount;
    }

    public int getWeixinCount() {
        return weixinCount;
    }

    public void setWeixinCount(int weixinCount) {
        this.weixinCount = weixinCount;
    }

    public int getZfbCount() {
        return zfbCount;
    }

    public void setZfbCount(int zfbCount) {
        this.zfbCount = zfbCount;
    }

    public int getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(int otherCount) {
        this.otherCount = otherCount;
    }
}
