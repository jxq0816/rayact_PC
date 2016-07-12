/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 项目管理Entity
 * @author xiaobin
 * @version 2015-12-29
 */
public class ReserveProject extends SaasEntity<ReserveProject> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 场地名称
	private String available;		// 是否启用
	
	public ReserveProject() {
		super();
	}

	public ReserveProject(String id){
		super(id);
	}

	@Length(min=0, max=30, message="场地名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="是否启用长度必须介于 0 和 1 之间")
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
}