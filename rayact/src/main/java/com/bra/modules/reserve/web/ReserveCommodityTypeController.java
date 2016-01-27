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
import com.bra.modules.reserve.entity.ReserveCommodityType;
import com.bra.modules.reserve.service.ReserveCommodityTypeService;

/**
 * 商品类别Controller
 * @author jiangxingqi
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/commodityType")
public class ReserveCommodityTypeController extends BaseController {

	@Autowired
	private ReserveCommodityTypeService reserveCommodityTypeService;
	
	@ModelAttribute
	public ReserveCommodityType get(@RequestParam(required=false) String id) {
		ReserveCommodityType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommodityTypeService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommodityType();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCommodityType commodityType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommodityType> page = reserveCommodityTypeService.findPage(new Page<ReserveCommodityType>(request, response), commodityType);
		model.addAttribute("page", page);
		return "reserve/commodity/reserveCommodityTypeList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveCommodityType commodityType, Model model) {
		model.addAttribute("commodityType", commodityType);
		return "reserve/commodity/reserveCommodityTypeForm";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveCommodityType commodityType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, commodityType)){
			return form(commodityType, model);
		}
		reserveCommodityTypeService.save(commodityType);
		addMessage(redirectAttributes, "保存商品类别成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/commodityType/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveCommodityType commodityType, RedirectAttributes redirectAttributes) {
		reserveCommodityTypeService.delete(commodityType);
		addMessage(redirectAttributes, "删除商品类别成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/commodityType/list";
	}

}