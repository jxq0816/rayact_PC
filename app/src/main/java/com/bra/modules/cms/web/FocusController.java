package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Focus;
import com.bra.modules.cms.service.FocusService;
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

/**
 * 关注Controller
 * @author ddt
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/focus")
public class FocusController extends BaseController {

	@Autowired
	private FocusService focusService;
	
	@ModelAttribute
	public Focus get(@RequestParam(required=false) String id) {
		Focus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = focusService.get(id);
		}
		if (entity == null){
			entity = new Focus();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:focus:view")
	@RequestMapping(value = {"list", ""})
	public String list(Focus focus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Focus> page = focusService.findPage(new Page<Focus>(request, response), focus); 
		model.addAttribute("page", page);
		return "modules/cms/focusList";
	}

	@RequiresPermissions("cms:focus:view")
	@RequestMapping(value = "form")
	public String form(Focus focus, Model model) {
		model.addAttribute("focus", focus);
		return "modules/cms/focusForm";
	}

	@RequiresPermissions("cms:focus:edit")
	@RequestMapping(value = "save")
	public String save(Focus focus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, focus)){
			return form(focus, model);
		}
		focusService.save(focus);
		addMessage(redirectAttributes, "保存关注成功");
		return "redirect:"+Global.getAdminPath()+"/cms/focus/?repage";
	}
	
	@RequiresPermissions("cms:focus:edit")
	@RequestMapping(value = "delete")
	public String delete(Focus focus, RedirectAttributes redirectAttributes) {
		focusService.delete(focus);
		addMessage(redirectAttributes, "删除关注成功");
		return "redirect:"+Global.getAdminPath()+"/cms/focus/?repage";
	}

	@RequestMapping(value = "app/save")
	public void saveApp(Focus focus,HttpServletResponse response) {
		JSONObject j = new JSONObject();
		if(focus!=null && focus.getFan()==null){
			focus.setFan(focus.getCreateBy());
		}
		focus.setCreateBy(UserUtils.getUser());
		List<Focus> list=  focusService.findListUn(focus);
		if(list!=null&&list.size()>0){
			for(Focus fo:list){
				fo.setDelFlag(focus.getDelFlag());
				focusService.updateFocus(fo);
			}
			if("1".equals(focus.getDelFlag())){
				j.put("status","success");
				j.put("msg","取消关注成功");
			}else {
				j.put("status","success");
				j.put("msg","关注成功");
			}
		}else {
			focusService.save(focus);
			j.put("status","success");
			j.put("msg","关注成功");
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException g) {

		}

	}

	@RequestMapping(value = "app/delete")
	public void deleteApp(Focus focus, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		focusService.delete(focus);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg","取消关注成功");
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException g) {

		}
	}
	//我关注的
	@RequestMapping(value = "app/list")
	public void listApp(Focus focus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Focus> page = focusService.findPage(new Page<Focus>(request, response), focus);
		model.addAttribute("page", page);
	}

}