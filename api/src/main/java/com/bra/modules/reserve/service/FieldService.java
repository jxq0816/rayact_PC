package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.FieldDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/2/23.
 */
@Service
@Transactional(readOnly = false)
public class FieldService extends CrudService<FieldDao,ReserveField> {

    public List<Map<String,Object>> findProjectByField(ReserveField field){
        return dao.findProjectByField(field);
    }

    public List<Map<String,Object>> findFieldByProjectId(String projectId,ReserveVenue venue){
        ReserveField field = new ReserveField();
        field.setProjectId(projectId);
        field.setReserveVenue(venue);
        return dao.findFieldByProjectId(field);
    }
}
