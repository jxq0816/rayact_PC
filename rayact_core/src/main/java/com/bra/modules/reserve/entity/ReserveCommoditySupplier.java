package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 供应商Entity
 * @author jiangxingqi
 * @version 2016-06-05
 */
public class ReserveCommoditySupplier extends SaasEntity<ReserveCommoditySupplier> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 供应商名称
	
	public ReserveCommoditySupplier() {
		super();
	}

	public ReserveCommoditySupplier(String id){
		super(id);
	}

	@Length(min=0, max=19, message="供应商名称长度必须介于 0 和 19 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}