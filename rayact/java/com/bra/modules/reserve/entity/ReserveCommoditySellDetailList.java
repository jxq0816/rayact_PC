package com.bra.modules.reserve.entity;

import com.bra.common.persistence.DataEntity;
import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 商品销售明细List
 * @author jiangxingqi
 * @version 2016-01-11
 */
@Component
public class ReserveCommoditySellDetailList extends SaasEntity<ReserveCommoditySellDetailList> {

	private List<ReserveCommoditySellDetail> reserveCommoditySellDetailList= new ArrayList<ReserveCommoditySellDetail>();

	public List<ReserveCommoditySellDetail> getReserveCommoditySellDetailList() {
		return reserveCommoditySellDetailList;
	}

	public void setReserveCommoditySellDetailList(List<ReserveCommoditySellDetail> reserveCommoditySellDetailList) {
		this.reserveCommoditySellDetailList = reserveCommoditySellDetailList;
	}
	
}