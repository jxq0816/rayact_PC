DROP TABLE IF EXISTS reserve_user;
DROP TABLE IF EXISTS reserve_office;
DROP TABLE IF EXISTS reserve_role;

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

