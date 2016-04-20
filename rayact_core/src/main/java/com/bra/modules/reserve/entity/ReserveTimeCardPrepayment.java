package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 次卡预付款Entity
 * @author jiangxingqi
 * @version 2016-04-19
 */
public class ReserveTimeCardPrepayment extends SaasEntity<ReserveTimeCardPrepayment> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private ReserveMember reserveMember;		// 会员
	private ReserveProject reserveProject;		// 项目
	private Integer remainTime;		// 剩余次数
	private Double remainder;		// 余额
	private Double singleTimePrice;		// 单次价格
	
	public ReserveTimeCardPrepayment() {
		super();
	}

	public ReserveTimeCardPrepayment(String id){
		super(id);
	}

	@Length(min=0, max=30, message="name长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReserveMember getReserveMember() {
		return reserveMember;
	}

	public void setReserveMember(ReserveMember reserveMember) {
		this.reserveMember = reserveMember;
	}

	public ReserveProject getReserveProject() {
		return reserveProject;
	}

	public void setReserveProject(ReserveProject reserveProject) {
		this.reserveProject = reserveProject;
	}

	public Integer getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(Integer remainTime) {
		this.remainTime = remainTime;
	}
	
	public Double getRemainder() {
		return remainder;
	}

	public void setRemainder(Double remainder) {
		this.remainder = remainder;
	}
	
	public Double getSingleTimePrice() {
		return singleTimePrice;
	}

	public void setSingleTimePrice(Double singleTimePrice) {
		this.singleTimePrice = singleTimePrice;
	}
	
	@Length(min=0, max=19, message="路由标识长度必须介于 0 和 19 之间")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}