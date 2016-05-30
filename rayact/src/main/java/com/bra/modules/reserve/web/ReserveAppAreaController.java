/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.bra.common.web.BaseController;
import com.bra.modules.sys.entity.Area;
import com.bra.modules.sys.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 区域Controller
 *
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/app/reserve/area")
public class ReserveAppAreaController extends BaseController {

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public String list(Area area) {
		List<Map> list = areaService.findListForAPP(area);
		String rtn=JSON.toJSONString(list);
		return rtn;
	}
}
