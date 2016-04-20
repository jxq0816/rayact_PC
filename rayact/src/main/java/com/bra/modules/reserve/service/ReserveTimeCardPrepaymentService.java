package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveTimeCardPrepaymentDao;
import com.bra.modules.reserve.entity.ReserveTimeCardPrepayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 次卡预付款Service
 * @author jiangxingqi
 * @version 2016-04-19
 */
@Service
@Transactional(readOnly = true)
public class ReserveTimeCardPrepaymentService extends CrudService<ReserveTimeCardPrepaymentDao, ReserveTimeCardPrepayment> {

	
	public ReserveTimeCardPrepayment get(String id) {
		ReserveTimeCardPrepayment reserveTimeCardPrepayment = super.get(id);
		return reserveTimeCardPrepayment;
	}
	
	public List<ReserveTimeCardPrepayment> findList(ReserveTimeCardPrepayment reserveTimeCardPrepayment) {
		return super.findList(reserveTimeCardPrepayment);
	}
	
	public Page<ReserveTimeCardPrepayment> findPage(Page<ReserveTimeCardPrepayment> page, ReserveTimeCardPrepayment reserveTimeCardPrepayment) {
		return super.findPage(page, reserveTimeCardPrepayment);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveTimeCardPrepayment reserveTimeCardPrepayment) {
		super.save(reserveTimeCardPrepayment);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveTimeCardPrepayment reserveTimeCardPrepayment) {
		super.delete(reserveTimeCardPrepayment);
	}
	
}