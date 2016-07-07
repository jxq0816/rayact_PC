/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Attitude;
import com.bra.modules.cms.entity.Comment;
import com.bra.modules.cms.service.CommentService;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.DictUtils;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 评论Controller
 *
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public Comment get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return commentService.get(id);
		}else{
			return new Comment();
		}
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "form")
	public String form(Attitude attitude, Model model) {
		model.addAttribute("attitude", attitude);
		return "modules/cms/attitudeForm";
	}

	@RequiresPermissions("cms:comment:view")
	@RequestMapping(value = {"list", ""})
	public String list(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment); 
        model.addAttribute("page", page);
		return "modules/cms/commentList";
	}

	@RequiresPermissions("cms:comment:edit")
	@RequestMapping(value = "save")
	public String save(Comment comment, RedirectAttributes redirectAttributes) {
		if (beanValidator(redirectAttributes, comment)){
			if (comment.getAuditUser() == null){
				comment.setAuditUser(UserUtils.getUser());
				comment.setAuditDate(new Date());
			}
			comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
			commentService.save(comment);
			addMessage(redirectAttributes, DictUtils.getDictLabel(comment.getDelFlag(), "cms_del_flag", "保存")
					+"评论'" + StringUtils.abbr(StringUtils.replaceHtml(comment.getContent()),50) + "'成功");
		}
		return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
	}
	
	@RequiresPermissions("cms:comment:edit")
	@RequestMapping(value = "delete")
	public String delete(Comment comment, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		commentService.delete(comment, isRe);
		addMessage(redirectAttributes, (isRe!=null&&isRe?"恢复审核":"删除")+"评论成功");
		return "redirect:" + adminPath + "/cms/comment/?repage&delFlag=2";
	}

	@RequestMapping(value = "app/list")
	public void listApp(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user!=null&&user.getId()!=null){
			comment.setCurrentUser(user);
		}else{
			user = new User();
			user.setId("*******");
			comment.setCurrentUser(user);
		}
		List<Map<String,String>> rtn = commentService.findListMap(new Page<Comment>(request, response), comment);
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(JSONArray.toJSONString(rtn, SerializerFeature.WriteMapNullValue));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "app/save")
	public void saveApp(Comment comment, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		JSONObject j = new JSONObject();
		String replyId = request.getParameter("replyId");
		try {
			if (beanValidator(redirectAttributes, comment)) {
				if (comment.getAuditUser() == null) {
					comment.setAuditUser(UserUtils.getUser());
					comment.setAuditDate(new Date());
				}
				if (StringUtils.isNotBlank(replyId)){
					Comment replyComment = commentService.get(replyId);
					if (replyComment != null){
						comment.setContent("@"+replyComment.getName()+":"
								+comment.getContent());
					}
				}
				comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
				commentService.save(comment);
				addMessage(redirectAttributes, DictUtils.getDictLabel(comment.getDelFlag(), "cms_del_flag", "保存")
						+ "评论'" + StringUtils.abbr(StringUtils.replaceHtml(comment.getContent()), 50) + "'成功");
				j.put("status","success");
				j.put("msg",comment.getId());
			}
		}catch (Exception e){
			j.put("status","failed");
			j.put("msg",e.getMessage());
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());

		} catch (IOException g) {

		}

	}

	@RequestMapping(value = "app/delete")
	public String deleteApp(HttpServletRequest request,HttpServletResponse response) {
		JSONObject j = new JSONObject();
		try {
			String id = request.getParameter("id");
			Comment d = commentService.get(id);
			if(d.getAuditUser().getId().equals(UserUtils.getUser().getId())){
				commentService.delete(d);
				j.put("status","success");
				j.put("msg",id);
			}else{
				throw new Exception("非本人评论不可删除");
			}
		}catch (Exception e){
			j.put("status","failed");
			j.put("msg",e.getMessage());
		}
		try {
			response.reset();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j.toJSONString());
			return null;
		} catch (IOException g) {
			return null;
		}

	}

	@RequiresPermissions("cms:comment:edit")
	@RequestMapping(value = "deleteAll")
	@ResponseBody
	public String deleteAll(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String[] ida = request.getParameterValues("ids[]");
		String del = " id in (";
		if(ida!=null&&ida.length>0){
			for(int i =0;i<ida.length;i++){
				if(i<ida.length-1)
					del+="'"+ida[i]+"',";
				else
					del+="'"+ida[i]+"')";
			}
		}
		Comment a = new Comment();
		a.getSqlMap().put("del",del);
		commentService.deleteAll(a);
		return "删除成功";
	}

}
