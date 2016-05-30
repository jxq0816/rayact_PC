package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 帖子Entity
 * @author ddt
 * @version 2016-05-25
 */
public class PostMain extends DataEntity<PostMain> {
	
	private static final long serialVersionUID = 1L;
	private String subject;		// subject
	private String content;		// content
	private Category group;		// fk_group_id
	private int orderNum;		// order
	private int postNum;
	
	public PostMain() {
		super();
	}

	public PostMain(String id){
		super(id);
	}

	@Length(min=0, max=30, message="subject长度必须介于 0 和 30 之间")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public Category getGroup() {
		return group;
	}

	public void setGroup(Category group) {
		this.group = group;
	}


	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}


	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}




}