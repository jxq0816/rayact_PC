package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.PostDao;
import com.bra.modules.cms.entity.Message;
import com.bra.modules.cms.entity.Post;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
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
		pm.setPostNum(pm.getPostNum()+1);
		pm.setOrderNum(pm.getOrderNum()+1);
		postMainService.save(pm);
		String postId = post.getPostId();
		String ptpId = post.getPtpId();
		if(StringUtils.isNotBlank(ptpId)){
            Post ptp = get(ptpId);
			notify = ptp.getCreateBy();
		}else if(StringUtils.isNotBlank(postId)){
			Post p = get(postId);
			notify = p.getCreateBy();
		}else if(StringUtils.isNotBlank(post.getRemarks())){
			notify = new User();
			notify.setId(post.getRemarks());
		}else if(pm != null){
			notify = pm.getCreateBy();
		}
		if(notify != null){
			Message m = new Message();
			m.setSubject("有人回复你了，快去看看吧！");
			m.setContent(UserUtils.getUser().getName() +" 在帖子‘"+pm.getSubject()+"' 里回复了你。");
			m.setTargetId(notify.getId());
			m.setTargetName(notify.getClass().getName());
			m.setSendId(pm.getId());
			m.setSendName(pm.getClass().getName());
			m.setUrl(com.bra.modules.sys.utils.StringUtils.ROOTPATH+"/cms/postMain/app/view?id="+pm.getId());
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