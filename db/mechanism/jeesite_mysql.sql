DROP TABLE IF EXISTS `mechanism_att_main`;
DROP TABLE IF EXISTS `mechanism_control_no`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mechanism_att_main` (
  `id` varchar(19) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `fd_content_type` varchar(255) DEFAULT NULL,
  `fd_data` longblob,
  `fd_file_name` varchar(255) DEFAULT NULL,
  `fd_file_path` varchar(255) DEFAULT NULL,
  `fd_file_type` varchar(255) DEFAULT NULL,
  `fd_key` varchar(255) DEFAULT NULL,
  `fd_model_id` varchar(255) DEFAULT NULL,
  `fd_model_name` varchar(255) DEFAULT NULL,
  `fd_order` varchar(255) DEFAULT NULL,
  `fd_size` double DEFAULT NULL,
  `fd_store_type` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `create_by_id` varchar(255) DEFAULT NULL,
  `update_by_id` varchar(255) DEFAULT NULL,
  `crc32` bigint(20) DEFAULT NULL,
  `re_size_path` varchar(255) DEFAULT NULL,
  `video_view` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE mechanism_control_no(
  id varchar(19) NOT NULL,
  model_name VARCHAR(50) NOT NULL,
  control_head VARCHAR(5) NOT NULL,
  control_no VARCHAR(6) DEFAULT '000001',
  control_date VARCHAR(8),
  PRIMARY KEY (`id`)
);