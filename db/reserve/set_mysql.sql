/* Create Tables */
/*存储卡设置*/
DROP TABLE if exists reserve_storedcard_member_set;
CREATE TABLE reserve_storedcard_member_set
(
	id varchar(19) NOT NULL,
	fk_reserve_project_id varchar(19) default null,
	name varchar(30),
	start_price Double,
	end_price Double,
  give_price Double,
  deadline DATE ,
  discount_rate double DEFAULT 0,
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);
DROP TABLE if exists reserve_timecard_member_set;
/*次卡设置*/
CREATE TABLE reserve_timecard_member_set
(
	id varchar(19) NOT NULL,
	fk_reserve_project_id varchar(19) default null,
	name varchar(30),
	start_time int,
	end_time int,
	/*起止次数*/
  give_time int,
  deadline DATE,
  minutes_per_time int,
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);