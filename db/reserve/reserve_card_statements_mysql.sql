/*交易记录表*/
DROP TABLE if EXISTS  reserve_card_statements;
CREATE TABLE reserve_card_statements
(
	id varchar(19) NOT NULL,
  fk_reserve_member_id varchar(19) comment '会员编号外键',
  transaction_type char(1) comment '1:充值；2：退款',
  transaction_volume decimal(16,2) comment '交易额',
  pay_type     VARCHAR(1) COMMENT '支付类型(1:会员卡,2:现金,3:银行卡,4:微信,5:支付宝,6:其它)',
	create_by varchar(64),
	create_date datetime,
	update_by varchar(64),
	update_date datetime,
	remarks varchar(255),
	del_flag char(1) DEFAULT '0' NOT NULL,
	tenant_id varchar(19) comment '路由标识',
	PRIMARY KEY (id)
);

