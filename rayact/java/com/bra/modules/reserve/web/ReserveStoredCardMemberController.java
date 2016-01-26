package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveStoredcardMemberSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @ModelAttribute
    public ReserveMember get(@RequestParam(required=false) String id) {
        ReserveMember entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = reserveMemberService.get(id);
        }
        if (entity == null){
            entity = new ReserveMember();
        }
        return entity;
    }

    @RequestMapping(value ="check")
    @ResponseBody
    public String check(String id,String cardno,String mobile,String sfz,HttpServletRequest request, HttpServletResponse response){
        String rs=null;

        ReserveMember rm1=new ReserveMember();
        rm1.setId(id);
        rm1.setCartno(cardno);

        ReserveMember rm2=new ReserveMember();
        rm2.setId(id);
        rm2.setMobile(mobile);

        ReserveMember rm3=new ReserveMember();
        rm3.setId(id);
        rm3.setSfz(sfz);

        List<ReserveMember> list1=reserveMemberService.findExactList(rm1);
        if(list1.size()!=0){
            rs="1";//卡号重复
            return rs;
        }

        List<ReserveMember> list2=reserveMemberService.findExactList(rm2);
        if(list2.size()!=0){
            rs="2";
            return rs;
        }

        List<ReserveMember> list3=reserveMemberService.findExactList(rm3);
        if(list3.size()!=0){
            rs="3";
            return rs;
        }
        return rs;
    }

    @RequestMapping(value ="list" )
    public String list( String cartType, ReserveMember reserveMember, HttpServletRequest request, HttpServletResponse response, Model model){
        reserveMember.setCartType("1");
        Page<ReserveMember> page = reserveMemberService.findPage(new Page<>(request, response), reserveMember);
        model.addAttribute("page", page);
        return "reserve/member/storedCardMemberList";
    }

    @RequestMapping(value = "form")
    @Token(save =true)
    public String form(ReserveMember reserveMember, Model model) {
        List<ReserveStoredcardMemberSet> storedcardSetList=storedcardSetService.findList(new ReserveStoredcardMemberSet());
        model.addAttribute("storedcardSetList", storedcardSetList);
        model.addAttribute("reserveMember", reserveMember);
        return "reserve/member/storedCardMemberForm";
    }

    @RequestMapping(value = "save")
    @Token(remove =true )
    public String save(ReserveMember reserveMember, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveMember)){
            return form(reserveMember, model);
        }
        reserveMember.setCartType("1");
        reserveMemberService.save(reserveMember);

        ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
        reserveCardStatements.setReserveMember(reserveMember);
        reserveCardStatements.setTransactionVolume(reserveMember.getRemainder());
        reserveCardStatements.setRemarks("储值卡会员注册");
        reserveCardStatements.setTransactionType("1");
        reserveCardStatementsService.save(reserveCardStatements);
        addMessage(redirectAttributes, "保存储值卡会员成功");
        return "redirect:"+ Global.getAdminPath()+"/reserve/storedCardMember/list";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveMember reserveMember, RedirectAttributes redirectAttributes) {
        reserveMemberService.delete(reserveMember);
        addMessage(redirectAttributes, "删除储值卡会员成功");
        return "redirect:"+Global.getAdminPath()+"/reserve/storedCardMember/list";
    }
}
