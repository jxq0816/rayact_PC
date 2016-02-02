package com.bra.modules.reserve.entity;

import org.hibernate.validator.constraints.Length;

import com.bra.common.persistence.DataEntity;

/**
 * 场馆损益Entity
 * @author jiangxingqi
 * @version 2016-02-02
 */
public class ReserveVenueBill extends DataEntity<ReserveVenueBill> {
	
	private static final long serialVersionUID = 1L;
	private Double waterBill;		// water_bill
	private String waterBillRemark;		// 水费说明
	private Double elecBill;		// elec_bill
	private String elecBillRemark;		// 电费说明
	private Double oilBill;		// oil_bill
	private String oilBillRemark;		// 油费说明
	private Double sportDeviceRepairBill;		// sport_device_repair_bill
	private String sportDeviceRepairBillRemark;		// 体育用品维修说明
	private Double officeDeviceRepairBill;		// office_device_repair_bill
	private String officeDeviceRepairBillRemark;		// 办公设备维修说明
	private Double venueDeviceRepairBill;		// venue_device_repair_bill
	private String venueDeviceRepairBillRemark;		// 场馆设备维修说明
	private Double otherBill;		// other_bill
	private String otherBillRemark;		// 其他说明
	private String fkReserveVenueId;		// fk_reserve_venue_id
	private String tenantId;		// 路由标识
	
	public ReserveVenueBill() {
		super();
	}

	public ReserveVenueBill(String id){
		super(id);
	}

	public Double getWaterBill() {
		return waterBill;
	}

	public void setWaterBill(Double waterBill) {
		this.waterBill = waterBill;
	}
	
	@Length(min=0, max=255, message="水费说明长度必须介于 0 和 255 之间")
	public String getWaterBillRemark() {
		return waterBillRemark;
	}

	public void setWaterBillRemark(String waterBillRemark) {
		this.waterBillRemark = waterBillRemark;
	}
	
	public Double getElecBill() {
		return elecBill;
	}

	public void setElecBill(Double elecBill) {
		this.elecBill = elecBill;
	}
	
	@Length(min=0, max=255, message="电费说明长度必须介于 0 和 255 之间")
	public String getElecBillRemark() {
		return elecBillRemark;
	}

	public void setElecBillRemark(String elecBillRemark) {
		this.elecBillRemark = elecBillRemark;
	}
	
	public Double getOilBill() {
		return oilBill;
	}

	public void setOilBill(Double oilBill) {
		this.oilBill = oilBill;
	}
	
	@Length(min=0, max=255, message="油费说明长度必须介于 0 和 255 之间")
	public String getOilBillRemark() {
		return oilBillRemark;
	}

	public void setOilBillRemark(String oilBillRemark) {
		this.oilBillRemark = oilBillRemark;
	}
	
	public Double getSportDeviceRepairBill() {
		return sportDeviceRepairBill;
	}

	public void setSportDeviceRepairBill(Double sportDeviceRepairBill) {
		this.sportDeviceRepairBill = sportDeviceRepairBill;
	}
	
	@Length(min=0, max=255, message="体育用品维修说明长度必须介于 0 和 255 之间")
	public String getSportDeviceRepairBillRemark() {
		return sportDeviceRepairBillRemark;
	}

	public void setSportDeviceRepairBillRemark(String sportDeviceRepairBillRemark) {
		this.sportDeviceRepairBillRemark = sportDeviceRepairBillRemark;
	}
	
	public Double getOfficeDeviceRepairBill() {
		return officeDeviceRepairBill;
	}

	public void setOfficeDeviceRepairBill(Double officeDeviceRepairBill) {
		this.officeDeviceRepairBill = officeDeviceRepairBill;
	}
	
	@Length(min=0, max=255, message="办公设备维修说明长度必须介于 0 和 255 之间")
	public String getOfficeDeviceRepairBillRemark() {
		return officeDeviceRepairBillRemark;
	}

	public void setOfficeDeviceRepairBillRemark(String officeDeviceRepairBillRemark) {
		this.officeDeviceRepairBillRemark = officeDeviceRepairBillRemark;
	}
	
	public Double getVenueDeviceRepairBill() {
		return venueDeviceRepairBill;
	}

	public void setVenueDeviceRepairBill(Double venueDeviceRepairBill) {
		this.venueDeviceRepairBill = venueDeviceRepairBill;
	}
	
	@Length(min=0, max=255, message="场馆设备维修说明长度必须介于 0 和 255 之间")
	public String getVenueDeviceRepairBillRemark() {
		return venueDeviceRepairBillRemark;
	}

	public void setVenueDeviceRepairBillRemark(String venueDeviceRepairBillRemark) {
		this.venueDeviceRepairBillRemark = venueDeviceRepairBillRemark;
	}
	
	public Double getOtherBill() {
		return otherBill;
	}

	public void setOtherBill(Double otherBill) {
		this.otherBill = otherBill;
	}
	
	@Length(min=0, max=255, message="其他说明长度必须介于 0 和 255 之间")
	public String getOtherBillRemark() {
		return otherBillRemark;
	}

	public void setOtherBillRemark(String otherBillRemark) {
		this.otherBillRemark = otherBillRemark;
	}
	
	@Length(min=0, max=19, message="fk_reserve_venue_id长度必须介于 0 和 19 之间")
	public String getFkReserveVenueId() {
		return fkReserveVenueId;
	}

	public void setFkReserveVenueId(String fkReserveVenueId) {
		this.fkReserveVenueId = fkReserveVenueId;
	}
	
	@Length(min=0, max=19, message="路由标识长度必须介于 0 和 19 之间")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}