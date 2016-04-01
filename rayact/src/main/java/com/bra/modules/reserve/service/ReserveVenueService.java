package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.*;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 场馆管理Service
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueService extends CrudService<ReserveVenueDao, ReserveVenue> {

    @Autowired
    private ReserveVenueDao dao;

    @Autowired
    private ReserveFieldService reserveFieldService;

    @Autowired
    private ReserveCardStatementsService reserveCardStatementsService;

    @Autowired
    private ReserveCommoditySellService reserveCommoditySellService;


    public ReserveVenue get(String id) {
        ReserveVenue reserveVenue = super.get(id);
        return reserveVenue;
    }

    public List<ReserveVenue> findList(ReserveVenue reserveVenue) {
        if (reserveVenue != null) {
            if (reserveVenue.getSqlMap().get("dsf") == null)
                reserveVenue.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.id"));
        }
        return super.findList(reserveVenue);
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
    public List<ReserveVenueProjectFieldDayReport> dayReport(ReserveVenueProjectFieldIntervalReport fieldReport){

        ReserveVenue venue=fieldReport.getReserveVenue();
        ReserveField field=fieldReport.getReserveField();
        ReserveProject project=fieldReport.getReserveProject();

        ReserveVenueProjectFieldDayReport dayReport=new ReserveVenueProjectFieldDayReport();
        dayReport.setReserveVenue(venue);
        dayReport.setReserveProject(project);
        dayReport.setReserveField(field);

        Date startDate=fieldReport.getStartDate();
        Date endDate=fieldReport.getEndDate();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);


        List<ReserveVenueProjectFieldDayReport> list = new ArrayList<>();

        while(endCal.after(startCal)|| endCal.equals(startCal)){

            Date day =startCal.getTime();
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
    //场馆收入统计
    public ReserveVenueIncomeIntervalReport reserveVenueIncomeIntervalReport(ReserveVenueProjectIntervalReport venueProjectReport) {
        Double billSum=0.0;
        Double storedCardSum=0.0;
        Double cashSum=0.0;
        Double bankCardSum=0.0;
        Double weiXinSum=0.0;
        Double aliPaySum=0.0;
        Double otherSum=0.0;
        Double dueSum=0.0;
        List<ReserveVenueProjectIntervalReport> venueProjectReports = dao.reserveVenueProjectIntervalReport(venueProjectReport);//场馆 项目 收入统计
        for (ReserveVenueProjectIntervalReport projectIntervalReport : venueProjectReports) {//项目遍历
            List<ReserveVenueProjectFieldIntervalReport> fieldReports = this.reserveVenueProjectFieldIntervalReport(projectIntervalReport);//场馆 项目 场地 收入统计
            projectIntervalReport.setFieldIntervalReports(fieldReports);
            billSum+=projectIntervalReport.getBill();
            storedCardSum+=projectIntervalReport.getFieldBillStoredCard();
            cashSum+=projectIntervalReport.getFieldBillCash();
            bankCardSum+=projectIntervalReport.getFieldBillBankCard();
            weiXinSum+=projectIntervalReport.getFieldBillWeiXin();
            aliPaySum+=projectIntervalReport.getFieldBillAliPay();
            otherSum+=projectIntervalReport.getFieldBillOther();
            dueSum+=projectIntervalReport.getFieldBillDue();
        }
        ReserveVenueIncomeIntervalReport venueReport=new ReserveVenueIncomeIntervalReport();
        venueReport.setBill(billSum);
        venueReport.setStoredCardBill(storedCardSum);
        venueReport.setCashBill(cashSum);
        venueReport.setBankCardBill(bankCardSum);
        venueReport.setWeiXinBill(weiXinSum);
        venueReport.setAliPayBill(aliPaySum);
        venueReport.setOtherBill(otherSum);
        venueReport.setDueBill(dueSum);
        venueReport.setProjectIntervalReports(venueProjectReports);
        return venueReport;
    }

    /**
     * 场馆 项目 场地 收入统计
     *
     */
    public List<ReserveVenueProjectFieldIntervalReport> reserveVenueProjectFieldIntervalReport(ReserveVenueProjectIntervalReport projectIntervalReport) {

        List<ReserveVenueProjectFieldIntervalReport> filedReports = dao.reserveVenueProjectFieldIntervalReport(projectIntervalReport);
        for(ReserveVenueProjectFieldIntervalReport report:filedReports){

            List<ReserveVenueProjectFieldDayReport> dayReports=this.dayReport(report);
            report.setDayReports(dayReports);
        }
        return filedReports;
    }

    public ReserveVenueTotalIntervalReport totalIncomeReport(ReserveVenueTotalIntervalReport intervalTotalReport) {

        ReserveVenue reserveVenue=intervalTotalReport.getReserveVenue();
        Date startDate=intervalTotalReport.getStartDate();
        Date endDate=intervalTotalReport.getEndDate();
        Double billSum=0.0;
        Double storedCardSum=0.0;
        Double cashSum=0.0;
        Double bankCardSum=0.0;
        Double weiXinSum=0.0;
        Double aliPaySum=0.0;
        Double otherSum=0.0;
        Double dueSum=0.0;
        //场馆收入
        ReserveVenueProjectIntervalReport intervalReport=new ReserveVenueProjectIntervalReport();
        intervalReport.setReserveVenue(reserveVenue);
        intervalReport.setStartDate(startDate);
        intervalReport.setEndDate(endDate);
        List<ReserveVenueProjectIntervalReport> list1 = dao.reserveVenueProjectIntervalReport(intervalReport);
        for (ReserveVenueProjectIntervalReport report : list1) {
            if(report!=null) {
                billSum += report.getBill();
                storedCardSum += report.getFieldBillStoredCard();
                cashSum += report.getFieldBillCash();
                bankCardSum += report.getFieldBillBankCard();
                weiXinSum += report.getFieldBillWeiXin();
                aliPaySum += report.getFieldBillAliPay();
                otherSum += report.getFieldBillOther();
                dueSum += report.getFieldBillDue();
            }
        }
       /* //会员收入
        ReserveMemberIntervalReport intervalMemberReport=new ReserveMemberIntervalReport();
        intervalMemberReport.setReserveVenue(reserveVenue);
        intervalMemberReport.setStartDate(startDate);
        intervalMemberReport.setEndDate(endDate);
        List<ReserveMemberIntervalReport> memberCollectReports=reserveCardStatementsService.memberIncomeCollectReport(intervalMemberReport);
        for(ReserveMemberIntervalReport report:memberCollectReports){
            if(report!=null){
                billSum+=report.getBill();
                storedCardSum+=report.getStoredCardBill();
                cashSum+=report.getCashBill();
                bankCardSum+=report.getBankCardBill();
                weiXinSum+=report.getWeiXinBill();
                aliPaySum+=report.getAliPayBill();
                otherSum+=report.getOtherBill();
                dueSum+=report.getDueBill();
            }

        }*/
        //商品收入
        ReserveCommodityIntervalReport reserveCommodityIntervalReport=new  ReserveCommodityIntervalReport();
        reserveCommodityIntervalReport.setReserveVenue(reserveVenue);
        reserveCommodityIntervalReport.setEndDate(endDate);
        reserveCommodityIntervalReport.setStartDate(startDate);
        List<ReserveCommodityIntervalReport> commodityIntervalReports=reserveCommoditySellService.commodityIncomeCollectReport(reserveCommodityIntervalReport);
        for(ReserveCommodityIntervalReport report:commodityIntervalReports){
            if(report!=null) {
                billSum += report.getBill();
                storedCardSum += report.getStoredCardBill();
                cashSum += report.getCashBill();
                bankCardSum += report.getBankCardBill();
                weiXinSum += report.getWeiXinBill();
                aliPaySum += report.getAliPayBill();
                otherSum += report.getOtherBill();
                dueSum += report.getDueBill();
            }
        }

        intervalTotalReport.setBill(billSum);
        intervalTotalReport.setStoredCardBill(storedCardSum);
        intervalTotalReport.setCashBill(cashSum);
        intervalTotalReport.setBankCardBill(bankCardSum);
        intervalTotalReport.setWeiXinBill(weiXinSum);
        intervalTotalReport.setAliPayBill(aliPaySum);
        intervalTotalReport.setOtherBill(otherSum);
        intervalTotalReport.setDueBill(dueSum);
        return intervalTotalReport;
    }
}