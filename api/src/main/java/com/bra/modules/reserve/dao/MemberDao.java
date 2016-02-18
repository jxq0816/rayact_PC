package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveMember;

/**
 * Created by xiaobin on 16/2/17.
 */
@MyBatisDao
public interface MemberDao extends CrudDao<ReserveMember>{
    //注册会员
    void  register(ReserveMember reserveMember);
    //查询是否存在手机号
    ReserveMember findRegisterMobile(ReserveMember  reserveMember);
}
