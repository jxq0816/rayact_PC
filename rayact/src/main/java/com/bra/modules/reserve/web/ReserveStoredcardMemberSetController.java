package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.persistence.Page;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.service.ReserveFieldService;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveStoredcardMemberSetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bra.modules.reserve.entity.ReserveStoredcardMemberSet;
import com.bra.common.config.Global;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;

import java.util.List;

/**
 * 储值卡设置Controller
 * @author 琪
 * @version 2016-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/storedCardMemberSet")
public class ReserveStoredcardMemberSetController extends BaseController {

    @Autowired
    private ReserveStoredcardMemberSetService reserveStoredcardMemberSetService;

    @Autowired
    private ReserveProjectService reserveProjectService;

    @Autowired
    private ReserveFieldService reserveFieldService;


    @ModelAttribute
    public ReserveStoredcardMemberSet get(@RequestParam(required=false) String id) {
        ReserveStoredcardMemberSet entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = reserveStoredcardMemberSetService.get(id);
        }
        if (entity == null){
            entity = new ReserveStoredcardMemberSet();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveStoredcardMemberSet reservestoredCardMemberSet, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveStoredcardMemberSet> page = reserveStoredcardMemberSetService.findPage(new Page<ReserveStoredcardMemberSet>(request, response), reservestoredCardMemberSet);
        model.addAttribute("page", page);
        return "reserve/member/storedCardSetList";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveStoredcardMemberSet reservestoredCardMemberSet, Model model) {

        List<ReserveProject> reserveProjectList=reserveProjectService.findList(new ReserveProject());

        ReserveField field=new ReserveField();
        List<ReserveField> reserveFieldList = reserveFieldService.findList(field);

        model.addAttribute("reserveProjectList", reserveProjectList);
        model.addAttribute("reserveFieldList", reserveFieldList);
        model.addAttribute("reservestoredCardMemberSet", reservestoredCardMemberSet);
        return "reserve/member/storedCardSetForm";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveStoredcardMemberSet reservestoredCardMemberSet, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reservestoredCardMemberSet)){
            return form(reservestoredCardMemberSet, model);
        }
        if(reservestoredCardMemberSet.getDiscountRate()==null){
            reservestoredCardMemberSet.setDiscountRate(0.0);
        }
        reserveStoredcardMemberSetService.save(reservestoredCardMemberSet);
        addMessage(redirectAttributes, "保存储值卡成功");
        return "redirect:"+Global.getAdminPath()+"/reserve/storedCardMemberSet/list";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveStoredcardMemberSet reservestoredCardMemberSet, RedirectAttributes redirectAttributes) {
        reserveStoredcardMemberSetService.delete(reservestoredCardMemberSet);
        addMessage(redirectAttributes, "删除储值卡成功");
        return "redirect:"+Global.getAdminPath()+"/reserve/storedCardMemberSet/list";
    }

}