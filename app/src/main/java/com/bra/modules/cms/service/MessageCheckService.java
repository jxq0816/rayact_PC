package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.MessageCheckDao;
import com.bra.modules.cms.entity.MessageCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息查收Service
 * @author ddt
 * @version 2016-06-07
 */
@Service
@Transactional(readOnly = true)
public class MessageCheckService extends CrudService<MessageCheckDao, MessageCheck> {

	public MessageCheck get(String id) {
		return super.get(id);
	}
	
	public List<MessageCheck> findList(MessageCheck messageCheck) {
		return super.findList(messageCheck);
	}
	
	public Page<MessageCheck> findPage(Page<MessageCheck> page, MessageCheck messageCheck) {
		return super.findPage(page, messageCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageCheck messageCheck) {
		super.save(messageCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageCheck messageCheck) {
		super.delete(messageCheck);
	}
	
}