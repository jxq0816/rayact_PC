package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.bra.modules.reserve.entity.ReserveFieldHolidayPriceSet;
import com.bra.modules.reserve.service.ReserveFieldHolidayPriceSetService;

/**
 * 场地价格设定（节假日价格）Controller
 * @author 肖斌
 * @version 2016-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveFieldHolidayPriceSet")
public class ReserveFieldHolidayPriceSetController extends BaseController {

	@Autowired
	private ReserveFieldHolidayPriceSetService reserveFieldHolidayPriceSetService;
	
	@ModelAttribute
	public ReserveFieldHolidayPriceSet get(@RequestParam(required=false) String id) {
		ReserveFieldHolidayPriceSet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveFieldHolidayPriceSetService.get(id);
		}
		if (entity == null){
			entity = new ReserveFieldHolidayPriceSet();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveFieldHolidayPriceSet:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveFieldHolidayPriceSet> page = reserveFieldHolidayPriceSetService.findPage(new Page<ReserveFieldHolidayPriceSet>(request, response), reserveFieldHolidayPriceSet); 
		model.addAttribute("page", page);
		return "modules/reserve/reserveFieldHolidayPriceSetList";
	}

	@RequiresPermissions("reserve:reserveFieldHolidayPriceSet:view")
	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet, Model model) {
		model.addAttribute("reserveFieldHolidayPriceSet", reserveFieldHolidayPriceSet);
		return "modules/reserve/reserveFieldHolidayPriceSetForm";
	}

	@RequiresPermissions("reserve:reserveFieldHolidayPriceSet:edit")
	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveFieldHolidayPriceSet)){
			return form(reserveFieldHolidayPriceSet, model);
		}
		reserveFieldHolidayPriceSetService.save(reserveFieldHolidayPriceSet);
		addMessage(redirectAttributes, "保存场地价格设定（节假日价格）成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveFieldHolidayPriceSet/?repage";
	}
	
	@RequiresPermissions("reserve:reserveFieldHolidayPriceSet:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet, RedirectAttributes redirectAttributes) {
		reserveFieldHolidayPriceSetService.delete(reserveFieldHolidayPriceSet);
		addMessage(redirectAttributes, "删除场地价格设定（节假日价格）成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveFieldHolidayPriceSet/?repage";
	}

}