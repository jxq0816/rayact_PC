package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.MD5Util;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.ReserveRoleAuth;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveRoleAuthService;
import com.bra.modules.reserve.service.ReserveUserService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
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
import java.util.List;

/**
 * Created by xiaobin on 16/1/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/user")
public class ReserveUserController extends BaseController {
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveUserService reserveUserService;
    @Autowired
    private ReserveRoleAuthService reserveRoleAuthService;
    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return reserveUserService.getUser(id);
        } else {
            return new User();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = reserveUserService.findUser(new Page<>(request, response), user);
        model.addAttribute("page", page);
        return "reserve/user/list";
    }

    @ResponseBody
    @RequestMapping(value = {"listData"})
    public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = reserveUserService.findUser(new Page<>(request, response), user);
        return page;
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        model.addAttribute("user", user);
        if (StringUtils.isNotBlank(user.getId())) {
            model.addAttribute("userRole", reserveUserService.getRole(user));
        }
        List<ReserveRoleAuth> roleAuthList = reserveRoleAuthService.findList(new ReserveRoleAuth());
        model.addAttribute("roleAuthList", roleAuthList);
        model.addAttribute("venueList", reserveVenueService.findList(new ReserveVenue()));
        model.addAttribute("authList", AuthorityUtils.getAll());
        return "reserve/user/form";
    }

    //验证原始密码 是否正确
    @RequestMapping(value = "checkPassword")
    @ResponseBody
    public String checkPassword(String id, String oldPassword) {
        User user = reserveUserService.get(id);
        String pswdMD5FromBack = user.getPassword();
        oldPassword=MD5Util.getMD5String(oldPassword);
        String rs = null;
        if (pswdMD5FromBack.equals(oldPassword)) {
            rs = "1";//原始密码正确
        } else {
            rs = "0";//原始密码不正确
        }
        return rs;
    }

    @RequestMapping(value = "updatePasswordForm")
    @Token(save = true)
    public String updatePassword(Model model) {
        User user = this.infoData();
        model.addAttribute("user", user);
        return "reserve/user/updatePasswordForm";
    }


    @RequestMapping(value = "updatePasswordSubmit")
    @Token(remove = true)
    public String updateSubmit(User user, RedirectAttributes redirectAttributes) {
        // 保存用户信息
        user.setPassword(MD5Util.getMD5String(user.getPassword()));
        reserveUserService.saveUser(user);
        // 清除当前用户缓存
        UserUtils.clearCache();
        addMessage(redirectAttributes, "密码修改成功");
        return "redirect:" + adminPath + "/logout";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(User user, HttpServletRequest request, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(UserUtils.getUser().getCompany());
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(MD5Util.getMD5String(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 保存用户信息
        reserveUserService.saveUser(user);
         reserveUserService.updateAttMain(user,attMainForm);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/reserve/user/list?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {
            reserveUserService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }
        return "redirect:" + adminPath + "/reserve/user/list?repage";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && reserveUserService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 用户信息显示及保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    @Token(remove = true)
    public String info(User user, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getName())) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userInfo";
            }
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setMobile(user.getMobile());
            currentUser.setRemarks(user.getRemarks());
            currentUser.setPhoto(user.getPhoto());
            reserveUserService.updateUserInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new Global());
        return "modules/sys/userInfo";
    }

    /**
     * 返回用户信息
     *
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
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @Token(remove = true)
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userModifyPwd";
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                reserveUserService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                model.addAttribute("message", "修改密码成功");
            } else {
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd";
    }

}
