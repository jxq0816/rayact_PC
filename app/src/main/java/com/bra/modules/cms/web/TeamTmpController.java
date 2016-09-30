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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 青草球队Controller
 * @author 青草球队
 * @version 2016-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/teamTmp")
public class TeamTmpController extends BaseController {

	@Autowired
	private TeamTmpService teamTmpService;
	@Autowired
	private TeamMemberTmpService teamMemberTmpService;
	@Autowired
	private AttMainService attMainService;
	
	@ModelAttribute
	public TeamTmp get(@RequestParam(required=false) String id) {
		TeamTmp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = teamTmpService.get(id);
		}
		if (entity == null){
			entity = new TeamTmp();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:teamTmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(TeamTmp teamTmp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TeamTmp> page = teamTmpService.findPage(new Page<TeamTmp>(request, response), teamTmp);
		model.addAttribute("page", page);
		return "modules/cms/teamTmpList";
	}

	@RequiresPermissions("cms:teamTmp:view")
	@RequestMapping(value = "form")
	public String form(TeamTmp teamTmp, Model model) {
		model.addAttribute("teamTmp", teamTmp);
		return "modules/cms/teamTmpForm";
	}

	@RequiresPermissions("cms:teamTmp:edit")
	@RequestMapping(value = "save")
	public String save(TeamTmp teamTmp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, teamTmp)){
			return form(teamTmp, model);
		}
		teamTmpService.save(teamTmp);
		addMessage(redirectAttributes, "保存青草球队成功");
		return "redirect:"+ Global.getAdminPath()+"/cms/teamTmp/?repage";
	}
	
	@RequiresPermissions("cms:teamTmp:edit")
	@RequestMapping(value = "delete")
	public String delete(TeamTmp teamTmp, RedirectAttributes redirectAttributes) {
		teamTmpService.delete(teamTmp);
		addMessage(redirectAttributes, "删除青草球队成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teamTmp/?repage";
	}

	@RequiresPermissions("cms:teamTmp:edit")
	@RequestMapping(value = "changeStatus")
	public String changeStatus(TeamTmp teamTmp,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String delFlag = request.getParameter("delFlag");
		teamTmp.setDelFlag(delFlag);
		teamTmpService.changeStatus(teamTmp);
		addMessage(redirectAttributes, "通过青草球队成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teamTmp/?repage";
	}

	@RequestMapping(value = "formToApp")
	public String formToApp() {
		return "modules/cms/newTeamTmp";
	}
	@RequestMapping(value = "app/checkSame")
	@ResponseBody
	public String checkSame(HttpServletRequest request)
	{
		String name = request.getParameter("name");
		TeamTmp t = new TeamTmp();
		t.setName(name);
		List<TeamTmp> l = teamTmpService.findList(t);
		if(l!=null&&l.size()>0){
			return "true";
		}else{
			return "false";
		}
	}
	@RequestMapping(value = "teamIndex")
	public String teamIndex(HttpServletRequest request) {
		return "modules/cms/teamTmpIndex";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(MultipartHttpServletRequest request, HttpServletResponse response)throws Exception {
		String leaderRemarks = "";
		TeamTmp tt = new TeamTmp();
		//队伍名称
		String teamName = request.getParameter("teamName");
		//报名组别
		String zb = request.getParameter("zb");
		//几人制
		String rz = request.getParameter("rz");
		tt.setName(teamName);
		tt.setZb(zb);
		tt.setRz(rz);
		MultipartFile logoImg = request.getFile("logoFiles");//队徽
		if(logoImg!=null){
			String logoImgUrl=dealAtt(logoImg,tt);
			tt.setReamrks(logoImgUrl);
		}
		teamTmpService.save(tt);

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

	private String dealAtt(MultipartFile files,TeamTmp tmt)throws Exception{
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

	@RequestMapping(value = "app/search")
	public String appSearch(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		TeamTmp tt = new TeamTmp();
		tt.setName(keyword);
		List<TeamTmp> list = teamTmpService.findList(tt);
		if(list!=null&&list.size()>0){
			TeamMemberTmp tmt = new TeamMemberTmp();
			tmt.setTeamTmp(list.get(0));
			tmt.setRole("1");
			List<TeamMemberTmp> leaders = teamMemberTmpService.findList(tmt);
			tmt.setRole("2");
			List<TeamMemberTmp> teachers = teamMemberTmpService.findList(tmt);
			tmt.setRole("3");
			List<TeamMemberTmp> members = teamMemberTmpService.findList(tmt);
			request.setAttribute("team",list.get(0));
			request.setAttribute("leader",(leaders!=null&&leaders.size()>0)?leaders.get(0):null);
			request.setAttribute("teacher",(teachers!=null&&teachers.size()>0)?teachers.get(0):new TeamMemberTmp());
			request.setAttribute("members",members);
		}else{
			TeamMemberTmp s = new TeamMemberTmp();
			s.setName(keyword);
			List<TeamMemberTmp> m = teamMemberTmpService.findList(s);
			if(m!=null&&m.size()>0){
				String teamId = m.get(0).getTeamTmp().getId();
				TeamTmp t = teamTmpService.get(teamId);
				if(t!=null){
					TeamMemberTmp tmt = new TeamMemberTmp();
					tmt.setTeamTmp(t);
					tmt.setRole("1");
					List<TeamMemberTmp> leaders = teamMemberTmpService.findList(tmt);
					tmt.setRole("2");
					List<TeamMemberTmp> teachers = teamMemberTmpService.findList(tmt);
					tmt.setRole("3");
					List<TeamMemberTmp> members = teamMemberTmpService.findList(tmt);
					request.setAttribute("team",t);
					request.setAttribute("leader",leaders.get(0));
					request.setAttribute("teacher",(teachers!=null&&teachers.size()>0)?teachers.get(0):new TeamMemberTmp());
					request.setAttribute("members",members);
				}
			}
		}
		return "modules/cms/teamTmpSearchResult";
	}

	@RequestMapping(value = "info")
	public String info(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		TeamTmp t = teamTmpService.get(id);
		TeamMemberTmp tmt = new TeamMemberTmp();
		tmt.setTeamTmp(t);
		tmt.setRole("1");
		List<TeamMemberTmp> leaders = teamMemberTmpService.findList(tmt);//领队
		tmt.setRole("2");
		List<TeamMemberTmp> teachers = teamMemberTmpService.findList(tmt);//教练
		tmt.setRole("3");
		List<TeamMemberTmp> members = teamMemberTmpService.findList(tmt);//队员
		tmt.setRole("4");
		List<TeamMemberTmp> captains = teamMemberTmpService.findList(tmt);//队长
		List<Map<String,String>> count =  teamTmpService.getMemberCount(t);
		request.setAttribute("counts",count);
		request.setAttribute("team",t);
		request.setAttribute("leader",(leaders!=null&&leaders.size()>0)?leaders.get(0):new TeamMemberTmp());
		request.setAttribute("teacher",(teachers!=null&&teachers.size()>0)?teachers.get(0):new TeamMemberTmp());
		request.setAttribute("captain",(captains!=null&&captains.size()>0)?captains.get(0):new TeamMemberTmp());
		request.setAttribute("members",members);
		return "modules/cms/teamTmpInfo";
	}

	@RequestMapping(value = "exportImg")
	@ResponseBody
	public String exportImg() {
		TeamTmp tt = new TeamTmp();
		List<TeamTmp> l = teamTmpService.findList(tt);
		if(l!=null&&l.size()>0){
			for(TeamTmp t:l){
				String teamN = t.getName();
				//创建队名文件夹
				String dn = createDir(teamN);
				if(StringUtils.isNotBlank(dn)){
					TeamMemberTmp tmt = new TeamMemberTmp();
					tmt.setTeamTmp(t);
					List<TeamMemberTmp> ms = teamMemberTmpService.findList(tmt);
					if(ms!=null&&ms.size()>0){
						for(TeamMemberTmp m:ms){
							String mn = m.getName();
							//创建人名文件夹
							String mdn = createDir(dn+"/"+mn);
							if(StringUtils.isNotBlank(mdn)){
								String remarks = m.getRemarks();
								String[] imgs = remarks.split(";");
								if(imgs!=null&&imgs.length>0){
									int k = 0;
									for(String i:imgs){
										k++;
										if(StringUtils.isNotBlank(i)){
											saveToFile(i,mdn,k);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return "done";
	}

	public void saveToFile(String destUrl,String pp,int k) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(pp+"/"+k);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (Exception e) {
			}
		}
	}

	public static String createDir(String destDirName) {
		String destDirNameNew = destDirName;
		File dir = new File(destDirName);
		int i = 0;
		while (dir.exists()) {
			i++;
			destDirNameNew = destDirName+i;
			dir = new File(destDirNameNew);
		}
		//创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return destDirNameNew;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return "";
		}
	}

}