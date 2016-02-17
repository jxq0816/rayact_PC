package com.bra.modules.reserve.entity.form;

/**

 * reserveEntity
 *
 * @author jiangxingqi
 * @version 2016-01-16
 */
public class ReserveCardRecordByCardType {

    private static final long serialVersionUID = 1L;
    private String cardTypeName;    // 会员类型 即储值卡类型
    private Integer memberCnt;  //会员总数
    private Double transactionVolume;        // 交易额
    private String projectName; //所属项目

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }

    public Double getTransactionVolume() {
        return transactionVolume;
    }

    public void setTransactionVolume(Double transactionVolume) {
        this.transactionVolume = transactionVolume;
    }
}