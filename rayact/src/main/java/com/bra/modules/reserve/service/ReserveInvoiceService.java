package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveInvoiceDao;
import com.bra.modules.reserve.entity.ReserveInvoice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 发票管理Service
 * @author xudl
 * @version 2016-04-18
 */
@Service
@Transactional(readOnly = true)
public class ReserveInvoiceService extends CrudService<ReserveInvoiceDao, ReserveInvoice> {

	public ReserveInvoice get(String id) {
		return super.get(id);
	}
	
	public List<ReserveInvoice> findList(ReserveInvoice reserveInvoice) {
		return super.findList(reserveInvoice);
	}
	
	public Page<ReserveInvoice> findPage(Page<ReserveInvoice> page, ReserveInvoice reserveInvoice) {
		return super.findPage(page, reserveInvoice);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveInvoice reserveInvoice) {
		super.save(reserveInvoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveInvoice reserveInvoice) {
		super.delete(reserveInvoice);
	}
	
}