package com.bra.modules.cms.web;

import com.alibaba.fastjson.JSONObject;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.cms.entity.Apply;
import com.bra.modules.cms.service.ActivityService;
import com.bra.modules.cms.service.ApplyService;
import com.bra.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
import java.net.URLDecoder;

/**
 * 活动报名Controller
 * @author ddt
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/apply")
public class ApplyController extends BaseController {

	@Autowired
	private ApplyService applyService;
	@Autowired
	private ActivityService activityService;
	
	@ModelAttribute
	public Apply get(@RequestParam(required=false) String id) {
		Apply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = applyService.get(id);
		}
		if (entity == null){
			entity = new Apply();
		}
		return entity;
	}
	
	@RequiresPermissions("cms:apply:view")
	@RequestMapping(value = {"list", ""})
	public String list(Apply apply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Apply> page = applyService.findPage(new Page<Apply>(request, response), apply); 
		model.addAttribute("page", page);
		return "modules/cms/applyList";
	}

	@RequiresPermissions("cms:apply:view")
	@RequestMapping(value = "form")
	public String form(Apply apply, Model model)throws Exception {
		model.addAttribute("apply", apply);
		return "modules/cms/applyForm";
	}

	@RequiresPermissions("cms:apply:edit")
	@RequestMapping(value = "save")
	public String save(Apply apply, Model model, RedirectAttributes redirectAttributes)throws Exception {
		if (!beanValidator(model, apply)){
			return form(apply, model);
		}
		apply.setUser(UserUtils.getUser());
		String data = StringEscapeUtils.unescapeHtml4(
				URLDecoder.decode(apply.getData(),"UTF-8"));
		JSONObject j = new JSONObject();
		String[] o = data.split("&");
		for(String tt:o){
			String[] kk = tt.split("=");
			if(kk.length>1)
				j.put(kk[0],kk[1]);
			else
				j.put(kk[0],"");
		}
		apply.setData(j.toJSONString());
		applyService.save(apply);
		addMessage(redirectAttributes, "保存活动报名成功");
		return "redirect:"+Global.getAdminPath()+"/cms/apply/?repage";
	}
	
	@RequiresPermissions("cms:apply:edit")
	@RequestMapping(value = "delete")
	public String delete(Apply apply, RedirectAttributes redirectAttributes) {
		applyService.delete(apply);
		addMessage(redirectAttributes, "删除活动报名成功");
		return "redirect:"+Global.getAdminPath()+"/cms/apply/?repage";
	}

	@RequiresPermissions("cms:apply:edit")
	@RequestMapping(value = "export")
	public void export(Apply apply, HttpServletResponse response) throws Exception{
//		String activityId = apply.getActivityId();
//		Activity ac = activityService.get(activityId);
//		List<Apply> list = applyService.findList(apply);
//		Set<String> keys = new HashSet<>();
//		List<String[]> contentList = new ArrayList<>();
//		if(list!=null&&list.size()>0){
//			String j = list.get(0).getData();
//			JSONObject jo = JSONObject.parseObject(j);
//			keys = jo.keySet();
//			titles = (String[])keys.toArray();
//			for(Apply tmp:list){
//				String json = tmp.getRemarks();
//				JSONObject jojo = JSONObject.parseObject(json);
//				List<String> content = new ArrayList<>();
//				for(int i = 0 ; i < titles.length ; i++){
//					content.add(String.valueOf(jojo.get(titles[i])));
//			}
//				contentList.add((String[])content.toArray());
//			}
//		}
//		Date now = new Date();
//		ExcelInfo info = new ExcelInfo(response,ac.getSubject()+"报名记录"+ DateUtils.formatDate(now),titles,contentList);
		//info.export();
	}

}