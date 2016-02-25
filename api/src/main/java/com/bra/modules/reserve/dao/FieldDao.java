package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveField;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/2/23.
 */
@MyBatisDao
public interface FieldDao extends CrudDao<ReserveField>{

    List<Map<String,Object>> findProjectByField(ReserveField field);

    List<Map<String,Object>> findFieldByProjectId(ReserveField field);
}
