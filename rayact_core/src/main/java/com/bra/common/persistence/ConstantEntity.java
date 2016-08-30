/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.common.persistence;

/**
 * Entity支持类
 *
 * @version 2014-05-16
 */
public abstract class ConstantEntity{
    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";


    public static final String YES = "1";
    public static final String NO = "0";

    /*交易类型*/
    public static final String STOREDCARD_RECHARGE = "1";//储值卡充值
    public static final String STOREDCARD_REFUND = "2";//储值卡退费
    public static final String COMMODITY_CONSUMPTION ="3";//商品消费
    public static final String COMMODITY_CONSUMPTION_TOTAL ="33";//商品消费合计
    public static final String ADMIN_EDIT_BALANCE ="4";//超级管理员修改余额
    public static final String CANCELLATION_REFUND ="5";//销户退还用户的金额
    public static final String CANCELLATION_PENALTY ="6";//储值卡销户违约金
    public static final String TIMECARD_RECHARGE ="7";//次卡充值
    public static final String FIELD_RESERVE ="8";//场地预订
    public static final String FIELD_TICKET ="9";//场次票售卖
    public static final String TUTOR_CHARGE_ ="10";//教练收入
    public static final String TIMECARD_REFUND="11";//次卡退还
    public static final String TIMECARD_PENALTY="11";//次卡销户违约金

    /*支付类型*/
    public static final String STOREDCARD="1";//储值卡,次卡（预储值）
    public static final String CASH="2";//现金
    public static final String BANK_CARD="3";//银行卡
    public static final String WEINXIN="4";//微信
    public static final String ALIPAY="5";//支付宝
    public static final String COUPON="6";//优惠券
    public static final String ON_ACCOUNT="7";//优惠券
    public static final String MULTIPLE_PAYMENT ="8";//多方式付款
    public static final String PERSONAL_WEIXIN ="9";//微信个人
    public static final String PERSONAL_ALIPAY ="10";//支付宝（个人）
    public static final String TRANSFER ="11";//转账
}
