-- ----------------------------
-- Table structure for reserve_venue_balance
/*场地损益表*/
-- ----------------------------

DROP TABLE IF EXISTS reserve_venue_bill;
CREATE TABLE reserve_venue_bill (
  id varchar(19) NOT NULL,
  water_bill decimal(16,2),
  water_bill_remark varchar(255) comment '水费说明',
  elec_bill decimal(16,2),
  elec_bill_remark varchar(255) comment '电费说明',
  oil_bill decimal(16,2),
  oil_bill_remark varchar(255) comment '油费说明',
  sport_device_repair_bill decimal(16,2),
  sport_device_repair_bill_remark varchar(255) comment '体育用品维修说明',
  office_device_repair_bill decimal (16,2),
  office_device_repair_bill_remark varchar(255) comment '办公设备维修说明',
  venue_device_repair_bill decimal (16,2),
  venue_device_repair_bill_remark varchar(255) comment '场馆设备维修说明',
  other_bill decimal (16,2),
  other_bill_remark varchar(255) comment '其他说明',
  fk_reserve_venue_id varchar(19),
  create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;