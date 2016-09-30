/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.TeamMemberTmp;
import com.bra.modules.cms.entity.TeamTmp;
import com.bra.modules.cms.service.TeamMemberTmpService;
import com.bra.modules.cms.service.TeamTmpService;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
	@Autowired
	private AttMainService attMainService;
	
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
				if(rzNew.equals(tt.getRz())){
					return "true";
				}
			}
			return "false";
		}else {
			return "false";
		}
	}
	@RequestMapping(value = "app/checkPhoneSame")
	@ResponseBody
	public String checkPhoneSame(HttpServletRequest request)
	{
		String phone = request.getParameter("phone");
		TeamMemberTmp t = new TeamMemberTmp();
		t.setPhone(phone);
		List<TeamMemberTmp> members = teamMemberTmpService.findList(t);
		if(members!=null&&members.size()>0){
			return "true";//存在
		}else {
			return "false";
		}
	}
	@RequestMapping(value = "join/team")
	public String joinTeam(String phone,Model model){
		TeamMemberTmp member=new TeamMemberTmp();
		member.setPhone(phone);
		List<TeamMemberTmp> memberList = teamMemberTmpService.findList(member);
		List<TeamTmp> teamList = teamTmpService.findList(new TeamTmp());
		model.addAttribute("teamList", teamList);
		model.addAttribute("query", (memberList!=null&&memberList.size()!=0)?memberList.get(0):null);
		return "modules/cms/newTeamMemberForm";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(MultipartHttpServletRequest request, HttpServletResponse response)throws Exception {
		String leaderRemarks = "";
		TeamTmp tt = new TeamTmp();
		String teamId = request.getParameter("teamTmp.id");
		tt.setId(teamId);
		TeamMemberTmp member = new TeamMemberTmp();
		member.setTeamTmp(tt);
		String name = request.getParameter("name");
		String playerNum = request.getParameter("playerNum");
		String phone = request.getParameter("phone");
		String idNo = request.getParameter("idNo");
		String height = request.getParameter("height");
		String weight = request.getParameter("weight");
		String[] roleList = request.getParameterValues("role");
		String role="";
		for(String i:roleList){
			role=i+role;
		}
		member.setName(name);
		member.setPhone(phone);
		member.setCardNo(idNo);
		if(StringUtils.isNoneBlank(playerNum)){
			member.setPlayerNum(Integer.valueOf(playerNum));
		}
		if(StringUtils.isNoneBlank(height)){
			member.setHeight(Integer.valueOf(height));
		}
		if(StringUtils.isNoneBlank(weight)){
			member.setWeight(Integer.valueOf(weight));
		}
		member.setRole(role);
		MultipartFile img = request.getFile("picFiles");//头像
		if(img!=null){
			leaderRemarks += dealAtt(img,member);
		}
		MultipartFile idCard = request.getFile("idCardFiles");//身份证
		if(idCard!=null){
			leaderRemarks += dealAtt(idCard,member);
		}
		member.setRemarks(leaderRemarks);
		teamMemberTmpService.save(member);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg","报名成功");
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}
	private String dealAtt(MultipartFile files,TeamMemberTmp tmt)throws Exception{
		FileModel fileModel = new FileModel();
		String destPath = Global.getBaseDir();
		String tmp = destPath + "resources/www";
		File f =  new File(tmp + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + String.valueOf(new Date().getTime())+ UserUtils.getUser().getId());
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		if (!f.exists())
			f.createNewFile();
		files.transferTo(f);
		fileModel.setStoreType(StoreType.SYSTEM);
		fileModel.setToken(new Date().toString());
		fileModel.setFilePath(f.getAbsolutePath());
		fileModel.setContentType("pic");
		AttMain attMain = new AttMain(fileModel);
		attMain.setFdModelName(tmt.getClass().getName());
		attMain = attMainService.saveAttMain(attMain);
		String modelId = tmt.getId();
		String modelNamea = tmt.getClass().getName();
		attMainService.updateAttMain(attMain.getId(),modelId,modelNamea,"pic");
		fileModel.setAttId(attMain.getId());
		return com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId()+";";
	}
}