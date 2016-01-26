package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveTutorOrder;

import java.util.List;

/**
 * 教练订单DAO接口
 * @author 肖斌
 * @version 2016-01-19
 */
@MyBatisDao
public interface ReserveTutorOrderDao extends CrudDao<ReserveTutorOrder> {


    List<ReserveTutorOrder> findByModelIdAndKey(ReserveTutorOrder tutorOrder);

    void deleteByModelIdAndKey(ReserveTutorOrder tutorOrder);

    void updateReserveType(ReserveTutorOrder tutorOrder);

    //查询已经预定和付款的
    List<ReserveTutorOrder> findNotCancel(ReserveTutorOrder tutorOrder);

    //根据类型查询教练订单
    List<ReserveTutorOrder> findByReserveType(ReserveTutorOrder tutorOrder);
}