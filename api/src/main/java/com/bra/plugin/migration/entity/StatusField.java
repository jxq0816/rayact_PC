package com.bra.plugin.migration.entity;

public class StatusField {
	
	/**
	 * 报头信息
	 */

	public static final String STATUS_CODE = "status_code";
	public static final String STATUS_DEC = "status_dec";
	
	/**
	 * 状态值
	 */
	public static final String DO_SUCCESS ="200";//成功
	public static final String DO_SUCCESS_DEC ="成功";//成功
	public static final String WORONG_PASSWORD ="201";//密码错误
	public static final String WORONG_PASSWORD_DEC ="密码有误";//密码错误
	public static final String NOT_EXIST ="202";//不存在
	public static final String NOT_EXIST_DEC ="不存在";//不存在
	public static final String IS_EXIST ="203";//已存在
	public static final String IS_EXIST_DEC ="已存在";//已存在
	public static final String SMS_INVALID ="204";//验证码无效
	public static final String SMS_INVALID_DEC ="验证码无效";//验证码无效
	public static final String DO_FAILED ="205";//失败
	public static final String DO_FAILED_DEC ="失败";//失败
	
	public static final String TOKEN = "token";
	public static final String UUID = "uuid";
}
