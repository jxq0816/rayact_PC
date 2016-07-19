package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveTutorOrder;

import java.util.List;
import java.util.Map;

/**
 * 教练订单DAO接口
 * @author 肖斌
 * @version 2016-01-19
 */
@MyBatisDao
public interface ReserveTutorOrderDao extends CrudDao<ReserveTutorOrder> {

    //查询已经预定和付款的
    List<ReserveTutorOrder> findNotCancel(ReserveTutorOrder tutorOrder);

    //根据类型查询教练订单
    List<ReserveTutorOrder> findByReserveType(ReserveTutorOrder tutorOrder);
    List<Map<String,Object>> getTutorDetail(ReserveTutorOrder tutorOrder);
    List<Map<String,Object>> getTutorOrderAll(ReserveTutorOrder tutorOrder);
}