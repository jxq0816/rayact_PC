package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveUser;

/**
 * 商家用户DAO接口
 * @author jiangxingqi
 * @version 2016-07-12
 */
@MyBatisDao
public interface ReserveUserDao extends CrudDao<ReserveUser> {
    ReserveUser getExactUser(ReserveUser reserveUser);
}