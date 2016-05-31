package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 话题Entity
 * @author ddt
 * @version 2016-05-25
 */
public class CmsSubject extends DataEntity<CmsSubject> {
	
	private static final long serialVersionUID = 1L;
	private Category group;		// fk_group_id
	private String title;		// title
	private String image;		// image
	private String usedNum;		// used_num
	
	public CmsSubject() {
		super();
	}

	public CmsSubject(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="image长度必须介于 0 和 255 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=11, message="used_num长度必须介于 0 和 11 之间")
	public String getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(String usedNum) {
		this.usedNum = usedNum;
	}


	public Category getGroup() {
		return group;
	}

	public void setGroup(Category group) {
		this.group = group;
	}



}