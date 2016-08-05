package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Post;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.cms.service.PostMainService;
import com.bra.modules.cms.service.PostService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 回帖Controller
 * @author ddt
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/post")
public class PostController extends BaseController {
	@Autowired
	private AttMainService attMainService;
	@Autowired
	private PostService postService;
	@Autowired
	private PostMainService postMainService;
	
	@ModelAttribute
	public Post get(@RequestParam(required=false) String id) {
		Post entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postService.get(id);
		}
		if (entity == null){
			entity = new Post();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:post:view")
	@RequestMapping(value = {"list", ""})
	public String list(Post post, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Post> page = postService.findPage(new Page<Post>(request, response), post); 
		model.addAttribute("page", page);
		return "modules/cms/postList";
	}

	@RequiresPermissions("cms:post:view")
	@RequestMapping(value = "form")
	public String form(Post post, Model model) {
		model.addAttribute("post", post);
		PostMain pm = new PostMain();
		List<PostMain> pms = postMainService.findList(pm);
		model.addAttribute("pms", pms);
		return "modules/cms/postForm";
	}

	@RequiresPermissions("cms:post:edit")
	@RequestMapping(value = "save")
	public String save(Post post, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, post)){
			return form(post, model);
		}
		postService.save(post);
		addMessage(redirectAttributes, "保存回帖成功");
		return "redirect:"+Global.getAdminPath()+"/cms/post/?repage";
	}
	
	@RequiresPermissions("cms:post:edit")
	@RequestMapping(value = "delete")
	public String delete(Post post, RedirectAttributes redirectAttributes) {
		postService.delete(post);
		addMessage(redirectAttributes, "删除回帖成功");
		return "redirect:"+Global.getAdminPath()+"/cms/post/?repage";
	}

	@RequestMapping(value = "app/ptp")
	@ResponseBody
	public String ptp(Post post,HttpServletRequest request, HttpServletResponse response) {
		Page<Post> page =  postService.findPage(new Page<Post>(request, response),post);
		return JSONArray.toJSONString(page.getList());
	}

	@RequestMapping(value = "app/list")
	public void listApp(Post post,HttpServletRequest request, HttpServletResponse response) {
		Page<Post> page =  postService.findPage(new Page<>(request, response),post);
		List<Post> list = page.getList();
		JSONArray ja = new JSONArray();
		if(list!=null && list.size()>0){
			for(Post p : list){
				Map<String,String> peanut = new HashMap<>();
				Post po = new Post();
				po.setPostId(p.getId());
				List pop = postService.findMapList(po);
				peanut.put("id",p.getId());
				peanut.put("pid",p.getCreateBy().getId());
				peanut.put("pname",p.getCreateBy().getName());
				peanut.put("photo",p.getCreateBy().getPhoto());
				peanut.put("content",p.getContent());
				peanut.put("time", DateUtils.formatDateTime(p.getCreateDate()));
				JSONObject jj = (JSONObject)JSONObject.toJSON(peanut);
				jj.put("pop",JSONArray.toJSON(pop));
				ja.add(jj);
			}
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(ja.toJSONString());
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/save")
	public void save(Post post, @RequestParam("files") MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) throws Exception{
		//判断file数组不能为空并且长度大于0
		List<String> attIds = new ArrayList<>();
		String remarks ="";
		if(files!=null && files.length>0&&files[0].getSize()>0){
			//循环获取file数组中得文件
			for(int i = 0;i<files.length;i++){
				MultipartFile file = files[i];
				FileModel fileModel = new FileModel();
				String destPath = Global.getBaseDir();
				String tmp = destPath + "resources/www";
				File f =  new File(tmp + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + String.valueOf(new Date().getTime())+ UserUtils.getUser().getId());
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				if (!f.exists())
					f.createNewFile();
				file.transferTo(f);
				fileModel.setStoreType(StoreType.SYSTEM);
				fileModel.setToken(new Date().toString());
				fileModel.setFilePath(f.getAbsolutePath());
				fileModel.setContentType("pic");
				AttMain attMain = new AttMain(fileModel);
				attMain.setFdModelName(post.getClass().getName());
				attMain = attMainService.saveAttMain(attMain);
				attIds.add(attMain.getId());
				fileModel.setAttId(attMain.getId());
				remarks += "<img src='"+com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId()+"'style='margin:2px'/><br>";
			}
			post.setContent(post.getContent()+remarks);
		}
		JSONObject j = new JSONObject();
		try {
			postService.save(post);
			String modelId = post.getId();
			String modelNamea = post.getClass().getName();
			for(String id:attIds){
				attMainService.updateAttMain(id,modelId,modelNamea,"pic");
			}
			j.put("status","success");
			j.put("msg","回复成功");
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j);
		} catch (IOException e) {
		}
	}

	@RequiresPermissions("cms:post:edit")
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
		Post a = new Post();
		a.getSqlMap().put("del",del);
		postService.deleteAll(a);
		return "删除成功";
	}

}