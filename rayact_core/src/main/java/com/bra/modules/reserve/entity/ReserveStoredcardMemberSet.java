package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 储值卡设置Entity
 * @author 琪
 * @version 2016-01-05
 */
public class ReserveStoredcardMemberSet extends SaasEntity<ReserveStoredcardMemberSet> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private ReserveProject reserveProject; //依赖项目
	private Double discountRate;	//打折比率
	private Double startPrice;		// 起始金额
	private Double endPrice;		// 结束金额
	private Double givePrice;		// 赠送金额
	
	public ReserveStoredcardMemberSet() {
		super();
	}

	public ReserveStoredcardMemberSet(String id){
		super(id);
	}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	
	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
	
	public Double getGivePrice() {
		return givePrice;
	}

	public void setGivePrice(Double givePrice) {
		this.givePrice = givePrice;
	}

	public ReserveProject getReserveProject() {
		return reserveProject;
	}

	public void setReserveProject(ReserveProject reserveProject) {
		this.reserveProject = reserveProject;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	
}