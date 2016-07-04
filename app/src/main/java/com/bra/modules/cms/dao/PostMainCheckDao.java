package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.PostMainCheck;

/**
 * 帖子核查DAO接口
 * @author ddt
 * @version 2016-06-24
 */
@MyBatisDao
public interface PostMainCheckDao extends CrudDao<PostMainCheck> {
	
}