package com.bra.modules.reserve.entity;

import com.bra.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 多方式付款Entity
 * @author jiangxingqi
 * @version 2016-04-20
 */
public class ReserveMultiplePayment extends DataEntity<ReserveMultiplePayment> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单编号
	private String payType;		// pay_type
	private Double paymentAmount;		// payment_amount
	private String tenantId;		// tenant_id
	
	public ReserveMultiplePayment() {
		super();
	}

	public ReserveMultiplePayment(String id){
		super(id);
	}

	@Length(min=1, max=19, message="订单编号长度必须介于 1 和 19 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=1, max=1, message="pay_type长度必须介于 1 和 1 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@NotNull(message="payment_amount不能为空")
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	@Length(min=0, max=19, message="tenant_id长度必须介于 0 和 19 之间")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}