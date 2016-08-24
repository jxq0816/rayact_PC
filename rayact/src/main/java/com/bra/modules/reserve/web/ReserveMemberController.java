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
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveVenueService;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.reserve.utils.StatementsUtils;
import com.bra.modules.reserve.utils.VenueOrderUtils;
import com.bra.modules.sys.utils.DictUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员管理Controller
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveMember")
public class ReserveMemberController extends BaseController {

	@Autowired
	private ReserveMemberService reserveMemberService;

	@Autowired
	private ReserveVenueService reserveVenueService;

	@Autowired
	private ReserveCardStatementsService reserveCardStatementsService;
	
	@ModelAttribute
	public ReserveMember get(@RequestParam(required=false) String id) {
		ReserveMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reserveMemberService.get(id);
		}
		if (entity == null){
			entity = new ReserveMember();
		}
		return entity;
	}
	
	@RequestMapping(value = {"listExport", ""})
	public void listExport(ReserveMember reserveMember, HttpServletResponse response) {
		List<ReserveMember> list = reserveMemberService.findList(reserveMember);
		String[] titles = {"场馆","场馆","手机号","性别","卡号","卡号类型","余额","备注"};
		List<String[]> contentList = new ArrayList<>();
		for(ReserveMember map :list){
			String[] o = new String[8];
			o[0] = map.getReserveVenue().getName();
			o[1] = map.getName();
			o[2] = map.getMobile();
			o[3] = DictUtils.getDictLabel(map.getSex(),"sex","");
			o[4] = map.getCardNo();
			o[5] = DictUtils.getDictLabel(map.getCardType(),"cart_type","");
			o[6] = String.valueOf(map.getRemainder());
			o[7] =  String.valueOf(map.getRemarks());
			contentList.add(o);
		}
		Date now = new Date();
		try {
			ExcelInfo info = new ExcelInfo(response,"会员统计列表 导出时间："+ DateUtils.formatDate(now),titles,contentList);
			info.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"list", ""})
	public String list(ReserveMember reserveMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveMember> page = reserveMemberService.findPage(new Page<ReserveMember>(request, response), reserveMember);
		List<ReserveVenue> venueList=reserveVenueService.findList(new ReserveVenue());
		model.addAttribute("userType",AuthorityUtils.getUserType());
		model.addAttribute("venueList", venueList);
		model.addAttribute("query", reserveMember);
		model.addAttribute("page", page);
		return "reserve/member/list";
	}

	@RequestMapping(value = "form")
	@Token(save = true)
	public String form(ReserveMember reserveMember, Model model) {
		List<ReserveVenue> venueList=reserveVenueService.findList(new ReserveVenue());
		model.addAttribute("reserveMember", reserveMember);
		model.addAttribute("venueList", venueList);
		return "reserve/member/form";
	}
	@RequestMapping(value = "checkBeforeSave")
	@ResponseBody
	public String check(String id,String cardNo,  String mobile, String sfz, HttpServletRequest request, HttpServletResponse response) {
		String rs = null;
		//验证卡号
		if (StringUtils.isNoneEmpty(cardNo)) {
			ReserveMember rm1 = new ReserveMember();
			if (StringUtils.isNoneEmpty(id)) {//修改用户
				rm1.setId(id);
			}
			rm1.setCardNo(cardNo);
			List<ReserveMember> list1 = reserveMemberService.findExactList(rm1);
			if (list1.size() != 0) {
				rs = "1";//卡号重复
				return rs;
			}
		}
		//验证手机号
		if (StringUtils.isNoneEmpty(mobile)) {
			ReserveMember rm2 = new ReserveMember();
			if (StringUtils.isNoneEmpty(id)) {//修改用户
				rm2.setId(id);
			}
			rm2.setMobile(mobile);
			List<ReserveMember> list2 = reserveMemberService.findExactList(rm2);
			if (list2.size() != 0) {
				rs = "2";//手机号重复
				return rs;
			}
		}
		//验证身份证
		if (StringUtils.isNoneEmpty(sfz)) {
			ReserveMember rm3 = new ReserveMember();
			if (StringUtils.isNoneEmpty(id)) {//修改用户
				rm3.setId(id);
			}
			rm3.setSfz(sfz);
			List<ReserveMember> list3 = reserveMemberService.findExactList(rm3);
			if (list3.size() != 0) {
				rs = "3";//身份证号重复
				return rs;
			}
		}
		return rs;
	}
	@RequestMapping(value = "save")
	@Token(remove = true)
	public String save(ReserveMember reserveMember, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reserveMember)){
			return form(reserveMember, model);
		}

		reserveMemberService.save(reserveMember);
		addMessage(redirectAttributes, "保存会员成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMember/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ReserveMember reserveMember, RedirectAttributes redirectAttributes) {
		reserveMemberService.delete(reserveMember);
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:"+Global.getAdminPath()+"/reserve/reserveMember/?repage";
	}

	@RequestMapping(value = "loadMember")
	@ResponseBody
	public ReserveMember loadMember(String id){
		return reserveMemberService.get(id);
	}

	@RequestMapping(value = "statements")
	public String statements(ReserveCardStatements statements, HttpServletRequest request, HttpServletResponse response, Model model){

		Page<ReserveCardStatements> page = reserveCardStatementsService.findPersonalStatementsPage(new Page<>(request, response),statements);
		model.addAttribute("page", page);
		ReserveMember member=reserveMemberService.get(statements.getReserveMember());
		statements.setReserveMember(member);
		model.addAttribute("statements", statements);
		return "reserve/member/statements";
	}

	@RequestMapping(value = {"statementsExport", ""})
	public void statementsExport(ReserveCardStatements statements,HttpServletResponse response)throws Exception {
		List<ReserveCardStatements> list = reserveCardStatementsService.findPersonalStatementsList(statements);
		String[] titles = {"充值金额","消费类型","半小时","支付方式","消费金额","会员余额","操作员","备注","操作时间"};
		List<String[]> contentList = new ArrayList<>();
		for(ReserveCardStatements map :list){
			String[] o = new String[9];
			if("1".equals(map.getTransactionType())||"7".equals(map.getTransactionType())){
				o[0] = map.getTransactionVolume().toString();
			}else{
				o[0]="";
			}
			o[1]= StatementsUtils.getTransactionType(map.getTransactionType());
			if(map.getTransactionNum()!=null){
				o[2]= map.getTransactionNum().toString();
			}else{
				o[2]="";
			}
			o[3]= VenueOrderUtils.getPayType(map.getPayType());
			o[4]= map.getTransactionVolume().toString();
			o[5]= map.getReserveMember().getRemainder().toString();
			o[6]= map.getCreateBy().getName();
			o[7]= map.getRemarks();
			o[8] = DateUtils.formatDateTime(map.getCreateDate());
			contentList.add(o);
		}
		Date now = new Date();
		ReserveMember member=reserveMemberService.get(statements.getReserveMember());
		ExcelInfo info = new ExcelInfo(response,member.getName()+"的交易明细，导出时间："+ DateUtils.formatDate(now),titles,contentList);
		info.export();
	}
}