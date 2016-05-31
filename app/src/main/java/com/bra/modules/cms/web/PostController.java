package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Post;
import com.bra.modules.cms.service.PostService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回帖Controller
 * @author ddt
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/post")
public class PostController extends BaseController {

	@Autowired
	private PostService postService;
	
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
	public void save(Post post,HttpServletResponse response) {
		JSONObject j = new JSONObject();
		try {
			postService.save(post);
			j.put("status","success");
			j.put("msg","回复成功");
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j);
		} catch (IOException e) {
		}
	}

}