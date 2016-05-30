package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.CmsSubject;

/**
 * 话题DAO接口
 * @author ddt
 * @version 2016-05-25
 */
@MyBatisDao
public interface CmsSubjectDao extends CrudDao<CmsSubject> {
	
}