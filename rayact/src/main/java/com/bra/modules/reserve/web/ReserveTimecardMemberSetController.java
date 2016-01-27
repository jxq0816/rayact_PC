package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.service.ReserveProjectService;
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
import com.bra.modules.reserve.entity.ReserveTimecardMemberSet;
import com.bra.modules.reserve.service.ReserveTimecardMemberSetService;

import java.util.List;

/**
 * 次卡设置Controller
 * @author jiangxingqi
 * @version 2016-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveTimecardMemberSet")
public class ReserveTimecardMemberSetController extends BaseController {

	@Autowired
	private ReserveTimecardMemberSetService reserveTimecardMemberSetService;

	@Autowired
	private ReserveProjectService reserveProjectService;
	
	@ModelAttribute
	public ReserveTimecardMemberSet get(@RequestParam(required=false) String id) {
		ReserveTimecardMemberSet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveTimecardMemberSetService.get(id);
		}
		if (entity == null){
			entity = new ReserveTimecardMemberSet();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveTimecardMemberSet reserveTimecardMemberSet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveTimecardMemberSet> page = reserveTimecardMemberSetService.findPage(new Page<ReserveTimecardMemberSet>(request, response), reserveTimecardMemberSet); 
		model.addAttribute("page", page);
		return "reserve/member/timeCardSetList";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveTimecardMemberSet reserveTimecardMemberSet, Model model) {
		List<ReserveProject> reserveProjectList=reserveProjectService.findList(new ReserveProject());
		model.addAttribute("reserveProjectList", reserveProjectList);
		model.addAttribute("reserveTimecardMemberSet", reserveTimecardMemberSet);
		return "reserve/member/timeCardSetForm";
	}

	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveTimecardMemberSet reserveTimecardMemberSet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveTimecardMemberSet)){
			return form(reserveTimecardMemberSet, model);
		}
		reserveTimecardMemberSetService.save(reserveTimecardMemberSet);
		addMessage(redirectAttributes, "保存次卡设置成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTimecardMemberSet/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveTimecardMemberSet reserveTimecardMemberSet, RedirectAttributes redirectAttributes) {
		reserveTimecardMemberSetService.delete(reserveTimecardMemberSet);
		addMessage(redirectAttributes, "删除次卡设置成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveTimecardMemberSet/?repage";
	}

}