package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveTutorOrder;
import com.bra.modules.reserve.dao.ReserveTutorOrderDao;

/**
 * 教练订单Service
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class ReserveTutorOrderService extends CrudService<ReserveTutorOrderDao, ReserveTutorOrder> {

    public ReserveTutorOrder get(String id) {
        return super.get(id);
    }

    public List<ReserveTutorOrder> findList(ReserveTutorOrder reserveTutorOrder) {
        return super.findList(reserveTutorOrder);
    }

    public List<ReserveTutorOrder> findByModelIdAndKey(String modelId, String modelKey) {
        ReserveTutorOrder tutorOrder = new ReserveTutorOrder(modelId, modelKey);
        return dao.findByModelIdAndKey(tutorOrder);
    }

    public Page<ReserveTutorOrder> findPage(Page<ReserveTutorOrder> page, ReserveTutorOrder reserveTutorOrder) {
        return super.findPage(page, reserveTutorOrder);
    }

    @Transactional(readOnly = false)
    public void save(ReserveTutorOrder reserveTutorOrder) {
        super.save(reserveTutorOrder);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveTutorOrder reserveTutorOrder) {
        super.delete(reserveTutorOrder);
    }

    public void updateReserveType(ReserveTutorOrder tutorOrder) {
        dao.updateReserveType(tutorOrder);
    }

    //查询已经预定和付款的
    public List<ReserveTutorOrder> findNotCancel(String modelId, String modelKey) {
        ReserveTutorOrder tutorOrder = new ReserveTutorOrder(modelId, modelKey);
        return dao.findNotCancel(tutorOrder);
    }

    //根据类型查询教练订单
    public List<ReserveTutorOrder> findByReserveType(String modelId, String modelKey) {
        ReserveTutorOrder tutorOrder = new ReserveTutorOrder(modelId, modelKey);
        return dao.findByReserveType(tutorOrder);
    }

}