/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.CommentDao;
import com.bra.modules.cms.entity.Article;
import com.bra.modules.cms.entity.Comment;
import com.bra.modules.cms.entity.Message;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
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
	@Autowired
	private MessageService messageService;
	@Autowired
	private ArticleService articleService;

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
		comment.setPage(page);
		return commentDao.findListMap(comment);
	}
	public void save(Comment comment) {
		super.save(comment);
		if(StringUtils.isNotBlank(comment.getRemarks())){
			String articleId = comment.getContentId();
			Article a = articleService.get(articleId);
			Message m = new Message();
			m.setSubject("有人回复你了，快去看看吧！");
			m.setContent(UserUtils.getUser().getName() +" 在资讯‘"+a.getTitle()+"' 里回复了你。");
			m.setTargetId(comment.getRemarks());
			m.setTargetName(User.class.getName());
			m.setSendId(a.getId());
			m.setSendName(a.getClass().getName());
			messageService.save(m);
		}
	}
}
