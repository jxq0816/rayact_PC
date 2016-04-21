package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 空场审核Entity
 * @author xudl
 * @version 2016-04-21
 */
public class ReserveVenueEmptyCheck extends SaasEntity<ReserveVenueEmptyCheck> {
	
	private static final long serialVersionUID = 1L;

	private ReserveVenue venue;//场馆
	private ReserveField field;//场地
	private String startTime;		// 预定时间
	private String endTime;		// 预定时间
	private Date checkDate;		// 预定时间
	private String checkWeek;		// 周几
	private String checkStatus;		// 审核状态（1，通过；2，未通过）
	private String halfCourt;		// 是否半场(1:是)
	
	public ReserveVenueEmptyCheck() {
		super();
	}

	public ReserveVenueEmptyCheck(String id){
		super(id);
	}

	public ReserveVenue getVenue() {
		return venue;
	}

	public void setVenue(ReserveVenue venue) {
		this.venue = venue;
	}

	public ReserveField getField() {
		return field;
	}

	public void setField(ReserveField field) {
		this.field = field;
	}
	
	@Length(min=0, max=12, message="预定时间长度必须介于 0 和 12 之间")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Length(min=0, max=12, message="预定时间长度必须介于 0 和 12 之间")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=5, message="周几长度必须介于 0 和 5 之间")
	public String getCheckWeek() {
		return checkWeek;
	}

	public void setCheckWeek(String checkWeek) {
		this.checkWeek = checkWeek;
	}
	
	@Length(min=0, max=2, message="审核状态（1，通过；2，未通过）长度必须介于 0 和 2 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Length(min=0, max=2, message="是否半场(1:是)长度必须介于 0 和 2 之间")
	public String getHalfCourt() {
		return halfCourt;
	}

	public void setHalfCourt(String halfCourt) {
		this.halfCourt = halfCourt;
	}

	
}