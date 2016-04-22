package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 账目审核Entity
 * @author xudl
 * @version 2016-04-22
 */
public class ReserveCheckDetail extends SaasEntity<ReserveCheckDetail> {
	
	private static final long serialVersionUID = 1L;

	private ReserveVenue venue;		// 场馆ID
	private Date startDate;		// 开始日期
	private Date endDate;		// 结束日期
	private Date checkDate;		// 审核日期
	private String itemName;		// 审核项目
	private String checkStatus;		// 审核状态
	private String url;		// 审核url
	
	public ReserveCheckDetail() {
		super();
	}

	public ReserveCheckDetail(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=64, message="审核项目长度必须介于 0 和 64 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=2, message="审核状态长度必须介于 0 和 2 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Length(min=0, max=255, message="审核url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ReserveVenue getVenue() {
		return venue;
	}

	public void setVenue(ReserveVenue venue) {
		this.venue = venue;
	}



}