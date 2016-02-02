package com.bra.modules.reserve.service;

import java.util.List;

import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveVenueVisitorsSet;
import com.bra.modules.reserve.dao.ReserveVenueVisitorsSetDao;

/**
 * 人次票设置Service
 * @author 肖斌
 * @version 2016-01-18
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueVisitorsSetService extends CrudService<ReserveVenueVisitorsSetDao, ReserveVenueVisitorsSet> {

	public ReserveVenueVisitorsSet get(String id) {
		return super.get(id);
	}
	
	public List<ReserveVenueVisitorsSet> findList(ReserveVenueVisitorsSet reserveVenueVisitorsSet) {
		reserveVenueVisitorsSet.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.venue_id"));
		return super.findList(reserveVenueVisitorsSet);
	}
	
	public Page<ReserveVenueVisitorsSet> findPage(Page<ReserveVenueVisitorsSet> page, ReserveVenueVisitorsSet reserveVenueVisitorsSet) {
		reserveVenueVisitorsSet.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.venue_id"));
		return super.findPage(page, reserveVenueVisitorsSet);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveVenueVisitorsSet reserveVenueVisitorsSet) {
		super.save(reserveVenueVisitorsSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveVenueVisitorsSet reserveVenueVisitorsSet) {
		super.delete(reserveVenueVisitorsSet);
	}
	
}