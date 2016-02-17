package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueOrder;

import java.util.List;
import java.util.Map;

/**
 * 场地订单DAO接口
 * @author 肖斌
 * @version 2016-01-19
 */
@MyBatisDao
public interface ReserveVenueOrderDao extends CrudDao<ReserveVenueOrder> {

    List<ReserveVenueOrder> findListOrder(ReserveVenueOrder venueOrder);

    /**
     * 聚合项目查询每个项目的销售金额
     * @param venueOrder
     * @return
     */
    List<Map<String,Object>> findPriceGroupProject(ReserveVenueOrder venueOrder);

    List<Map<String,Object>> findPriceGroupProjectReport(ReserveVenueOrder venueOrder);
}
