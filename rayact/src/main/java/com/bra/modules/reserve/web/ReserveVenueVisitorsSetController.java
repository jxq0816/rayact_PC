package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueVisitorsSet;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.service.ReserveVenueVisitorsSetService;
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
 * 人次票设置Controller
 * @author 肖斌
 * @version 2016-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueVisitorsSet")
public class ReserveVenueVisitorsSetController extends BaseController {

	@Autowired
	private ReserveVenueVisitorsSetService reserveVenueVisitorsSetService;
	@Autowired
	private ReserveVenueService reserveVenueService;
	@Autowired
	private ReserveProjectService reserveProjectService;
	
	@ModelAttribute
	public ReserveVenueVisitorsSet get(@RequestParam(required=false) String id) {
		ReserveVenueVisitorsSet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveVenueVisitorsSetService.get(id);
		}
		if (entity == null){
			entity = new ReserveVenueVisitorsSet();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveVenueVisitorsSet reserveVenueVisitorsSet, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ReserveVenueVisitorsSet> page = reserveVenueVisitorsSetService.findList(reserveVenueVisitorsSet);
		model.addAttribute("page", page);
		return "reserve/visitorsset/list";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveVenueVisitorsSet reserveVenueVisitorsSet, Model model) {
		model.addAttribute("reserveVenues",reserveVenueService.findList(new ReserveVenue())) ;
		ReserveProject project = new ReserveProject();
		/*project.setTicketType("2");*/
		model.addAttribute("projects",reserveProjectService.findList(project));
		model.addAttribute("reserveVenueVisitorsSet", reserveVenueVisitorsSet);
		return "reserve/visitorsset/form";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	@Token(remove = true)
	public String save(ReserveVenueVisitorsSet reserveVenueVisitorsSet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveVenueVisitorsSet)){
			return form(reserveVenueVisitorsSet, model);
		}
		reserveVenueVisitorsSetService.save(reserveVenueVisitorsSet);
		return "success";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveVenueVisitorsSet reserveVenueVisitorsSet, RedirectAttributes redirectAttributes) {
		reserveVenueVisitorsSetService.delete(reserveVenueVisitorsSet);
		addMessage(redirectAttributes, "删除人次票设置成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveVenueVisitorsSet/?repage";
	}

}