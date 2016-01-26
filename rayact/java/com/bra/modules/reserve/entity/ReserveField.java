/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 场地管理Entity
 * @author 肖斌
 * @version 2015-12-29
 */
public class ReserveField extends SaasEntity<ReserveField> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 场地名称
	private String available;		// 是否启用
	private ReserveProject reserveProject;		// 所属项目
	private ReserveVenue reserveVenue;//所属场馆


	
	public ReserveField() {
		super();
	}

	public ReserveField(String id){
		super(id);
	}

	@Length(min=0, max=30, message="场地名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="是否启用长度必须介于 0 和 1 之间")
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public ReserveProject getReserveProject() {
		return reserveProject;
	}

	public void setReserveProject(ReserveProject reserveProject) {
		this.reserveProject = reserveProject;
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	@Override
	public String getModelName() {
		return "ReserveField";
	}

	//--------------------以下和数据库无关字段------------------------------
	private Double originalPrice;//原价格
	private Double actualPrice;//实际价格

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}
}