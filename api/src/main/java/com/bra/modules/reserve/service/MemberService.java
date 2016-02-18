package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.MemberDao;
import com.bra.modules.reserve.dao.MemberExtendDao;
import com.bra.modules.reserve.entity.ReserveMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaobin on 16/2/17.
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends CrudService<MemberDao, ReserveMember> {

    @Autowired
    private MemberExtendDao memberExtendDao;

    @Transactional(readOnly = false)
    public ReserveMember findRegisterMobile(ReserveMember member) {
        //数据库中注册的会员的手机号
       return dao.findRegisterMobile(member);
    }
    @Transactional(readOnly = false)
    public void register(ReserveMember member) {
        //注册会员保存到数据库
        member.preInsert();//ID值
        dao.register(member);
    }

    /**
     * 完善用户信息
     */
    public void prefectUserInfo(ReserveMember member){
        dao.prefectUserInfo(member);
        memberExtendDao.update(member.getMemberExtend());
    }


}
