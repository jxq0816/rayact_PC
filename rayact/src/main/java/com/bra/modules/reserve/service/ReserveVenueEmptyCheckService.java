package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveVenueEmptyCheckDao;
import com.bra.modules.reserve.entity.ReserveVenueEmptyCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 空场审核Service
 * @author xudl
 * @version 2016-04-21
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueEmptyCheckService extends CrudService<ReserveVenueEmptyCheckDao, ReserveVenueEmptyCheck> {

	public ReserveVenueEmptyCheck get(String id) {
		return super.get(id);
	}
	
	public List<ReserveVenueEmptyCheck> findList(ReserveVenueEmptyCheck reserveVenueEmptyCheck) {
		return super.findList(reserveVenueEmptyCheck);
	}
	
	public Page<ReserveVenueEmptyCheck> findPage(Page<ReserveVenueEmptyCheck> page, ReserveVenueEmptyCheck reserveVenueEmptyCheck) {
		return super.findPage(page, reserveVenueEmptyCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveVenueEmptyCheck reserveVenueEmptyCheck) {
		super.save(reserveVenueEmptyCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveVenueEmptyCheck reserveVenueEmptyCheck) {
		super.delete(reserveVenueEmptyCheck);
	}
	
}