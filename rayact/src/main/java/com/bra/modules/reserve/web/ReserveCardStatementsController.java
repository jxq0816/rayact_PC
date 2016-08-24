package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveMemberDayReport;
import com.bra.modules.reserve.entity.form.ReserveMemberIntervalReport;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.reserve.utils.VenueOrderUtils;
import com.bra.modules.sys.utils.UserUtils;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * reserveController
 * @author jiangxingqi
 * @version 2016-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveCardStatements")
public class ReserveCardStatementsController extends BaseController {

	@Autowired
	private ReserveCardStatementsService reserveCardStatementsService;

	@Autowired
	private ReserveMemberService reserveMemberService;

	@Autowired
	private ReserveVenueService reserveVenueService;

	@ModelAttribute
	public ReserveCardStatements get(@RequestParam(required=false) String id) {
		ReserveCardStatements entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveCardStatementsService.get(id);
		}
		if (entity == null){
			entity = new ReserveCardStatements();
		}
		return entity;
	}
	/*记录*/
	@RequestMapping(value = {"list", ""})
	public String list(ReserveCardStatements reserveCardStatements, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveCardStatements> page = reserveCardStatementsService.findPage(new Page<ReserveCardStatements>(request, response), reserveCardStatements);
		model.addAttribute("page", page);
		List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());
		model.addAttribute("reserveVenueList", reserveVenueList);
		String type=reserveCardStatements.getTransactionType();
		String rs=null;
		if("1".equals(type)){
			//储值卡充值
			rs= "reserve/record/reserveCardRechargeStatementsList";
		}else if("2".equals(type)){
			//会员退费
			rs= "reserve/record/reserveCardRefundStatementsList";
		}else if("5".equals(type)){
			//储值卡销户记录
			rs= "reserve/record/reserveCardCancellationStatementsList";
		}else if("7".equals(type)){
			//次卡充值记录
			rs= "reserve/record/timeCardRechargeStatementsList";
		}else if("11".equals(type)){
			//次卡充值记录
			rs= "reserve/record/timeCardCancellationStatementsList";
		}
		model.addAttribute("query", reserveCardStatements);
		String userType= UserUtils.getUser().getUserType();
		model.addAttribute("userType", userType);
		return rs;
	}

	/*记录导出*/
	@RequestMapping(value = {"listExport", ""})
	public void listExport(ReserveCardStatements reserveCardStatements, HttpServletResponse response)throws Exception {
		List<ReserveCardStatements> list = reserveCardStatementsService.findList(reserveCardStatements);
		String type=reserveCardStatements.getTransactionType();
		//"储值卡充值记录"
		if("1".equals(type)){
			String[] titles = {"场馆","姓名","卡号","金额","电话","支付方式","操作人","时间"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveCardStatements report :list){
				String[] o = new String[8];
				ReserveVenue venue = report.getVenue();
				if(venue!=null){
					o[0] = report.getVenue().getName();
				}else {
					o[0] = "";
				}
				o[1] = report.getReserveMember().getName();
				o[2] = report.getReserveMember().getCardNo();
				o[3] = String.valueOf(report.getTransactionVolume());
				o[4] = String.valueOf(report.getReserveMember().getMobile());
				o[5] = String.valueOf(VenueOrderUtils.getPayType(report.getPayType()));
				o[6] = String.valueOf(report.getCreateBy().getName());
				o[7] = DateUtils.formatDate(report.getCreateDate());
				contentList.add(o);
			}
			Date now = new Date();
			ExcelInfo info = new ExcelInfo(response,"充值记录"+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		}
		/*会员退费记录*/
		if("2".equals(type)){
			String[] titles = {"场馆","姓名","卡号","金额","电话","操作人","时间"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveCardStatements report :list){
				String[] o = new String[7];
				o[0] = report.getVenue().getName();
				o[1] = report.getReserveMember().getName();
				o[2] = report.getReserveMember().getCardNo();
				o[3] = String.valueOf(report.getTransactionVolume());
				o[4] = String.valueOf(report.getReserveMember().getMobile());
				o[5] = String.valueOf(report.getCreateBy().getName());
				o[6] = DateUtils.formatDate(report.getCreateDate());
				contentList.add(o);
			}
			Date now = new Date();
			ExcelInfo info = new ExcelInfo(response,"退费记录"+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		}
		/*储值卡销户记录*/
		if("5".equals(type)){
			String[] titles = {"场馆","姓名","卡号","卡内余额","退还金额","违约金","电话","操作人","时间"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveCardStatements report :list){
				String[] o = new String[9];
				o[0] = report.getVenue().getName();
				o[1] = report.getReserveMember().getName();
				o[2] = report.getReserveMember().getCardNo();
				o[3] = String.valueOf(report.getTransactionVolume()/0.8);
				o[4] = String.valueOf(report.getTransactionVolume());
				o[5] = String.valueOf(report.getTransactionVolume()/4);
				o[6] = String.valueOf(report.getReserveMember().getMobile());
				o[7] = String.valueOf(report.getCreateBy().getName());
				o[8] = DateUtils.formatDate(report.getCreateDate());
				contentList.add(o);
			}
			Date now = new Date();
			ExcelInfo info = new ExcelInfo(response,"储值卡销户记录"+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		}
		/*次卡销户记录*/
		if("11".equals(type)){
			String[] titles = {"场馆","姓名","卡内余额","退还金额","手机号","操作人","时间"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveCardStatements report :list){
				String[] o = new String[7];
				o[0] = report.getVenue().getName();
				o[1] = report.getReserveMember().getName();
				o[2] = String.valueOf(report.getReserveMember().getRemainder());
				o[3] = String.valueOf(report.getTransactionVolume());
				o[4] = String.valueOf(report.getReserveMember().getMobile());
				o[5] = String.valueOf(report.getCreateBy().getName());
				o[6] = DateUtils.formatDate(report.getCreateDate());
				contentList.add(o);
			}
			Date now = new Date();
			ExcelInfo info = new ExcelInfo(response,"次卡销户记录    导出时间："+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		}
	}

	/*会员收入统计:对应*/
	@RequestMapping(value = {"memberIncomeReport", ""})
	public String listByStoredCardType(ReserveMemberIntervalReport reserveMemberIntervalReport,
									   @RequestParam(value="queryType", defaultValue="1") String queryType, Model model) {

		List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());
		reserveMemberIntervalReport.setReserveVenue(reserveVenueService.get(reserveMemberIntervalReport.getReserveVenue()));//场馆信息完善

		if(reserveMemberIntervalReport.getStartDate()==null){
			reserveMemberIntervalReport.setStartDate(new Date());
		}
		if(reserveMemberIntervalReport.getEndDate()==null){
			reserveMemberIntervalReport.setEndDate(new Date());
		}
		model.addAttribute("reserveVenueList", reserveVenueList);
		//1：汇总 2：明细
		if("1".equals(queryType)){
			List<ReserveMemberIntervalReport> collectReport=reserveCardStatementsService.memberIncomeCollectReport(reserveMemberIntervalReport);
			model.addAttribute("collectReport", collectReport);
			model.addAttribute("reserveMemberIntervalReport", reserveMemberIntervalReport);//请求参数返回
			return "reserve/report/memberIncomeCollectReport";
		}else{
			List<ReserveMemberIntervalReport> intervalReports=reserveCardStatementsService.memberIncomeIntervalReport(reserveMemberIntervalReport);
			model.addAttribute("intervalReports", intervalReports);
			model.addAttribute("reserveMemberIntervalReport", reserveMemberIntervalReport);//请求参数返回
			return "reserve/report/memberIncomeDetailReport";
		}
	}


	/*会员收入统计:导出*/
	@RequestMapping(value = {"memberIncomeReportExport", ""})
	public void listByStoredCardTypeExport(ReserveMemberIntervalReport reserveMemberIntervalReport, String queryType,HttpServletResponse response)throws Exception {

		reserveMemberIntervalReport.setReserveVenue(reserveVenueService.get(reserveMemberIntervalReport.getReserveVenue()));//场馆信息完善

		if(reserveMemberIntervalReport.getStartDate()==null){
			reserveMemberIntervalReport.setStartDate(new Date());
		}
		if(reserveMemberIntervalReport.getEndDate()==null){
			reserveMemberIntervalReport.setEndDate(new Date());
		}
		//1：汇总 2：明细
		if("1".equals(queryType)){
			List<ReserveMemberIntervalReport> collectReport=reserveCardStatementsService.memberIncomeCollectReport(reserveMemberIntervalReport);
			String[] titles = {"日期","现金","银行卡","转账","微信","微信(个人)","支付宝","支付宝（个人）","优惠券","合计"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveMemberIntervalReport report :collectReport) {
				String[] o = new String[10];
				o[0] = report.getReserveVenue().getName();
				o[1] = String.valueOf(report.getCashBill());
				o[2] = String.valueOf(report.getBankCardBill());
				o[3] = String.valueOf(report.getTransferBill());
				o[4] = String.valueOf(report.getWeiXinBill());
				o[5] = String.valueOf(report.getPersonalWeiXinBill());
				o[6] = String.valueOf(report.getAliPayBill());
				o[7] = String.valueOf(report.getPersonalAliPayBill());
				o[8] = String.valueOf(report.getOtherBill());
				o[9] = String.valueOf(report.getBill());
				contentList.add(o);
			}
			Date now = new Date();
			ReserveVenue venue = reserveMemberIntervalReport.getReserveVenue();
			String theme = venue!=null?venue.getName():""+"会员充值汇总 ："+ DateUtils.formatDate(now);
			ExcelInfo info = new ExcelInfo(response,theme,titles,contentList);
			info.export();
		}else if("2".equals(queryType)){
			List<ReserveMemberIntervalReport> intervalReports=reserveCardStatementsService.memberIncomeIntervalReport(reserveMemberIntervalReport);
			String[] titles = {"日期","现金","银行卡","转账","微信","微信(个人)","支付宝","支付宝（个人）","优惠券","合计"};
			List<String[]> contentList = new ArrayList<>();
			for(ReserveMemberIntervalReport report :intervalReports){
				String[] o = new String[10];
				o[0] = DateUtils.formatDate(reserveMemberIntervalReport.getStartDate())+"至"+DateUtils.formatDate(reserveMemberIntervalReport.getEndDate());
				o[1] = String.valueOf(report.getCashBill());
				o[2] = String.valueOf(report.getBankCardBill());
				o[3] = String.valueOf(report.getTransferBill());
				o[4] = String.valueOf(report.getWeiXinBill());
				o[5] = String.valueOf(report.getPersonalWeiXinBill());
				o[6] = String.valueOf(report.getAliPayBill());
				o[7] = String.valueOf(report.getPersonalAliPayBill());
				o[8] = String.valueOf(report.getOtherBill());
				o[9] = String.valueOf(report.getBill());
				contentList.add(o);
				for(ReserveMemberDayReport day:report.getDayReports()){
					String[] dayin = new String[10];
					dayin[0] = DateUtils.formatDate(day.getDay());
					dayin[1] = String.valueOf(day.getCashBill());
					dayin[2] = String.valueOf(day.getBankCardBill());
					dayin[3] = String.valueOf(day.getTransferBill());
					dayin[4] = String.valueOf(day.getWeiXinBill());
					dayin[5] = String.valueOf(day.getPersonalWeiXinBill());
					dayin[6] = String.valueOf(day.getAliPayBill());
					dayin[7] = String.valueOf(day.getPersonalAliPayBill());
					dayin[8] = String.valueOf(day.getOtherBill());
					dayin[9] = String.valueOf(day.getBill());
					contentList.add(dayin);
				}
			}
			Date now = new Date();
			ReserveVenue venue = reserveMemberIntervalReport.getReserveVenue();
			String theme = venue!=null?venue.getName():""+"会员充值明细"+ DateUtils.formatDate(now);
			ExcelInfo info = new ExcelInfo(response,theme,titles,contentList);
			info.export();
		}
	}


	/*充值表单*/
	@RequestMapping(value = "rechargeForm")
	@Token(save = true)
	public String rechargeForm(ReserveMember reserveMember, Model model) {
		reserveMember=reserveMemberService.get(reserveMember.getId());
		model.addAttribute("reserveMember", reserveMember);
		return "reserve/member/storedCardMemberRechargeForm";
	}
	/*充值*/
	@RequestMapping(value = "recharge")
	@ResponseBody
	@Token(remove = true)
	public String recharge(Double rechargeVolume,String id,String payType,String remarks,RedirectAttributes redirectAttributes) {

		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder=rechargeVolume+reserveMember.getRemainder();
		reserveMember.setRemainder(remainder);
		reserveMemberService.save(reserveMember);
		addMessage(redirectAttributes, "充值成功");

		/*充值记录*/
		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("1");//充值
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setVenue(reserveMember.getReserveVenue());
		reserveCardStatements.setTransactionVolume(rechargeVolume);
		reserveCardStatements.setPayType(payType);
		reserveCardStatements.setRemarks(remarks);
		reserveCardStatementsService.save(reserveCardStatements);
		return "success";
	}
	/*销户表单*/
	@RequestMapping(value = "cancellationForm")
	@Token(save = true)
	public String refundForm(ReserveMember reserveMember, Model model) {
		reserveMember=reserveMemberService.get(reserveMember.getId());
		model.addAttribute("reserveMember", reserveMember);
		return "reserve/member/storedCardMemberCancellationForm";
	}
	/*销户*/
	@RequestMapping(value = "cancellation")
	@ResponseBody
	@Token(remove = true)
	public String refund(Double realRefundVolume,String id) {
		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder= reserveMember.getRemainder();
		reserveMember.setRemainder(0.0);
		reserveMemberService.save(reserveMember);//余额清空
		reserveMemberService.delete(reserveMember);//销户
		Double difference=remainder-realRefundVolume;//差额

		DecimalFormat df=new DecimalFormat("0.00");
		difference=new Double(df.format(difference).toString());

		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("5");//销户退还用户的金额记录
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setVenue(reserveMember.getReserveVenue());
		reserveCardStatements.setTransactionVolume(realRefundVolume);// 退还用户的金额
		reserveCardStatementsService.save(reserveCardStatements);

		ReserveCardStatements statements=new ReserveCardStatements();
		statements.setTransactionType("6");//销户违约金
		statements.setReserveMember(reserveMember);
		reserveCardStatements.setVenue(reserveMember.getReserveVenue());
		statements.setTransactionVolume(difference);//销户退还用户余下的差额
		reserveCardStatementsService.save(statements);
		return "success";
	}
	/*大客户退费表单*/
	@RequestMapping(value = "refundForVIPForm")
	@Token(save = true)
	public String refundBtnForVIP(ReserveMember reserveMember, Model model) {
		reserveMember=reserveMemberService.get(reserveMember.getId());
		model.addAttribute("reserveMember", reserveMember);
		return "reserve/member/storedCardMemberRefundForVIPForm";
	}
	/*大客户退费*/
	@RequestMapping(value = "refundForVIP")
	@ResponseBody
	@Token(remove = true)
	public String refundForVIP(Double refundVolume,String remarks,String id) {
		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder= reserveMember.getRemainder();
		if(remainder<refundVolume){
			return "退款金额不能大于余额";
		}
		remainder-=refundVolume;//回扣提现
		reserveMember.setRemainder(remainder);
		reserveMemberService.save(reserveMember);

		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("2");//大客户退费
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setVenue(reserveMember.getReserveVenue());
		reserveCardStatements.setTransactionVolume(refundVolume);
		reserveCardStatements.setRemarks(remarks);
		reserveCardStatementsService.save(reserveCardStatements);
		return "success";
	}

	@RequestMapping(value = "delete")
	public String delete(ReserveCardStatements reserveCardStatements,RedirectAttributes redirectAttributes) {
		reserveCardStatementsService.delete(reserveCardStatements);
		addMessage(redirectAttributes, "删除交易记录成功");
		redirectAttributes.addAttribute("transactionType",reserveCardStatements.getTransactionType());
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCardStatements/list";
	}
}