package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommodity;

import java.util.List;

/**
 * 商品DAO接口
 * @author jiangxingqi
 * @version 2016-01-07
 */
@MyBatisDao
public interface ReserveCommodityDao extends CrudDao<ReserveCommodity> {
     List<ReserveCommodity> checkCommodityId(ReserveCommodity commodity);
}