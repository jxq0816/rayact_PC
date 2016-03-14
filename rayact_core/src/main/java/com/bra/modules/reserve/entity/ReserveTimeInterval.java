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
	private Integer startMonth;		// 开始月
	private Integer endMonth;		// 结束月
	private Integer startDate;		// 开始日
	private Integer endDate;		// 结束日

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

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
}