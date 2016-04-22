package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 预定信息
 * @author 11
 * @version 2016-01-06
 */
public class ReserveVenueConsItem extends SaasEntity<ReserveVenueConsItem> {

	private static final long serialVersionUID = 1L;
	private ReserveVenue reserveVenue;//所属场馆
	private ReserveField reserveField;//所属场地
	private Double consPrice;		//(实际收费价格)
	private Double orderPrice;   	//
	private String consInfo;//预定信息
	private Date consDate;//预订时间
	private String frequency;//频率(1:单次;2:每天;3:每周)
	private String consWeek;//周几?
	private String halfCourt;//是否半场(1:是)
	private ReserveVenueCons consData;
	private Date startDate;
	private Date endDate;

	private String startTime;
	private String endTime;

	public ReserveVenueConsItem() {
		super();
	}

	public ReserveVenueConsItem(String id){
		super(id);
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	public ReserveField getReserveField() {
		return reserveField;
	}

	public void setReserveField(ReserveField reserveField) {
		this.reserveField = reserveField;
	}

	public Double getConsPrice() {
		return consPrice;
	}

	public void setConsPrice(Double consPrice) {
		this.consPrice = consPrice;
	}

	public String getConsInfo() {
		return consInfo;
	}

	public void setConsInfo(String consInfo) {
		this.consInfo = consInfo;
	}

	public ReserveVenueCons getConsData() {
		return consData;
	}

	public void setConsData(ReserveVenueCons consData) {
		this.consData = consData;
	}



	public Date getConsDate() {
		return consDate;
	}

	public void setConsDate(Date consDate) {
		this.consDate = consDate;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}



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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getConsWeek() {
		return consWeek;
	}

	public void setConsWeek(String consWeek) {
		this.consWeek = consWeek;
	}

	public String getHalfCourt() {
		return halfCourt;
	}

	public void setHalfCourt(String halfCourt) {
		this.halfCourt = halfCourt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}