/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.entity;

import com.bra.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 青草球队Entity
 * @author 青草球队
 * @version 2016-07-26
 */
public class TeamTmp extends DataEntity<TeamTmp> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String reamrks;		// reamrks
	private String zb;		// zb
	private String rz;		// rz
	
	public TeamTmp() {
		super();
	}

	public TeamTmp(String id){
		super(id);
	}

	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="reamrks长度必须介于 0 和 255 之间")
	public String getReamrks() {
		return reamrks;
	}

	public void setReamrks(String reamrks) {
		this.reamrks = reamrks;
	}
	
	@Length(min=0, max=255, message="zb长度必须介于 0 和 255 之间")
	public String getZb() {
		return zb;
	}

	public void setZb(String zb) {
		this.zb = zb;
	}
	
	@Length(min=0, max=255, message="rz长度必须介于 0 和 255 之间")
	public String getRz() {
		return rz;
	}

	public void setRz(String rz) {
		this.rz = rz;
	}
	
}