/* Create Tables */
/*项目设置*/
CREATE TABLE reserve_project
(
	id varchar(19) NOT NULL,
	name varchar(30),
	ticket_type VARCHAR(2) COMMENT '1:场次售卖；2:人次售卖；3:全部',
	interface_id varchar(1) COMMENT '1：篮球 2：足球 3：网球 4：羽毛球 5：乒乓球',
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
	email varchar(30) DEFAULT NULL,
	sfz varchar(18) ,
	province varchar(32),
	city varchar(32),
	area varchar(32),
	address varchar(100),
	sex varchar(1) COMMENT '1:男 2：女',
	remainder DOUBLE COMMENT '储值卡余额',
	residue INT COMMENT '次卡剩余次数',
	cartNo varchar(20),
	cart_type varchar(1) DEFAULT '1' COMMENT '会员状态，0-无储值卡，1-储值卡，2-次卡',
	fk_reserve_venue_id VARCHAR (19) ,
	fk_reserve_storedcard_member_set_id varchar(19) DEFAULT NULL,
  fk_reserve_timecard_member_set_id varchar(19) DEFAULT NULL,
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
  fk_reserve_venue_id VARCHAR (19) comment '场馆外键',
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
/*商品销售表*/
DROP TABLE if EXISTS  reserve_commodity_sell;
CREATE TABLE reserve_commodity_sell(
  id varchar(19),
  fk_reserve_member_id varchar(19),
  pay_type     VARCHAR(1) COMMENT '支付类型(1:储值卡 2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',
  gift_flag char(1) comment '赠品标识',
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
*人次票设置*/
CREATE TABLE reserve_venue_visitors_set (
  id          VARCHAR(19)         NOT NULL,
  name        VARCHAR(30)         NOT NULL,
  project_id  VARCHAR(19)         NOT NULL
  COMMENT '所属项目',
  venue_id    VARCHAR(19) COMMENT '场馆ID',
  available   CHAR(1), /*是否启用*/
  price       DOUBLE, /*价格*/
  create_by   VARCHAR(64),
  create_date DATETIME,
  update_by   VARCHAR(64),
  update_date DATETIME,
  remarks     VARCHAR(255),
  del_flag    CHAR(1) DEFAULT '0' NOT NULL,
  tenant_id   VARCHAR(19) COMMENT '路由标识',
  PRIMARY KEY (id)

);

drop  table if EXISTS reserve_venue_cons;
/*场地预定主信息表*/
CREATE TABLE reserve_venue_cons (
  id           VARCHAR(19)         NOT NULL,
  member_id    VARCHAR(19) COMMENT '会员ID',
  reserve_type VARCHAR(1) COMMENT '交易类型(0:可预定,1:已预定,2:锁场)',
  venue_id     VARCHAR(19) COMMENT '场馆ID',
  cons_mobile  DOUBLE COMMENT '预定人手机号',
  user_name    VARCHAR(30) COMMENT '预定人姓名',
  cons_type    VARCHAR(1) COMMENT '预订的类型1：散客 2：会员 3：团体',
  cons_date    DATE COMMENT '预定日期(yyyy-MM-dd)',
  project_id   VARCHAR(19) COMMENT '所属项目',

  order_price  DOUBLE COMMENT '实际收费金额',
  should_price   DOUBLE COMMENT '应收费金额',
  discount_price DOUBLE COMMENT '打折金额(针对会员)',
  cons_price   DOUBLE COMMENT '预定金额',
  pay_type     VARCHAR(1) COMMENT '支付类型(1:会员卡,2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',

  checkout_id VARCHAR(19) COMMENT '核对人',

  create_by    VARCHAR(64),
  create_date  DATETIME,
  update_by    VARCHAR(64),
  update_date  DATETIME,
  remarks      VARCHAR(255),
  del_flag     CHAR(1) DEFAULT '0' NOT NULL,
  tenant_id    VARCHAR(19) COMMENT '路由标识',
  PRIMARY KEY (id)
);

/* Create Tables */
/*场地预订*/
CREATE TABLE reserve_venue_cons_item
(
  id           VARCHAR(19)         NOT NULL,
  cons_data_id VARCHAR(19)         NOT NULL
  COMMENT '场地预定主表Id',
  venue_id     VARCHAR(19) COMMENT '场馆ID',
  field_id     VARCHAR(19) COMMENT '场地ID',
  cons_price   DOUBLE COMMENT '预定价格(系统价格)',
  order_price  DOUBLE COMMENT '实际缴费金额',
  start_time   VARCHAR(12) COMMENT '预定开始时刻',
  end_time     VARCHAR(12) COMMENT '预定结束时刻',
  cons_time    VARCHAR(12) COMMENT '预定时间',
  cons_info    VARCHAR(50) COMMENT '预定信息',
  frequency    VARCHAR(2) COMMENT '频率(1:单次;2:每天;3:每周)',
  start_date   DATE COMMENT '预定开始日期',
  end_date   DATE COMMENT '预定结束日期',
  cons_week    VARCHAR(5) COMMENT '周几',
  half_court   VARCHAR(2) COMMENT '是否半场(1:是)',

  create_by    VARCHAR(64),
  create_date  DATETIME,
  update_by    VARCHAR(64),
  update_date  DATETIME,
  remarks      VARCHAR(255),
  del_flag     CHAR(1) DEFAULT '0' NOT NULL,
  tenant_id    VARCHAR(19) COMMENT '路由标识',
  PRIMARY KEY (id)
);

/*人次票售卖*/
CREATE TABLE reserve_venue_order (
  id              VARCHAR(19)         NOT NULL,
  venue_id        VARCHAR(19) COMMENT '场馆ID',
  visitors_set_id VARCHAR(19) COMMENT '所属人次票',
  member_id       VARCHAR(19) COMMENT '会员ID',
  cons_mobile     VARCHAR(19) COMMENT '订单人手机号',
  user_name       VARCHAR(30) COMMENT '订单人姓名',
  order_type      VARCHAR(1) COMMENT '订单类型1：散客 2：会员 3：团体',
  order_date      DATE COMMENT '订单日期(yyyy-MM-dd)',
  order_price     DOUBLE COMMENT '人次订单金额',
  collect_price   DOUBLE COMMENT '实收金额',
  collect_count   INT COMMENT '商品数量',
  pay_type        VARCHAR(1) COMMENT '支付类型(1:会员卡,2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',

  create_by       VARCHAR(64),
  create_date     DATETIME,
  update_by       VARCHAR(64),
  update_date     DATETIME,
  remarks         VARCHAR(255),
  del_flag        CHAR(1) DEFAULT '0' NOT NULL,
  PRIMARY KEY (id)
);

/**教练费用*/
CREATE TABLE reserve_tutor_order (
  id          VARCHAR(19)         NOT NULL,
  tutor_id    VARCHAR(19)         NOT NULL
  COMMENT '所属教练',
  order_price DOUBLE COMMENT '教练费用',
  order_count INT COMMENT '次数',
  total_price DOUBLE COMMENT '总费用',
  model_id    VARCHAR(19) COMMENT '业务编号(如:场地的教练费用的ID,人次售卖的教练费用的ID)',
  model_key   VARCHAR(19) COMMENT '业务标识(如用field标识场地的教练费用,用visitor标识人次售卖的教练费用)',
  create_by   VARCHAR(64),
  create_date DATETIME,
  update_by   VARCHAR(64),
  update_date DATETIME,
  remarks     VARCHAR(255),
  del_flag    CHAR(1) DEFAULT '0' NOT NULL,
  tenant_id   VARCHAR(19) COMMENT '路由标识',
  PRIMARY KEY (id)
);

/**场地赠品*/
CREATE TABLE reserve_venue_gift(
  id          VARCHAR(19)         NOT NULL,
  gift_id     VARCHAR(19)         NOT NULL COMMENT '商品ID',
  model_id    VARCHAR(19)         NOT NULL,
  model_key   VARCHAR(30)         NOT NULL,
  num         INT  COMMENT '数量',
  create_by   VARCHAR(64),
  create_date DATETIME,
  update_by   VARCHAR(64),
  update_date DATETIME,
  remarks     VARCHAR(255),
  del_flag    CHAR(1) DEFAULT '0' NOT NULL,
  tenant_id   VARCHAR(19) COMMENT '路由标识',
  PRIMARY KEY (id)
)
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

CREATE TABLE reserve_user
(
	id varchar(64) NOT NULL COMMENT '编号',
	company_id varchar(64) COMMENT '归属公司',
	login_name varchar(100) NOT NULL COMMENT '登录名',
	password varchar(100) NOT NULL COMMENT '密码',
	no varchar(100) COMMENT '工号',
	name varchar(100) NOT NULL COMMENT '姓名',
	email varchar(200) COMMENT '邮箱',
	phone varchar(200) COMMENT '电话',
	mobile varchar(200) COMMENT '手机',
	user_type char(1) COMMENT '用户类型',
	photo varchar(1000) COMMENT '用户头像',
	login_ip varchar(100) COMMENT '最后登陆IP',
	login_date datetime COMMENT '最后登陆时间',
	login_flag varchar(64) COMMENT '是否可登录',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '用户表';

CREATE TABLE reserve_office
(
	id varchar(64) NOT NULL COMMENT '编号',
	name varchar(100) NOT NULL COMMENT '名称',
	sort decimal(10,0) NOT NULL COMMENT '排序',
	area_id varchar(64) COMMENT '归属区域',
	address varchar(255) COMMENT '联系地址',
	phone varchar(200) COMMENT '电话',
	USEABLE varchar(64) COMMENT '是否启用',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '机构表';

/*用户角色*/
CREATE TABLE reserve_role (
	id         VARCHAR(19) NOT NULL,
	user_id    VARCHAR(19) NOT NULL
	COMMENT '对应用户ID',
	authority  VARCHAR(3600) COMMENT '对应用户权限(json字符串)',
	user_type  VARCHAR(2) COMMENT '用户类型(1:管理员;2:场馆管理员;3:财务;4:收银)',
	venue_json VARCHAR(500) COMMENT '对应场馆的id(json字符串)',
	PRIMARY KEY (id)
);

/*交易记录表*/
DROP TABLE if EXISTS  reserve_card_statements;
CREATE TABLE reserve_card_statements
(
	id varchar(19) NOT NULL,
  fk_reserve_member_id varchar(19) comment '会员编号外键',
  transaction_type char(1) comment '1:充值；2：退款；3:超级管理员修改余额',
  transaction_volume decimal(16,2) comment '交易额',
  pay_type     VARCHAR(1) COMMENT '支付类型(2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for reserve_member
-- ----------------------------
DROP TABLE IF EXISTS `reserve_member`;
CREATE TABLE `reserve_member` (
  `id` varchar(19) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `password` varchar(16) DEFAULT NULL,
  `sfz` varchar(18) DEFAULT NULL,
  `province` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `area` varchar(32) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL COMMENT '1:男 2：女',
  `remainder` double DEFAULT NULL COMMENT '储值卡余额',
  `residue` int(11) DEFAULT NULL COMMENT '次卡剩余次数',
  `cartNo` varchar(20) DEFAULT NULL,
  `cart_type` varchar(1) DEFAULT '1' COMMENT '会员状态，0-无储值卡，1-储值卡，2-次卡',
  `fk_reserve_venue_id` varchar(19) DEFAULT NULL,
  `fk_reserve_storedcard_member_set_id` varchar(19) DEFAULT NULL,
  `fk_reserve_timecard_member_set_id` varchar(19) DEFAULT NULL,
  `validityStart` datetime DEFAULT NULL,
  `validityEnd` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `tenant_id` varchar(19) DEFAULT NULL COMMENT '路由标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
  start_date datetime comment '开始时间',
  end_date datetime comment '结束时间',
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



