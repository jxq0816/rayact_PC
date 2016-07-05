package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveMenu;
import com.bra.modules.reserve.service.ReserveMenuService;
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
 * 菜单配置Controller
 * @author jiangxingqi
 * @version 2016-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveMenu")
public class ReserveMenuController extends BaseController {

	@Autowired
	private ReserveMenuService reserveMenuService;

	@ModelAttribute
	public ReserveMenu get(@RequestParam(required=false) String id) {
		ReserveMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveMenuService.get(id);
		}
		if (entity == null){
			entity = new ReserveMenu();
		}
		return entity;
	}

	/*@RequiresPermissions("reserve:reserveMenu:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(ReserveMenu reserveMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveMenu> page = reserveMenuService.findPage(new Page<ReserveMenu>(request, response), reserveMenu);
		model.addAttribute("page", page);
		return "reserve/menu/reserveMenuList";
	}

	/*@RequiresPermissions("reserve:reserveMenu:view")*/
	@RequestMapping(value = "form")
	public String form(ReserveMenu reserveMenu, Model model) {
		model.addAttribute("reserveMenu", reserveMenu);
		List<ReserveMenu> menuList=reserveMenuService.findList(new ReserveMenu());
		model.addAttribute("menuList",menuList);
		return "reserve/menu/reserveMenuForm";
	}

	/*@RequiresPermissions("reserve:reserveMenu:edit")*/
	@RequestMapping(value = "save")
	public String save(ReserveMenu reserveMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveMenu)){
			return form(reserveMenu, model);
		}
		reserveMenuService.save(reserveMenu);
		addMessage(redirectAttributes, "保存菜单成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMenu/?repage";
	}

	/*@RequiresPermissions("reserve:reserveMenu:edit")*/
	@RequestMapping(value = "delete")
	public String delete(ReserveMenu reserveMenu, RedirectAttributes redirectAttributes) {
		reserveMenuService.delete(reserveMenu);
		addMessage(redirectAttributes, "删除菜单成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMenu/?repage";
	}

}