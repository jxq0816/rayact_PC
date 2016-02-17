package com.bra.modules.reserve.entity;

import com.bra.common.persistence.SaasEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 人次票设置Entity
 * @author 肖斌
 * @version 2016-01-18
 */
public class ReserveVenueVisitorsSet extends SaasEntity<ReserveVenueVisitorsSet> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private ReserveProject project;		// project_id
	private ReserveVenue reserveVenue;		// 场馆ID
	private String available;		// available
	private Double price;		// price
	
	public ReserveVenueVisitorsSet() {
		super();
	}

	public ReserveVenueVisitorsSet(String id){
		super(id);
	}

	@Length(min=1, max=30, message="name长度必须介于 1 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReserveProject getProject() {
		return project;
	}

	public void setProject(ReserveProject project) {
		this.project = project;
	}

	public ReserveVenue getReserveVenue() {
		return reserveVenue;
	}

	public void setReserveVenue(ReserveVenue reserveVenue) {
		this.reserveVenue = reserveVenue;
	}

	@Length(min=0, max=1, message="available长度必须介于 0 和 1 之间")
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}