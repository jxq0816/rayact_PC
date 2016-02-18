package com.bra.modules.reserve.service;

import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.MemberDao;
import com.bra.modules.reserve.entity.ReserveMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaobin on 16/2/17.
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends CrudService<MemberDao, ReserveMember> {

    @Transactional(readOnly = false)
    public void register(ReserveMember member) {
        //注册会员保存到数据库
        member.preInsert();//ID值
        dao.register(member);
    }


}
