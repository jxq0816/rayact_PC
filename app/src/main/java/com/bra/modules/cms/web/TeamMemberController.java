package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.cms.entity.TeamMember;
import com.bra.modules.cms.service.TeamMemberService;
import com.bra.modules.cms.service.TeamService;
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
import java.util.List;
import java.util.Map;

/**
 * 战队成员Controller
 * @author ddt
 * @version 2016-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/teamMember")
public class TeamMemberController extends BaseController {
	@Autowired
	private TeamService teamService;

	@Autowired
	private TeamMemberService teamMemberService;
	
	@ModelAttribute
	public TeamMember get(@RequestParam(required=false) String id) {
		TeamMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teamMemberService.get(id);
		}
		if (entity == null){
			entity = new TeamMember();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:teamMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamMember teamMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TeamMember> page = teamMemberService.findPage(new Page<TeamMember>(request, response), teamMember); 
		model.addAttribute("page", page);
		return "modules/cms/teamMemberList";
	}

	@RequiresPermissions("cms:teamMember:view")
	@RequestMapping(value = "form")
	public String form(TeamMember teamMember, Model model) {
		Team t =new Team();
		List<Team> teams = teamService.findList(t);
		model.addAttribute("teams", teams);
		model.addAttribute("teamMember", teamMember);
		return "modules/cms/teamMemberForm";
	}

	@RequiresPermissions("cms:teamMember:edit")
	@RequestMapping(value = "save")
	public String save(TeamMember teamMember, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teamMember)){
			return form(teamMember, model);
		}
		teamMemberService.save(teamMember);
		addMessage(redirectAttributes, "保存战队成员成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teamMember/?repage";
	}
	
	@RequiresPermissions("cms:teamMember:edit")
	@RequestMapping(value = "delete")
	public String delete(TeamMember teamMember, RedirectAttributes redirectAttributes) {
		teamMemberService.delete(teamMember);
		addMessage(redirectAttributes, "删除战队成员成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teamMember/?repage";
	}

	@RequestMapping(value = "app/list")
	public void findListMap(TeamMember teamMember,HttpServletResponse response){
		List<Map<String,String>> rtn = teamMemberService.findListMap(teamMember);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));
		} catch (IOException g) {

		}
	}

	@RequestMapping(value = "app/save")
	public void saveApp(HttpServletRequest request,HttpServletResponse response){
		String teamId = request.getParameter("teamId");
		String members = request.getParameter("members");
		JSONObject j = JSONObject.parseObject(members);
		JSONArray ja = JSONArray.parseArray(j.get("member").toString());
		for(int i =0 ;i<ja.size();i++){
			JSONObject jo = ja.getJSONObject(i);
			String phone = jo.getString("phone");
			if(StringUtils.isNotBlank(phone)){
				String name = jo.getString("name");
				String role = jo.getString("role");
				Team t = new Team();
				t.setId(teamId);
				TeamMember member = new TeamMember();
				member.setPhone(phone);
				member.setTeam(t);
				List<TeamMember> list = teamMemberService.findList(member);
				TeamMember tm = null;
				if(list!=null && list.size()>0){
					tm = list.get(0);
					tm.setName(name);
					tm.setRole(role);
				}else{
					tm = new TeamMember();
					tm.setName(name);
					tm.setPhone(phone);
					tm.setRole(role);
					tm.setTeam(t);
				}
				teamMemberService.save(tm);
			}
		}
	}

	@RequestMapping(value = "app/form")
	public String formApp(HttpServletRequest request,HttpServletResponse response){
		String teamId = request.getParameter("teamId");
		TeamMember tm = new TeamMember();
		Team t = new Team();
		t.setId(teamId);
		tm.setTeam(t);
		List<TeamMember> l = teamMemberService.findList(tm);
		request.setAttribute("members",l);
		request.setAttribute("teamId",teamId);
		return "modules/cms/members";
	}

}