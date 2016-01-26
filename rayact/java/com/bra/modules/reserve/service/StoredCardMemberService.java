package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveMemberDao;
import com.bra.modules.reserve.entity.ReserveMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 储值卡会员Service
 * @author jiangxingqi
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class StoredCardMemberService extends CrudService<ReserveMemberDao, ReserveMember> {

	public ReserveMember get(String id) {
		return super.get(id);
	}
	
	public List<ReserveMember> findList(ReserveMember reserveMember) {
		return super.findList(reserveMember);
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
	
}