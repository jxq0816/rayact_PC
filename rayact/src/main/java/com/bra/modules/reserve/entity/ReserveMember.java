/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 会员管理Entity
 * @author 肖斌
 * @version 2015-12-29
 */
public class ReserveMember extends SaasEntity<ReserveMember> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String mobile;		// 手机号
	private String password;		// 密码
	private String sfz;		// 身份证
	private String province;		// province
	private String city;		// city
	private String area;		// area
	private String address;		// 地址
	private String email;		// 邮箱
	private String sex;		// 性别
	private Double remainder ; //储值卡余额
	private Integer residue;//次卡剩余次数

	//-------------------------储值卡-次卡------------------------------
	private String cartno;		// 卡号
	private String cartType;		// 卡号类型(会员类型1:储值卡,2:次卡)
	private ReserveStoredcardMemberSet storedcardSet;
	private ReserveTimecardMemberSet timecardSet;

	public ReserveStoredcardMemberSet getStoredcardSet() {
		return storedcardSet;
	}

	public void setStoredcardSet(ReserveStoredcardMemberSet storedcardSet) {
		this.storedcardSet = storedcardSet;
	}

	public ReserveTimecardMemberSet getTimecardSet() {
		return timecardSet;
	}

	public void setTimecardSet(ReserveTimecardMemberSet timecardSet) {
		this.timecardSet = timecardSet;
	}

	private Date validitystart;		// 卡号开始日期
	private Date validityend;		// 卡号结束日期
	
	public ReserveMember() {
		super();
	}

	public ReserveMember(String id){
		super(id);
	}

	@Length(min=0, max=30, message="姓名长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=16, message="密码长度必须介于 0 和 16 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=18, message="身份证长度必须介于 0 和 18 之间")
	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}
	
	@Length(min=0, max=32, message="province长度必须介于 0 和 32 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=32, message="city长度必须介于 0 和 32 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=32, message="area长度必须介于 0 和 32 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=100, message="地址长度必须介于 0 和 100 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=30, message="邮箱长度必须介于 0 和 30 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=20, message="卡号长度必须介于 0 和 20 之间")
	public String getCartno() {
		return cartno;
	}

	public void setCartno(String cartno) {
		this.cartno = cartno;
	}
	
	@Length(min=0, max=1, message="卡号类型长度必须介于 0 和 1 之间")
	public String getCartType() {
		return cartType;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValiditystart() {
		return validitystart;
	}

	public void setValiditystart(Date validitystart) {
		this.validitystart = validitystart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidityend() {
		return validityend;
	}

	public void setValidityend(Date validityend) {
		this.validityend = validityend;
	}

	public Double getRemainder() {
		return remainder;
	}

	public void setRemainder(Double remainder) {
		this.remainder = remainder;
	}

	public Integer getResidue() {
		return residue;
	}

	public void setResidue(Integer residue) {
		this.residue = residue;
	}
}