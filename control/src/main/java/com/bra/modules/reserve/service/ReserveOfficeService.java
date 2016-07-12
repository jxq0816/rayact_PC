package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveOfficeDao;
import com.bra.modules.reserve.entity.ReserveOffice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 集团Service
 * @author jiangxingqi
 * @version 2016-07-11
 */
@Service
@Transactional(readOnly = true)
public class ReserveOfficeService extends CrudService<ReserveOfficeDao, ReserveOffice> {

	public ReserveOffice get(String id) {
		return super.get(id);
	}
	
	public List<ReserveOffice> findList(ReserveOffice reserveOffice) {
		return super.findList(reserveOffice);
	}
	
	public Page<ReserveOffice> findPage(Page<ReserveOffice> page, ReserveOffice reserveOffice) {
		return super.findPage(page, reserveOffice);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveOffice reserveOffice) {
		super.save(reserveOffice);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveOffice reserveOffice) {
		super.delete(reserveOffice);
	}
	
}