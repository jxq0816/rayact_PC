package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.eneity.Comment;

import java.util.List;

/**
 * 评论接口
 */

@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {
     int insert(Comment comment);
     int update(Comment comment);
     int delete(Comment comment);
     List<Comment> findList(Comment comment);
}
