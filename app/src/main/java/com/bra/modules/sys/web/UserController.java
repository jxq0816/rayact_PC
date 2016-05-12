/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.MD5Util;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.sys.entity.Office;
import com.bra.modules.sys.entity.Role;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(MD5Util.getMD5String(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 更改个人资料
	 */
	@RequestMapping(value = "api/updateInfo", method = RequestMethod.POST)
	@ResponseBody
	public String updateInfo(HttpServletRequest request, HttpServletResponse response) {
		JSONObject rtn = new JSONObject();
		try{
			boolean flag = true;
			String photo = request.getParameter("photo");
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String area = request.getParameter("area");
			String phone = request.getParameter("phone");
			String qq = request.getParameter("qq");
			String weixin = request.getParameter("weixin");
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String qqName = request.getParameter("qqName");
			String weixinName = request.getParameter("weixinName");
			User user = UserUtils.getUser();
			if(user!=null){
				if(!com.bra.modules.sys.utils.StringUtils.isNull(photo)){
					user.setPhoto(photo);
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(sex)){
					user.setSex(sex);
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(qq)){
					User tmp = systemService.getUserByQq(qq);
					User now = UserUtils.getUser();
					if(tmp!=null&&now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","已经绑定");
					}else if(tmp!=null&&!now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","该QQ号已经注册");
					}else{
						user.setQq(qq);
						user.setQqName(qqName);
					}
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(area)){
					user.setArea(area);
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(phone)){
					User tmp = systemService.getUserByMobile(phone);
					User now = UserUtils.getUser();
					if(tmp!=null&&now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","已经绑定");
					}else if(tmp!=null&&!now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","该手机号已经注册");
					}else{
						user.setMobile(phone);
						user.setLoginName(phone);
					}
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(weixin)){
					User tmp = systemService.getUserByWeixin(weixin);
					User now = UserUtils.getUser();
					if(tmp!=null&&now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","已经绑定");
					}else if(tmp!=null&&!now.getId().equals(tmp.getId())){
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","该微信号已经注册");
					}else{
						user.setWeixin(weixin);
						user.setWeixinName(weixinName);
					}
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(name)){
					user.setName(name);
				}
				if(!com.bra.modules.sys.utils.StringUtils.isNull(oldPassword)&&!com.bra.modules.sys.utils.StringUtils.isNull(newPassword)){
					User tmp = new User();
					tmp.setLoginName(UserUtils.getUser().getLoginName());
					tmp.setPassword(com.bra.modules.sys.utils.StringUtils.entryptPassword(oldPassword));
					List<User> users = systemService.findListApi(tmp);
					if(users!=null&&users.size()>0){
						user.setPassword(newPassword);
					}else{
						flag = false;
						rtn.put("status","fail");
						rtn.put("msg","原密码输入有误");
					}
				}
			}
			if(flag){
				systemService.saveUser(user);
				rtn.put("status","success");
				rtn.put("msg","操作成功");
			}
		}catch(Exception e){
			rtn.put("status","fail");
			rtn.put("msg","操作失败");
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(rtn.toJSONString());
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "api/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		JSONObject rtn = new JSONObject();
		User user = UserUtils.getUser();
		rtn.put("userImage",user.getPhoto()==null?"":user.getPhoto());
		rtn.put("name",user.getName()==null?"":user.getName());
		rtn.put("sex",user.getSex()==null?"":user.getSex());
		rtn.put("area",user.getArea()==null?"":user.getArea());
		rtn.put("phone",user.getMobile()==null?"":user.getMobile());
		rtn.put("qqName",user.getQqName()==null?"":user.getQqName());
		rtn.put("weixinName",user.getWeixinName()==null?"":user.getWeixinName());
		rtn.put("qq",user.getQq()==null?"":user.getQq());
		rtn.put("weixin",user.getWeixin()==null?"":user.getWeixin());
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(rtn.toJSONString());
			return null;
		} catch (IOException e) {
			return null;
		}
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
