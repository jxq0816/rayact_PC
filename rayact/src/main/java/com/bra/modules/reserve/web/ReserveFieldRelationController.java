package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
import com.bra.modules.reserve.service.ReserveFieldRelationService;
import com.bra.modules.reserve.service.ReserveFieldService;
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
 * 场地管理Controller
 *
 * @author jiang
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveFieldRelation")
public class ReserveFieldRelationController extends BaseController {
    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveFieldRelationService reserveFieldRelationService;

    @ModelAttribute
    public ReserveFieldRelation get(@RequestParam(required = false) String id) {
        ReserveFieldRelation entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveFieldRelationService.get(id);
        }
        if (entity == null) {
            entity = new ReserveFieldRelation();
        }
        return entity;
    }
    //场地关系表单
    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveFieldRelation reserveFieldRelation, Model model){
        //全场与半场之间的关系
        reserveFieldRelation =this.get(reserveFieldRelation.getId());
        //场地列表
        List<ReserveField> fields = reserveFieldService.findList(new ReserveField());
        model.addAttribute("fields", fields);
        model.addAttribute("reserveFieldRelation", reserveFieldRelation);
        return "reserve/fieldRelation/form";
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveFieldRelation reserveFieldRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ReserveFieldRelation> reserveFieldRelationList = reserveFieldRelationService.findList(reserveFieldRelation);
        List<ReserveField> fields = reserveFieldService.findList(new ReserveField());
        model.addAttribute("fields", fields);
        model.addAttribute("reserveFieldRelationList", reserveFieldRelationList);
        return "reserve/fieldRelation/list";
    }

    @RequestMapping(value = "checkChildRelation")
    @ResponseBody
    public String checkChildRelation(String id,String childFieldId) {
        ReserveFieldRelation relation=new ReserveFieldRelation();
        ReserveField child=new ReserveField();
        child.setId(childFieldId);
        relation.setChildField(child);
        List<ReserveFieldRelation> list=reserveFieldRelationService.findList(relation);
        if(list.size()!=0) {
            //添加关系且数据库已存在该半场
            if (StringUtils.isEmpty(id)) {
                return "false";
            }else{//修改关系
                return "true";
            }
        }else{
            return "true";
        }
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public String save(String id,String parentFieldId,String childFieldId,RedirectAttributes redirectAttributes) {
        ReserveFieldRelation reserveFieldRelation =new ReserveFieldRelation();
        if(StringUtils.isNoneEmpty(id)){
            reserveFieldRelation.setId(id);
        }
        ReserveField parentField=new ReserveField();
        parentField.setId(parentFieldId);
        reserveFieldRelation.setParentField(parentField);
        ReserveField childField=new ReserveField();
        childField.setId(childFieldId);
        reserveFieldRelation.setChildField(childField);
        reserveFieldRelationService.save(reserveFieldRelation);
        return "success";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveFieldRelation reserveFieldRelation, RedirectAttributes redirectAttributes) {
        reserveFieldRelationService.physicalDelete(reserveFieldRelation);
        addMessage(redirectAttributes, "删除场地关系成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveFieldRelation/list";
    }
}