package com.bra.modules.cms.entity;

import com.bra.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 活动Entity
 * @author ddt
 * @version 2016-05-18
 */
public class Activity extends DataEntity<Activity> {
	
	private static final long serialVersionUID = 1L;
	private String subject;		// subject
	private String fkManagerId;		// fk_manager_id
	private String fkManagerName; //发起人姓名
	private Category category;//分类
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private String content;		// content
	private String url;		// url
	private String isAvaliable;		// is_avaliable
	private int order;


	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}


	public Activity() {
		super();
	}

	public Activity(String id){
		super(id);
	}

	@Length(min=0, max=30, message="subject长度必须介于 0 和 30 之间")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Length(min=0, max=19, message="fk_manager_id长度必须介于 0 和 19 之间")
	public String getFkManagerId() {
		return fkManagerId;
	}

	public void setFkManagerId(String fkManagerId) {
		this.fkManagerId = fkManagerId;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	@Length(min=0, max=1, message="is_avaliable长度必须介于 0 和 1 之间")
	public String getIsAvaliable() {
		return this.isAvaliable;
	}

	public void setIsAvaliable(String isAvaliable) {
		this.isAvaliable = isAvaliable;
	}


	public String getFkManagerName() {
		return fkManagerName;
	}

	public void setFkManagerName(String fkManagerName) {
		this.fkManagerName = fkManagerName;
	}


	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}





}