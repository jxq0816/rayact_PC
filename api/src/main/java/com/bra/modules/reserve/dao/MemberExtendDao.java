package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.MemberExtend;

/**
 * Created by xiaobin on 16/2/18.
 */
@MyBatisDao
public interface MemberExtendDao extends CrudDao<MemberExtend> {

    //更新用户token信息
    void updateToken(MemberExtend memberExtend);

    //退出用户
    void logout(MemberExtend memberExtend);
}
