package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveCommodityStorageLog;
import com.bra.modules.reserve.entity.ReserveCommoditySupplier;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveCommodityStorageLogService;
import com.bra.modules.reserve.service.ReserveCommoditySupplierService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.ExcelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 商品入库记录Controller
 * @author jiangxingqi
 * @version 2016-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCommodityStorageLog")
public class ReserveCommodityStorageLogController extends BaseController {

	@Autowired
	private ReserveCommodityStorageLogService reserveCommodityStorageLogService;
	@Autowired
	private ReserveVenueService reserveVenueService;
	@Autowired
	private ReserveCommoditySupplierService reserveCommoditySupplierService;
	@ModelAttribute
	public ReserveCommodityStorageLog get(@RequestParam(required=false) String id) {
		ReserveCommodityStorageLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCommodityStorageLogService.get(id);
		}
		if (entity == null){
			entity = new ReserveCommodityStorageLog();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCommodityStorageLog reserveCommodityStorageLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCommodityStorageLog> page = reserveCommodityStorageLogService.findPage(new Page<>(request, response), reserveCommodityStorageLog);
		List<ReserveCommoditySupplier> reserveCommoditySupplierList=reserveCommoditySupplierService.findList(new ReserveCommoditySupplier());
		model.addAttribute("reserveCommoditySupplierList", reserveCommoditySupplierList);
		model.addAttribute("page", page);
		model.addAttribute("query", reserveCommodityStorageLog);
		model.addAttribute("venues", reserveVenueService.findList(new ReserveVenue()));
		return "reserve/record/reserveCommodityStorageLogList";
	}

	@RequestMapping(value = {"listExport", ""})
	public void listExport(ReserveCommodityStorageLog reserveCommodityStorageLog,HttpServletResponse response){
		List<ReserveCommodityStorageLog> list = reserveCommodityStorageLogService.findList(reserveCommodityStorageLog);
		String[] titles = {"场馆","商品","入库箱数","入库量","入库前库存量","入库后库存量","入库单箱价格","入库单价","入库金额","操作人","备注","供应商","时间"};
		List<String[]> contentList = new ArrayList<>();
		for(ReserveCommodityStorageLog log :list){
			String[] o = new String[13];
			o[0] = log.getReserveVenue().getName();
			o[1] = log.getReserveCommodity().getName();
			o[2] = log.getNum().toString();
			o[3] = log.getBoxNum().toString();
			o[4] = log.getBeforeNum().toString();
			o[5] = log.getAfterNum().toString();
			o[6] = log.getBoxPrice().toString();
			o[7] = log.getPrice().toString();
			o[8] = String.valueOf(log.getBoxPrice()*log.getBoxNum());
			o[9] = log.getCreateBy().getName();
			o[10]=log.getRemarks();
			if(log.getReserveCommoditySupplier()!=null){
				o[11]=log.getReserveCommoditySupplier().getName();
			}else{
				o[11]="";
			}
			o[12] = String.valueOf(DateUtils.formatDateTime(log.getCreateDate()));
			contentList.add(o);
		}
		Date now = new Date();
		try {
			ExcelInfo info = new ExcelInfo(response,"商品入库记录 导出时间："+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "form")
	public String form(ReserveCommodityStorageLog reserveCommodityStorageLog, Model model) {
		model.addAttribute("reserveCommodityStorageLog", reserveCommodityStorageLog);
		return "reserve/record/reserveCommodityStorageLogForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveCommodityStorageLog reserveCommodityStorageLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveCommodityStorageLog)){
			return form(reserveCommodityStorageLog, model);
		}
		reserveCommodityStorageLogService.save(reserveCommodityStorageLog);
		addMessage(redirectAttributes, "保存商品入库记录成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCommodityStorageLog/?repage";
	}

}