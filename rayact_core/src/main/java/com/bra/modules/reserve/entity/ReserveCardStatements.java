package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

import java.util.Date;

/**
 * reserveEntity
 * @author jiangxingqi
 * @version 2016-01-16
 */
public class ReserveCardStatements extends SaasEntity<ReserveCardStatements> {
	
	private static final long serialVersionUID = 1L;
	public static final String CANCEL_ACCOUNT_RETURN = "11";
	private ReserveVenue venue;//交易发生场馆
	private ReserveMember reserveMember;	// 会员
	private ReserveCommodity reserveCommodity;//商品
	private Integer transactionNum;//数量或小时
	private String orderId;//订单编号
	private String transactionType;  //交易类型 (1：储值卡充值，2：储值卡退费，3：商品消费,33:商品消费合计 4:超级管理员修改余额 5：销户退还用户的金额 6：销户违约金;7:次卡充值,8:场地预订，9：场次票售卖，10：教练收入;11次卡退还;12：次卡违约金)
	private Double transactionVolume;		// 交易额
	private String payType; //支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款;9:微信个人，10：支付宝（个人）)

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public ReserveCardStatements() {
		super();
	}

	public ReserveCardStatements(String id){
		super(id);
	}

	public ReserveMember getReserveMember() {
		return reserveMember;
	}

	public void setReserveMember(ReserveMember reserveMember) {
		this.reserveMember = reserveMember;
	}

	public Double getTransactionVolume() {
		return transactionVolume;
	}

	public void setTransactionVolume(Double transactionVolume) {
		this.transactionVolume = transactionVolume;
	}

	public ReserveCommodity getReserveCommodity() {
		return reserveCommodity;
	}

	public void setReserveCommodity(ReserveCommodity reserveCommodity) {
		this.reserveCommodity = reserveCommodity;
	}

	public Integer getTransactionNum() {
		return transactionNum;
	}

	public void setTransactionNum(Integer transactionNum) {
		this.transactionNum = transactionNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	//---------------------------------------------------------
	private Date startDate;
	private Date endDate;
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

	//--------------------------------------

	public void setVenue(ReserveVenue venue){
		this.venue = venue;
	}
	public ReserveVenue getVenue(){
		return venue;
	}
}