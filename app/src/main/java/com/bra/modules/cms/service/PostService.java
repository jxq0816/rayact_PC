package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.PostDao;
import com.bra.modules.cms.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 回帖Service
 * @author ddt
 * @version 2016-05-25
 */
@Service
@Transactional(readOnly = true)
public class PostService extends CrudService<PostDao, Post> {
    @Autowired
	private PostDao postDao;
	public Post get(String id) {
		return super.get(id);
	}
	
	public List<Post> findList(Post post) {
		return super.findList(post);
	}
	
	public Page<Post> findPage(Page<Post> page, Post post) {
		return super.findPage(page, post);
	}
	
	@Transactional(readOnly = false)
	public void save(Post post) {
		super.save(post);
	}
	
	@Transactional(readOnly = false)
	public void delete(Post post) {
		super.delete(post);
	}

	public List<Map<String,String>> findMapList(Post post){
		return postDao.findMapList(post);
	}
	
}