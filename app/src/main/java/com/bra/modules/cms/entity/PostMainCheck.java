package com.bra.modules.cms.entity;


import com.bra.common.persistence.DataEntity;

/**
 * 帖子核查Entity
 * @author ddt
 * @version 2016-06-24
 */
public class PostMainCheck extends DataEntity<PostMainCheck> {
	
	private static final long serialVersionUID = 1L;
	
	public PostMainCheck() {
		super();
	}

	public PostMainCheck(String id){
		super(id);
	}

}