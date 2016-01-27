package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.service.ReserveCommoditySellService;

/**
 * 商品销售主表Controller
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommoditySell")
public class ReserveCommoditySellController extends BaseController {

	@Autowired
	private ReserveCommoditySellService reserveCommoditySellService;

	@ModelAttribute
	public ReserveCommoditySell get(@RequestParam(required=false) String id) {
		ReserveCommoditySell entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommoditySellService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommoditySell();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	@Token(save = true)
	public String list(ReserveCommoditySell reserveCommoditySell, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommoditySell> page = reserveCommoditySellService.findPage(new Page<ReserveCommoditySell>(request, response), reserveCommoditySell);
		model.addAttribute("page", page);
		return "reserve/commodity/reserveCommoditySellList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveCommoditySell reserveCommoditySell, Model model) {
		model.addAttribute("reserveCommoditySell", reserveCommoditySell);
		return "reserve/commodity/reserveCommoditySellForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	@Token(remove = true)
	public String insert(ReserveCommoditySell reserveCommoditySell) {
		reserveCommoditySellService.save(reserveCommoditySell);
		String id=reserveCommoditySell.getId();
		return id;
	}

	@RequestMapping(value = "delete")
	public String delete(ReserveCommoditySell reserveCommoditySell, RedirectAttributes redirectAttributes) {
		reserveCommoditySellService.delete(reserveCommoditySell);
		addMessage(redirectAttributes, "删除商品销售主表成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommoditySell/?repage";
	}

}