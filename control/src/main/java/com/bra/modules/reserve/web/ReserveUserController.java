package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.MD5Util;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveOffice;
import com.bra.modules.reserve.entity.ReserveUser;
import com.bra.modules.reserve.service.ReserveOfficeService;
import com.bra.modules.reserve.service.ReserveUserService;
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
import java.util.List;

/**
 * 商家用户Controller
 * @author jiangxingqi
 * @version 2016-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveUser")
public class ReserveUserController extends BaseController {

	@Autowired
	private ReserveUserService reserveUserService;
	@Autowired
	private ReserveOfficeService reserveOfficeService;
	@ModelAttribute
	public ReserveUser get(@RequestParam(required=false) String id) {
		ReserveUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveUserService.get(id);
		}
		if (entity == null){
			entity = new ReserveUser();
		}
		return entity;
	}
	
	@RequiresPermissions("reserve:reserveUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReserveUser reserveUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveUser> page = reserveUserService.findPage(new Page<ReserveUser>(request, response), reserveUser); 
		model.addAttribute("page", page);
		return "reserve/user/reserveUserList";
	}

	@RequiresPermissions("reserve:reserveUser:view")
	@RequestMapping(value = "form")
	public String form(ReserveUser reserveUser, Model model) {
		List<ReserveOffice> reserveOfficeList = reserveOfficeService.findList(new ReserveOffice());
		model.addAttribute("reserveUser", reserveUser);
		model.addAttribute("reserveOfficeList", reserveOfficeList);
		return "reserve/user/reserveUserForm";
	}

	@RequiresPermissions("reserve:reserveUser:edit")
	@RequestMapping(value = "save")
	public String save(ReserveUser reserveUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveUser)){
			return form(reserveUser, model);
		}
		if (StringUtils.isEmpty(reserveUser.getId())) {//用户注册
			reserveUser.setPassword(MD5Util.getMD5String(reserveUser.getPassword()));
		}else if(StringUtils.isNotEmpty(reserveUser.getPassword())){//用户修改密码
			reserveUser.setPassword(MD5Util.getMD5String(reserveUser.getPassword()));
		}else{
			reserveUser.setPassword(reserveUserService.get(reserveUser).getPassword());//用户修改资料，但是没有密码
		}
		reserveUserService.save(reserveUser);
		addMessage(redirectAttributes, "保存商家用户成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveUser/?repage";
	}
	
	@RequiresPermissions("reserve:reserveUser:edit")
	@RequestMapping(value = "delete")
	public String delete(ReserveUser reserveUser, RedirectAttributes redirectAttributes) {
		reserveUserService.delete(reserveUser);
		addMessage(redirectAttributes, "删除商家用户成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveUser/?repage";
	}

}