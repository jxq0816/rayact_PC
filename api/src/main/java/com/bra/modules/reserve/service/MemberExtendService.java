package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.MemberExtendDao;
import com.bra.modules.reserve.entity.MemberExtend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaobin on 16/2/18.
 */
@Service
@Transactional(readOnly = true)
public class MemberExtendService extends CrudService<MemberExtendDao, MemberExtend> {

    //更新用户token
    @Transactional(readOnly = false)
    public void updateToken(MemberExtend memberExtend) {
        MemberExtend _me = dao.get(memberExtend);
        if (_me == null) {
            memberExtend.preInsert();
            dao.insert(memberExtend);
        }else{
            _me.setToken(memberExtend.getToken());
            _me.preUpdate();
            dao.update(_me);
        }
    }

    //用户退出
    @Transactional(readOnly = false)
    public void logout(MemberExtend memberExtend){
        dao.logout(memberExtend);
    }
}
