package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Attitude;
import com.bra.modules.cms.service.AttitudeService;
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

/**
 * 点赞Controller
 * @author ddt
 * @version 2016-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/attitude")
public class AttitudeController extends BaseController {

	@Autowired
	private AttitudeService attitudeService;
	
	@ModelAttribute
	public Attitude get(@RequestParam(required=false) String id) {
		Attitude entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attitudeService.get(id);
		}
		if (entity == null){
			entity = new Attitude();
		}
		return entity;
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"list", ""})
	public String list(Attitude attitude, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Attitude> page = attitudeService.findPage(new Page<Attitude>(request, response), attitude); 
		model.addAttribute("page", page);
		return "modules/cms/attitudeList";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "form")
	public String form(Attitude attitude, Model model) {
		model.addAttribute("attitude", attitude);
		return "modules/cms/attitudeForm";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "save")
	public String save(Attitude attitude, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attitude)){
			return form(attitude, model);
		}
		attitudeService.save(attitude);
		addMessage(redirectAttributes, "保存点赞成功");
		return "redirect:"+Global.getAdminPath()+"/cms/attitude/?repage";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	public String delete(Attitude attitude, RedirectAttributes redirectAttributes) {
		attitudeService.delete(attitude);
		addMessage(redirectAttributes, "删除点赞成功");
		return "redirect:"+Global.getAdminPath()+"/cms/attitude/?repage";
	}

	@RequestMapping(value = "app/num")
	@ResponseBody
	public String count(Attitude attitude, HttpServletResponse response)throws Exception {
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(attitudeService.getCount(attitude));
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@RequestMapping(value = "app/save")
	public String saveApp(Attitude attitude, Model model, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		if (!beanValidator(model, attitude)){
			return form(attitude, model);
		}
		attitudeService.save(attitude);
		addMessage(redirectAttributes, "保存点赞成功");
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg",attitude.getId());
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	@RequestMapping(value = "app/delete")
	public String deleteApp(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		Attitude del = attitudeService.get(id);
		attitudeService.delete(del);
		JSONObject j = new JSONObject();
		j.put("status","success");
		j.put("msg",del.getId());
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
			return null;
		} catch (IOException e) {
			return null;
		}
	}

}