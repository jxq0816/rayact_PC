package com.bra.modules.reserve.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveMember;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.service.ReserveCardStatementsService;
import com.bra.modules.reserve.service.ReserveMemberService;
import com.bra.modules.reserve.service.ReserveVenueService;
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
	
	@RequestMapping(value = {"list", ""})
	public String list(ReserveMember reserveMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReserveMember> page = reserveMemberService.findPage(new Page<ReserveMember>(request, response), reserveMember);
		List<ReserveVenue> venueList=reserveVenueService.findList(new ReserveVenue());
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
			rm1.setCartno(cardNo);
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
	public String statements(String memberId, HttpServletRequest request, HttpServletResponse response, Model model){
		ReserveCardStatements query=new ReserveCardStatements();
		ReserveMember member=reserveMemberService.get(memberId);
		query.setReserveMember(member);
		Page<ReserveCardStatements> page = reserveCardStatementsService.findPage(new Page<ReserveCardStatements>(request, response),query);
		model.addAttribute("page", page);
		model.addAttribute("member", member);
		return "reserve/member/statements";
	}

}