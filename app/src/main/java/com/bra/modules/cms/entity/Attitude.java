package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 点赞Entity
 * @author ddt
 * @version 2016-05-17
 */
public class Attitude extends DataEntity<Attitude> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// model_id
	private String modelName;		// model_name
	private String status;		// status
	
	public Attitude() {
		super();
	}

	public Attitude(String id){
		super(id);
	}

	@Length(min=0, max=19, message="model_id长度必须介于 0 和 19 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Length(min=0, max=100, message="model_name长度必须介于 0 和 100 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=1, message="status长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}