package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveMenu;
import com.bra.modules.reserve.entity.ReserveRoleAuth;
import com.bra.modules.reserve.entity.json.Authority;
import com.bra.modules.reserve.service.ReserveMenuService;
import com.bra.modules.reserve.service.ReserveRoleAuthService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色权限管理Controller
 * @author jiangxingqi
 * @version 2016-06-28
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveRoleAuth")
public class ReserveRoleAuthController extends BaseController {
	@Autowired
	private ReserveMenuService reserveMenuService;

	@Autowired
	private ReserveRoleAuthService reserveRoleAuthService;
	
	@ModelAttribute
	public ReserveRoleAuth get(@RequestParam(required=false) String id) {
		ReserveRoleAuth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveRoleAuthService.get(id);
		}
		if (entity == null){
			entity = new ReserveRoleAuth();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveRoleAuth reserveRoleAuth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveRoleAuth> page = reserveRoleAuthService.findPage(new Page<ReserveRoleAuth>(request, response), reserveRoleAuth); 
		model.addAttribute("page", page);
		return "reserve/role/reserveRoleAuthList";
	}

	@RequestMapping(value = "form")
	public String form(ReserveRoleAuth reserveRoleAuth, Model model) {
		List<Authority> authorities = AuthorityUtils.findByAuths(reserveRoleAuth.getAuthorityList());
		reserveRoleAuth.setAuthorityList(authorities);
		model.addAttribute("reserveRoleAuth", reserveRoleAuth);
		model.addAttribute("allAuthList", AuthorityUtils.getAll());
		List<ReserveMenu> menuList = reserveMenuService.findList(new ReserveMenu());
		model.addAttribute("menuList", menuList);
		return "reserve/role/reserveRoleAuthForm";
	}
	@RequestMapping(value = "menuForm")
	public String menuform(ReserveRoleAuth reserveRoleAuth, Model model) {
		List<Authority> authorities = AuthorityUtils.findByAuths(reserveRoleAuth.getAuthorityList());
		reserveRoleAuth.setAuthorityList(authorities);
		model.addAttribute("reserveRoleAuth", reserveRoleAuth);
		model.addAttribute("allAuthList", AuthorityUtils.getAll());
		List<ReserveMenu> menuList = reserveMenuService.findList(new ReserveMenu());
		model.addAttribute("menuList", menuList);
		return "reserve/role/reserveRoleMenuForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveRoleAuth reserveRoleAuth, Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, reserveRoleAuth)){
			return form(reserveRoleAuth, model);
		}
		reserveRoleAuth.setAuthorityList(reserveRoleAuth.getFrontAuthorityList());
		reserveRoleAuthService.save(reserveRoleAuth);
		addMessage(redirectAttributes, "保存角色权限成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveRoleAuth/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveRoleAuth reserveRoleAuth, RedirectAttributes redirectAttributes) {
		reserveRoleAuthService.delete(reserveRoleAuth);
		addMessage(redirectAttributes, "删除角色权限成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveRoleAuth/?repage";
	}

}