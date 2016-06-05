package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveCommoditySupplier;
import com.bra.modules.reserve.dao.ReserveCommoditySupplierDao;

/**
 * 供应商Service
 * @author jiangxingqi
 * @version 2016-06-05
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommoditySupplierService extends CrudService<ReserveCommoditySupplierDao, ReserveCommoditySupplier> {

	public ReserveCommoditySupplier get(String id) {
		return super.get(id);
	}
	
	public List<ReserveCommoditySupplier> findList(ReserveCommoditySupplier reserveCommoditySupplier) {
		return super.findList(reserveCommoditySupplier);
	}
	
	public Page<ReserveCommoditySupplier> findPage(Page<ReserveCommoditySupplier> page, ReserveCommoditySupplier reserveCommoditySupplier) {
		return super.findPage(page, reserveCommoditySupplier);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCommoditySupplier reserveCommoditySupplier) {
		super.save(reserveCommoditySupplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCommoditySupplier reserveCommoditySupplier) {
		super.delete(reserveCommoditySupplier);
	}
	
}