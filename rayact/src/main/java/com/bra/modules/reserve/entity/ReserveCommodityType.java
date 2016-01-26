package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商品类别Entity
 * @author jiangxingqi
 * @version 2016-01-07
 */
public class ReserveCommodityType extends SaasEntity<ReserveCommodityType> {
	
	private static final long serialVersionUID = 1L;
	private String id; 			//编号
	private String name;		// 名称
	
	public ReserveCommodityType() {
		super();
	}

	public ReserveCommodityType(String id){
		super(id);
	}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=19, message="编号长度必须介于 0 和 30 之间")
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}