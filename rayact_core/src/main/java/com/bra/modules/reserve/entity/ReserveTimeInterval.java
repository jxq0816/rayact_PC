package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

/**
 * 商品Entity
 * @author jiangxingqi
 * @version 2016-01-07
 */
public class ReserveTimeInterval extends SaasEntity<ReserveTimeInterval> {

	private static final long serialVersionUID = 1L;
	private String id;//商品编号
	private String name;		// 名称
	private String startDate;		// 开始日期
	private String endDate;		// 结束日期

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}