package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.CmsCollection;

import java.util.List;
import java.util.Map;

/**
 * 收藏DAO接口
 * @author ddt
 * @version 2016-06-02
 */
@MyBatisDao
public interface CmsCollectionDao extends CrudDao<CmsCollection> {
	public List<Map<String,String>> findMapList(CmsCollection cmsCollection);
	public List<CmsCollection> findListUn(CmsCollection cmsCollection);
	public void updateCollection(CmsCollection cmsCollection);
}