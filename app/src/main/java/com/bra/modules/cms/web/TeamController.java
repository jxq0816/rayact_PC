package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.cms.entity.TeamMember;
import com.bra.modules.cms.service.TeamMemberService;
import com.bra.modules.cms.service.TeamService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 战队Controller
 * @author ddt
 * @version 2016-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/team")
public class TeamController extends BaseController {

	@Autowired
	private TeamMemberService teamMemberService;
	@Autowired
	private TeamService teamService;
	
	@ModelAttribute
	public Team get(@RequestParam(required=false) String id) {
		Team entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teamService.get(id);
		}
		if (entity == null){
			entity = new Team();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:team:view")
	@RequestMapping(value = {"list", ""})
	public String list(Team team, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Team> page = teamService.findPage(new Page<Team>(request, response), team); 
		model.addAttribute("page", page);
		return "modules/cms/teamList";
	}

	@RequiresPermissions("cms:team:view")
	@RequestMapping(value = "form")
	public String form(Team team, Model model) {
		model.addAttribute("team", team);
		return "modules/cms/teamForm";
	}

	@RequiresPermissions("cms:team:edit")
	@RequestMapping(value = "save")
	public String save(Team team, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, team)){
			return form(team, model);
		}
		teamService.save(team,attMainForm);
		addMessage(redirectAttributes, "保存战队成功");
		return "redirect:"+Global.getAdminPath()+"/cms/team/?repage";
	}
	
	@RequiresPermissions("cms:team:edit")
	@RequestMapping(value = "delete")
	public String delete(Team team, RedirectAttributes redirectAttributes) {
		teamService.delete(team);
		addMessage(redirectAttributes, "删除战队成功");
		return "redirect:"+Global.getAdminPath()+"/cms/team/?repage";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(Team team,HttpServletRequest request, HttpServletResponse response) {
		JSONObject j = new JSONObject();
		teamService.save(team);
		String teamMember = request.getParameter("members");
		List<TeamMember> tms = strToList(teamMember);
		for(TeamMember tm:tms){
			teamMemberService.save(tm);
		}
		j.put("status","success");
		j.put("msg","队伍创建成功");
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException g) {

		}

	}

	@RequestMapping(value = "app/list")
	public void listApp(Team team,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,String>> list = teamService.findListMap(team);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(list));
		} catch (IOException g) {

		}
	}

	@RequestMapping(value = "app/delete")
	public void deleteApp(Team team,HttpServletRequest request, HttpServletResponse response) {
		TeamMember tm = new TeamMember();
		tm.setTeam(team);
		List<TeamMember> tms = teamMemberService.findList(tm);
		if(tms!=null){
			for(TeamMember mm:tms){
				teamMemberService.delete(mm);
			}
		}
		teamService.delete(team);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg","删除成功");
		List<Map<String,String>> list = teamService.findListMap(team);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException g) {

		}
	}

	public List strToList(String a){
		return new ArrayList<>();
	}

}