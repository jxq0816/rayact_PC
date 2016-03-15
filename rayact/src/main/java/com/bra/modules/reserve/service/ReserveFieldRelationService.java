package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveFieldRelationDao;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 场地关系管理Service
 *
 * @author jiang
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldRelationService extends CrudService<ReserveFieldRelationDao, ReserveFieldRelation> {

    public ReserveFieldRelation get(String id) {
        ReserveFieldRelation reserveFieldRelation = super.get(id);
        return reserveFieldRelation;
    }

    public List<ReserveFieldRelation> findList(ReserveFieldRelation relation) {
        return super.findList(relation);
    }

    @Transactional(readOnly = false)
    public void save(ReserveFieldRelation relation) {
        super.save(relation);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveFieldRelation relation) {
        super.delete(relation);
    }

}