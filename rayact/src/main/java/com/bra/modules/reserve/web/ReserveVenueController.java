package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSONArray;
import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.utils.DateUtils;
import com.bra.common.utils.SpringContextHolder;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.common.web.annotation.Token;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.ReserveVenueIncomeIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectFieldIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueTotalIntervalReport;
import com.bra.modules.reserve.service.*;
import com.bra.modules.reserve.utils.ExcelInfo;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.web.LoginController;
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
import java.util.*;

/**
 * 场馆管理Controller
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/reserve/reserveVenue")
public class ReserveVenueController extends BaseController {
    private String rootURL = "http://101.200.148.75:8080";
    @Autowired
    private ReserveFieldRelationService reserveFieldRelationService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;

    @Autowired
    private ReserveProjectService reserveProjectService;

    @ModelAttribute
    public ReserveVenue get(@RequestParam(required = false) String id) {
        ReserveVenue entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = reserveVenueService.get(id);
        }
        if (entity == null) {
            entity = new ReserveVenue();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(ReserveVenue reserveVenue, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ReserveVenue> page = reserveVenueService.findPage(new Page<>(request, response), reserveVenue);
        model.addAttribute("page", page);
        return "reserve/venue/list";
    }

    @RequestMapping(value = "form")
    @Token(save = true)
    public String form(ReserveVenue reserveVenue, Model model) {
        model.addAttribute("reserveVenue", reserveVenue);
        return "reserve/venue/form";
    }

    @RequestMapping(value = "save")
    @Token(remove = true)
    public String save(ReserveVenue reserveVenue, AttMainForm attMainForm, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, reserveVenue)) {
            return form(reserveVenue, model);
        }
        reserveVenueService.save(reserveVenue, attMainForm);
        addMessage(redirectAttributes, "保存场馆成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenue/list";
    }

    @RequestMapping(value = "delete")
    public String delete(ReserveVenue reserveVenue,RedirectAttributes redirectAttributes) {
        reserveVenueService.delete(reserveVenue);
        addMessage(redirectAttributes, "删除场馆成功");
        return "redirect:" + Global.getAdminPath() + "/reserve/reserveVenue/list";
    }

    @RequestMapping(value = "totalIncomeReport")
    public String totalIncomeReport(ReserveVenueTotalIntervalReport intervalTotalReport, Model model) {
        Date startDate=intervalTotalReport.getStartDate();
        Date endDate=intervalTotalReport.getEndDate();
        if(startDate==null){
            intervalTotalReport.setStartDate(new Date());
        }
        if(endDate==null){
            intervalTotalReport.setEndDate(new Date());
        }
        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表

        List<ReserveVenueTotalIntervalReport> totalIntervalReportList=reserveVenueService.totalIncomeReport(intervalTotalReport);
        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表
        model.addAttribute("intervalTotalReport",intervalTotalReport);//请求参数
        model.addAttribute("totalIntervalReportList",totalIntervalReportList);//返回结果
        return "reserve/report/venueIncomeTotalReport";
    }

    @RequestMapping(value = "report")
    public String report(ReserveVenueProjectIntervalReport venueProjectReport, @RequestParam(required=false,defaultValue="1",value="queryType") String queryType, Model model) {
        Date startDate=venueProjectReport.getStartDate();
        Date endDate=venueProjectReport.getEndDate();
        venueProjectReport.setReserveVenue(reserveVenueService.get(venueProjectReport.getReserveVenue()));
        if(startDate==null){
            startDate=new Date();//默认当天
            venueProjectReport.setStartDate(startDate);
        }
        if(endDate==null){
            endDate=new Date();//默认当天
            venueProjectReport.setEndDate(endDate);
        }
        ReserveVenueIncomeIntervalReport incomeReport=reserveVenueService.reserveVenueIncomeIntervalReport(venueProjectReport);//场馆区间报表
        incomeReport.setStartDate(startDate);
        incomeReport.setEndDate(endDate);
        model.addAttribute("incomeReport",incomeReport);//收入统计
        model.addAttribute("venueProjectReport",venueProjectReport);//查询参数回传
        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表
        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表
        List<ReserveProject> reserveProjectList=reserveProjectService.findList(new ReserveProject());
        model.addAttribute("reserveProjectList",reserveProjectList);//收入统计
        if("1".equals(queryType)){
            return "reserve/report/venueIncomeReport";
        }else{
            List<ReserveVenueProjectIntervalReport> venueProjectDividedReports = reserveVenueService.reserveVenueProjectDividedIntervalReport(venueProjectReport);//场馆 项目 散客 收入统计
            model.addAttribute("venueProjectDividedReports",venueProjectDividedReports);//查询参数回传
            return "reserve/report/venueIncomeDetailReport";
        }
    }

    @RequestMapping(value = "reportExport")
    public void reportExport(HttpServletResponse response,ReserveVenueProjectIntervalReport venueProjectReport, String queryType)throws Exception {
        Date startDate=venueProjectReport.getStartDate();
        Date endDate=venueProjectReport.getEndDate();
        venueProjectReport.setReserveVenue(reserveVenueService.get(venueProjectReport.getReserveVenue()));
        if(startDate==null){
            startDate=new Date();//默认当天
            venueProjectReport.setStartDate(startDate);
        }
        if(endDate==null){
            endDate=new Date();//默认当天
            venueProjectReport.setEndDate(endDate);
        }
        if(StringUtils.isEmpty(queryType)){
            queryType="1";
        }
        ReserveVenueIncomeIntervalReport incomeReport=reserveVenueService.reserveVenueIncomeIntervalReport(venueProjectReport);//场馆区间报表
        incomeReport.setStartDate(startDate);
        incomeReport.setEndDate(endDate);
        if("1".equals(queryType)){
            String[] titles = {"场馆","项目","储值卡","现金收入","银行卡收入","微信收入","支付宝收入","欠账","其它","合计"};
            List<String[]> contentList = new ArrayList<>();
            for(ReserveVenueProjectIntervalReport report :incomeReport.getProjectIntervalReports()){
                String[] o = new String[10];
                o[0] = report.getReserveVenue().getName();
                o[1] = report.getReserveProject().getName();
                o[2] = String.valueOf(report.getFieldBillStoredCard());
                o[3] = String.valueOf(report.getFieldBillCash());
                o[4] = String.valueOf(report.getFieldBillBankCard());
                o[5] = String.valueOf(report.getFieldBillWeiXin());
                o[6] = String.valueOf(report.getFieldBillAliPay());
                o[7] = String.valueOf(report.getFieldBillDue());
                o[8] = String.valueOf(report.getFieldBillOther());
                o[9] = String.valueOf(report.getBill());
                contentList.add(o);
            }
            String[] o = new String[10];
            o[0] = "合计";
            o[1] = "";
            o[2] = String.valueOf(incomeReport.getStoredCardBill());
            o[3] = String.valueOf(incomeReport.getCashBill());
            o[4] = String.valueOf(incomeReport.getBankCardBill());
            o[5] = String.valueOf(incomeReport.getWeiXinBill());
            o[6] = String.valueOf(incomeReport.getAliPayBill());
            o[7] = String.valueOf(incomeReport.getDueBill());
            o[8] = String.valueOf(incomeReport.getOtherBill());
            o[9] = String.valueOf(incomeReport.getBill());
            contentList.add(o);
            Date now = new Date();
            ExcelInfo info = new ExcelInfo(response,"场馆收入汇总"+ DateUtils.formatDate(now),titles,contentList);
            info.export();
        }else{
            String[] titles = {"场馆","项目","场地","储值卡","现金收入","银行卡收入","微信收入","支付宝收入","欠账","其它","合计"};
            List<String[]> contentList = new ArrayList<>();
            for(ReserveVenueProjectIntervalReport report :incomeReport.getProjectIntervalReports()){
                for(ReserveVenueProjectFieldIntervalReport field:report.getFieldIntervalReports()){
                    String[] o = new String[11];
                    o[0] = field.getReserveVenue().getName();
                    o[1] = field.getReserveProject().getName();
                    o[2] = field.getReserveField().getName();
                    o[3] = String.valueOf(field.getFieldBillStoredCard());
                    o[4] = String.valueOf(field.getFieldBillCash());
                    o[5] = String.valueOf(field.getFieldBillBankCard());
                    o[6] = String.valueOf(field.getFieldBillWeiXin());
                    o[7] = String.valueOf(field.getFieldBillAliPay());
                    o[8] = String.valueOf(field.getFieldBillDue());
                    o[9] = String.valueOf(field.getFieldBillOther());
                    o[10] = String.valueOf(field.getBill());
                    contentList.add(o);
                }
            }
            Date now = new Date();
            ExcelInfo info = new ExcelInfo(response,"场馆收入明细"+ DateUtils.formatDate(now),titles,contentList);
            info.export();
        }
    }

    @RequestMapping(value = {"mobile/rv/list", ""})
    public @ResponseBody String mobileList(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String tenantId = "";
        ReserveVenue reserveVenue = new ReserveVenue();
        if(userId!=null&&!userId.equals("")){
            User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            tenantId = user.getCompany().getId();
            reserveVenue.setTenantId(tenantId);
        }
        reserveVenue.getSqlMap().put("dsf"," and 1=1 ");
        List<Map<String,String>> rtn = new ArrayList();
        List<ReserveVenue> list = reserveVenueService.findList(reserveVenue);
        if(list!=null){
            for(ReserveVenue v:list){
                int sum = 0;//总场地数，只算半场和没有半场的全场
                ReserveField field = new ReserveField();
                field.setReserveVenue(v);
                field.setTenantId(tenantId);
                field.setDelFlag("0");
                List<ReserveField> fields = reserveFieldService.findList(field);
                for(ReserveField f:fields){
                    ReserveFieldRelation r = new ReserveFieldRelation();
                    r.setParentField(f);
                    r.setTenantId(tenantId);
                    List<ReserveFieldRelation> rs = reserveFieldRelationService.findList(r);
                    if(rs==null||rs.size()<=0){
                        sum += 1;
                    }
                }
                //查询当前场地占用数
                ReserveVenueConsItem item = new ReserveVenueConsItem();
                item.setReserveVenue(v);
                int usedNum = reserveVenueConsItemService.getUsedVenueNum(item);
                int freeNum = sum -usedNum;
                float rate = (sum!=0)?usedNum*1.0f/sum:0;
                Map<String,String> venueNode = new HashMap();
                venueNode.put("venueName",v.getName());
                venueNode.put("venueId",v.getId());
                List<Map<String,String>> users = LoginController.users;
                String userName = "";
                for(Map<String,String> user : users){
                    if(user.get("venuesId").contains(v.getId())){
                        userName += user.get("userName")+";";
                    }
                }
                if(!"".equals(userName)){
                    userName.substring(0,userName.length()-1);
                }
                venueNode.put("managerName",userName);//从排班中获取
                venueNode.put("managerId","");//从排班中获取
                List<AttMain> attMains = new ArrayList();
                AttMainService attMainService = SpringContextHolder.getBean("attMainService");
                attMains = attMainService.getAttMain(v.getId(), "ReserveVenue", "venuePic");
                if(attMains.size()>0){
                    venueNode.put("imgUrl",rootURL+"/mechanism/file/image/"+attMains.get(0).getId());
                }else{
                    venueNode.put("imgUrl","");
                }
                venueNode.put("usedNum",String.valueOf(usedNum));
                venueNode.put("freeNum",String.valueOf(freeNum));
                venueNode.put("usedRate",String.valueOf((int)(rate*100)));
                rtn.add(venueNode);
            }
        }
        return JSONArray.toJSONString(rtn);
    }

    @RequestMapping(value = {"mobile/rv/detail", ""})
    public @ResponseBody String detail(HttpServletRequest request, HttpServletResponse response) {
        String venueId = request.getParameter("venueId");
        ReserveVenue venue = reserveVenueService.get(venueId);
        List<Map<String,String>> rtn = new ArrayList<>();
        if(venue!=null){
            ReserveField field = new ReserveField();
            ReserveVenueConsItem item = new ReserveVenueConsItem();
            item.setReserveVenue(venue);
            field.setReserveVenue(venue);
            List<Map<String,Object>> fieldList = reserveFieldService.getFieldNumByProject(field);
            List<Map<String,Object>> fieldUsedList = reserveVenueConsItemService.getUsedVenueNumByProject(item);
            for(Map<String,Object> f :fieldList){
                Map<String,String> node = new HashMap<>();
                long sum = (Long) f.get("num");
                node.put("projectId",String.valueOf(f.get("projectId")));
                node.put("projectName",String.valueOf(f.get("projectName")));
                node.put("sum",String.valueOf(sum));
                for(Map<String,Object> fu :fieldUsedList){
                    if(f.get("projectId").equals(fu.get("projectId"))){
                        long usedNum = (Long)fu.get("num");
                        node.put("usedNum",String.valueOf(fu.get("num")));
                        node.put("usedRate",String.valueOf(sum==0?0:usedNum*100/sum));
                    }
                }
                if(!node.containsKey("usedNum")){
                    node.put("usedNum","");
                }
                if(!node.containsKey("usedRate")){
                    node.put("usedRate","");
                }
                rtn.add(node);
            }
        }
        return JSONArray.toJSONString(rtn);
    }

    @RequestMapping(value = "mobile/venueAll")
    public @ResponseBody String venueAll(HttpServletRequest request) {
        List<Map<String,String>> rtn = new ArrayList<>();
        ReserveVenue reserveVenue = new ReserveVenue();
        String userId = request.getParameter("userId");
        if(userId!=null&&!userId.equals("")){
            User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            reserveVenue.setTenantId(user.getCompany().getId());
        }
        reserveVenue.getSqlMap().put("dsf"," and 1=1 ");
        List<ReserveVenue> vs = reserveVenueService.findList(reserveVenue);
        if(vs!=null){
            for(ReserveVenue v:vs){
                Map<String,String> node = new HashMap<>();
                node.put("venueId",v.getId());
                node.put("venueName",v.getName());
                rtn.add(node);
            }
        }
        return JSONArray.toJSONString(rtn);
    }
}