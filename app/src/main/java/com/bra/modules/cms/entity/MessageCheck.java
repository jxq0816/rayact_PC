package com.bra.modules.cms.entity;


import com.bra.common.persistence.DataEntity;

/**
 * 消息查收Entity
 * @author ddt
 * @version 2016-06-07
 */
public class MessageCheck extends DataEntity<MessageCheck> {
	
	private static final long serialVersionUID = 1L;
	
	public MessageCheck() {
		super();
	}

	public MessageCheck(String id){
		super(id);
	}

}