package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Post;

import java.util.List;
import java.util.Map;

/**
 * 回帖DAO接口
 * @author ddt
 * @version 2016-05-25
 */
@MyBatisDao
public interface PostDao extends CrudDao<Post> {
	public List<Map<String,String>> findMapList(Post post);
}