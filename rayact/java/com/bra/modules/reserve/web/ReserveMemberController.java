package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.ReserveMemberService;

/**
 * 会员管理Controller
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveMember")
public class ReserveMemberController extends BaseController {

	@Autowired
	private ReserveMemberService reserveMemberService;
	
	@ModelAttribute
	public ReserveMember get(@RequestParam(required=false) String id) {
		ReserveMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveMemberService.get(id);
		}
		if (entity == null){
			entity = new ReserveMember();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveMember reserveMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveMember> page = reserveMemberService.findPage(new Page<ReserveMember>(request, response), reserveMember); 
		model.addAttribute("page", page);
		return "reserve/member/list";
	}

	@RequiresPermissions("reserve:reserveMember:view")
	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveMember reserveMember, Model model) {
		model.addAttribute("reserveMember", reserveMember);
		return "reserve/member/form";
	}

	@RequiresPermissions("reserve:reserveMember:edit")
	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveMember reserveMember, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveMember)){
			return form(reserveMember, model);
		}

		reserveMemberService.save(reserveMember);
		addMessage(redirectAttributes, "保存会员成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMember/?repage";
	}
	
	@RequiresPermissions("reserve:reserveMember:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveMember reserveMember, RedirectAttributes redirectAttributes) {
		reserveMemberService.delete(reserveMember);
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMember/?repage";
	}

	@RequestMapping(value = "loadMember")
	@ResponseBody
	public ReserveMember loadMember(String id){
		return reserveMemberService.get(id);
	}

}