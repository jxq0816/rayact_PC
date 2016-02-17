package com.bra.modules.reserve.entity.form;

import com.bra.common.persistence.DataEntity;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetail;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品销售主表Entity
 * @author jiangxingqi
 * @version 2016-01-12
 */
public class ReserveCommoditySellReport extends DataEntity<ReserveCommoditySellReport> {
	
	private static final long serialVersionUID = 1L;
	private List<ReserveCommoditySellDetail> sellDetailList;//销售明细列表
	private Double totalSum;		// 总计

	public List<ReserveCommoditySellDetail> getSellDetailList() {
		return sellDetailList;
	}

	public void setSellDetailList(List<ReserveCommoditySellDetail> sellDetailList) {
		this.sellDetailList = sellDetailList;
	}


	public ReserveCommoditySellReport() {
		super();
	}

	public ReserveCommoditySellReport(String id){
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