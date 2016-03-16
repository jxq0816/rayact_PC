package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.service.*;
import com.bra.modules.reserve.utils.TimeUtils;
import com.bra.modules.reserve.web.form.HolidayPrice;
import com.bra.modules.reserve.web.form.RoutinePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 场地管理Controller
 *
 * @author jiang
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveField")
public class ReserveFieldController extends BaseController {

    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveFieldRelationService reserveFieldRalationService;
    @Autowired
    private ReserveProjectService reserveProjectService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveFieldPriceSetService reserveFieldPriceSetService;
    @Autowired
    private ReserveFieldHolidayPriceSetService reserveFieldHolidayPriceSetService;

    @ModelAttribute
    public ReserveField get(@RequestParam(required = false) String id) {
        ReserveField entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveFieldService.get(id);
        }
        if (entity == null) {
            entity = new ReserveField();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveField reserveField, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveField> page = reserveFieldService.findPage(new Page<>(request, response), reserveField);
        List<ReserveVenue> venues = reserveVenueService.findList(new ReserveVenue());
        model.addAttribute("page", page);
        model.addAttribute("venues", venues);
        return "reserve/field/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveField reserveField, Model model) throws ParseException {

        //获取营业时间
        List<String> times = TimeUtils.getTimeSpacList("09:00:00", "23:00:00", 60);
        model.addAttribute("times", times);
        ReserveFieldPriceSet priceSet = null;
        if (StringUtils.isNotBlank(reserveField.getId())) {
            priceSet = new ReserveFieldPriceSet();
            reserveField = reserveFieldService.get(reserveField.getId());
            priceSet.setReserveVenue(reserveField.getReserveVenue());
            priceSet.setReserveField(reserveField);
        }
        //场地列表
        List<ReserveField> fields = reserveFieldService.findList(new ReserveField());

        //按日期价格
        ReserveFieldHolidayPriceSet holidayPriceSet = null;
        if (StringUtils.isNotBlank(reserveField.getId())) {
            holidayPriceSet = new ReserveFieldHolidayPriceSet();
            reserveField = reserveFieldService.get(reserveField.getId());
            holidayPriceSet.setReserveVenue(reserveField.getReserveVenue());
            holidayPriceSet.setReserveField(reserveField);
        }
        //全场与半场之间的关系
        ReserveFieldRelation reserveFieldRelation = new ReserveFieldRelation();
        reserveFieldRelation.setChildField(reserveField);
        List<ReserveFieldRelation> relations= reserveFieldRalationService.findList(reserveFieldRelation);//查询数据库是否已有父场地
        if(relations!=null && relations.size()!=0){//已有父场地
            reserveFieldRelation = relations.get(0);
            if (reserveFieldRelation != null) {
                reserveField.setReserveParentField(reserveFieldRelation.getParentField());//给子场地设置父场地
            }
        }
        //事件周期
        model.addAttribute("weekDays", TimeUtils.WEEK_DAYS);
        model.addAttribute("reserveField", reserveField);
        model.addAttribute("fields", fields);
        //常规价格
        model.addAttribute("priceSetList", reserveFieldPriceSetService.findListByField(priceSet));
        model.addAttribute("venues", reserveVenueService.findList(new ReserveVenue()));
        model.addAttribute("projects", reserveProjectService.findList(new ReserveProject()));
        model.addAttribute("holidayPriceSetList", reserveFieldHolidayPriceSetService.findList(holidayPriceSet));
        return "reserve/field/form";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveField reserveField, AttMainForm attMainForm, RoutinePrice routinePrice, HolidayPrice holidayPrice,
                       Model model, RedirectAttributes redirectAttributes) throws ParseException {
        if (!beanValidator(model, reserveField)) {
            return form(reserveField, model);
        }
        reserveFieldService.save(reserveField, routinePrice, holidayPrice, attMainForm);
        //全场与半场的关系保存
        ReserveFieldRelation relation = new ReserveFieldRelation();
        relation.setChildField(reserveField);//设置该厂为子半场
        ReserveField parentField = reserveField.getReserveParentField();
        if(StringUtils.isNotEmpty(parentField.getId())){//如果选择了父场地
            List<ReserveFieldRelation> relationDBList = reserveFieldRalationService.findList(relation);//数据库查询是否已有关系
            if(relationDBList.size()==0){//没有则新建
                ReserveFieldRelation fieldRelation = new ReserveFieldRelation();
                fieldRelation.setChildField(reserveField);
                fieldRelation.setParentField(parentField);
                reserveFieldRalationService.save(fieldRelation);
            }
            else {//已有则更新
                ReserveFieldRelation relationDB=relationDBList.get(0);
                relationDB.setParentField(parentField);
                reserveFieldRalationService.save(relationDB);
            }
        }else{//如果没有选择父场地，则将数据库中已有的关系删除
            List<ReserveFieldRelation> relationDBList = reserveFieldRalationService.findList(relation);//数据库查询是否已有关系
            if(relationDBList.size()!=0){//已有则删除
                ReserveFieldRelation relationDB=relationDBList.get(0);
                reserveFieldRalationService.delete(relationDB);
            }
        }
        addMessage(redirectAttributes, "保存场地成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveField/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveField reserveField, RedirectAttributes redirectAttributes) {
        reserveFieldService.delete(reserveField);
        addMessage(redirectAttributes, "删除场地成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveField/?repage";
    }

}