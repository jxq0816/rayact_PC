package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCommoditySupplier;
import com.bra.modules.reserve.service.ReserveCommoditySupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 供应商Controller
 * @author jiangxingqi
 * @version 2016-06-05
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommoditySupplier")
public class ReserveCommoditySupplierController extends BaseController {

	@Autowired
	private ReserveCommoditySupplierService reserveCommoditySupplierService;
	
	@ModelAttribute
	public ReserveCommoditySupplier get(@RequestParam(required=false) String id) {
		ReserveCommoditySupplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommoditySupplierService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommoditySupplier();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCommoditySupplier reserveCommoditySupplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommoditySupplier> page = reserveCommoditySupplierService.findPage(new Page<ReserveCommoditySupplier>(request, response), reserveCommoditySupplier); 
		model.addAttribute("page", page);
		return "reserve/commodity/reserveCommoditySupplierList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveCommoditySupplier reserveCommoditySupplier, Model model) {
		model.addAttribute("reserveCommoditySupplier", reserveCommoditySupplier);
		return "reserve/commodity/reserveCommoditySupplierForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveCommoditySupplier reserveCommoditySupplier, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveCommoditySupplier)){
			return form(reserveCommoditySupplier, model);
		}
		reserveCommoditySupplierService.save(reserveCommoditySupplier);
		addMessage(redirectAttributes, "保存供应商成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySupplier/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveCommoditySupplier reserveCommoditySupplier, RedirectAttributes redirectAttributes) {
		reserveCommoditySupplierService.delete(reserveCommoditySupplier);
		addMessage(redirectAttributes, "删除供应商成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySupplier/list";
	}

}