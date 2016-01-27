
DROP TABLE reserve_project;
DROP TABLE reserve_venue;
DROP TABLE reserve_field;


/* Create Tables */
/*项目设置*/
CREATE TABLE reserve_project
(
	id varchar(19) NOT NULL,
	name varchar(30),
	available char(1),
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);

/*场馆设置*/
CREATE TABLE reserve_venue
(
	id varchar(19) NOT NULL,
	name varchar(30),
	available char(1),
	address varchar(200),
	address_x varchar(20),
	address_y varchar(20),
	charge_user_id varchar(19),/*负责人*/
	start_time varchar(9),
	end_time varchar(9),
	tel varchar(20),
	more_service varchar(600),/*更多服务*/
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);
/*场地设置*/
CREATE TABLE reserve_field(
  id varchar(19) NOT NULL,
	name varchar(30),
	available char(1),
	project_id varchar(19),/*所属项目*/
	venue_id varchar(19),/*所属场馆*/
	original_price double,/*原价格*/
	actual_price double,/*实际价格*/
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);
DROP TABLE reserve_member;
/* 会员 */
CREATE TABLE reserve_member
(
	id varchar(19) NOT NULL,
	name varchar(30),
	mobile varchar(20) NOT NULL ,
	password varchar(16),
	sfz varchar(18) ,
	province varchar(32),
	city varchar(32),
	area varchar(32),
	address varchar(100),
	email varchar(30),
	sex varchar(1),
	remainder DOUBLE COMMENT '储值卡余额',
	residue INT COMMENT '次卡剩余次数',
	cartNo varchar(20) NOT NULL ,
	cart_type varchar(1) DEFAULT '1' COMMENT '会员状态，0-无储值卡，1-储值卡，2-次卡',
	validityStart datetime,
	validityEnd datetime,
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);




