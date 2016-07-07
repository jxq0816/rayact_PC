package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Team;
import com.bra.modules.cms.entity.TeamMember;
import com.bra.modules.cms.service.FocusService;
import com.bra.modules.cms.service.TeamMemberService;
import com.bra.modules.cms.service.TeamService;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.codec.Base64;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
	private FocusService focusService;
	@Autowired
	private AttMainService attMainService;
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
		try {
			teamService.save(team,attMainForm);
		}catch (Exception e){
			addMessage(redirectAttributes, "数据重复");
			return "redirect:"+Global.getAdminPath()+"/cms/team/form?id="+team.getId();
		}
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
	public void saveApp(Team team,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String file = request.getParameter("files");
		String modelName = request.getParameter("modelName");
		if(!"".equals(file)&&file!=null){
			byte[] image = Base64.decode(file);
			FileModel fileModel = new FileModel();
			String destPath = Global.getBaseDir();
			String tmp = destPath + "resources/www";
			File f =  new File(tmp + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + String.valueOf(new Date().getTime())+ UserUtils.getUser().getId());
			if (!f.getParentFile().exists())
				f.getParentFile().mkdirs();
			if (!f.exists())
				f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(image);
			fos.close();
			fileModel.setStoreType(StoreType.SYSTEM);
			fileModel.setToken(new Date().toString());
			fileModel.setFilePath(f.getAbsolutePath());
			fileModel.setContentType("pic");
			AttMain attMain = new AttMain(fileModel);
			attMain.setFdModelName(modelName);
			attMain = attMainService.saveAttMain(attMain);
			fileModel.setAttId(attMain.getId());
			team.setPhoto(com.bra.modules.sys.utils.StringUtils.ATTPATH+attMain.getId());
		}
		JSONObject j = new JSONObject();
		boolean flag = true;
		if(!StringUtils.isNotBlank(team.getId())){
			try{
				teamService.save(team,null);
			}catch (Exception e){
				j.put("status","fail");
				j.put("msg","此球队已存在");
				j.put("teamId","");
				flag=false;
			}
		}
		if(flag){
			String teamMember = request.getParameter("members");
			saveMembers(teamMember,team);
			j.put("status","success");
			j.put("msg","队伍创建成功");
			j.put("teamId",team.getId());
		}
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
		String mode = request.getParameter("mode");
		String userId = request.getParameter("userId");
		if(StringUtils.isNotBlank(userId)){
			if("create".equals(mode)){
				team.getSqlMap().put("addition"," and a.create_by = '"+userId+"' ");
			}else if("focus".equals(mode)){
				team.getSqlMap().put("addition"," and f.create_by = '"+ userId +"' ");
			}else if("join".equals(mode)){
				team.getSqlMap().put("addition"," and a.member_ids like '%"+userId+"%' ");
			}
		}else if(UserUtils.getUser()!=null&&StringUtils.isNotBlank(UserUtils.getUser().getId())){
			if("create".equals(mode)){
				team.getSqlMap().put("addition"," and a.create_by = '"+UserUtils.getUser().getId()+"' ");
			}else if("focus".equals(mode)){
				team.getSqlMap().put("addition"," and f.create_by = '"+ UserUtils.getUser().getId() +"' ");
			}else if("join".equals(mode)){
				team.getSqlMap().put("addition"," and a.member_ids like '%"+UserUtils.getUser().getId()+"%' ");
			}
		}
		List<Map<String,String>> list = teamService.findListMap(new Page<Team>(request, response),team);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(list, SerializerFeature.WriteMapNullValue));
		} catch (IOException g) {

		}
	}

	@RequestMapping(value = "app/delete")
	public void deleteApp(Team team, HttpServletResponse response) {
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
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException g) {

		}
	}

	public void saveMembers(String a,Team team){
		a = a.replaceAll("&quot;","\"");
		JSONArray ja = JSONArray.parseArray(a);
		if(ja.size()>0){
			for(int i =0;i<ja.size();i++){
				JSONObject jo = ja.getJSONObject(i);
				String id = jo.get("id")!=null?String.valueOf(jo.get("id")):"";
				String phone = jo.get("phone")!=null?String.valueOf(jo.get("phone")):"";
				if(StringUtils.isNotBlank(id)){
					TeamMember tm = teamMemberService.get(id);
					tm.setTeam(team);
					tm.setName(String.valueOf(jo.get("name")));
					tm.setPhone(phone);
					tm.setRole(String.valueOf(jo.get("role")));
					tm.setIscaptain(jo.get("isCaptain")!=null ? String.valueOf(jo.get("isCaptain")):"0");
					tm.setRemarks(String.valueOf(jo.get("remarks")));
					teamMemberService.save(tm);
				}else{
					TeamMember tms = new TeamMember();
					tms.setPhone(phone);
					tms.setTeam(team);
					List<TeamMember> l = teamMemberService.findList(tms);
					if(l!=null&&l.size()>0){
						continue;
					}else{
						TeamMember tm = new TeamMember();
						tm.setTeam(team);
						tm.setName(String.valueOf(jo.get("name")));
						tm.setPhone(phone);
						tm.setRole(String.valueOf(jo.get("role")));
						tm.setIscaptain(jo.get("isCaptain")!=null ? String.valueOf(jo.get("isCaptain")):"0");
						tm.setRemarks(String.valueOf(jo.get("remarks")));
						teamMemberService.save(tm);
					}
				}
			}
		}
	}

	@RequiresPermissions("cms:team:edit")
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public String deleteAll(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String[] ida = request.getParameterValues("ids[]");
		String del = " id in (";
		if(ida!=null&&ida.length>0){
			for(int i =0;i<ida.length;i++){
				if(i<ida.length-1)
					del+="'"+ida[i]+"',";
				else
					del+="'"+ida[i]+"')";
			}
		}
		Team a = new Team();
		a.getSqlMap().put("del",del);
		teamService.deleteAll(a);
		return "删除成功";
	}

}