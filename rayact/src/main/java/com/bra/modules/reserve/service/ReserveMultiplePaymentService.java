package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveMultiplePaymentDao;
import com.bra.modules.reserve.entity.ReserveMultiplePayment;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectFieldIntervalReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 多方式付款Service
 * @author jiangxingqi
 * @version 2016-04-20
 */
@Service
@Transactional(readOnly = true)
public class ReserveMultiplePaymentService extends CrudService<ReserveMultiplePaymentDao, ReserveMultiplePayment> {

	public ReserveMultiplePayment get(String id) {
		return super.get(id);
	}
	
	public List<ReserveMultiplePayment> findList(ReserveMultiplePayment reserveMultiplePayment) {
		return super.findList(reserveMultiplePayment);
	}

	public ReserveVenueProjectFieldIntervalReport reserveFieldMultiplePaymentReport(ReserveVenueProjectFieldIntervalReport report){
		return dao.reserveFieldMultiplePaymentReport(report);
	}
	
	public Page<ReserveMultiplePayment> findPage(Page<ReserveMultiplePayment> page, ReserveMultiplePayment reserveMultiplePayment) {
		return super.findPage(page, reserveMultiplePayment);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveMultiplePayment reserveMultiplePayment) {
		super.save(reserveMultiplePayment);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveMultiplePayment reserveMultiplePayment) {
		super.delete(reserveMultiplePayment);
	}
	
}