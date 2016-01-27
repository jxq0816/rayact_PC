DROP TABLE IF EXISTS reserve_venue_cons;
DROP TABLE IF EXISTS reserve_venue_cons_item;
DROP TABLE IF EXISTS reserve_venue_order;
/*人次票设置*/
DROP TABLE IF EXISTS reserve_venue_visitors_set;
/*人次票*/
DROP TABLE IF EXISTS reserve_venue_visitors;
/**教练费用*/
DROP TABLE IF EXISTS reserve_tutor_order;

/*人次票设置*/
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

  order_price  DOUBLE COMMENT '预定单据总金额',
  cons_price   DOUBLE COMMENT '预定金额',
  pay_type     VARCHAR(1) COMMENT '支付类型(1:会员卡,2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',

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
  start_time   VARCHAR(12) COMMENT '预定时间',
  end_time     VARCHAR(12) COMMENT '预定时间',
  cons_time    VARCHAR(12) COMMENT '预定时间',
  cons_info    VARCHAR(50) COMMENT '预定信息',
  frequency    VARCHAR(2) COMMENT '频率(1:单次;2:每天;3:每周)',
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