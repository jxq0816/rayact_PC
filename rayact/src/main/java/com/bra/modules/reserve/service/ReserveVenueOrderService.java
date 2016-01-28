package com.bra.modules.reserve.service;

import java.util.List;
import java.util.Map;

import com.bra.modules.reserve.event.visitors.VenueOrderEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveVenueOrder;
import com.bra.modules.reserve.dao.ReserveVenueOrderDao;

/**
 * 场地人次票订单Service
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueOrderService extends CrudService<ReserveVenueOrderDao, ReserveVenueOrder> {

    public ReserveVenueOrder get(String id) {
        return super.get(id);
    }

    public List<ReserveVenueOrder> findList(ReserveVenueOrder reserveVenueOrder) {
        return super.findList(reserveVenueOrder);
    }

    public List<Map<String,Object>> findPriceGroupProjectReport(ReserveVenueOrder venueOrder){
        return dao.findPriceGroupProjectReport(venueOrder);
    }

    public List<ReserveVenueOrder> findListOrder(ReserveVenueOrder venueOrder){
        return dao.findListOrder(venueOrder);
    }

    public List<Map<String,Object>> findPriceGroupProject(ReserveVenueOrder venueOrder){
        return dao.findPriceGroupProject(venueOrder);
    }

    public Page<ReserveVenueOrder> findPage(Page<ReserveVenueOrder> page, ReserveVenueOrder reserveVenueOrder) {
        return super.findPage(page, reserveVenueOrder);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenueOrder reserveVenueOrder) {
        super.save(reserveVenueOrder);
        applicationContext.publishEvent(new VenueOrderEvent(reserveVenueOrder));
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueOrder reserveVenueOrder) {
        super.delete(reserveVenueOrder);
    }

}