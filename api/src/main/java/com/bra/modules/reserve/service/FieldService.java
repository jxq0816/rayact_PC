package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.FieldDao;
import com.bra.modules.reserve.entity.ReserveField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2016/2/23.
 */
@Service
@Transactional(readOnly = false)
public class FieldService extends CrudService<FieldDao,ReserveField> {
}
