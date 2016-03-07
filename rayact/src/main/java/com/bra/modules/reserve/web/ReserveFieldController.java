package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;

import java.text.ParseException;
import java.util.List;

/**
 * 场地管理Controller
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveField")
public class ReserveFieldController extends BaseController {

	@Autowired
	private ReserveFieldService reserveFieldService;
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
		List<ReserveVenue> venues=reserveVenueService.findList(new ReserveVenue());
		model.addAttribute("page", page);
		model.addAttribute("venues", venues);
		return "reserve/field/list";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveField reserveField, Model model) throws ParseException {
		model.addAttribute("reserveField", reserveField);
		model.addAttribute("venues", reserveVenueService.findList(new ReserveVenue()));
		model.addAttribute("projects", reserveProjectService.findList(new ReserveProject()));
		//事件周期
		model.addAttribute("weekDays", TimeUtils.WEEK_DAYS);
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
		//常规价格
		model.addAttribute("priceSetList",reserveFieldPriceSetService.findListByField(priceSet));
		//按日期价格
		ReserveFieldHolidayPriceSet holidayPriceSet = null;
		if (StringUtils.isNotBlank(reserveField.getId())) {
			holidayPriceSet = new ReserveFieldHolidayPriceSet();
			reserveField = reserveFieldService.get(reserveField.getId());
			holidayPriceSet.setReserveVenue(reserveField.getReserveVenue());
			holidayPriceSet.setReserveField(reserveField);
		}
		model.addAttribute("holidayPriceSetList",reserveFieldHolidayPriceSetService.findList(holidayPriceSet));
		return "reserve/field/form";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveField reserveField, AttMainForm attMainForm, RoutinePrice routinePrice,HolidayPrice holidayPrice,
					   Model model, RedirectAttributes redirectAttributes) throws ParseException {
		if (!beanValidator(model, reserveField)) {
			return form(reserveField, model);
		}
		reserveFieldService.save(reserveField, routinePrice,holidayPrice, attMainForm);
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