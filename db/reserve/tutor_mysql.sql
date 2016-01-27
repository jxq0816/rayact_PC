/*教练表*/
DROP TABLE if EXISTS  reserve_tutor;
CREATE TABLE reserve_tutor
(
	id varchar(19) NOT NULL,
	fk_project_id varchar(19),
	name varchar(30),
	price double,
	level varchar (30) ,
	start_using char (1) DEFAULT '0' NOT NULL,
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id),
	foreign key (fk_project_id) REFERENCES reserve_project(id) on DELETE CASCADE on UPDATE CASCADE
);