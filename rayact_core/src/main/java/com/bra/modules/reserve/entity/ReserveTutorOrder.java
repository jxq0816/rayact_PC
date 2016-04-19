package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 教练订单Entity
 * @author 肖斌
 * @version 2016-01-19
 */
public class ReserveTutorOrder extends SaasEntity<ReserveTutorOrder> {
	
	private static final long serialVersionUID = 1L;
	private ReserveTutor tutor;		// 所属教练
	private Double orderPrice;		// 教练费用
	private Integer orderCount;    //订单数量 以半小时为单位
	private Double totalPrice;
	private String modelId;		// 业务编号(如:场地的教练费用的ID,人次售卖的教练费用的ID)
	private String modelKey;		// 业务标识(如用field标识场地的教练费用,用visitor标识人次售卖的教练费用)
	private String reserveType;     //1:预定,2:结账,3:取消
	private Date startDate;
	private Date endDate;
	
	public ReserveTutorOrder() {
		super();
	}

	public ReserveTutorOrder(String id){
		super(id);
	}

	public ReserveTutorOrder(String modelId,String modelKey){
		super();
		this.modelId = modelId;
		this.modelKey = modelKey;
	}

	public ReserveTutor getTutor() {
		return tutor;
	}

	public void setTutor(ReserveTutor tutor) {
		this.tutor = tutor;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	@Length(min=0, max=19, message="业务编号(如:场地的教练费用的ID,人次售卖的教练费用的ID)长度必须介于 0 和 19 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Length(min=0, max=19, message="业务标识(如用field标识场地的教练费用,用visitor标识人次售卖的教练费用)长度必须介于 0 和 19 之间")
	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}

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