package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;

/**
 * 11Service
 * @author 11
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueConsItemService extends CrudService<ReserveVenueConsItemDao, ReserveVenueConsItem> {

	public ReserveVenueConsItem get(String id) {
		return super.get(id);
	}

	public List<ReserveVenueConsItem> findVenueConsByMoblie(ReserveVenueConsItem reserveVenueCons){
		return dao.findVenueConsByMoblie(reserveVenueCons);
	}
	public List<ReserveVenueConsItem> findList(ReserveVenueConsItem reserveVenueCons) {
		return super.findList(reserveVenueCons);
	}
	
	public Page<ReserveVenueConsItem> findPage(Page<ReserveVenueConsItem> page, ReserveVenueConsItem reserveVenueCons) {
		return super.findPage(page, reserveVenueCons);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveVenueConsItem reserveVenueCons) {
		super.save(reserveVenueCons);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveVenueConsItem reserveVenueCons) {
		super.delete(reserveVenueCons);
	}

	List<ReserveVenueConsItem> findListByDate(ReserveVenueConsItem reserveVenueCons){
		return dao.findListByDate(reserveVenueCons);
	}
}