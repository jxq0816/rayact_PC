package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.ConstantEntity;
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
    private ReserveTimeIntervalService reserveTimeIntervalService;

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
    //场地基本信息保存
    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveField reserveField,
                       Model model, RedirectAttributes redirectAttributes) throws ParseException {
        if (!beanValidator(model, reserveField)) {
            return form(reserveField, model);
        }
        reserveFieldService.save(reserveField);
        addMessage(redirectAttributes, "保存场地基本信息成功");
        redirectAttributes.addAttribute("reserveVenue.id",reserveField.getReserveVenue().getId());
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveField/list";
    }
    //场地表单
    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveField reserveField, Model model) throws ParseException {
        //全场与半场之间的关系
        if(StringUtils.isNoneEmpty(reserveField.getId())){
            ReserveFieldRelation reserveFieldRelation = new ReserveFieldRelation();
            reserveFieldRelation.setChildField(reserveField);
            List<ReserveFieldRelation> relations= reserveFieldRalationService.findList(reserveFieldRelation);//查询数据库是否已有父场地
            if(relations!=null && relations.size()!=0){//已有父场地
                reserveFieldRelation = relations.get(0);
                if (reserveFieldRelation != null) {
                    reserveField.setReserveParentField(reserveFieldRelation.getParentField());//给子场地设置父场地
                }
            }
        }
        //场地列表
        List<ReserveField> fields = reserveFieldService.findList(new ReserveField());
        model.addAttribute("reserveField", reserveField);
        model.addAttribute("fields", fields);
        model.addAttribute("venues", reserveVenueService.findList(new ReserveVenue()));
        model.addAttribute("projects", reserveProjectService.findList(new ReserveProject()));
        return "reserve/field/form";
    }
    //场地价格设置表单
    @RequestMapping(value = "priceSetForm")
    @Token(save = true)
    public String priceSetForm(ReserveField reserveField, String timeIntervalId,Model model) throws ParseException {
        //获取营业时间

        ReserveFieldPriceSet priceSet = new ReserveFieldPriceSet();
        if (StringUtils.isNotBlank(reserveField.getId())) {
            reserveField = reserveFieldService.get(reserveField.getId());
            priceSet.setReserveVenue(reserveField.getReserveVenue());
            priceSet.setReserveField(reserveField);
        }

        //时令列表
        List<ReserveTimeInterval> reserveTimeIntervalList = reserveTimeIntervalService.findList(new ReserveTimeInterval() );

        //时令 默认夏令
        if(ConstantEntity.YES.equals(reserveField.getIsTimeInterval())){//该场地分时令
            ReserveTimeInterval reserveTimeInterval=new ReserveTimeInterval();
            if(StringUtils.isNoneEmpty(timeIntervalId)){
                reserveTimeInterval=reserveTimeIntervalService.get(timeIntervalId);
            }else{
                reserveTimeInterval.setName("夏令");
                List<ReserveTimeInterval> reserveTimeIntervals = reserveTimeIntervalService.findList(reserveTimeInterval);
                if(reserveTimeIntervals!=null&&reserveTimeIntervals.size()!=0){
                    reserveTimeInterval=reserveTimeIntervals.get(0);
                }
            }
            model.addAttribute("reserveTimeInterval", reserveTimeInterval);
            priceSet.setReserveTimeInterval(reserveTimeInterval);//设置夏令
        }
        //常规价格
        List<ReserveFieldPriceSet> priceSetList = reserveFieldPriceSetService.findListByField(priceSet);
        //时令列表
        model.addAttribute("reserveTimeIntervalList", reserveTimeIntervalList);
        //时间列表
        ReserveVenue venue=reserveField.getReserveVenue();
        venue=reserveVenueService.get(venue);
        String startTime=venue.getStartTime();
        if(StringUtils.isEmpty(startTime)){
            startTime="06:00:00";
        }
        String endTime=venue.getEndTime();
        if(StringUtils.isEmpty(endTime)){
            endTime="00:30:00";
        }
        List<String> times = TimeUtils.getTimeSpacList(startTime, endTime, 60);
        model.addAttribute("times", times);

        //事件周期
        model.addAttribute("weekDays", TimeUtils.WEEK_DAYS);
        model.addAttribute("reserveField", reserveField);
        model.addAttribute("priceSetList", priceSetList);
        model.addAttribute("venues", reserveVenueService.findList(new ReserveVenue()));
        model.addAttribute("projects", reserveProjectService.findList(new ReserveProject()));
        if("1".equals(reserveField.getIsTimeInterval())){//如果分时令，跳转到时令价格设置界面
            return "reserve/field/timeIntervalPriceSetForm";
        }else{
            return "reserve/field/priceSetForm";
        }
    }

    //场地价格保存
    @RequestMapping(value = "savePrice")
    @Token(remove = true)
    public String savePrice(ReserveField reserveField, AttMainForm attMainForm, RoutinePrice routinePrice, HolidayPrice holidayPrice,
                       Model model, RedirectAttributes redirectAttributes) throws ParseException {
        if (!beanValidator(model, reserveField)) {
            return form(reserveField, model);
        }
        reserveFieldService.savePrice(reserveField, routinePrice, holidayPrice, attMainForm);
        addMessage(redirectAttributes, "保存场地成功");
        redirectAttributes.addAttribute("reserveVenue.id",reserveField.getReserveVenue().getId());
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveField/?repage";
    }
    @RequestMapping(value = "delete")
    public String delete(ReserveField reserveField, RedirectAttributes redirectAttributes) {
        reserveFieldService.delete(reserveField);
        addMessage(redirectAttributes, "删除场地成功");
        redirectAttributes.addAttribute("reserveVenue.id",reserveField.getReserveVenue().getId());
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveField/?repage";
    }

}