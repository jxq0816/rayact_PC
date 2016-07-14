package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.CmsCollection;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.cms.service.CmsCollectionService;
import com.bra.modules.cms.service.PostMainService;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
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
 * 收藏Controller
 * @author ddt
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsCollection")
public class CmsCollectionController extends BaseController {

	@Autowired
	private CmsCollectionService cmsCollectionService;
	@Autowired
	private PostMainService postMainService;
	
	@ModelAttribute
	public CmsCollection get(@RequestParam(required=false) String id) {
		CmsCollection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsCollectionService.get(id);
		}
		if (entity == null){
			entity = new CmsCollection();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:cmsCollection:view")
	@RequestMapping(value = {"list", ""})
	public String list(CmsCollection cmsCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsCollection> page = cmsCollectionService.findPage(new Page<CmsCollection>(request, response), cmsCollection); 
		model.addAttribute("page", page);
		return "modules/cms/cmsCollectionList";
	}

	@RequiresPermissions("cms:cmsCollection:view")
	@RequestMapping(value = "form")
	public String form(CmsCollection cmsCollection, Model model) {
		model.addAttribute("cmsCollection", cmsCollection);
		return "modules/cms/cmsCollectionForm";
	}

	@RequiresPermissions("cms:cmsCollection:edit")
	@RequestMapping(value = "save")
	public String save(CmsCollection cmsCollection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cmsCollection)){
			return form(cmsCollection, model);
		}
		cmsCollectionService.save(cmsCollection);
		addMessage(redirectAttributes, "保存收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsCollection/?repage";
	}
	
	@RequiresPermissions("cms:cmsCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(CmsCollection cmsCollection, RedirectAttributes redirectAttributes) {
		cmsCollectionService.delete(cmsCollection);
		addMessage(redirectAttributes, "删除收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsCollection/?repage";
	}

	@RequestMapping(value = "app/list")
	public void getMapList(CmsCollection cmsCollection,HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String modelName = request.getParameter("modelName");
		if(StringUtils.isNotBlank(userId)){
			User u = new User();
			u.setId(userId);
			cmsCollection.setCreateBy(u);
		}else{
			cmsCollection.setCreateBy(UserUtils.getUser());
		}
		if("postMain".equals(modelName)){
			cmsCollection.getSqlMap().put("additionJ"," left join post_main pm on a.model_id = pm.id left join sys_user u on u.id = pm.create_by left join post p on p.main_id = pm.id ");
			cmsCollection.getSqlMap().put("additionS",",pm.fk_group_id as cateId,pm.subject as subject,pm.content as content,pm.remarks as photos,u.id as createId,u.name as createName,u.photo as createPhoto,count(p.id) as postNum  ");
		}else if("article".equals(modelName)){
			cmsCollection.getSqlMap().put("additionJ"," left join cms_article ar on a.model_id = ar.id left join sys_user u on u.id = ar.create_by left join cms_comment c on c.content_id = ar.id ");
			cmsCollection.getSqlMap().put("additionS",",ar.category_id as cateId,ar.title as subject,ar.description as content,ar.image as photos,u.id as createId,u.name as createName,u.photo as createPhoto,count(c.id) as postNum ");
		}
		List<Map<String,String>> rtn = cmsCollectionService.findMapList(cmsCollection);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/save")
	public void saveApp(CmsCollection cmsCollection, HttpServletResponse response) {
		JSONObject j = new JSONObject();
		cmsCollection.setCreateBy(UserUtils.getUser());
		List<CmsCollection> list=  cmsCollectionService.findListUn(cmsCollection);
		if(list!=null&&list.size()>0){
			for(CmsCollection co:list){
				co.setDelFlag(cmsCollection.getDelFlag());
				cmsCollectionService.updateCollection(co);
			}
			if("1".equals(cmsCollection.getDelFlag())){
				j.put("status","success");
				j.put("msg","取消收藏成功");
			}else {
				j.put("status","success");
				j.put("msg","收藏成功");
			}
		}else {
			cmsCollectionService.save(cmsCollection);
			j.put("status","success");
			j.put("msg","收藏成功");
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/isCollect")
	public void isCollect(CmsCollection cmsCollection, HttpServletResponse response) {
		JSONObject j = new JSONObject();
		cmsCollection.setCreateBy(UserUtils.getUser());
		List<CmsCollection> list=  cmsCollectionService.findList(cmsCollection);
		if(list!=null&&list.size()>0){
			j.put("status","true");
		}else {
			j.put("status","false");
		}
		if("postMain".equals(cmsCollection.getModelName())){
			PostMain pm = postMainService.get(cmsCollection.getModelId());
			if(pm!=null){
				User uc = pm.getCreateBy();
				User now = UserUtils.getUser();
				if(uc!=null&&now!=null&&uc.getId().equals(now.getId())){
					j.put("isCreate","true");
				}else{
					j.put("isCreate","false");
				}
			}else{
				j.put("isCreate","false");
			}
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}

}