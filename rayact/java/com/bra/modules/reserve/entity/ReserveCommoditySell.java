package com.bra.modules.reserve.entity;

import javax.validation.constraints.NotNull;

import com.bra.common.persistence.DataEntity;
import com.bra.common.persistence.SaasEntity;

/**
 * 商品销售主表Entity
 * @author jiangxingqi
 * @version 2016-01-12
 */
public class ReserveCommoditySell extends SaasEntity<ReserveCommoditySell> {
	
	private static final long serialVersionUID = 1L;
	private Double totalSum;		// 总计
	
	public ReserveCommoditySell() {
		super();
	}

	public ReserveCommoditySell(String id){
		super(id);
	}

	@NotNull(message="总计不能为空")
	public Double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Double totalSum) {
		this.totalSum = totalSum;
	}
	
}