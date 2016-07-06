/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveMenu;

import java.util.List;

/**
 * 菜单配置DAO接口
 * @author jiangxingqi
 * @version 2016-07-05
 */
@MyBatisDao
public interface ReserveMenuDao extends CrudDao<ReserveMenu> {

    List<ReserveMenu> findByParentIdsLike(ReserveMenu menu);

    List<ReserveMenu> findByUserId(ReserveMenu menu);

    int updateParentIds(ReserveMenu menu);

    int updateSort(ReserveMenu menu);
}