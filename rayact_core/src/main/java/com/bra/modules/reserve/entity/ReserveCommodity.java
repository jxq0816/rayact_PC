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
	private ReserveCommodityType commodityType;		// 商品类别外键
	private String shelvesStatus;		// 上架状态
	private String unit; //单位
	private String quickSearch;//快速搜索

	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
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
	
}