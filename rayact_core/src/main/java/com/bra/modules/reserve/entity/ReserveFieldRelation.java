/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;

/**
 * 场地管理关系Entity
 * @author jiang
 * @version 2015-12-29
 */
public class ReserveFieldRelation extends SaasEntity<ReserveFieldRelation> {

	private static final long serialVersionUID = 1L;

	private ReserveField parentField;		// 父场地

	private ReserveField childField;		//子场地

	public ReserveField getParentField() {
		return parentField;
	}

	public void setParentField(ReserveField parentField) {
		this.parentField = parentField;
	}

	public ReserveField getChildField() {
		return childField;
	}

	public void setChildField(ReserveField childField) {
		this.childField = childField;
	}

	public ReserveFieldRelation() {
		super();
	}

	public ReserveFieldRelation(String id){
		super(id);
	}
}