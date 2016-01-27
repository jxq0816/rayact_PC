DROP TABLE reserve_field_price_set;
DROP TABLE reserve_field_holiday_price_set;


/* Create Tables */
/*场地价格设定(常规价格设定)*/
CREATE TABLE reserve_field_price_set
(
	id varchar(19) NOT NULL,
	venue_id varchar(19) NOT NULL COMMENT '所属场馆',
	field_id varchar(19) NOT NULL COMMENT '所属场地',
	week varchar(5)  COMMENT '类型?(如:1:周一至周五,2:周六,3:周日)',
	field_time varchar(10) COMMENT '时间',
	cons_type varchar(1) COMMENT '消费类型(1:散客,2:会员,3:团体)',
	cons_price DOUBLE COMMENT '价格',
	cons_json varchar(1000) COMMENT '按照时间和价格组装成json',
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);

/*场地价格设定(节假日价格设定)*/
create TABLE reserve_field_holiday_price_set(
	id varchar(19) NOT NULL,
	venue_id varchar(19) NOT NULL COMMENT '所属场馆',
	field_id varchar(19) NOT NULL COMMENT '所属场地',
	start_date varchar(10)  COMMENT '假日开始时间' COMMENT '日期类型',
	end_date varchar(10) COMMENT '假日结束时间'  COMMENT  '日期类型',
	field_start_time VARCHAR(12) COMMENT  '开始时间' COMMENT '时间类型',
	field_end_time VARCHAR(12) COMMENT  '结束时间' COMMENT '时间类型',
	cons_type varchar(1) COMMENT '消费类型(1:散客,2:会员,3:团体)',
	cons_price DOUBLE COMMENT '消费价格',
	cons_json varchar(1000) COMMENT '按照时间和价格组装成json',
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);


