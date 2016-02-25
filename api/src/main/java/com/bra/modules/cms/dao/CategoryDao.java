package com.bra.modules.cms.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.cms.eneity.Category;

/**
 * Created by dell on 2016/2/25.
 */
@MyBatisDao
public interface CategoryDao extends CrudDao<Category> {
}
