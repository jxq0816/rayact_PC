package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveTimeIntervalDao;
import com.bra.modules.reserve.entity.ReserveTimeInterval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教练Service
 * @author jiangxingqi
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class ReserveTimeIntervalService extends CrudService<ReserveTimeIntervalDao, ReserveTimeInterval> {

	public ReserveTimeInterval get(String id) {
		return super.get(id);
	}
	
	public List<ReserveTimeInterval> findList(ReserveTimeInterval reserveTimeInterval) {
		return super.findList(reserveTimeInterval);
	}
	
	public Page<ReserveTimeInterval> findPage(Page<ReserveTimeInterval> page, ReserveTimeInterval reserveTimeInterval) {
		return super.findPage(page, reserveTimeInterval);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveTimeInterval reserveTimeInterval) {
		super.save(reserveTimeInterval);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveTimeInterval reserveTimeInterval) {
		super.delete(reserveTimeInterval);
	}
	
}