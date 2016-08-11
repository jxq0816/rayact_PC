package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.*;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.server.ServerConfig;
import com.bra.modules.util.BaiduAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 场馆管理Service
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueService extends CrudService<ReserveVenueDao, ReserveVenue> {

    @Autowired
    private ReserveVenueDao dao;
    @Autowired
    private ReserveMultiplePaymentService reserveMultiplePaymentService;

    public ReserveVenue get(String id) {
        ReserveVenue reserveVenue = super.get(id);
        return reserveVenue;
    }
    public  Map getForApp(ReserveVenue reserveVenue){
        Map venue = dao.getForApp(reserveVenue);//获得所有场馆的信息
        List<Map> projectList=dao.findProjectListOfVenueForApp(reserveVenue);
        for(Map project:projectList){
            project.remove("venueId");
        }
        List<Map> imgList=findImgList(reserveVenue);
        int imgCnt=imgList.size();
        String cnt=Integer.toString(imgCnt);
        venue.put("imgCnt",cnt);
        venue.put("projectList",projectList);
        return venue;
    }

    public List<ReserveVenue> findList(ReserveVenue reserveVenue) {
        if (reserveVenue != null) {
            if (reserveVenue.getSqlMap().get("dsf") == null)
                reserveVenue.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.id"));
        }
        return super.findList(reserveVenue);
    }

    public List<Map> findListForApp(ReserveVenue reserveVenue) {//查询参数中没有id

        List<Map> venueList = dao.findListForApp(reserveVenue);//获得所有场馆的信息
        List<Map> venueListRS = new ArrayList<>();//结果
        if(StringUtils.isNoneEmpty(reserveVenue.getProjectId())){//有项目筛选条件
            List<Map> projectList=dao.findProjectListOfVenueForApp(reserveVenue);//有项目筛选条件
            for(Map venueMap:venueList){//遍历所有的场馆
                String venueId=(String)venueMap.get("venueId");//场馆的编号
                for(Map projectMap:projectList){//拥有用户所选择项目的场馆
                    String x=(String)projectMap.get("venueId");
                    if(venueId.equals(x)){
                        //加载图片
                        venueMap=this.loadImg(venueId,venueMap);
                       /*计算距离*/
                        venueMap=this.loadDistance(reserveVenue,venueMap);
                        venueListRS.add(venueMap);
                    }
                }
            }
        }else{
            for (Map venueMap : venueList) {
                String venueId = (String) venueMap.get("venueId");
                /*加载图片*/
                venueMap=this.loadImg(venueId,venueMap);
                /*计算距离*/
                venueMap=this.loadDistance(reserveVenue,venueMap);
                venueListRS.add(venueMap);

            }
        }
        return venueListRS;
    }
    public Map loadDistance(ReserveVenue reserveVenue,Map venueMap){
        Double longitude=reserveVenue.getLongitude();//用户的经度
        Double latitude=reserveVenue.getLatitude();//用户的维度
        if(longitude!=null && latitude !=null){
            if(venueMap.get("addressX")!=null&&venueMap.get("addressY")!=null){
                String addressX=venueMap.get("addressX").toString();
                String addressY=venueMap.get("addressY").toString();
                if(StringUtils.isNotEmpty(addressX)){
                    double addX=Double.parseDouble(addressX);//用户的经度
                    double addY=Double.parseDouble(addressY);//用户的维度
                    String distance= BaiduAPI.getDistance(longitude,latitude,addX,addY);
                    venueMap.put("distance",distance);
                }else{
                    venueMap.put("distance","0");
                }
            }
        }
        venueMap.remove("addressX");
        venueMap.remove("addressY");
        return venueMap;
    }
    //首页 加载图片
    public Map loadImg(String venueId,Map venueMap){
        ReserveVenue venue = new ReserveVenue();
        venue.setId(venueId);
        /*加载图片*/
        List<Map> imgList = dao.findImgPathList(venue);
        if(imgList!=null){
            Map img=imgList.get(0);
            if(img!=null){
                String imgId=(String)img.get("imgId");
                String pre="http://"+ServerConfig.ip+":"+ServerConfig.port+"/mechanism/file/image/";
                String url=pre+imgId;
                venueMap.put("imgUrl", url);
            }
        }
        return venueMap;
    }
    //更多图片
    public List<Map> findImgList(ReserveVenue reserveVenue) {
        List<Map> imgIdList = dao.findImgPathList(reserveVenue);
        List<Map> imgList=new ArrayList<>();
        for(Map img:imgIdList){
            String imgId=(String)img.get("imgId");
            String pre="http://"+ServerConfig.ip+":"+ServerConfig.port+"/mechanism/file/image/";
            String url=pre+imgId;
            Map map=new HashMap<>();
            map.put("imgUrl", url);
            imgList.add(map);
        }
        return imgList;
    }

    public Page<ReserveVenue> findPage(Page<ReserveVenue> page, ReserveVenue reserveVenue) {
        return super.findPage(page, reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue) {
        super.save(reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue, AttMainForm attMainForm) {
        super.save(reserveVenue);
        updateAttMain(reserveVenue, attMainForm);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenue reserveVenue) {
        super.delete(reserveVenue);
    }

    //日报表
    public List<ReserveVenueProjectFieldDayReport> dayReport(ReserveVenueProjectFieldIntervalReport fieldReport) {

        ReserveVenue venue = fieldReport.getReserveVenue();
        ReserveField field = fieldReport.getReserveField();
        ReserveProject project = fieldReport.getReserveProject();

        ReserveVenueProjectFieldDayReport dayReport = new ReserveVenueProjectFieldDayReport();
        dayReport.setReserveVenue(venue);
        dayReport.setReserveProject(project);
        dayReport.setReserveField(field);

        Date startDate = fieldReport.getStartDate();
        Date endDate = fieldReport.getEndDate();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);


        List<ReserveVenueProjectFieldDayReport> list = new ArrayList<>();

        while (endCal.after(startCal) || endCal.equals(startCal)) {

            Date day = startCal.getTime();
            dayReport.setDay(day);
            List<ReserveVenueProjectFieldDayReport> dayReportList = dao.dayReport(dayReport);
            if (dayReportList == null) {
                continue;
            }
            for (ReserveVenueProjectFieldDayReport report : dayReportList) {

                Double bill = report.getBill();
                if (bill != 0.0) {
                    list.add(report);
                }
            }
            startCal.add(Calendar.DATE, 1);
        }
        return list;
    }

    /* 场地散客收入*/
    public List<ReserveVenueProjectIntervalReport> reserveVenueProjectDividedIntervalReport(ReserveVenueProjectIntervalReport reserveVenueProjectIntervalReport) {
        List<ReserveVenueProjectIntervalReport> venueProjectDividedReports = dao.reserveVenueProjectDividedIntervalReport(reserveVenueProjectIntervalReport);//场馆 项目 散客 收入统计
        return venueProjectDividedReports;
    }

    //场地收入统计
    public ReserveVenueIncomeIntervalReport reserveVenueIncomeIntervalReport(ReserveVenueProjectIntervalReport venueProjectReport,String queryType) {
        /*最下面的总合计*/
        Double billSum = 0.0;
        Double storedCardSum = 0.0;
        Double cashSum = 0.0;
        Double bankCardSum = 0.0;
        Double transferSum = 0.0;
        Double weiXinSum = 0.0;
        Double personalWeiXinSum = 0.0;
        Double aliPaySum = 0.0;
        Double personalAliPaySum = 0.0;
        Double otherSum = 0.0;
        Double dueSum = 0.0;
        if (venueProjectReport != null) {
            if (venueProjectReport.getSqlMap().get("dsf") == null)
                venueProjectReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
        }
        List<ReserveVenueProjectIntervalReport> venueProjectList = dao.findVenueProjectList(venueProjectReport);//查询场馆下的所有场地

        List<ReserveVenueProjectIntervalReport> venueProjectBlockReports = dao.reserveVenueProjectBlockIntervalReport(venueProjectReport);//场馆 项目 包场 收入统计
        List<ReserveVenueProjectIntervalReport> venueProjectDividedReports = dao.reserveVenueProjectDividedIntervalReport(venueProjectReport);//场馆 项目 散客 收入统计
        for (ReserveVenueProjectIntervalReport i : venueProjectList) {// 遍历 场馆和项目 并求和 这次遍历主要是为了求汇总界面的数据
            ReserveProject project = i.getReserveProject();
            ReserveVenue venue = i.getReserveVenue();
             /*场地项目 收入合计*/
            Double bill = 0.0;
            Double storedCard = 0.0;
            Double cash = 0.0;
            Double bankCard = 0.0;
            Double transfer = 0.0;
            Double weiXin = 0.0;
            Double personalWeiXin = 0.0;
            Double aliPay = 0.0;
            Double personalAliPay = 0.0;
            Double other = 0.0;
            Double due = 0.0;
            //将包场收入加入
            for (ReserveVenueProjectIntervalReport j : venueProjectBlockReports) {
                if (j.getReserveProject().getId().equals(project.getId()) && j.getReserveVenue().getId().equals(venue.getId())) {
                    bill += j.getBill();
                    storedCard += j.getStoredCardBill();
                    cash += j.getCashBill();
                    bankCard += j.getBankCardBill();
                    transfer+=j.getTransferBill();
                    weiXin += j.getWeiXinBill();
                    personalWeiXin+=j.getPersonalWeiXinBill();
                    aliPay += j.getAliPayBill();
                    personalAliPay+=j.getPersonalAliPayBill();
                    other += j.getOtherBill();
                    due += j.getDueBill();
                }
            }
            //将散客收入加入
            for (ReserveVenueProjectIntervalReport k : venueProjectDividedReports) {
                if (k.getReserveProject().getId().equals(project.getId()) && k.getReserveVenue().getId().equals(venue.getId())) {
                    bill += k.getBill();
                    storedCard += k.getStoredCardBill();
                    cash += k.getCashBill();
                    bankCard += k.getBankCardBill();
                    transfer+=k.getTransferBill();
                    weiXin += k.getWeiXinBill();
                    personalWeiXin+=k.getPersonalWeiXinBill();
                    personalAliPay+=k.getPersonalAliPayBill();
                    aliPay += k.getAliPayBill();
                    other += k.getOtherBill();
                    due += k.getDueBill();
                }
            }
             /*场地项目 收入合计 保存*/
            i.setStoredCardBill(storedCard);
            i.setBill(bill);
            i.setCashBill(cash);
            i.setBankCardBill(bankCard);
            i.setTransferBill(transfer);
            i.setWeiXinBill(weiXin);
            i.setPersonalWeiXinBill(personalWeiXin);
            i.setAliPayBill(aliPay);
            i.setPersonalAliPayBill(personalAliPay);
            i.setOtherBill(other);
            i.setDueBill(due);
            //最下面的总合计 计算
            billSum += bill;
            storedCardSum += storedCard;
            cashSum += cash;
            bankCardSum += bankCard;
            transferSum+=transfer;
            weiXinSum += weiXin;
            personalWeiXinSum+=personalWeiXin;
            aliPaySum += aliPay;
            personalAliPaySum+=personalAliPay;
            otherSum += other;
            dueSum += due;
        }
        /*明细 开始*/
        if("2".equals(queryType)){
            for (ReserveVenueProjectIntervalReport i : venueProjectList) {//场馆 项目遍历
                List<ReserveVenueProjectFieldIntervalReport> fieldReports = this.reserveVenueProjectFieldIntervalReport(i);//查询A场馆B项目下的场地 收入统计明细
                i.setFieldIntervalReports(fieldReports);//场馆 项目 再精确到几号场地
            }
        }

        /*明细 结束*/
        //场地 项目 总合计设置
        ReserveVenueIncomeIntervalReport venueReport = new ReserveVenueIncomeIntervalReport();
        venueReport.setBill(billSum);
        venueReport.setStoredCardBill(storedCardSum);
        venueReport.setCashBill(cashSum);
        venueReport.setBankCardBill(bankCardSum);
        venueReport.setTransferBill(transferSum);
        venueReport.setWeiXinBill(weiXinSum);
        venueReport.setPersonalWeiXinBill(personalWeiXinSum);
        venueReport.setAliPayBill(aliPaySum);
        venueReport.setPersonalAliPayBill(personalAliPaySum);
        venueReport.setOtherBill(otherSum);
        venueReport.setDueBill(dueSum);
        venueReport.setProjectIntervalReports(venueProjectList);//设置场馆项目的统计
        return venueReport;
    }

    /**
     * 查询A场馆B项目下的场地 收入统计明细
     */
    public List<ReserveVenueProjectFieldIntervalReport> reserveVenueProjectFieldIntervalReport(ReserveVenueProjectIntervalReport projectIntervalReport) {
        if (projectIntervalReport != null) {
            if (projectIntervalReport.getSqlMap().get("dsf") == null)
                projectIntervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("s.venue_id"));
        }
        ///查询A场馆B项目下的场地 收入统计明细 该方法只能获得单方式付款的收入
        List<ReserveVenueProjectFieldIntervalReport> filedReports = dao.reserveVenueProjectFieldIntervalReport(projectIntervalReport);
        ///查询A场馆B项目下的场地 收入统计明细 该方法累加多方式付款的收入
        for(ReserveVenueProjectFieldIntervalReport i: filedReports){
            //如果该场地存在多方式付款
            if(i.getMultiplePaymentBill()!=0.0){
                ReserveVenueProjectFieldIntervalReport multiple=reserveMultiplePaymentService.reserveFieldMultiplePaymentReport(i);
                i.setStoredCardBill(i.getStoredCardBill()+multiple.getStoredCardBill());
                i.setCashBill(i.getCashBill()+multiple.getCashBill());
                i.setBankCardBill(i.getBankCardBill()+multiple.getBankCardBill());
                i.setWeiXinBill(i.getWeiXinBill()+multiple.getWeiXinBill());
                i.setPersonalWeiXinBill(i.getPersonalWeiXinBill()+multiple.getPersonalWeiXinBill());
                i.setAliPayBill(i.getAliPayBill()+multiple.getAliPayBill());
                i.setPersonalAliPayBill(i.getPersonalAliPayBill()+multiple.getPersonalAliPayBill());
            }
        }
        return filedReports;
    }

    public List<ReserveVenueTotalIntervalReport> totalIncomeReport(ReserveVenueTotalIntervalReport intervalTotalReport) {
        if (intervalTotalReport != null) {
            if (intervalTotalReport.getSqlMap().get("dsf") == null)
                intervalTotalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
        }
        List<ReserveVenueTotalIntervalReport> list = dao.totalIncomeReport(intervalTotalReport);
        return list;
    }
}