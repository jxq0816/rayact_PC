package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.PostMainDao;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.StringUtils;
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
	public void save(PostMain postMain,AttMainForm attMainForm) {
		String remarks = "";
		if(attMainForm!=null){
			List<AttMain> list = attMainForm.getAttMains1();
			if(list!=null&&list.size()>0){
				for(AttMain att:list){
					if(att.getId()!=null&&!StringUtils.isNull(att.getId()))
						remarks += StringUtils.ATTPATH + att.getId()+";";
				}
			}
		}
		if(!StringUtils.isNull(remarks)){
			postMain.setRemarks(remarks);
		}
		postMain.setSubject(StringEscapeUtils.unescapeHtml4(
				postMain.getSubject()));
		if (postMain.getContent() != null) {
			String c = postMain.getContent();
			c = c.replaceAll("\n","<br>");
			c = c.replaceAll(" ","&nbsp;");
			postMain.setContent(c);
		}
		super.save(postMain);
		updateAttMain(postMain,attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostMain postMain) {
		super.delete(postMain);
	}

	public List<Map<String,String>> getPostMainList(Page<PostMain> page,PostMain postMain){
		postMain.setPage(page);
		return postMainDao.getPostMainList(postMain);
	}
	
}