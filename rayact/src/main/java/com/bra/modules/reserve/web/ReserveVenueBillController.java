package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
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
import com.bra.modules.reserve.entity.ReserveVenueBill;
import com.bra.modules.reserve.service.ReserveVenueBillService;

import java.util.List;

/**
 * 场馆损益Controller
 * @author jiangxingqi
 * @version 2016-02-02
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueBill")
public class ReserveVenueBillController extends BaseController {

	@Autowired
	private ReserveVenueBillService reserveVenueBillService;

	@Autowired
	private ReserveVenueService reserveVenueService;

	@Autowired
	private ReserveUserService reserveUserService;
	
	@ModelAttribute
	public ReserveVenueBill get(@RequestParam(required=false) String id) {
		ReserveVenueBill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveVenueBillService.get(id);
		}
		if (entity == null){
			entity = new ReserveVenueBill();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveVenueBill reserveVenueBill, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveVenueBill> page = reserveVenueBillService.findPage(new Page<ReserveVenueBill>(request, response), reserveVenueBill);

		List<ReserveVenue> venueList = reserveVenueService.findList(new ReserveVenue());
		model.addAttribute("venueList", venueList);

		model.addAttribute("page", page);
		return "reserve/venue/billList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveVenueBill reserveVenueBill, Model model) {
		model.addAttribute("reserveVenueBill", reserveVenueBill);
		model.addAttribute("venueList", reserveVenueService.findList(new ReserveVenue()));
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			model.addAttribute("userRole", reserveUserService.getRole(user));
		}
		return "reserve/venue/billForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveVenueBill reserveVenueBill, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveVenueBill)){
			return form(reserveVenueBill, model);
		}
		reserveVenueBillService.save(reserveVenueBill);
		addMessage(redirectAttributes, "保存场馆损益成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveVenueBill/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveVenueBill reserveVenueBill, RedirectAttributes redirectAttributes) {
		reserveVenueBillService.delete(reserveVenueBill);
		addMessage(redirectAttributes, "删除场馆损益成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveVenueBill/?repage";
	}

}