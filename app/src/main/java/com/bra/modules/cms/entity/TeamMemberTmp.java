/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.entity;

import com.bra.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 青草球队队员Entity
 * @author 青草球队队员
 * @version 2016-07-26
 */
public class TeamMemberTmp extends DataEntity<TeamMemberTmp> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String phone;		// phone
	private String role;		// role 1:领队 2:教练 3:普通队员 4:队长
	private String cardNo;		// card_no
	private String teamNo;
	private TeamTmp teamTmp;		// team_id
	private Integer playerNum;//队员编号
	private Integer height;//身高
	private Integer weight;//体重

	public TeamMemberTmp() {
		super();
	}

	public TeamMemberTmp(String id){
		super(id);
	}

	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="phone长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="role长度必须介于 0 和 255 之间")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Length(min=0, max=255, message="card_no长度必须介于 0 和 255 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public TeamTmp getTeamTmp() {
		return teamTmp;
	}

	public void setTeamTmp(TeamTmp teamTmp) {
		this.teamTmp = teamTmp;
	}


	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(Integer playerNum) {
		this.playerNum = playerNum;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}




}