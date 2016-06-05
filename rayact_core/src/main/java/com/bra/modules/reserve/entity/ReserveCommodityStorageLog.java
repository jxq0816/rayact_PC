package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商品入库记录Entity
 * @author jiangxingqi
 * @version 2016-04-13
 */
public class ReserveCommodityStorageLog extends SaasEntity<ReserveCommodityStorageLog> {

	private static final long serialVersionUID = 1L;
	private ReserveVenue reserveVenue;        // 所属场馆
	private ReserveCommodity reserveCommodity;        // 入库商品
	private ReserveCommoditySupplier reserveCommoditySupplier;        // 供应商
	private Integer boxNum;        // 入库箱数
	private Integer num;        // 入库瓶数
	private Integer beforeNum;        // 入库前多少瓶
	private Integer afterNum;        // 入库后多少瓶
	private Double boxPrice;        // 一箱的价格
	private Double price;        // 一瓶水的入库价格

	public ReserveCommodityStorageLog() {
		super();
	}

	public ReserveCommodityStorageLog(String id) {
		super(id);
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	public ReserveCommodity getReserveCommodity() {
		return reserveCommodity;
	}

	public void setReserveCommodity(ReserveCommodity reserveCommodity) {
		this.reserveCommodity = reserveCommodity;
	}

	@NotNull(message = "入库箱数不能为空")
	public Integer getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}

	@NotNull(message = "入库瓶数不能为空")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@NotNull(message = "入库前多少瓶不能为空")
	public Integer getBeforeNum() {
		return beforeNum;
	}

	public void setBeforeNum(Integer beforeNum) {
		this.beforeNum = beforeNum;
	}

	@NotNull(message = "入库后多少瓶不能为空")
	public Integer getAfterNum() {
		return afterNum;
	}

	public void setAfterNum(Integer afterNum) {
		this.afterNum = afterNum;
	}

	@NotNull(message = "一箱的价格不能为空")
	public Double getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(Double boxPrice) {
		this.boxPrice = boxPrice;
	}

	@NotNull(message = "一瓶水的入库价格不能为空")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public ReserveCommoditySupplier getReserveCommoditySupplier() {
		return reserveCommoditySupplier;
	}

	public void setReserveCommoditySupplier(ReserveCommoditySupplier reserveCommoditySupplier) {
		this.reserveCommoditySupplier = reserveCommoditySupplier;
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