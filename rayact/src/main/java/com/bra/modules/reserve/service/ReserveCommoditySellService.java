package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.dao.ReserveCommoditySellDao;

/**
 * 商品销售主表Service
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommoditySellService extends CrudService<ReserveCommoditySellDao, ReserveCommoditySell> {

	public ReserveCommoditySell get(String id) {
		return super.get(id);
	}
	
	public List<ReserveCommoditySell> findList(ReserveCommoditySell reserveCommoditySell) {
		return super.findList(reserveCommoditySell);
	}
	
	public Page<ReserveCommoditySell> findPage(Page<ReserveCommoditySell> page, ReserveCommoditySell reserveCommoditySell) {
		return super.findPage(page, reserveCommoditySell);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCommoditySell reserveCommoditySell) {
		super.save(reserveCommoditySell);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCommoditySell reserveCommoditySell) {
		super.delete(reserveCommoditySell);
	}
	
}