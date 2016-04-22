package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveCheckDetail;
import com.bra.modules.reserve.dao.ReserveCheckDetailDao;

/**
 * 账目审核Service
 * @author xudl
 * @version 2016-04-22
 */
@Service
@Transactional(readOnly = true)
public class ReserveCheckDetailService extends CrudService<ReserveCheckDetailDao, ReserveCheckDetail> {

	public ReserveCheckDetail get(String id) {
		return super.get(id);
	}
	
	public List<ReserveCheckDetail> findList(ReserveCheckDetail reserveCheckDetail) {
		return super.findList(reserveCheckDetail);
	}
	
	public Page<ReserveCheckDetail> findPage(Page<ReserveCheckDetail> page, ReserveCheckDetail reserveCheckDetail) {
		return super.findPage(page, reserveCheckDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCheckDetail reserveCheckDetail) {
		super.save(reserveCheckDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCheckDetail reserveCheckDetail) {
		super.delete(reserveCheckDetail);
	}
	
}