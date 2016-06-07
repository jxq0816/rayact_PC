package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.entity.Focus;

import java.util.List;

/**
 * 关注DAO接口
 * @author ddt
 * @version 2016-05-23
 */
@MyBatisDao
public interface FocusDao extends CrudDao<Focus> {
	public void updateFocus(Focus focus);
    public List<Focus> findListUn(Focus focus);
}