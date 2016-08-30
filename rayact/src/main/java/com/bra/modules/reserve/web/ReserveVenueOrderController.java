package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSON;
import com.bra.common.config.Global;
import com.bra.common.persistence.ConstantEntity;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.ViewResult;
import com.bra.common.web.annotation.Token;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.service.*;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * 场地订单Controller
 *
 * @author 肖斌
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenueOrder")
public class ReserveVenueOrderController extends BaseController {

    @Autowired
    private ReserveTimecardMemberSetService reserveTimecardMemberSetService;
    @Autowired
    private ReserveVenueOrderService reserveVenueOrderService;
    @Autowired
    private ReserveVenueVisitorsSetService reserveVenueVisitorsSetService;
    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveTutorService reserveTutorService;
    @Autowired
    private ReserveMemberService reserveMemberService;
    @Autowired
    private ReserveProjectService reserveProjectService;
    @Autowired
    private ReserveTimeCardPrepaymentService reserveTimeCardPrepaymentService;

    @RequestMapping(value = "list")
    public String list(ReserveVenueVisitorsSet visitorsSet, Model model) {
        List<ReserveVenueVisitorsSet> visitorsSets = reserveVenueVisitorsSetService.findList(visitorsSet);
        model.addAttribute("visitorsSets", visitorsSets);

        ReserveProject project = new ReserveProject();
       /* project.setTicketType("2");*/
        List<ReserveProject> projects = reserveProjectService.findList(project);
        if (visitorsSet.getProject() != null && StringUtils.isNotBlank(visitorsSet.getProject().getId())) {
            model.addAttribute("projectId", visitorsSet.getProject().getId());
        }
        model.addAttribute("projects", projects);
        return "reserve/visitorsSetOrder/list";
    }

    //人次票 付款界面
    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(String vsId, String venueId, Model model) {
        ReserveVenueVisitorsSet set = reserveVenueVisitorsSetService.get(vsId);
        ReserveVenue venue = reserveVenueService.get(venueId);
        ReserveField field = new ReserveField();
        field.setReserveVenue(venue);
        List<ReserveField> fieldList = reserveFieldService.findList(field);
        model.addAttribute("fieldList", fieldList);
        model.addAttribute("visitorsSet", set);
        //教练
        ReserveTutor tutor = new ReserveTutor();
        ReserveProject project = set.getProject();
        tutor.setProject(project);
        model.addAttribute("tutors", reserveTutorService.findList(tutor));
        //会员
        ReserveMember member = new ReserveMember();
        member.setReserveVenue(venue);
        member.setCardType("2");
        model.addAttribute("memberList", reserveMemberService.findList(member));
        //获取预定开始时间
        List<String> times = TimeUtils.getTimeSpacList(venue.getStartTime(), venue.getEndTime(), TimeUtils.BENCHMARK);
        model.addAttribute("times", times);
        model.addAttribute("orderDate", new Date());
        return "reserve/visitorsSetOrder/form";
    }

    //确认购买 表单
    @RequestMapping(value = "detail")
    @Token(save = true)
    public String detail(ReserveVenueOrder reserveVenueOrder, Model model) {
        model.addAttribute("venueOrder", reserveVenueOrder);
        return "reserve/visitorsSetOrder/detail";
    }

    //确认购买
    @RequestMapping(value = "checkSave")
    @ResponseBody
    public String checkSave(ReserveVenueOrder reserveVenueOrder) {
        ViewResult rs = null;
        ReserveVenueVisitorsSet typeSet = reserveVenueOrder.getVisitorsSet();//获得场次票的属性
        typeSet = reserveVenueVisitorsSetService.get(typeSet);
        ReserveProject p = typeSet.getProject();
        String payType=reserveVenueOrder.getPayType();
        //如果是会员预订
        if (reserveVenueOrder.getMember() != null && StringUtils.isNoneEmpty(reserveVenueOrder.getMember().getId()) && ConstantEntity.STOREDCARD.equals(payType)) {
            ReserveMember member = reserveMemberService.get(reserveVenueOrder.getMember());
            ReserveTimecardMemberSet memberTimeCardSet = member.getTimecardSet();//该用户不拥有打折次卡

            if (memberTimeCardSet == null) {
                rs = new ViewResult(false, "该用户没有次卡！");
            } else {
                memberTimeCardSet = reserveTimecardMemberSetService.get(memberTimeCardSet);
                ReserveProject project = memberTimeCardSet.getReserveProject();
                if (project != null && StringUtils.isNoneEmpty(project.getId())) {
                    //如果用户 拥有该项目的折扣卡
                    if (p.getId().equals(project.getId())) {
                        int residue = member.getResidue();//次卡剩余次数 等于 预付款的次数之和
                        int ticketNum = reserveVenueOrder.getCollectCount();//买了几张票
                        if (ticketNum > residue) {
                            rs = new ViewResult(false, "该用户剩余次数不足！剩余次数=" + residue);
                        } else {
                            rs = new ViewResult(true, "检测成功");
                        }
                    } else {
                        rs = new ViewResult(false, "该用户的次票不可在该场地使用！");
                    }
                } else {
                    rs = new ViewResult(false, "该用户的次卡没有指定项目！");
                }
            }
        } else {
            rs = new ViewResult(true, "检测成功");
        }
        return JSON.toJSONString(rs);
    }

