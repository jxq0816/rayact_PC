-- ----------------------------
-- Table structure for reserve_member
-- ----------------------------
DROP TABLE IF EXISTS `reserve_member`;
CREATE TABLE `reserve_member` (
  `id` varchar(19) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `sfz` varchar(18) DEFAULT NULL,
  `province` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `area` varchar(32) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `remainder` DECIMAL(18,2) DEFAULT NULL,
  `residue` int(11) DEFAULT NULL,
  `cartNo` varchar(20) NOT NULL,
  `cart_type` varchar(1) DEFAULT '1' COMMENT '会员状态，0-无储值卡，1-储值卡，2-次卡',
  `validityStart` datetime DEFAULT NULL,
  `validityEnd` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `password` varchar(16) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `fk_reserve_storedcard_member_set_id` varchar(19) DEFAULT NULL,
  `tenant_id` varchar(19) DEFAULT NULL,
  `fk_reserve_timecard_member_set_id` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;