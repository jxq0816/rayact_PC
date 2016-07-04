package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Message;
import com.bra.modules.cms.entity.MessageCheck;
import com.bra.modules.cms.service.MessageCheckService;
import com.bra.modules.cms.service.MessageService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息Controller
 * @author ddt
 * @version 2016-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/message")
public class MessageController extends BaseController {
	@Autowired
    private MessageCheckService messageCheckService;
	@Autowired
	private MessageService messageService;
	
	@ModelAttribute
	public Message get(@RequestParam(required=false) String id) {
		Message entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageService.get(id);
		}
		if (entity == null){
			entity = new Message();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:message:view")
	@RequestMapping(value = {"list", ""})
	public String list(Message message, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Message> page = messageService.findPage(new Page<Message>(request, response), message); 
		model.addAttribute("page", page);
		return "modules/cms/messageList";
	}

	@RequiresPermissions("cms:message:view")
	@RequestMapping(value = "form")
	public String form(Message message, Model model) {
		model.addAttribute("message", message);
		return "modules/cms/messageForm";
	}

	@RequiresPermissions("cms:message:edit")
	@RequestMapping(value = "save")
	public String save(Message message, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, message)){
			return form(message, model);
		}
		messageService.save(message);
		addMessage(redirectAttributes, "保存消息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/message/?repage";
	}
	
	@RequiresPermissions("cms:message:edit")
	@RequestMapping(value = "delete")
	public String delete(Message message, RedirectAttributes redirectAttributes) {
		messageService.delete(message);
		addMessage(redirectAttributes, "删除消息成功");
		return "redirect:"+Global.getAdminPath()+"/cms/message/?repage";
	}

	@RequestMapping(value = "app/list")
	public void listApp(Message message, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		List<Map<String,String>> rtn = new ArrayList<>();
		if(user!=null && user.getId()!=null){
			message.setTargetId(user.getId());
			rtn = messageService.findMapList(new Page<Message>(request, response), message);
			MessageCheck mcf = null;
			if(rtn!=null && rtn.size()>0){
				MessageCheck mc = new MessageCheck();
				mc.setCreateBy(user);
				List<MessageCheck> list = messageCheckService.findList(mc);
				if (list != null && list.size() > 0) {
					mcf = list.get(0);
				} else {
					mcf = new MessageCheck();
				}
				for (Map<String, String> m : rtn) {
					if (mcf.getUpdateDate() != null) {
						String date = m.get("createDate");
						Date d = DateUtils.parseDate(date);
						if (d.before(mcf.getUpdateDate())) {
							m.put("isNew", "false");
						} else {
							m.put("isNew", "true");
						}
					} else {
						m.put("isNew", "true");
					}
				}
				messageCheckService.save(mcf);
			}
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "app/hasNew")
	public void hasNew(Message message, HttpServletRequest request, HttpServletResponse response, Model model) {
		String flag = "false";
		User user = UserUtils.getUser();
		List<Map<String,String>> rtn = new ArrayList<>();
		if(user!=null && user.getId()!=null){
			message.setTargetId(user.getId());
			rtn = messageService.findMapList(new Page<Message>(request, response), message);
			MessageCheck mcf = null;
			if(rtn!=null && rtn.size()>0){
				MessageCheck mc = new MessageCheck();
				mc.setCreateBy(user);
				List<MessageCheck> list = messageCheckService.findList(mc);
				if (list != null && list.size() > 0) {
					mcf = list.get(0);
				} else {
					flag = "true";
				}
				for (Map<String, String> m : rtn) {
					if (mcf!=null && mcf.getUpdateDate() != null) {
						String date = m.get("createDate");
						Date d = DateUtils.parseDate(date);
						if (d.after(mcf.getUpdateDate())) {
							flag = "true";
						}
					}
				}
			}
		}
		JSONObject j = new JSONObject();
		j.put("hasNew",flag);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
		} catch (IOException e) {
		}
	}

}