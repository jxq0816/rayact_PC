package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.PostMainDao;
import com.bra.modules.cms.entity.PostMain;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 帖子Service
 * @author ddt
 * @version 2016-05-25
 */
@Service
@Transactional(readOnly = true)
public class PostMainService extends CrudService<PostMainDao, PostMain> {
	@Autowired
	private PostMainDao postMainDao;

	public PostMain get(String id) {
		return super.get(id);
	}
	
	public List<PostMain> findList(PostMain postMain) {
		return super.findList(postMain);
	}
	
	public Page<PostMain> findPage(Page<PostMain> page, PostMain postMain) {
		return super.findPage(page, postMain);
	}
	
	@Transactional(readOnly = false)
	public void save(PostMain postMain) {
		if (postMain.getContent() != null) {
			postMain.setContent(StringEscapeUtils.unescapeHtml4(
					postMain.getContent()));
		}
		super.save(postMain);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostMain postMain) {
		super.delete(postMain);
	}

	public List<Map<String,String>> getPostMainList(PostMain postMain){
		return postMainDao.getPostMainList(postMain);
	}
	
}