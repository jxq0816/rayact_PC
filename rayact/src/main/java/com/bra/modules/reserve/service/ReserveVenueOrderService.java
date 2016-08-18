package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.DateUtils;
import com.bra.modules.reserve.dao.ReserveVenueOrderDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.event.visitors.VenueOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 场地人次票订单Service
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueOrderService extends CrudService<ReserveVenueOrderDao, ReserveVenueOrder> {

    @Autowired
    private  ReserveCardStatementsService reserveCardStatementsService;
    @Autowired
    private ReserveTimecardMemberSetService reserveTimecardMemberSetService;
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveTimeCardPrepaymentService reserveTimeCardPrepaymentService;

    public ReserveVenueOrder get(String id) {
        return super.get(id);
    }

    public List<ReserveVenueOrder> findList(ReserveVenueOrder reserveVenueOrder) {
        return super.findList(reserveVenueOrder);
    }

    public List<Map<String,Object>> findPriceGroupProjectReport(ReserveVenueOrder venueOrder){
        return dao.findPriceGroupProjectReport(venueOrder);
    }

    public List<ReserveVenueOrder> findListOrder(ReserveVenueOrder venueOrder){
        return dao.findListOrder(venueOrder);
    }

    public List<Map<String,Object>> findPriceGroupProject(ReserveVenueOrder venueOrder){
        return dao.findPriceGroupProject(venueOrder);
    }

    public Page<ReserveVenueOrder> findPage(Page<ReserveVenueOrder> page, ReserveVenueOrder reserveVenueOrder) {
        return super.findPage(page, reserveVenueOrder);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenueOrder reserveVenueOrder) {
        super.save(reserveVenueOrder);
        //记录日志
        ReserveCardStatements card = new ReserveCardStatements();
        card.setVenue(reserveVenueOrder.getReserveVenue());
        card.setTransactionVolume(reserveVenueOrder.getCollectPrice());
        card.setPayType(reserveVenueOrder.getPayType());
        card.setTransactionNum(reserveVenueOrder.getCollectCount());
        card.setTransactionType("9");
        card.setReserveMember(reserveVenueOrder.getMember());
        card.setRemarks(reserveVenueOrder.getRemarks());
        card.setCreateDate(reserveVenueOrder.getOrderDate());//当订单时间与操作时间不一致时，需要调整，最后的总收入才准确
        card.setOrderId(reserveVenueOrder.getId());
        reserveCardStatementsService.save(card);
        applicationContext.publishEvent(new VenueOrderEvent(reserveVenueOrder));
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueOrder reserveVenueOrder) {
        super.delete(reserveVenueOrder);
        ReserveCardStatements statements = new ReserveCardStatements();
        statements.setOrderId(reserveVenueOrder.getId());
        List<ReserveCardStatements> list = reserveCardStatementsService.findList(statements);
        //金额冲回
        for(ReserveCardStatements i:list){
            if("1".equals(i.getPayType())){
                reserveVenueOrder=this.get(reserveVenueOrder);
                /*将用户的次数和金额 作为新的预储值冲回*/
                int ticketNum=reserveVenueOrder.getCollectCount();
                double collectPrice=reserveVenueOrder.getCollectPrice();
                ReserveMember member=reserveVenueOrder.getMember();
                member=reserveMemberService.get(member);
                ReserveTimecardMemberSet memberTimecarSet = member.getTimecardSet();
                memberTimecarSet = reserveTimecardMemberSetService.get(memberTimecarSet);
                ReserveProject project = memberTimecarSet.getReserveProject();
                ReserveTimeCardPrepayment prepayment = new ReserveTimeCardPrepayment();
                prepayment.setReserveMember(member);
                prepayment.setReserveProject(project);
                prepayment.setRemainder(collectPrice);
                prepayment.setRemainTime(ticketNum);
                prepayment.setSingleTimePrice(collectPrice/ticketNum);
                prepayment.setRemarks("系统退还");
                reserveTimeCardPrepaymentService.save(prepayment);
                //会员剩余次数变更，
                member.setRemainder(member.getRemainder()+collectPrice);
                member.setResidue(member.getResidue()+ticketNum);
                reserveMemberService.save(member);
            }
            reserveCardStatementsService.delete(i);
        }
        //记录 冲回金额
        if("1".equals(reserveVenueOrder.getPayType())){
            ReserveCardStatements log = new ReserveCardStatements();
            log.setTransactionType("4");
            ReserveMember member=reserveVenueOrder.getMember();
            member=reserveMemberService.get(member);
            log.setReserveMember(member);
            log.setRemarks(DateUtils.formatDate(reserveVenueOrder.getOrderDate())+"场次票 充回");
            reserveCardStatementsService.save(log);
        }
    }
}