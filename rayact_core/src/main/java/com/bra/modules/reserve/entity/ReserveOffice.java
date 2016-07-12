package com.bra.modules.reserve.entity;

import org.hibernate.validator.constraints.Length;
import com.bra.modules.sys.entity.Area;

import com.bra.common.persistence.DataEntity;

/**
 * 集团Entity
 * @author jiangxingqi
 * @version 2016-07-11
 */
public class ReserveOffice extends DataEntity<ReserveOffice> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String sort;		// 排序
	private Area area;		// 归属区域
	private String address;		// 联系地址
	private String phone;		// 电话
	private String useable;		// 是否启用
	
	public ReserveOffice() {
		super();
	}

	public ReserveOffice(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="联系地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 0 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=64, message="是否启用长度必须介于 0 和 64 之间")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
}