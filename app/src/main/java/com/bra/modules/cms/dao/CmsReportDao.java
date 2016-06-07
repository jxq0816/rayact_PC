package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.CmsReport;

/**
 * 举报DAO接口
 * @author ddt
 * @version 2016-06-04
 */
@MyBatisDao
public interface CmsReportDao extends CrudDao<CmsReport> {
	
}