/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Article;
import com.bra.modules.cms.entity.Category;
import com.bra.modules.cms.entity.Site;
import com.bra.modules.cms.service.CategoryService;
import com.bra.modules.cms.service.FileTplService;
import com.bra.modules.cms.service.FocusService;
import com.bra.modules.cms.service.SiteService;
import com.bra.modules.cms.utils.TplUtils;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
 * 栏目Controller
 *
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {
	@Autowired
	private FocusService focusService;
	@Autowired
	private AttMainService attMainService;
	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request,Category category,Model model) {
		String module = request.getParameter("module");
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByUser(true,module);
		Category.sortList(list, sourcelist, "1");
        model.addAttribute("list", list);
		return "modules/cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category("1"));
		}
		Category parent = categoryService.get(category.getParent().getId());
		category.setParent(parent);
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(parent.getOffice());
		}
        model.addAttribute("listViewList",getTplContent(Category.DEFAULT_TEMPLATE));
        model.addAttribute("category_DEFAULT_TEMPLATE",Category.DEFAULT_TEMPLATE);
        model.addAttribute("contentViewList",getTplContent(Article.DEFAULT_TEMPLATE));
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("office", category.getOffice());
		model.addAttribute("category", category);
		return "modules/cms/categoryForm";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/";
		}
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		try{
			categoryService.save(category,attMainForm);
		}catch (Exception e){
			addMessage(redirectAttributes, "圈子名称重复！");
			return "redirect:" + adminPath + "/cms/category/form?id="+category.getId();
		}
		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
		return "redirect:" + adminPath + "/cms/category/";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/";
		}
		if (Category.isRoot(category.getId())){
			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(category);
			addMessage(redirectAttributes, "删除栏目成功");
		}
		return "redirect:" + adminPath + "/cms/category/";
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Category[] entitys = new Category[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = categoryService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		categoryService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/cms/category/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByUser(true, module);
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				map.put("module", e.getModule());
				mapList.add(map);
			}
		}
		return mapList;
	}

    private List<String> getTplContent(String prefix) {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, prefix, "");
   		return tplList;
   	}


	@RequestMapping(value = "app/cateList")
	public void treeData(HttpServletRequest request, HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		String module = request.getParameter("module");
		String mode = request.getParameter("mode");
		String userId = request.getParameter("userId");
		Category cate = new Category();
		if(StringUtils.isNotBlank(parentId)){
			Category parent = new Category();
			parent.setId(parentId);
			cate.setParent(parent);
		}
		if(StringUtils.isNotBlank(module)){
			cate.setModule(module);
		}
		if("create".equals(mode)){
			if(StringUtils.isNotBlank(userId)){
				cate.getSqlMap().put("addition","and c.create_by = '"+userId+"' ");
			}else{
				cate.getSqlMap().put("addition","and c.create_by = '"+UserUtils.getUser().getId()+"' ");
			}
		}else if("focus".equals(mode)){
			if(StringUtils.isNotBlank(userId)){
				cate.getSqlMap().put("addition"," and f.create_by = '"+ userId +"' ");
			}else{
				cate.getSqlMap().put("addition"," and f.create_by = '"+ UserUtils.getUser().getId() +"' ");
			}
		}
		List<Map<String,Object>> rtn = categoryService.getCate(cate);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/save")
	public void saveApp(Category category,HttpServletRequest request,HttpServletResponse response)throws Exception {
		String file = request.getParameter("files");
		String modelName = request.getParameter("modelName");
		if(StringUtils.isNotBlank(file)){
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
			if("user".equals(modelName)){
				attMain.setFdModelName(modelName);
			}else if("postMain".equals(modelName)){
				attMain.setFdModelName(modelName);
			}
			attMain = attMainService.saveAttMain(attMain);
			fileModel.setAttId(attMain.getId());
			category.setImage(com.bra.modules.sys.utils.StringUtils.ATTPATH + attMain.getId());
		}
		JSONObject j = new JSONObject();
		try{
			categoryService.save(category,null);
			j.put("status","success");
			j.put("msg","圈子新建成功");
		}catch (Exception e){
			j.put("status","fail");
			j.put("msg","圈子名称重复");
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
