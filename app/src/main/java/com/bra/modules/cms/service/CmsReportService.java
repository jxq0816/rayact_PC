package com.bra.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.entity.CmsReport;
import com.bra.modules.cms.dao.CmsReportDao;

/**
 * 举报Service
 * @author ddt
 * @version 2016-06-04
 */
@Service
@Transactional(readOnly = true)
public class CmsReportService extends CrudService<CmsReportDao, CmsReport> {

	public CmsReport get(String id) {
		return super.get(id);
	}
	
	public List<CmsReport> findList(CmsReport cmsReport) {
		return super.findList(cmsReport);
	}
	
	public Page<CmsReport> findPage(Page<CmsReport> page, CmsReport cmsReport) {
		return super.findPage(page, cmsReport);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsReport cmsReport) {
		super.save(cmsReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsReport cmsReport) {
		super.delete(cmsReport);
	}
	
}