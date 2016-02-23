package com.bra.modules.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.service.ReserveMemberService;
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
	public String listByStoredCardType(ReserveCardStatements reserveCardStatements, String payType,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(payType)){
			payType="1";
		}
		if("1".equals(payType)){
			List<Map<String,Object>> page=reserveCardStatementsService.memberIncomeCollectRecord(reserveCardStatements);
			model.addAttribute("page", page);
			return "reserve/report/memberIncomeCollectRecord";
		}else{

		}
		return null;
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
	/*退费表单*/
	@RequestMapping(value = "refundForm")
	@Token(save = true)
	public String refundForm(ReserveMember reserveMember, Model model) {
		reserveMember=reserveMemberService.get(reserveMember.getId());
		model.addAttribute("reserveMember", reserveMember);
		return "reserve/member/storedCardMemberRefundForm";
	}
	/*退费*/
	@RequestMapping(value = "refund")
	@ResponseBody
	@Token(remove = true)
	public String refund(Double realRefundVolume,String id) {
		ReserveMember reserveMember=reserveMemberService.get(id);
		Double remainder= reserveMember.getRemainder();
		remainder+=realRefundVolume;
		reserveMember.setRemainder(remainder);
		reserveMemberService.save(reserveMember);

		ReserveCardStatements reserveCardStatements=new ReserveCardStatements();
		reserveCardStatements.setTransactionType("2");//退费
		reserveCardStatements.setReserveMember(reserveMember);
		reserveCardStatements.setTransactionVolume(realRefundVolume);
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