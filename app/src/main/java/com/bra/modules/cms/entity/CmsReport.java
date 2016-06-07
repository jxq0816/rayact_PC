package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 举报Entity
 * @author ddt
 * @version 2016-06-04
 */
public class CmsReport extends DataEntity<CmsReport> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// model_id
	private String modelName;		// model_name
	
	public CmsReport() {
		super();
	}

	public CmsReport(String id){
		super(id);
	}

	@Length(min=0, max=11, message="model_id长度必须介于 0 和 11 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Length(min=0, max=255, message="model_name长度必须介于 0 和 255 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
}