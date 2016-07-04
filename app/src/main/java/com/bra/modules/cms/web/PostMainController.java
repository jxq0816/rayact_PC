package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.*;
import com.bra.modules.cms.service.*;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.ImageUtils;
import com.bra.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 * 帖子Controller
 * @author ddt
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/postMain")
public class PostMainController extends BaseController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AttMainService attMainService;
	@Autowired
	private PostMainService postMainService;
	@Autowired
	private PostService postService;
	@Autowired
	private PostMainCheckService postMainCheckService;
	
	@ModelAttribute
	public PostMain get(@RequestParam(required=false) String id) {
		PostMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postMainService.get(id);
		}
		if (entity == null){
			entity = new PostMain();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:postMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(PostMain postMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Category> list = categoryService.findByUser(true,"postMain");
		Page<PostMain> page = postMainService.findPage(new Page<PostMain>(request, response), postMain); 
		model.addAttribute("page", page);
		return "modules/cms/postMainList";
	}

	@RequiresPermissions("cms:postMain:view")
	@RequestMapping(value = "form")
	public String form(PostMain postMain, Model model) {
		String c = postMain.getContent();
		if(StringUtils.isNotBlank(c)){
			c = c.replaceAll("<br>","\n");
			c = c.replaceAll("&nbsp;"," ");
			postMain.setContent(c);
		}
		model.addAttribute("postMain", postMain);
		return "modules/cms/postMainForm";
	}

	@RequiresPermissions("cms:postMain:edit")
	@RequestMapping(value = "save")
	public String save(PostMain postMain, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, postMain)){
			return form(postMain, model);
		}
		postMainService.save(postMain,attMainForm);
		addMessage(redirectAttributes, "保存帖子成功");
		return "redirect:"+Global.getAdminPath()+"/cms/postMain/?repage";
	}
	
	@RequiresPermissions("cms:postMain:edit")
	@RequestMapping(value = "delete")
	public String delete(PostMain postMain, RedirectAttributes redirectAttributes) {
		postMainService.delete(postMain);
		addMessage(redirectAttributes, "删除帖子成功");
		return "redirect:"+Global.getAdminPath()+"/cms/postMain/?repage";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(PostMain postMain, HttpServletRequest request,HttpServletResponse response)throws Exception {
		String files = request.getParameter("files");
		String modelName = request.getParameter("modelName");
		String[] file = files.split(",");
		String remarks ="";
		if(file!=null&&file.length>0&&StringUtils.isNotBlank(file[0])){
			for(int i =0;i<file.length;i++){
				String obj = file[i];
				byte[] image = Base64.decode(obj);
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
				ImageUtils.resizeFix(f,768,1024);
				fileModel.setStoreType(StoreType.SYSTEM);
				fileModel.setToken(new Date().toString());
				fileModel.setFilePath(f.getAbsolutePath());
				fileModel.setContentType("pic");
				AttMain attMain = new AttMain(fileModel);
				if("user".equals(modelName)){
					attMain.setFdModelName(modelName);
				}else if("postMain".equals(modelName)){
					attMain.setFdModelName(modelName);
				}
				attMain = attMainService.saveAttMain(attMain);
				fileModel.setAttId(attMain.getId());
				remarks += com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId()+";";
			}
		}
		postMain.setRemarks(remarks);
		postMain.setContent(StringEscapeUtils.unescapeHtml4(
				postMain.getContent()));
		postMain.setSubject(StringEscapeUtils.unescapeHtml4(
				postMain.getSubject()));
		postMainService.save(postMain);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg","发帖成功");
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/list")
	public void listApp(PostMain postMain,  HttpServletRequest request,HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		String mode = request.getParameter("mode");
		String userId = request.getParameter("userId");
		Date last = null;
		if("create".equals(mode)){
			if(StringUtils.isNotBlank(userId)){
				postMain.getSqlMap().put("addition"," and pm.create_by = '"+ userId +"' ");
			}else{
				PostMainCheck check = new PostMainCheck();
				check.setCreateBy(UserUtils.getUser());
				List<PostMainCheck> list = postMainCheckService.findList(check);
				if(list!=null&&list.size()>0){
					PostMainCheck cl = list.get(0);
					last = cl.getUpdateDate();
					postMainCheckService.save(cl);
				}else{
					PostMainCheck c = new PostMainCheck();
					postMainCheckService.save(c);
				}
				postMain.getSqlMap().put("addition"," and pm.create_by = '"+ UserUtils.getUser().getId()+"' ");
			}
		}else if("focus".equals(mode)){
			Focus focus = new Focus();
			focus.setModelName("user");
			User u = UserUtils.getUser();
			if(u!=null && StringUtils.isNotBlank(u.getId())){
				focus.setCreateBy(u);
				List<Focus> list = focusService.findList(focus);
				String in = "(";
				if(list!=null&&list.size()>0){
					for(Focus f:list){
						in += "'"+f.getModelId()+"',";
					}
					in = in.substring(0,in.length()-1)+") ";
					postMain.getSqlMap().put("addition"," and pm.create_by in "+ in );
				}else{
					postMain.getSqlMap().put("addition"," and 1 = 2 ");
				}
			}else {
				postMain.getSqlMap().put("addition"," and 1 = 2 ");
			}
		}
		if(StringUtils.isNotBlank(keyword)){
			postMain.setContent(keyword);
			postMain.setSubject(keyword);
		}
		if(last!=null){
			postMain.setUpdateDate(last);
		}else{
			postMain.setUpdateDate(DateUtils.parseDate("1970-09-01"));
		}
		List<Map<String,String>> rtn = 	postMainService.getPostMainList(new Page<PostMain>(request, response),postMain);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/view.html")
	public String viewApp(PostMain postMain,HttpServletRequest request,HttpServletResponse response){
		PostMain p = postMainService.get(postMain.getId());
		request.setAttribute("postMain",p);
		if(p!=null){
			String[] remarks = p.getRemarks()!=null ? p.getRemarks().split(";"):null;
			request.setAttribute("imgs",remarks);
		}
		Post post = new Post();
		post.setPostMain(postMain);
		Page<Post> lop = new Page<>(request,response);
		lop.setPageSize(5);
		lop.setPageNo(1);
		Page<Post> ptm = postService.findPage(lop,post);
		request.setAttribute("count",ptm.getCount());
		request.setAttribute("ptm",ptm.getList());
		return "modules/cms/postMainView";
	}


}