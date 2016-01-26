package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

import java.util.Date;

/**
 * reserveEntity
 * @author jiangxingqi
 * @version 2016-01-16
 */
public class ReserveCardStatements extends SaasEntity<ReserveCardStatements> {
	
	private static final long serialVersionUID = 1L;
	private ReserveMember reserveMember;	// 会员编号外键
	private String transactionType;  //交易类型
	private Double transactionVolume;		// 交易额


	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public ReserveCardStatements() {
		super();
	}

	public ReserveCardStatements(String id){
		super(id);
	}

	public ReserveMember getReserveMember() {
		return reserveMember;
	}

	public void setReserveMember(ReserveMember reserveMember) {
		this.reserveMember = reserveMember;
	}

	public Double getTransactionVolume() {
		return transactionVolume;
	}

	public void setTransactionVolume(Double transactionVolume) {
		this.transactionVolume = transactionVolume;
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