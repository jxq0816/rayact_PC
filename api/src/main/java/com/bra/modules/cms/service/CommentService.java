/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.CommentDao;

import com.bra.modules.cms.eneity.Category;
import com.bra.modules.cms.eneity.Comment;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论Service
 *
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

	public void delete(Comment entity) {
		dao.delete(entity);
	}

	public void update(Comment comment){
		dao.update(comment);
	}

    public Page<Comment> listComment(String conentId,String pNo) {
        Integer pageNo = StringUtils.isBlank(pNo) ? 1 : NumberUtils.toInt(pNo, 1);
        Page<Comment> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(10);
        Comment comment = new Comment();
        comment.setContentId(conentId);
        return super.findPage(page, comment);
    }
}
