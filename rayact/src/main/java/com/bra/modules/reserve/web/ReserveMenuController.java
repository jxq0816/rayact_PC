package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveMenu;
import com.bra.modules.reserve.service.ReserveMenuService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.List;
import java.util.Map;

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
		List<ReserveMenu> list = Lists.newArrayList();
		List<ReserveMenu> sourcelist = reserveMenuService.findList(reserveMenu);
		ReserveMenu.sortList(list, sourcelist, reserveMenu.getRootId(), true);
		model.addAttribute("list", list);
		return "reserve/menu/reserveMenuList";
	}

	/*@RequiresPermissions("reserve:reserveMenu:view")*/
	@RequestMapping(value = "form")
	public String form(ReserveMenu reserveMenu, Model model) {
		model.addAttribute("reserveMenu", reserveMenu);
		if (reserveMenu.getParent()==null||reserveMenu.getParent().getId()==null){
			reserveMenu.setParent(new ReserveMenu(ReserveMenu.getRootId()));
		}
		reserveMenu.setParent(reserveMenuService.get(reserveMenu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(reserveMenu.getId())){
			List<ReserveMenu> list = Lists.newArrayList();
			List<ReserveMenu> sourcelist = reserveMenuService.findList(new ReserveMenu());
			ReserveMenu.sortList(list, sourcelist, reserveMenu.getParentId(), false);
			if (list.size() > 0){
				reserveMenu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("reserveMenu",reserveMenu);
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

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ReserveMenu> list = reserveMenuService.findList(new ReserveMenu());
		for (int i=0; i<list.size(); i++){
			ReserveMenu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < ids.length; i++) {
			ReserveMenu menu = new ReserveMenu(ids[i]);
			menu.setSort(sorts[i]);
			reserveMenuService.updateMenuSort(menu);
		}
		addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:" + adminPath + "/reserve/reserveMenu";
	}

}