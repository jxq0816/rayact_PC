package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

import javax.validation.constraints.NotNull;

/**
 * 商品销售主表Entity
 * @author jiangxingqi
 * @version 2016-01-12
 */
public class ReserveCommoditySell extends SaasEntity<ReserveCommoditySell> {
	
	private static final long serialVersionUID = 1L;
	private Double totalSum;		// 总计
	private String payType;  		//支付类型(1:储值卡 2:现金,3:银行卡,4:微信,5:支付宝,6:其它)
	private ReserveMember reserveMember;  //会员
	private ReserveCardStatements reserveCardStatement;		// 商品销售流水

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



	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public ReserveCardStatements getReserveCardStatement() {
		return reserveCardStatement;
	}

	public void setReserveCardStatement(ReserveCardStatements reserveCardStatement) {
		this.reserveCardStatement = reserveCardStatement;
	}
	
}