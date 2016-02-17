package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveTimecardMemberSet;

/**
 * 次卡设置DAO接口
 * @author jiangxingqi
 * @version 2016-01-06
 */
@MyBatisDao
public interface ReserveTimecardMemberSetDao extends CrudDao<ReserveTimecardMemberSet> {
	
}