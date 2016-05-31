package com.bra.modules.cms.entity;

import com.bra.common.persistence.DataEntity;
import com.bra.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 关注Entity
 * @author ddt
 * @version 2016-05-23
 */
public class Focus extends DataEntity<Focus> {
	
	private static final long serialVersionUID = 1L;
	private String modelName;		// model_name
	private String modelId;		// model_id
	protected User fan;		//
	
	public Focus() {
		super();
	}

	public Focus(String id){
		super(id);
	}

	@Length(min=0, max=100, message="model_name长度必须介于 0 和 100 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=19, message="model_id长度必须介于 0 和 19 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public User getFan() {
		return fan;
	}

	public void setFan(User fan) {
		this.fan = fan;
	}



}