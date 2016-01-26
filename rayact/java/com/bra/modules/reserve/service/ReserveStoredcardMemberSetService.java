package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.dao.ReserveStoredcardMemberSetDao;

/**
 * 储值卡设置Service
 * @author 琪
 * @version 2016-01-05
 */
@Service
@Transactional(readOnly = true)
public class ReserveStoredcardMemberSetService extends CrudService<ReserveStoredcardMemberSetDao, ReserveStoredcardMemberSet> {

	public ReserveStoredcardMemberSet get(String id) {
		return super.get(id);
	}
	
	public List<ReserveStoredcardMemberSet> findList(ReserveStoredcardMemberSet reserveStoredcardMemberSet) {
		return super.findList(reserveStoredcardMemberSet);
	}
	
	public Page<ReserveStoredcardMemberSet> findPage(Page<ReserveStoredcardMemberSet> page, ReserveStoredcardMemberSet reserveStoredcardMemberSet) {
		return super.findPage(page, reserveStoredcardMemberSet);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveStoredcardMemberSet reserveStoredcardMemberSet) {
		Double startPrice =reserveStoredcardMemberSet.getStartPrice();
		Double endPrice =reserveStoredcardMemberSet.getEndPrice();
		Double givePrice=reserveStoredcardMemberSet.getGivePrice();
		if(startPrice==null){
			reserveStoredcardMemberSet.setStartPrice(0.0);
		}
		if(endPrice==null){
			reserveStoredcardMemberSet.setEndPrice(0.0);
		}
		if(givePrice==null){
			reserveStoredcardMemberSet.setGivePrice(0.0);
		}
		super.save(reserveStoredcardMemberSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveStoredcardMemberSet ReserveStoredcardMemberSet) {
		super.delete(ReserveStoredcardMemberSet);
	}
	
}