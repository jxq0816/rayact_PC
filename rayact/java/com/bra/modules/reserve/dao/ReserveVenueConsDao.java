package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueCons;

import java.util.List;
import java.util.Map;

/**
 * 场地预定主表DAO接口
 *
 * @author 肖斌
 * @version 2016-01-11
 */
@MyBatisDao
public interface ReserveVenueConsDao extends CrudDao<ReserveVenueCons> {

    List<ReserveVenueCons> findListOrder(ReserveVenueCons venueCons);

    /**
     * 聚合项目查询每个项目的销售金额
     * @param venueCons
     * @return
     */
    List<Map<String,Object>> findPriceGroupProject(ReserveVenueCons venueCons);
}