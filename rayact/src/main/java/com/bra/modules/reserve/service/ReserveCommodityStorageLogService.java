package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCommodityStorageLogDao;
import com.bra.modules.reserve.entity.ReserveCommodityStorageLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品入库记录Service
 * @author jiangxingqi
 * @version 2016-04-13
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommodityStorageLogService extends CrudService<ReserveCommodityStorageLogDao, ReserveCommodityStorageLog> {

	public ReserveCommodityStorageLog get(String id) {
		return super.get(id);
	}
	
	public List<ReserveCommodityStorageLog> findList(ReserveCommodityStorageLog reserveCommodityStorageLog) {
		return super.findList(reserveCommodityStorageLog);
	}
	
	public Page<ReserveCommodityStorageLog> findPage(Page<ReserveCommodityStorageLog> page, ReserveCommodityStorageLog reserveCommodityStorageLog) {
		return super.findPage(page, reserveCommodityStorageLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCommodityStorageLog reserveCommodityStorageLog) {
		super.save(reserveCommodityStorageLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCommodityStorageLog reserveCommodityStorageLog) {
		super.delete(reserveCommodityStorageLog);
	}
	
}