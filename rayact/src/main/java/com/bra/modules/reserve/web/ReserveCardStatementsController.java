package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveMemberIntervalReport;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveProjectService;
import com.bra.modules.reserve.service.ReserveVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.web.BaseController;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.service.ReserveCardStatementsService;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private ReserveProjectService reserveProjectService;

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
		//求和
		List<ReserveCardStatements> list = page.getList();
		double sum=0;
		for(ReserveCardStatements i:list){
			sum+=i.getTransactionVolume();
		}
		model.addAttribute("sum",sum);

		String type=reserveCardStatements.getTransactionType();
		String rs=null;
		if("1".equals(type)){
			rs= "reserve/record/reserveCardRechargeStatementsList";
		}
		if("2".equals(type)){
			rs= "reserve/record/reserveCardRefundStatementsList";
		}
		return rs;
	}
	/*会员收入统计*/
	@RequestMapping(value = {"memberIncomeReport", ""})
	public String listByStoredCardType(ReserveMemberIntervalReport reserveMemberIntervalReport, String queryType, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());
		reserveMemberIntervalReport.setReserveVenue(reserveVenueService.get(reserveMemberIntervalReport.getReserveVenue()));//场馆信息完善

		if(StringUtils.isEmpty(queryType)){
			queryType="1";
		}
		if(reserveMemberIntervalReport.getStartDate()==null){
			reserveMemberIntervalReport.setStartDate(new Date());
		}
		if(reserveMemberIntervalReport.getEndDate()==null){
			reserveMemberIntervalReport.setEndDate(new Date());
		}
		model.addAttribute("reserveVenueList", reserveVenueList);

		if("1".equals(queryType)){
			List<ReserveMemberIntervalReport> collectReport=reserveCardStatementsService.memberIncomeCollectReport(reserveMemberIntervalReport);
			model.addAttribute("collectReport", collectReport);
			model.addAttribute("reserveMemberIntervalReport", reserveMemberIntervalReport);//请求参数返回
			return "reserve/report/memberIncomeCollectReport";
		}else{
			reserveMemberIntervalReport.setReserveProject(reserveProjectService.get(reserveMemberIntervalReport.getReserveProject()));//项目信息完善
			List<ReserveProject> projectList = reserveProjectService.findList(new ReserveProject());
			List<ReserveMemberIntervalReport> intervalReports=reserveCardStatementsService.memberIncomeIntervalReport(reserveMemberIntervalReport);
			model.addAttribute("intervalReports", intervalReports);
			model.addAttribute("projectList", projectList);
			model.addAttribute("reserveMemberIntervalReport", reserveMemberIntervalReport);//请求参数返回
			return "reserve/report/memberIncomeDetailReport";
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
	public String recharge(Double rechargeVolume,String id,String payType,RedirectAttributes redirectAttributes) {

		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder=rechargeVolume+reserveMember.getRemainder();
		reserveMember.setRemainder(remainder);
		reserveMemberService.save(reserveMember);
		addMessage(redirectAttributes, "充值成功");

		/*充值记录*/
		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("1");//充值
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setTransactionVolume(rechargeVolume);
		reserveCardStatements.setPayType(payType);
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

		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("4");//销户退还用户余下的差额
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setTransactionVolume(difference);
		reserveCardStatementsService.save(reserveCardStatements);
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
	public String refundForVIP(Double refundVolume,String id) {
		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder= reserveMember.getRemainder();
		if(remainder<refundVolume){
			return "退款金额不能大于余额";
		}
		remainder-=refundVolume;//回扣提现
		reserveMember.setRemainder(remainder);
		reserveMemberService.save(reserveMember);

		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("3");//大客户退费
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setTransactionVolume(refundVolume);
		reserveCardStatementsService.save(reserveCardStatements);
		return "success";
	}

	@RequestMapping(value = "delete")
	public String delete(ReserveCardStatements reserveCardStatements, RedirectAttributes redirectAttributes) {
		reserveCardStatementsService.delete(reserveCardStatements);
		addMessage(redirectAttributes, "删除交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveCardStatements/?repage";
	}

}