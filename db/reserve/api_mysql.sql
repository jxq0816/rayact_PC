DROP TABLE IF EXISTS reserve_sms;
CREATE TABLE reserve_sms
(
  id          VARCHAR(19) NOT NULL,
  mobile      VARCHAR(20),
  mobile_code VARCHAR(6) COMMENT '手机验证码',
  bak         VARCHAR(10) COMMENT '手机接口返回值',
  service_id  VARCHAR(10) COMMENT '业务编号',
  create_date DATETIME,
  update_date DATETIME,
  remarks     VARCHAR(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS reserve_member_extend;
CREATE TABLE reserve_member_extend
(
  id       VARCHAR(19) NOT NULL,
  token    VARCHAR(32),
  nickname VARCHAR(50),
  qq       VARCHAR(20),
  birthday VARCHAR(20),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS reserve_member_extend;
CREATE TABLE reserve_member_extend
(
  id       VARCHAR(19) NOT NULL,
  token    VARCHAR(32),
  nickname VARCHAR(50),
  qq       VARCHAR(20),
  birthday VARCHAR(20),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS mechanism_collection;
CREATE TABLE mechanism_collection
(
  id VARCHAR(19) NOT NULL,
  member_id VARCHAR(19) NOT NULL,
  create_date DATE,
  model_id VARCHAR(19) NOT NULL,
  model_key VARCHAR(19) NOT NULL,
  PRIMARY KEY (id)
);

