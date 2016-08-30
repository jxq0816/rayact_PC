package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

import java.util.Date;

/**
 * 商品销售明细Entity
 * @author jiangxingqi
 * @version 2016-01-12

 */
public class ReserveCommoditySellDetail extends SaasEntity<ReserveCommoditySellDetail> {
	
	private static final long serialVersionUID = 1L;
	private ReserveCommodity reserveCommodity;		// 商品
	private ReserveCommoditySell reserveCommoditySell;		// 商品销售主表
	private ReserveCardStatements reserveCardStatement;		// 商品销售流水

	private ReserveMember reserveMember;//会员
	private String giftFlag;       //赠品标识 0代表非赠品，1代表赠品
	private Integer num;		// 数量
	private Double price;		// 价格
	private Double detailSum;		// 总和

	public ReserveMember getReserveMember() {
		return reserveMember;
	}

	public void setReserveMember(ReserveMember reserveMember) {
		this.reserveMember = reserveMember;
	}

	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}
	
	public ReserveCommoditySellDetail() {
		super();
	}

	public ReserveCommoditySellDetail(String id){
		super(id);
	}

	public ReserveCommodity getReserveCommodity() {
		return reserveCommodity;
	}

	public void setReserveCommodity(ReserveCommodity reserveCommodity) {
		this.reserveCommodity = reserveCommodity;
	}

	public ReserveCommoditySell getReserveCommoditySell() {
		return reserveCommoditySell;
	}

	public void setReserveCommoditySell(ReserveCommoditySell reserveCommoditySell) {
		this.reserveCommoditySell = reserveCommoditySell;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getDetailSum() {
		return detailSum;
	}

	public void setDetailSum(Double detailSum) {
		this.detailSum = detailSum;
	}


	public ReserveCardStatements getReserveCardStatement() {
		return reserveCardStatement;
	}

	public void setReserveCardStatement(ReserveCardStatements reserveCardStatement) {
		this.reserveCardStatement = reserveCardStatement;
	}


	//---------------------------------------------------------
	private Date startDate;
	private Date endDate;
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}