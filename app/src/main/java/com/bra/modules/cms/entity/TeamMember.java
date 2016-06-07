package com.bra.modules.cms.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 战队成员Entity
 * @author ddt
 * @version 2016-05-28
 */
public class TeamMember extends DataEntity<TeamMember> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String memberId;		// member_id
	private String role;		// role
	private String phone;
	private Team team;		// team_id
	private String iscaptain;		// iscaptain
	private int height;
	private int weight;
	private int age;
	private String sportAge;
	
	public TeamMember() {
		super();
	}

	public TeamMember(String id){
		super(id);
	}

	@Length(min=0, max=10, message="name长度必须介于 0 和 10 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=19, message="member_id长度必须介于 0 和 19 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=10, message="role长度必须介于 0 和 10 之间")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Length(min=0, max=1, message="iscaptain长度必须介于 0 和 1 之间")
	public String getIscaptain() {
		return iscaptain;
	}

	public void setIscaptain(String iscaptain) {
		this.iscaptain = iscaptain;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSportAge() {
		return sportAge;
	}

	public void setSportAge(String sportAge) {
		this.sportAge = sportAge;
	}



}