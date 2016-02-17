package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.Sms;

/**
 * Created by xiaobin on 16/2/17.
 */
@MyBatisDao
public interface SmsDao extends CrudDao<Sms>{

    //查询手机号
    Sms findMobile(Sms sms);
}
