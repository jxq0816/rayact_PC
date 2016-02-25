package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;

import java.util.List;

/**
 * Created by dell on 2016/2/25.
 */
@MyBatisDao
public interface VenueConsItemDao extends CrudDao<ReserveVenueConsItem>{

    List<ReserveVenueConsItem> findListByDate(ReserveVenueConsItem reserveVenueCons);
}
