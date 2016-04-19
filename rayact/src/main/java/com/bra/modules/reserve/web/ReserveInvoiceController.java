package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.reserve.entity.ReserveInvoice;
import com.bra.modules.reserve.service.ReserveInvoiceService;
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
 * 发票管理Controller
 * @author xudl
 * @version 2016-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveInvoice")
public class ReserveInvoiceController extends BaseController {

	@Autowired
	private ReserveInvoiceService reserveInvoiceService;
	
	@ModelAttribute
	public ReserveInvoice get(@RequestParam(required=false) String id) {
		ReserveInvoice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveInvoiceService.get(id);
		}
		if (entity == null){
			entity = new ReserveInvoice();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list(ReserveInvoice reserveInvoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveInvoice> page = reserveInvoiceService.findPage(new Page<ReserveInvoice>(request, response), reserveInvoice); 
		model.addAttribute("page", page);
		return "modules/reserve/reserveInvoiceList";
	}

	@RequestMapping(value = {"listExport", ""})
	public void listExport(ReserveInvoice reserveInvoice,  HttpServletResponse response)throws Exception {
		List<ReserveInvoice> list = reserveInvoiceService.findList(reserveInvoice);
		String[] titles = {"客户姓名","发票抬头","发票项目","发票金额","消费日期","申请日期","是否快递","地址信息","备注","是否已开票"};
		List<String[]> contentList = new ArrayList<>();
		for(ReserveInvoice invoice :list){
			String[] o = new String[10];
			o[0] = invoice.getUserName();
			o[1] = invoice.getInvoiceHead();
			o[2] = invoice.getInvoiceProject();
			o[3] = String.valueOf(invoice.getInvoicePrice());
			o[4] = DateUtils.formatDate(invoice.getConsumerTime());
			o[5] = DateUtils.formatDate(invoice.getApplyTime());
			o[6] = "1".equals(invoice.getIsExpress())?"是":"否";
			o[7] =  invoice.getAddress();
			o[8] =  invoice.getRemarks();
			o[9] =  "1".equals(invoice.getIsExpress())?"是":"否";
			contentList.add(o);
		}
		Date now = new Date();
		ExcelInfo info = new ExcelInfo(response,"发票信息"+ DateUtils.formatDate(now),titles,contentList);
		info.export();
	}

	@RequestMapping(value = "form")
	public String form(ReserveInvoice reserveInvoice, Model model) {
		model.addAttribute("reserveInvoice", reserveInvoice);
		return "modules/reserve/reserveInvoiceForm";
	}

	@RequestMapping(value = "save")
	public String save(ReserveInvoice reserveInvoice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveInvoice)){
			return form(reserveInvoice, model);
		}
		reserveInvoiceService.save(reserveInvoice);
		addMessage(redirectAttributes, "保存发票管理成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveInvoice/list?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(ReserveInvoice reserveInvoice, RedirectAttributes redirectAttributes) {
		reserveInvoiceService.delete(reserveInvoice);
		addMessage(redirectAttributes, "删除发票管理成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveInvoice/list?repage";
	}

}