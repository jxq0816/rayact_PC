package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveTimecardMemberSet;
import com.bra.modules.reserve.dao.ReserveTimecardMemberSetDao;

/**
 * 次卡设置Service
 * @author jiangxingqi
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class ReserveTimecardMemberSetService extends CrudService<ReserveTimecardMemberSetDao, ReserveTimecardMemberSet> {

	public ReserveTimecardMemberSet get(String id) {
		return super.get(id);
	}
	
	public List<ReserveTimecardMemberSet> findList(ReserveTimecardMemberSet reserveTimecardMemberSet) {
		return super.findList(reserveTimecardMemberSet);
	}
	
	public Page<ReserveTimecardMemberSet> findPage(Page<ReserveTimecardMemberSet> page, ReserveTimecardMemberSet reserveTimecardMemberSet) {
		return super.findPage(page, reserveTimecardMemberSet);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveTimecardMemberSet reserveTimecardMemberSet) {
		if(reserveTimecardMemberSet.getStartTime()==null){
			reserveTimecardMemberSet.setStartTime(0);
		}
		if(reserveTimecardMemberSet.getEndTime()==null){
			reserveTimecardMemberSet.setEndTime(0);
		}
		if(reserveTimecardMemberSet.getGiveTime()==null){
			reserveTimecardMemberSet.setGiveTime(0);
		}
		super.save(reserveTimecardMemberSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveTimecardMemberSet reserveTimecardMemberSet) {
		super.delete(reserveTimecardMemberSet);
	}
	
}