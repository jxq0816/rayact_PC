DROP TABLE IF EXISTS reserve_sms;
CREATE TABLE reserve_sms
(
	id varchar(19) NOT NULL,
	mobile varchar(20),
	mobile_code VARCHAR(6) COMMENT '手机验证码',
	bak VARCHAR(10) COMMENT '手机接口返回值',
	service_id VARCHAR(10) COMMENT '业务编号',
	create_date datetime,
	update_date datetime,
	remarks varchar(255),
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
	`id` int(11) NOT NULL auto_increment,
	`name` varchar(255) collate utf8_bin NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

