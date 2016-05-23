package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.service.ReserveProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理Controller
 * @author jiangxingqi
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/reserveProject")
public class ReserveAppProjectController extends BaseController {

	@Autowired
	private ReserveProjectService reserveProjectService;
	

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public String appList(ReserveProject reserveProject) {
		List<Map> list = new ArrayList<>();
		Map map=new HashMap<>();
		map.put("projectId","");
		map.put("projectName","全部");
		list.add(map);
		list.addAll(reserveProjectService.findListForApp(reserveProject));
		String projectList= JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
		return projectList;
	}
}