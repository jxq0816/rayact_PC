package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**

 * 教练Entity
 * @author jiangxingqi
 * @version 2016-01-15
 */
public class ReserveTutor extends SaasEntity<ReserveTutor> {
	
	private static final long serialVersionUID = 1L;
	private ReserveProject project;		// 项目外键
	private String name;		// 姓名
	private Double price;		// 价格
	private String level;		// 级别
	private String startUsing;		// 是否启用
	
	public ReserveTutor() {
		super();
	}

	public ReserveTutor(String id){
		super(id);
	}


	
	@Length(min=0, max=30, message="姓名长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=0, max=30, message="级别长度必须介于 0 和 30 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=1, max=1, message="是否启用长度必须介于 1 和 1 之间")
	public String getStartUsing() {
		return startUsing;
	}

	public void setStartUsing(String startUsing) {
		this.startUsing = startUsing;
	}

	public ReserveProject getProject() {
		return project;
	}

	public void setProject(ReserveProject project) {
		this.project = project;
	}
}