package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.PostMainCheckDao;
import com.bra.modules.cms.entity.PostMainCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帖子核查Service
 * @author ddt
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class PostMainCheckService extends CrudService<PostMainCheckDao, PostMainCheck> {

	public PostMainCheck get(String id) {
		return super.get(id);
	}
	
	public List<PostMainCheck> findList(PostMainCheck postMainCheck) {
		return super.findList(postMainCheck);
	}
	
	public Page<PostMainCheck> findPage(Page<PostMainCheck> page, PostMainCheck postMainCheck) {
		return super.findPage(page, postMainCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(PostMainCheck postMainCheck) {
		super.save(postMainCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostMainCheck postMainCheck) {
		super.delete(postMainCheck);
	}
	
}