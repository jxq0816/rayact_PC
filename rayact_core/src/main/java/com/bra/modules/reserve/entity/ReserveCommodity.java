package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商品Entity
 * @author jiangxingqi
 * @version 2016-01-07
 */
public class ReserveCommodity extends SaasEntity<ReserveCommodity> {


	private static final long serialVersionUID = 1L;
	private String commodityId;//商品编号
	private String name;		// 名称
	private double price;		// 价格
	private Integer repertoryNum;		// 库存数量

	private ReserveCommodityType commodityType;		// 商品类别
	private ReserveVenue reserveVenue;//场馆
	private ReserveCommoditySupplier reserveCommoditySupplier;//供应商
	private String shelvesStatus;		// 上架状态
	private int unit; //规格
	private String unitName; //单位名称 如 瓶
	private String quickSearch;//快速搜索

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public ReserveCommodity() {
		super();
	}

	public ReserveCommodity(String id){
		super(id);
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public ReserveCommodityType getCommodityType() {return commodityType;}
	public void setCommodityType(ReserveCommodityType commodityType) {this.commodityType = commodityType;}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Integer getRepertoryNum() {
		return repertoryNum;
	}

	public void setRepertoryNum(Integer repertoryNum) {
		this.repertoryNum = repertoryNum;
	}

	
	@Length(min=1, max=1, message="上架状态长度必须介于 1 和 1 之间")
	public String getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(String shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	public ReserveCommoditySupplier getReserveCommoditySupplier() {
		return reserveCommoditySupplier;
	}

	public void setReserveCommoditySupplier(ReserveCommoditySupplier reserveCommoditySupplier) {
		this.reserveCommoditySupplier = reserveCommoditySupplier;
	}
	
}