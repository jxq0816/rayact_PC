package com.bra.modules.reserve.service;

import java.util.List;

import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.dao.ReserveVenueDao;

/**
 * 场馆管理Service
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueService extends CrudService<ReserveVenueDao, ReserveVenue> {


    public ReserveVenue get(String id) {
        ReserveVenue reserveVenue = super.get(id);
        return reserveVenue;
    }

    public List<ReserveVenue> findList(ReserveVenue reserveVenue) {
        return super.findList(reserveVenue);
    }

    public Page<ReserveVenue> findPage(Page<ReserveVenue> page, ReserveVenue reserveVenue) {
        return super.findPage(page, reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue) {
        super.save(reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue, AttMainForm attMainForm) {
        super.save(reserveVenue);
        updateAttMain(reserveVenue, attMainForm);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenue reserveVenue) {
        super.delete(reserveVenue);
    }

}