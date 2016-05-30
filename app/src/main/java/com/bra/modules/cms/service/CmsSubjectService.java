package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.CmsSubjectDao;
import com.bra.modules.cms.entity.CmsSubject;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 话题Service
 * @author ddt
 * @version 2016-05-25
 */
@Service
@Transactional(readOnly = true)
public class CmsSubjectService extends CrudService<CmsSubjectDao, CmsSubject> {

	public CmsSubject get(String id) {
		return super.get(id);
	}
	
	public List<CmsSubject> findList(CmsSubject cmsSubject) {
		return super.findList(cmsSubject);
	}
	
	public Page<CmsSubject> findPage(Page<CmsSubject> page, CmsSubject cmsSubject) {
		return super.findPage(page, cmsSubject);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsSubject cmsSubject, AttMainForm attMainForm) {
		super.save(cmsSubject);
		updateAttMain(cmsSubject,attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsSubject cmsSubject) {
		super.delete(cmsSubject);
	}
	
}