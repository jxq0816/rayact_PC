package com.bra.modules.mechanism.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.mechanism.entity.MCollection;

/**
 * Created by dell on 2016/2/24.
 */
@MyBatisDao
public interface CollectionDao extends CrudDao<MCollection>{

    //取消收藏
    void cancel(MCollection collection);

    MCollection findByModelIdKeyUser(MCollection collection);
}
