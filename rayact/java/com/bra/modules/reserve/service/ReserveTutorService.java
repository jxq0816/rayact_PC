package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveTutor;
import com.bra.modules.reserve.dao.ReserveTutorDao;

/**
 * 教练Service
 * @author jiangxingqi
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class ReserveTutorService extends CrudService<ReserveTutorDao, ReserveTutor> {

	public ReserveTutor get(String id) {
		return super.get(id);
	}
	
	public List<ReserveTutor> findList(ReserveTutor reserveTutor) {
		return super.findList(reserveTutor);
	}
	
	public Page<ReserveTutor> findPage(Page<ReserveTutor> page, ReserveTutor reserveTutor) {
		return super.findPage(page, reserveTutor);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveTutor reserveTutor) {
		super.save(reserveTutor);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveTutor reserveTutor) {
		super.delete(reserveTutor);
	}
	
}