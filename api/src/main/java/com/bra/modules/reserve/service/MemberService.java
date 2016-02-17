package com.bra.modules.reserve.service;

import com.bra.modules.reserve.dao.ReserveMemberDao;
import com.bra.modules.reserve.entity.ReserveMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaobin on 16/2/17.
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    @Transactional(readOnly = false)
    public void register(ReserveMember member){

    }

    @Autowired
    private ReserveMemberDao reserveMemberDao;
}
