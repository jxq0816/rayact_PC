package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenue;
/**
 * Created by dell on 2016/2/23.
 */
@MyBatisDao
public interface VenueDao extends CrudDao<ReserveVenue>{


}
