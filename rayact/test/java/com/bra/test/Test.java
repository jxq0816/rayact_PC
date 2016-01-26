package com.bra.test;

import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		String orderNo = calendar.getTime().getTime() + "";
		System.out.println(orderNo.length());
	}
}
