package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.PostMain;

import java.util.List;
import java.util.Map;

/**
 * 帖子DAO接口
 * @author ddt
 * @version 2016-05-25
 */
@MyBatisDao
public interface PostMainDao extends CrudDao<PostMain> {
    public List<Map<String,String>> getPostMainList(PostMain postMain);
	
}