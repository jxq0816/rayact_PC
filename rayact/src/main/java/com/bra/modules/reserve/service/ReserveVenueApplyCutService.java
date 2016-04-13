package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveVenueApplyCutDao;
import com.bra.modules.reserve.entity.ReserveVenueApplyCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 场馆管理Service
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueApplyCutService extends CrudService<ReserveVenueApplyCutDao, ReserveVenueApplyCut> {

    @Autowired
    private ReserveVenueApplyCutDao dao;


    public ReserveVenueApplyCut get(String id) {
        ReserveVenueApplyCut reserveVenueApplyCut = super.get(id);
        return reserveVenueApplyCut;
    }

    public List<ReserveVenueApplyCut> findList(ReserveVenueApplyCut reserveVenueApplyCut) {
        return super.findList(reserveVenueApplyCut);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenueApplyCut reserveVenueApplyCut) {
        super.save(reserveVenueApplyCut);
    }


    @Transactional(readOnly = false)
    public void delete(ReserveVenueApplyCut reserveVenueApplyCut) {
        super.delete(reserveVenueApplyCut);
    }

}