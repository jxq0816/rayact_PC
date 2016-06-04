package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 收藏Entity
 * @author ddt
 * @version 2016-06-02
 */
public class CmsCollection extends DataEntity<CmsCollection> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// model_id
	private String modelName;		// model_name
	private String url;		// url
	
	public CmsCollection() {
		super();
	}

	public CmsCollection(String id){
		super(id);
	}

	@Length(min=0, max=19, message="model_id长度必须介于 0 和 19 之间")
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
	
	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}