/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.TeamMemberTmp;
import com.bra.modules.cms.entity.TeamTmp;
import com.bra.modules.cms.service.TeamMemberTmpService;
import com.bra.modules.cms.service.TeamTmpService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 青草球队队员Controller
 * @author 青草球队队员
 * @version 2016-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/teamMemberTmp")
public class TeamMemberTmpController extends BaseController {
	@Autowired
	private TeamTmpService teamTmpService;
	@Autowired
	private TeamMemberTmpService teamMemberTmpService;
	
	@ModelAttribute
	public TeamMemberTmp get(@RequestParam(required=false) String id) {
		TeamMemberTmp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teamMemberTmpService.get(id);
		}
		if (entity == null){
			entity = new TeamMemberTmp();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:teamMemberTmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamMemberTmp teamMemberTmp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TeamMemberTmp> page = teamMemberTmpService.findPage(new Page<TeamMemberTmp>(request, response), teamMemberTmp);
		model.addAttribute("page", page);
		return "modules/cms/teamMemberTmpList";
	}

	@RequiresPermissions("cms:teamMemberTmp:view")
	@RequestMapping(value = "form")
	public String form(TeamMemberTmp teamMemberTmp,HttpServletRequest request, Model model) {
		TeamTmp t = new TeamTmp();
		List<TeamTmp> l = teamTmpService.findList(t);
		request.setAttribute("teams", l);
		model.addAttribute("teamMemberTmp", teamMemberTmp);
		return "modules/cms/teamMemberTmpForm";
	}
	@RequiresPermissions("cms:teamMemberTmp:view")
	@RequestMapping(value = "info")
	public String info(TeamMemberTmp teamMemberTmp, Model model) {
		model.addAttribute("teamMemberTmp", teamMemberTmp);
		return "modules/cms/teamMemberTmpInfo";
	}

	@RequiresPermissions("cms:teamMemberTmp:edit")
	@RequestMapping(value = "save")
	public String save(TeamMemberTmp teamMemberTmp, AttMainForm attMainForm,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teamMemberTmp)){
			return form(teamMemberTmp,request, model);
		}
		teamMemberTmpService.save(teamMemberTmp,attMainForm);
		addMessage(redirectAttributes, "保存青草球队队员成功");
		return "redirect:"+ Global.getAdminPath()+"/cms/teamMemberTmp/?repage";
	}
	
	@RequiresPermissions("cms:teamMemberTmp:edit")
	@RequestMapping(value = "delete")
	public String delete(TeamMemberTmp teamMemberTmp, RedirectAttributes redirectAttributes) {
		teamMemberTmpService.delete(teamMemberTmp);
		addMessage(redirectAttributes, "删除青草球队队员成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teamMemberTmp/?repage";
	}

	@RequestMapping(value = "app/checkIdCard")
	@ResponseBody
	public String checkIdCard(HttpServletRequest request)
	{
		String card = request.getParameter("card");
		String rzNew = request.getParameter("rz");
		TeamMemberTmp t = new TeamMemberTmp();
		t.setCardNo(card);
		List<TeamMemberTmp> members = teamMemberTmpService.findList(t);
		if(members!=null&&members.size()>0){
			for(TeamMemberTmp m:members){
				String id = m.getTeamTmp().getId();
				TeamTmp tt = teamTmpService.get(id);
				if(tt.getRz().equals(rzNew)){
					return "true";
				}
			}
			return "false";
		}else {
			return "false";
		}
	}

}