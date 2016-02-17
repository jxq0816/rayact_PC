package com.bra.modules.reserve.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品销售明细List
 * @author jiangxingqi
 * @version 2016-01-11
 */
public class ReserveCommoditySellDetailList {

	private List<ReserveCommoditySellDetail> reserveCommoditySellDetailList= new ArrayList<ReserveCommoditySellDetail>();
	private String payType;
	private ReserveMember reserveStoredCardMember;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public ReserveMember getReserveStoredCardMember() {
		return reserveStoredCardMember;
	}

	public void setReserveStoredCardMember(ReserveMember reserveStoredCardMember) {
		this.reserveStoredCardMember = reserveStoredCardMember;
	}

	public List<ReserveCommoditySellDetail> getReserveCommoditySellDetailList() {
		return reserveCommoditySellDetailList;
	}

	public void setReserveCommoditySellDetailList(List<ReserveCommoditySellDetail> reserveCommoditySellDetailList) {
		this.reserveCommoditySellDetailList = reserveCommoditySellDetailList;
	}
	
}