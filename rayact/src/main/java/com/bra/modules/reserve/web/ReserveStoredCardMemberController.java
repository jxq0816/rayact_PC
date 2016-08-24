package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveStoredcardMemberSetService;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 储值卡会员管理
 * Created by jiangxingqi on 2016/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/storedCardMember")
public class ReserveStoredCardMemberController extends BaseController {

    @Autowired
    private ReserveMemberService reserveMemberService;

    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;

    @Autowired
    private ReserveStoredcardMemberSetService storedcardSetService;

    @Autowired
    private ReserveVenueService reserveVenueService;


    @ModelAttribute
    public ReserveMember get(@RequestParam(required = false) String id) {
        ReserveMember entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveMemberService.get(id);
        }
        if (entity == null) {
            entity = new ReserveMember();
        }
        return entity;
    }



    @RequestMapping(value = "list")
    public String list(ReserveMember reserveMember, HttpServletRequest request, HttpServletResponse response, Model model) {
        reserveMember.setCardType("1");
        Page<ReserveMember> page = reserveMemberService.findPage(new Page<>(request, response), reserveMember);
        model.addAttribute("page", page);
        return "reserve/member/storedCardMemberList";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveMember reserveMember, Model model) {
        List<ReserveStoredcardMemberSet> storedcardSetList = storedcardSetService.findList(new ReserveStoredcardMemberSet());
        List<ReserveVenue> venueList = reserveVenueService.findList(new ReserveVenue());
        model.addAttribute("venueList", venueList);
        model.addAttribute("reserveMember", reserveMember);
        model.addAttribute("storedcardSetList", storedcardSetList);
        return "reserve/member/storedCardMemberForm";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveMember reserveMember, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveMember)) {
            return form(reserveMember, model);
        }
        reserveMember.setCardType("1");//储值卡会员
        ReserveCardStatements reserveCardStatements = new ReserveCardStatements();
        reserveCardStatements.setReserveMember(reserveMember);
        Double remainder = reserveMember.getRemainder();

        if (remainder == null) {
            reserveMember.setRemainder(0.0);//设置默认余额
            reserveMemberService.save(reserveMember);
        } else if (reserveMember.getId()!=null&&!StringUtils.isEmpty(reserveMember.getId())) {//修改用户
            reserveMemberService.save(reserveMember);
            ReserveMember reserveMemberOld = reserveMemberService.get(reserveMember);
            Double remainderOld = reserveMemberOld.getRemainder();
            Double transactionVolume = remainder - remainderOld;
            reserveCardStatements.setTransactionVolume(transactionVolume);
            reserveCardStatements.setReserveMember(reserveMember);
            reserveCardStatements.setRemarks("超级管理员修改余额");
            reserveCardStatements.setTransactionType("4");//交易类型
            reserveCardStatements.setVenue(reserveMember.getReserveVenue());
            reserveCardStatementsService.save(reserveCardStatements);
        } else {//新增用户
            reserveMemberService.save(reserveMember);
            reserveCardStatements.setTransactionVolume(reserveMember.getRemainder());
            reserveCardStatements.setRemarks("储值卡会员注册");
            reserveCardStatements.setTransactionType("1");//交易类型
            reserveCardStatements.setVenue(reserveMember.getReserveVenue());
            reserveCardStatementsService.save(reserveCardStatements);

        }

        addMessage(redirectAttributes, "保存储值卡会员成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/storedCardMember/list";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveMember reserveMember, RedirectAttributes redirectAttributes) {
        reserveMemberService.delete(reserveMember);
        addMessage(redirectAttributes, "删除储值卡会员成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/storedCardMember/list";
    }
}
