package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.PostDao;
import com.bra.modules.cms.entity.Message;
import com.bra.modules.cms.entity.Post;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.sys.entity.User;
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
	private PostMainService postMainService;
	@Autowired
	private MessageService messageService;
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
		User notify = null;
		PostMain pm = post.getPostMain();
		//更新回复数
		pm = postMainService.get(pm.getId());
		pm.setPostNum(pm.getOrderNum()+1);
		postMainService.save(pm);
		String postId = post.getPostId();
		String ptpId = post.getPtpId();
		if(StringUtils.isNotBlank(ptpId)){
            Post ptp = get(ptpId);
			notify = ptp.getCreateBy();
		}else if(StringUtils.isNotBlank(postId)){
			Post p = get(postId);
			notify = p.getCreateBy();
		}else if(pm != null){
			notify = pm.getCreateBy();
		}
		if(notify != null){
			Message m = new Message();
			m.setSubject("有人回复你了，快去看看吧！");
			m.setTargetId(notify.getId());
			m.setTargetName(notify.getClass().getName());
			m.setSendId(post.getId());
			m.setSendName(post.getClass().getName());
			m.setUrl(com.bra.modules.sys.utils.StringUtils.ROOTPATH+"/cms/postMain/app/view?id="+pm.getId());//需改
			m.setContent("");
			messageService.save(m);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Post post) {
		super.delete(post);
	}

	public List<Map<String,String>> findMapList(Post post){
		return postDao.findMapList(post);
	}
	
}