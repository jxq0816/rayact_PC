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
import com.bra.modules.cms.entity.Post;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.cms.service.PostMainService;
import com.bra.modules.cms.service.PostService;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
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
	private SystemService systemService;
	@Autowired
	private AttMainService attMainService;
	@Autowired
	private PostMainService postMainService;
	@Autowired
	private PostService postService;
	
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
		Page<PostMain> page = postMainService.findPage(new Page<PostMain>(request, response), postMain); 
		model.addAttribute("page", page);
		return "modules/cms/postMainList";
	}

	@RequiresPermissions("cms:postMain:view")
	@RequestMapping(value = "form")
	public String form(PostMain postMain, Model model) {
		model.addAttribute("postMain", postMain);
		return "modules/cms/postMainForm";
	}

	@RequiresPermissions("cms:postMain:edit")
	@RequestMapping(value = "save")
	public String save(PostMain postMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, postMain)){
			return form(postMain, model);
		}
		postMainService.save(postMain);
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
		if(file!=null&&file.length>0){
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
		String mode = request.getParameter("mode");
		String userId = request.getParameter("userId");
		if("create".equals(mode)){
			if(StringUtils.isNotBlank(userId)){
				postMain.getSqlMap().put("addition"," and pm.create_by = '"+ userId +"' ");
			}else{
				postMain.getSqlMap().put("addition"," and pm.create_by = '"+ UserUtils.getUser().getId()+"' ");
			}
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

	@RequestMapping(value = "app/view")
	public String viewApp(PostMain postMain,HttpServletRequest request,HttpServletResponse response){
		PostMain p = postMainService.get(postMain.getId());
		request.setAttribute("postMain",p);
		String[] remarks = p.getRemarks()!=null ? p.getRemarks().split(";"):null;
		request.setAttribute("imgs",remarks);
		Post post = new Post();
		post.setPostMain(postMain);
		Page<Post> lop = new Page<>(request,response);
		lop.setPageSize(3);
		lop.setPageNo(1);
		Page<Post> ptm = postService.findPage(lop,post);
		request.setAttribute("ptm",ptm.getList());
		return "modules/cms/postMainView";
	}

}