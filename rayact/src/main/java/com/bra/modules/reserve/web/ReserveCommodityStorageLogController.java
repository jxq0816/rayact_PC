package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCommodityStorageLog;
import com.bra.modules.reserve.service.ReserveCommodityStorageLogService;
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
 * 商品入库记录Controller
 * @author jiangxingqi
 * @version 2016-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommodityStorageLog")
public class ReserveCommodityStorageLogController extends BaseController {

	@Autowired
	private ReserveCommodityStorageLogService reserveCommodityStorageLogService;
	
	@ModelAttribute
	public ReserveCommodityStorageLog get(@RequestParam(required=false) String id) {
		ReserveCommodityStorageLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommodityStorageLogService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommodityStorageLog();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCommodityStorageLog reserveCommodityStorageLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommodityStorageLog> page = reserveCommodityStorageLogService.findPage(new Page<ReserveCommodityStorageLog>(request, response), reserveCommodityStorageLog); 
		model.addAttribute("page", page);
		model.addAttribute("query", reserveCommodityStorageLog);
		return "reserve/record/reserveCommodityStorageLogList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveCommodityStorageLog reserveCommodityStorageLog, Model model) {
		model.addAttribute("reserveCommodityStorageLog", reserveCommodityStorageLog);
		return "reserve/record/reserveCommodityStorageLogForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveCommodityStorageLog reserveCommodityStorageLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveCommodityStorageLog)){
			return form(reserveCommodityStorageLog, model);
		}
		reserveCommodityStorageLogService.save(reserveCommodityStorageLog);
		addMessage(redirectAttributes, "保存商品入库记录成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommodityStorageLog/?repage";
	}

}