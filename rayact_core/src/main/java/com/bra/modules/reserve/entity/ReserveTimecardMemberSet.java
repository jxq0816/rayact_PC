package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 次卡设置Entity
 * @author jiangxingqi
 * @version 2016-01-06
 */
public class ReserveTimecardMemberSet extends SaasEntity<ReserveTimecardMemberSet> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private ReserveProject reserveProject; //依赖项目
	private Integer minutesPerTime; //分钟每次
	private Integer startTime;		// 起始次数
	private Integer endTime;		// 结束次数
	private Integer giveTime;		// 赠送次数
	
	public ReserveTimecardMemberSet() {
		super();
	}

	public ReserveTimecardMemberSet(String id){
		super(id);
	}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	
	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	public Integer getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Integer giveTime) {
		this.giveTime = giveTime;
	}

	public ReserveProject getReserveProject() {
		return reserveProject;
	}

	public void setReserveProject(ReserveProject reserveProject) {
		this.reserveProject = reserveProject;
	}

	public Integer getMinutesPerTime() {
		return minutesPerTime;
	}

	public void setMinutesPerTime(Integer minutesPerTime) {
		this.minutesPerTime = minutesPerTime;
	}
	
}