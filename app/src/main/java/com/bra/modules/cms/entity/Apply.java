package com.bra.modules.cms.entity;

import com.bra.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 活动报名Entity
 * @author ddt
 * @version 2016-05-18
 */
public class Apply extends DataEntity<Apply> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private String activityId;		// activity_id
	private String data;
	private String name;
	private String sex;
	private int age;
	private String phone;
	
	public Apply() {
		super();
	}

	public Apply(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=19, message="activity_id长度必须介于 0 和 19 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



}