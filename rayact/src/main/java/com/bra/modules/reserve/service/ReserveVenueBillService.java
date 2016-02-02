package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveVenueBill;
import com.bra.modules.reserve.dao.ReserveVenueBillDao;

/**
 * 场馆损益Service
 * @author jiangxingqi
 * @version 2016-02-02
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueBillService extends CrudService<ReserveVenueBillDao, ReserveVenueBill> {

	public ReserveVenueBill get(String id) {
		return super.get(id);
	}
	
	public List<ReserveVenueBill> findList(ReserveVenueBill reserveVenueBill) {
		return super.findList(reserveVenueBill);
	}
	
	public Page<ReserveVenueBill> findPage(Page<ReserveVenueBill> page, ReserveVenueBill reserveVenueBill) {
		return super.findPage(page, reserveVenueBill);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveVenueBill reserveVenueBill) {
		super.save(reserveVenueBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveVenueBill reserveVenueBill) {
		super.delete(reserveVenueBill);
	}
	
}