    //确认购买
    @RequestMapping(value = "save")
    @ResponseBody
    @Token(remove = true)
    public String save(ReserveVenueOrder reserveVenueOrder) {
        //如果是会员预订
        String payType=reserveVenueOrder.getPayType();
        if (reserveVenueOrder.getMember() != null && StringUtils.isNoneEmpty(reserveVenueOrder.getMember().getId()) && ConstantEntity.STOREDCARD.equals(payType)) {
            ReserveMember member = reserveMemberService.get(reserveVenueOrder.getMember());
            ReserveTimecardMemberSet memberTimecarSet = member.getTimecardSet();
            memberTimecarSet = reserveTimecardMemberSetService.get(memberTimecarSet);
            ReserveProject project = memberTimecarSet.getReserveProject();
            int residue = member.getResidue();//次卡剩余次数 等于 预付款的次数之和
            int ticketNum = reserveVenueOrder.getCollectCount();//买了几张票
            residue -= ticketNum;//修改该用户的剩余次数
            double remainder = member.getRemainder();
            /* 预付次数减*/
            ReserveTimeCardPrepayment prepayment = new ReserveTimeCardPrepayment();
            prepayment.setReserveMember(member);
            prepayment.setReserveProject(project);
            List<ReserveTimeCardPrepayment> list = reserveTimeCardPrepaymentService.findList(prepayment);
            double collectPrice=0.0;//次卡扣款 如果是次卡会员，与前端传递的金额不一致
            for (ReserveTimeCardPrepayment i : list) {
                double remainderPre = i.getRemainder();//预付款余额
                double singleTimePrice = i.getSingleTimePrice();//单价
                int remainTime = i.getRemainTime();//预付剩余次数

                //如果剩余次数大于等于购买票数
                if (remainTime >= ticketNum) {
                    remainderPre -= singleTimePrice * ticketNum;
                    collectPrice+=singleTimePrice * ticketNum;
                    i.setRemainder(remainderPre);//修改预付款余额
                    remainTime -= ticketNum;
                    i.setRemainTime(remainTime);//修改预付款次数
                    reserveTimeCardPrepaymentService.save(i);
                    break;//本次预付款就可以结清
                } else {
                    //如果剩余次数 小于 购买票数
                    i.setRemainTime(0);//次数减为零
                    i.setRemainder(0.0);//余额清空
                    reserveTimeCardPrepaymentService.save(i);
                    ticketNum -= remainTime;//修改购买票数
                    continue;//需要 其它的预付款 来结算
                }
            }
            member.setResidue(residue);//保存剩余次数
            remainder -= collectPrice;//会员余额 减去 应付
            member.setRemainder(remainder);//会员余额更新
            reserveMemberService.save(member);
            reserveVenueOrder.setMember(member);
            reserveVenueOrder.setCollectPrice(collectPrice);//记录订单金额
            reserveVenueOrderService.save(reserveVenueOrder);//会员结算保存
        }else{
            reserveVenueOrderService.save(reserveVenueOrder);//非会员
        }
        ViewResult rs = new ViewResult(true, "保存成功");
        return JSON.toJSONString(rs);
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveVenueOrder reserveVenueOrder, RedirectAttributes redirectAttributes) {
        reserveVenueOrderService.delete(reserveVenueOrder);
        addMessage(redirectAttributes, "删除场地订单成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenueOrder/?repage";
    }

}