package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 消息Entity
 * @author ddt
 * @version 2016-05-23
 */
public class Message extends DataEntity<Message> {
	
	private static final long serialVersionUID = 1L;
	private String subject;		// subject
	private String targetName;		// target_name
	private String targetId;		// target_id
	private String content;		// content
	private String url;		// url
	private String sendId;		// send_id
	private String sendName;		// send_name
	
	public Message() {
		super();
	}

	public Message(String id){
		super(id);
	}

	@Length(min=0, max=30, message="subject长度必须介于 0 和 30 之间")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Length(min=0, max=100, message="target_name长度必须介于 0 和 100 之间")
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	@Length(min=0, max=19, message="target_id长度必须介于 0 和 19 之间")
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=19, message="send_id长度必须介于 0 和 19 之间")
	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	
	@Length(min=0, max=100, message="send_name长度必须介于 0 和 100 之间")
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	
}