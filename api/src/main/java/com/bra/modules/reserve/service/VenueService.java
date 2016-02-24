package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.VenueDao;
import com.bra.modules.reserve.entity.ReserveVenue;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2016/2/23.
 */
@Service
@Transactional(readOnly = true)
public class VenueService extends CrudService<VenueDao,ReserveVenue>{

    public Page<ReserveVenue> findPage(String pageNo, ReserveVenue venue){
        Page<ReserveVenue> page = new Page<>();
        page.setPageSize(10);
        page.setPageNo(NumberUtils.toInt(pageNo,1));
        return super.findPage(page,venue);
    }
}
