package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.CmsCollectionDao;
import com.bra.modules.cms.entity.CmsCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 收藏Service
 * @author ddt
 * @version 2016-06-02
 */
@Service
@Transactional(readOnly = true)
public class CmsCollectionService extends CrudService<CmsCollectionDao, CmsCollection> {
	@Autowired
	private CmsCollectionDao cmsCollectionDao;
	public CmsCollection get(String id) {
		return super.get(id);
	}
	
	public List<CmsCollection> findList(CmsCollection cmsCollection) {
		return super.findList(cmsCollection);
	}
	
	public Page<CmsCollection> findPage(Page<CmsCollection> page, CmsCollection cmsCollection) {
		return super.findPage(page, cmsCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsCollection cmsCollection) {
		super.save(cmsCollection);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsCollection cmsCollection) {
		super.delete(cmsCollection);
	}


	public List<Map<String,String>> findMapList(CmsCollection cmsCollection){
		return cmsCollectionDao.findMapList(cmsCollection);
	}
}