package com.bra.modules.reserve.entity;

import javax.validation.constraints.NotNull;

import com.bra.common.persistence.SaasEntity;

/**
 * 商品销售主表Entity
 * @author jiangxingqi
 * @version 2016-01-12
 */
public class ReserveCommoditySell extends SaasEntity<ReserveCommoditySell> {
	
	private static final long serialVersionUID = 1L;
	private Double totalSum;		// 总计
	private String giftFlag;       //赠品标识 0代表非赠品，1代表赠品
	private ReserveMember reserveMember;  //会员

	public ReserveMember getReserveMember() {
		return reserveMember;
	}

	public void setReserveMember(ReserveMember reserveMember) {
		this.reserveMember = reserveMember;
	}
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

	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}
	
}