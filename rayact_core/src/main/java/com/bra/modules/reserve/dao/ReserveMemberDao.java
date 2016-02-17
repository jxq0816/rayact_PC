/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveMember;

import java.util.List;

/**
 * 会员管理DAO接口
 * @author 肖斌
 * @version 2015-12-29
 */
@MyBatisDao
public interface ReserveMemberDao extends CrudDao<ReserveMember> {
	List<ReserveMember> findExactList(ReserveMember reserveMember);
	Integer memberRegisterOfMonth(ReserveMember reserveMember);
	Integer memberRegisterOfAll(ReserveMember reserveMember);
}