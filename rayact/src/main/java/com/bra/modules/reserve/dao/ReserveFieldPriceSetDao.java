package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveFieldPriceSet;

/**
 * 场地价格DAO接口
 * @author 肖斌
 * @version 2016-01-06
 */
@MyBatisDao
public interface ReserveFieldPriceSetDao extends CrudDao<ReserveFieldPriceSet> {
    void physicalDelete(ReserveFieldPriceSet reserveFieldPriceSet);
}