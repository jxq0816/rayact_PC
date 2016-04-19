package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 发票管理Entity
 * @author xudl
 * @version 2016-04-18
 */
public class ReserveInvoice extends SaasEntity<ReserveInvoice> {
	
	private static final long serialVersionUID = 1L;

	private String userName;
	private String isDone;//
	private String invoiceHead;		// invoice_head
	private String invoiceProject;		// invoice_project
	private double invoicePrice;		// invoice_price
	private Date consumerTime;		// consumer_time
	private Date applyTime;		// apply_time
	private String isExpress;		// is_express
	private String address;		// address

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ReserveInvoice() {
		super();
	}

	public ReserveInvoice(String id){
		super(id);
	}
	
	@Length(min=0, max=200, message="invoice_head长度必须介于 0 和 200 之间")
	public String getInvoiceHead() {
		return invoiceHead;
	}

	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}
	
	@Length(min=0, max=200, message="invoice_project长度必须介于 0 和 200 之间")
	public String getInvoiceProject() {
		return invoiceProject;
	}

	public void setInvoiceProject(String invoiceProject) {
		this.invoiceProject = invoiceProject;
	}
	
	public double getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConsumerTime() {
		return consumerTime;
	}

	public void setConsumerTime(Date consumerTime) {
		this.consumerTime = consumerTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=0, max=2, message="is_express长度必须介于 0 和 2 之间")
	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	
	@Length(min=0, max=600, message="address长度必须介于 0 和 600 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=19, message="tenant_id长度必须介于 0 和 19 之间")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}