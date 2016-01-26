package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;

/**
 * 储值卡设置DAO接口
 * @author 琪
 * @version 2016-01-05
 */
@MyBatisDao
public interface ReserveStoredcardMemberSetDao extends CrudDao<ReserveStoredcardMemberSet> {
	
}