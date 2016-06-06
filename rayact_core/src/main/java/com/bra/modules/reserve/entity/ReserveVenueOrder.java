package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 场地人次票订单Entity
 * @author jiangxingqi
 * @version 2016-01-19
 */
public class ReserveVenueOrder extends SaasEntity<ReserveVenueOrder> {

	public static final String MODEL_KEY = "visitor";
	
	private static final long serialVersionUID = 1L;
	private ReserveVenue reserveVenue;		// 场馆ID
	private ReserveField reserveField;		// 场地ID
	private ReserveVenueVisitorsSet visitorsSet;		// 所属人次票
	private ReserveMember member;		// 会员ID
	private String consMobile;		// 订单人手机号
	private String userName;		// 订单人姓名
	private String orderType;		// 订单类型1：散客 2：会员
	private Date orderDate;		// 预定日期(yyyy-MM-dd)
	private Double orderPrice;		// 场次票单价
	private Integer collectCount;		// 商品数量
	private Double collectPrice;		// 实收金额
	private String payType;		// 支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款)
	private String startTime;
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ReserveVenueOrder() {
		super();
	}

	public ReserveVenueOrder(String id){
		super(id);
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	public ReserveVenueVisitorsSet getVisitorsSet() {
		return visitorsSet;
	}

	public void setVisitorsSet(ReserveVenueVisitorsSet visitorsSet) {
		this.visitorsSet = visitorsSet;
	}

	public ReserveMember getMember() {
		return member;
	}

	public void setMember(ReserveMember member) {
		this.member = member;
	}

	public String getConsMobile() {
		return consMobile;
	}

	public void setConsMobile(String consMobile) {
		this.consMobile = consMobile;
	}
	
	@Length(min=0, max=30, message="订单人姓名长度必须介于 0 和 30 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=1, message="订单类型1：散客 2：会员 3：团体长度必须介于 0 和 1 之间")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public Double getCollectPrice() {
		return collectPrice;
	}

	public void setCollectPrice(Double collectPrice) {
		this.collectPrice = collectPrice;
	}
	
	@Length(min=0, max=1, message="支付类型长度必须介于 0 和 1 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public ReserveField getReserveField() {
		return reserveField;
	}

	public void setReserveField(ReserveField reserveField) {
		this.reserveField = reserveField;
	}

	//----------------和数据库无关---------------------------

	private ReserveTutor tutor;

	public ReserveTutor getTutor() {
		return tutor;
	}

	public void setTutor(ReserveTutor tutor) {
		this.tutor = tutor;
	}


}