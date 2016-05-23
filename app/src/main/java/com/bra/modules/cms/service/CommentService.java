/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.CommentDao;
import com.bra.modules.cms.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 评论Service
 *
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = false)
public class CommentService extends CrudService<CommentDao, Comment> {
	@Autowired
	private CommentDao commentDao;

	public Comment get(String id) {
		return super.get(id);
	}

	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
		comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));
		
		return super.findPage(page, comment);
	}
	
	public void delete(Comment entity, Boolean isRe) {
		super.delete(entity);
	}

	public List<Map<String,String>> findListMap(Page page,Comment comment){
		if(page.getPageSize()==0)
			page.setPageSize(10);
		page.setPageNo((page.getPageNo()-1)*page.getPageSize());
		comment.setPage(page);
		return commentDao.findListMap(comment);
	}
}
