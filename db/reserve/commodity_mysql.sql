/*商品类别表*/
DROP TABLE if EXISTS  reserve_commodity_type;
CREATE TABLE reserve_commodity_type
(
	id varchar(19) NOT NULL,
	name varchar(30),
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
  tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);
UPDATE reserve_commodity_type set tenant_id = 'ZUEQ0kypa9ptQ6CXMNN'
/*商品表*/
DROP TABLE  if EXISTS reserve_commodity;
CREATE TABLE reserve_commodity
(
	id varchar(19) NOT NULL,
	commodity_id varchar(19) unique NOT NULL,
	name varchar(30),
	price double,
	repertory_num int DEFAULT 0 ,
  commodity_type_id varchar(19),
  shelves_status char(1) DEFAULT '0' NOT NULL,
  unit VARCHAR(10) comment '单位',
  quick_search varchar(10) comment '快速搜索',
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);
UPDATE reserve_commodity set tenant_id = 'ZUEQ0kypa9ptQ6CXMNN'
/*商品销售表明细*/
DROP TABLE if EXISTS  reserve_commodity_sell_detail;
CREATE TABLE reserve_commodity_sell_detail(
  id varchar(19),
  fk_reserve_commodity_sell_id VARCHAR (19),
  fk_reserve_commodity_id VARCHAR (19),
  fk_reserve_member_id varchar(19),
  num int,
  price double,
  detail_sum double,
  create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
)
UPDATE reserve_commodity_sell_detail set tenant_id = 'ZUEQ0kypa9ptQ6CXMNN'
/*商品销售表*/
DROP TABLE if EXISTS  reserve_commodity_sell;
CREATE TABLE reserve_commodity_sell(
  id varchar(19),
  fk_reserve_member_id varchar(19),
  total_sum Double ,
  create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	tenant_id varchar(19) comment '路由标识',
	del_flag char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (id)
)
UPDATE reserve_commodity_sell set tenant_id = 'ZUEQ0kypa9ptQ6CXMNN'
