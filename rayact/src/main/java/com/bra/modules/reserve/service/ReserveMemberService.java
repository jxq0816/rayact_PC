package com.bra.modules.reserve.service;

import java.util.List;
import java.util.Map;

import com.bra.modules.reserve.entity.ReserveCardStatements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.dao.ReserveMemberDao;

/**
 * 会员管理Service
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveMemberService extends CrudService<ReserveMemberDao, ReserveMember> {

	public ReserveMember get(String id) {
		return super.get(id);
	}
	
	public List<ReserveMember> findList(ReserveMember reserveMember) {
		return super.findList(reserveMember);
	}
	public List<ReserveMember> findExactList(ReserveMember reserveMember) {
		return dao.findExactList(reserveMember);
	}

	public Page<ReserveMember> findPage(Page<ReserveMember> page, ReserveMember reserveMember) {
		return super.findPage(page, reserveMember);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveMember reserveMember) {
		super.save(reserveMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveMember reserveMember) {
		super.delete(reserveMember);
	}

	public Integer memberRegisterOfMonth(ReserveMember reserveMember) {
		return dao.memberRegisterOfMonth(reserveMember);
	}
	public Integer memberRegisterOfAll(ReserveMember reserveMember){
		return dao.memberRegisterOfAll(reserveMember);
	}
}