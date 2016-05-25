package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 多方式付款Entity
 * @author jiangxingqi
 * @version 2016-04-20
 */
public class ReserveMultiplePayment extends SaasEntity<ReserveMultiplePayment> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单编号
	private ReserveField field;//场地 该字段主要是为了统计场地收入而添加
	private String payType;		//支付类型(1:储值卡，2:现金,3:银行卡,4:微信,5:支付宝,6:优惠券，7：打白条;8:多方式付款;9:微信个人，10：支付宝（个人）)
	private Double paymentAmount;		// payment_amount
	
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

	public ReserveField getField() {
		return field;
	}

	public void setField(ReserveField field) {
		this.field = field;
	}

}