package com.bra.modules.reserve.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.CheckUtils;
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
    private String rootURL="http://"+Global.getConfig("server.ip")+":"+Global.getConfig("server.port");
    //private String rootURL = "http://101.200.148.75:8080";
    @Autowired
    private ReserveFieldRelationService reserveFieldRelationService;
    @Autowired
    private ReserveVenueService reserveVenueService;
    @Autowired
    private ReserveFieldService reserveFieldService;
    @Autowired
    private ReserveVenueConsItemService reserveVenueConsItemService;
    @Autowired
    private ReserveRoleService reserveRoleService;
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
    public String totalIncomeReport(HttpServletRequest request,ReserveVenueTotalIntervalReport intervalTotalReport, Model model) {
        String check = request.getParameter("isChecked");
        if("true".equals(check)){
            CheckUtils.saveCheckRecord(request,"totalIncomeReport");
        }
        //检查是否已经审核过
        request.setAttribute("checkStatus",CheckUtils.hasCheckRecord(request,"totalIncomeReport"));
        if(StringUtils.isEmpty(intervalTotalReport.getQueryType())){
            intervalTotalReport.setQueryType("0");//默认流水
        }
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

    @RequestMapping(value = "fieldReport")
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
        ReserveVenueIncomeIntervalReport incomeReport=reserveVenueService.reserveVenueIncomeIntervalReport(venueProjectReport,queryType);//场馆 项目 区间报表
        incomeReport.setStartDate(startDate);
        incomeReport.setEndDate(endDate);
        model.addAttribute("incomeReport",incomeReport);//收入统计
        model.addAttribute("venueProjectReport",venueProjectReport);//查询参数回传
        List<ReserveVenue> reserveVenueList=reserveVenueService.findList(new ReserveVenue());//场馆列表
        model.addAttribute("reserveVenueList",reserveVenueList);//场馆列表
        List<ReserveProject> reserveProjectList=reserveProjectService.findList(new ReserveProject());//项目列表
        model.addAttribute("reserveProjectList",reserveProjectList);//收入统计
        if("1".equals(queryType)){
            return "reserve/report/venueFieldIncomeReport";
        }else{
            List<ReserveVenueProjectIntervalReport> venueProjectDividedReports = reserveVenueService.reserveVenueProjectDividedIntervalReport(venueProjectReport);//场馆 项目 散客 收入统计
            model.addAttribute("venueProjectDividedReports",venueProjectDividedReports);//查询参数回传
            return "reserve/report/venueFieldIncomeDetailReport";
        }
    }

    @RequestMapping(value = "reportExport")
    public void reportExport(HttpServletResponse response,ReserveVenueProjectIntervalReport venueProjectReport,
                             @RequestParam(required=false,defaultValue="1",value="queryType") String queryType
    )throws Exception {
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
        ReserveVenueIncomeIntervalReport incomeReport=reserveVenueService.reserveVenueIncomeIntervalReport(venueProjectReport,queryType);//场馆区间报表
        incomeReport.setStartDate(startDate);
        incomeReport.setEndDate(endDate);
        if("1".equals(queryType)){
            String[] titles = {"场馆","项目","储值卡","现金","银行卡","转账","微信","微信（个人）","支付宝","支付宝（个人）","优惠券","合计",};
            List<String[]> contentList = new ArrayList<>();
            for(ReserveVenueProjectIntervalReport report :incomeReport.getProjectIntervalReports()){
                String[] o = new String[12];
                o[0] = report.getReserveVenue().getName();
                o[1] = report.getReserveProject().getName();
                o[2] = String.valueOf(report.getStoredCardBill());
                o[3] = String.valueOf(report.getCashBill());
                o[4] = String.valueOf(report.getBankCardBill());
                o[5] = String.valueOf(report.getTransferBill());
                o[6] = String.valueOf(report.getWeiXinBill());
                o[7] = String.valueOf(report.getPersonalWeiXinBill());
                o[8] = String.valueOf(report.getAliPayBill());
                o[9] = String.valueOf(report.getPersonalAliPayBill());
                o[10] = String.valueOf(report.getOtherBill());
                o[11] = String.valueOf(report.getBill());
                contentList.add(o);
            }
            String[] o = new String[12];
            o[0] = "合计";
            o[1] = "";
            o[2] = String.valueOf(incomeReport.getStoredCardBill());
            o[3] = String.valueOf(incomeReport.getCashBill());
            o[4] = String.valueOf(incomeReport.getBankCardBill());
            o[5] = String.valueOf(incomeReport.getTransferBill());
            o[6] = String.valueOf(incomeReport.getWeiXinBill());
            o[7] = String.valueOf(incomeReport.getPersonalWeiXinBill());
            o[8] = String.valueOf(incomeReport.getAliPayBill());
            o[9] = String.valueOf(incomeReport.getPersonalAliPayBill());
            o[10] = String.valueOf(incomeReport.getOtherBill());
            o[11] = String.valueOf(incomeReport.getBill());
            contentList.add(o);
            Date now = new Date();
            ExcelInfo info = new ExcelInfo(response,"场地收入汇总"+ DateUtils.formatDate(now),titles,contentList);
            info.export();
        }else{
            String[] titles = {"场馆","项目","场地","储值卡","现金","银行卡","转账","微信","微信（个人）","支付宝","支付宝（个人）","优惠券","合计"};
            List<String[]> contentList = new ArrayList<>();
            for(ReserveVenueProjectIntervalReport report :incomeReport.getProjectIntervalReports()){
                for(ReserveVenueProjectFieldIntervalReport field:report.getFieldIntervalReports()){
                    String[] o = new String[13];
                    o[0] = field.getReserveVenue().getName();
                    o[1] = field.getReserveProject().getName();
                    o[2] = field.getReserveField().getName();
                    o[3] = String.valueOf(field.getStoredCardBill());
                    o[4] = String.valueOf(field.getCashBill());
                    o[5] = String.valueOf(field.getBankCardBill());
                    o[6] = String.valueOf(field.getTransferBill());
                    o[7] = String.valueOf(field.getWeiXinBill());
                    o[8] = String.valueOf(incomeReport.getPersonalWeiXinBill());
                    o[9] = String.valueOf(field.getAliPayBill());
                    o[10] = String.valueOf(incomeReport.getPersonalAliPayBill());
                    o[11] = String.valueOf(field.getOtherBill());
                    o[12] = String.valueOf(field.getBill());
                    contentList.add(o);
                }
            }
            Date now = new Date();
            ExcelInfo info = new ExcelInfo(response,"场地收入明细"+ DateUtils.formatDate(now),titles,contentList);
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
            //场馆权限过滤
            if (reserveVenue.getSqlMap().get("dsf") == null) {
                ReserveRole reserveRole = new ReserveRole();
                reserveRole.setTenantId(tenantId);
                reserveRole.setUser(user);
                List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
                String venues = AuthorityUtils.getVenueIds(venueIds, "a.id");
                reserveVenue.getSqlMap().put("dsf", venues);
            }
        }
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
                item.setTenantId(tenantId);
                int usedNum = reserveVenueConsItemService.getUsedVenueNum(item);
                int freeNum = sum -usedNum;
                float rate = (sum!=0)?usedNum*1.0f/sum:0;
                Map<String,String> venueNode = new HashMap();
                venueNode.put("venueName",v.getName());
                venueNode.put("venueId",v.getId());
                List<Map<String,String>> users = LoginController.users;
                String userName = "";
                String userIds = "";
                String mobile = "";
                for(Map<String,String> user : users){
                    if(user.get("venuesId").contains(v.getId())){
                        userName += user.get("userName")+",";
                        userIds += user.get("userId")+",";
                        mobile += user.get("mobile")+",";
                    }
                }
                if(!"".equals(userName)&&!"".equals(userIds)){
                    userName = userName.substring(0,userName.length()-1);
                    userIds = userIds.substring(0,userIds.length()-1);
                    mobile = mobile.substring(0,mobile.length()-1);
                }
                venueNode.put("managerName",userName);//从排班中获取
                venueNode.put("managerId",userIds);//从排班中获取
                venueNode.put("mobile",mobile);
                List<AttMain> attMains = new ArrayList();
                AttMainService attMainService = SpringContextHolder.getBean("attMainService");
                attMains = attMainService.getAttMain(v.getId(), "ReserveVenue", "venuePic");
                if(attMains.size()>0){
                    /*rootURL="http://"+Global.getConfig("server.ip")+":"+Global.getConfig("server.port");*/
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
                String url = getProjectImgUrl(String.valueOf(f.get("projectName")));
                node.put("projectUrl",url);
                if(!node.containsKey("usedNum")){
                    node.put("usedNum","0");
                }
                if(!node.containsKey("usedRate")){
                    node.put("usedRate","");
                }
                rtn.add(node);
            }
        }
        return JSONArray.toJSONString(rtn);
    }

    /**
     * 场地列表
     * @param request
     * @return
     */
    @RequestMapping(value = "mobile/venueAll")
    public @ResponseBody String venueAll(HttpServletRequest request) {
        List<Map<String,String>> rtn = new ArrayList<>();
        ReserveVenue reserveVenue = new ReserveVenue();
        String userId = request.getParameter("userId");
        if(userId!=null&&!userId.equals("")){
            User user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            reserveVenue.setTenantId(user.getCompany().getId());
            //场馆权限过滤
            if (reserveVenue.getSqlMap().get("dsf") == null) {
                ReserveRole reserveRole = new ReserveRole();
                reserveRole.setTenantId(user.getCompany().getId());
                reserveRole.setUser(user);
                List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
                String venues = AuthorityUtils.getVenueIds(venueIds, "a.id");
                reserveVenue.getSqlMap().put("dsf", venues);
            }
        }
        List<ReserveVenue> vs = reserveVenueService.findList(reserveVenue);
        if(vs!=null){
            for(ReserveVenue v:vs){
                Map<String,String> node = new HashMap<>();
                node.put("venueId",v.getId());
                node.put("venueName",v.getName());
             /*   node.put("venueBg","/static/images/venue_bg.png");*/
                rtn.add(node);
            }
        }
        return JSONArray.toJSONString(rtn);
    }

    private String getProjectImgUrl(String name){
        if("篮球".equals(name)){
            return rootURL+"/static/images/basketball.png";
        }else if("足球".equals(name)){
            return rootURL+"/static/images/football.png";
        }else if("网球".equals(name)){
            return rootURL+"/static/images/tennis.png";
        }else{
            return rootURL+"/static/images/basketball.png";
        }
    }
    @RequestMapping(value = {"mobile/rv/fieldAll", ""})
    public @ResponseBody String fieldAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rtn = new JSONObject();
        String userId = request.getParameter("userId");
        String tenantId = "";
        ReserveVenue reserveVenue = new ReserveVenue();
        User user = null;
        if(userId!=null&&!userId.equals("")){
            user = SpringContextHolder.getBean(SystemService.class).getUser(userId);
            tenantId = user.getCompany().getId();
            reserveVenue.setTenantId(tenantId);
        }
        int sum = 0;//总场地数，只算半场和没有半场的全场
        ReserveField field = new ReserveField();
        field.setTenantId(tenantId);
        field.setDelFlag("0");
        //场馆权限过滤
        if (field.getSqlMap().get("dsf") == null) {
            ReserveRole reserveRole = new ReserveRole();
            reserveRole.setTenantId(tenantId);
            reserveRole.setUser(user);
            List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
            String venues = AuthorityUtils.getVenueIds(venueIds, "v.id");
            field.getSqlMap().put("dsf", venues);
        }
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
        item.setTenantId(tenantId);
        if (item.getSqlMap().get("dsf") == null) {
            ReserveRole reserveRole = new ReserveRole();
            reserveRole.setTenantId(tenantId);
            reserveRole.setUser(user);
            List<String> venueIds = reserveRoleService.findVenueIdsByRole(reserveRole);
            String venues = AuthorityUtils.getVenueIds(venueIds, "i.venue_id");
            item.getSqlMap().put("dsf", venues);
        }
        int usedNum = reserveVenueConsItemService.getUsedVenueNum(item);
        Map<String,String> venueNode = new HashMap();
        rtn.put("userName",user.getName());
        List<AttMain> attMains = new ArrayList();
        rtn.put("usedNum",String.valueOf(usedNum));
        rtn.put("sumNum",String.valueOf(sum));
        return rtn.toJSONString();
    }
}