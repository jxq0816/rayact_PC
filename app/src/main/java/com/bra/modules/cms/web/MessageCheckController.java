package com.bra.modules.cms.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.MessageCheck;
import com.bra.modules.cms.service.MessageCheckService;
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

/**
 * 消息查收Controller
 * @author ddt
 * @version 2016-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/messageCheck")
public class MessageCheckController extends BaseController {

	@Autowired
	private MessageCheckService messageCheckService;
	
	@ModelAttribute
	public MessageCheck get(@RequestParam(required=false) String id) {
		MessageCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageCheckService.get(id);
		}
		if (entity == null){
			entity = new MessageCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:messageCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageCheck messageCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageCheck> page = messageCheckService.findPage(new Page<MessageCheck>(request, response), messageCheck); 
		model.addAttribute("page", page);
		return "modules/cms/messageCheckList";
	}

	@RequiresPermissions("cms:messageCheck:view")
	@RequestMapping(value = "form")
	public String form(MessageCheck messageCheck, Model model) {
		model.addAttribute("messageCheck", messageCheck);
		return "modules/cms/messageCheckForm";
	}

	@RequiresPermissions("cms:messageCheck:edit")
	@RequestMapping(value = "save")
	public String save(MessageCheck messageCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageCheck)){
			return form(messageCheck, model);
		}
		messageCheckService.save(messageCheck);
		addMessage(redirectAttributes, "保存消息查收成功");
		return "redirect:"+Global.getAdminPath()+"/cms/messageCheck/?repage";
	}
	
	@RequiresPermissions("cms:messageCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageCheck messageCheck, RedirectAttributes redirectAttributes) {
		messageCheckService.delete(messageCheck);
		addMessage(redirectAttributes, "删除消息查收成功");
		return "redirect:"+Global.getAdminPath()+"/cms/messageCheck/?repage";
	}

}