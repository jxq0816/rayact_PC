package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectDayReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectIntervalReport;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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

    //获得month月的日报表
    public List<ReserveVenueProjectDayReport> dayReport(ReserveVenueProjectIntervalReport intervalReport){

        ReserveVenue reserveVenue=intervalReport.getReserveVenue();
        ReserveProject project=intervalReport.getReserveProject();

        Date startDate=intervalReport.getStartDate();
        Date endDate=intervalReport.getEndDate();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        ReserveField reserveField = new ReserveField();
        reserveField.setReserveVenue(reserveVenue);


        List<ReserveVenueProjectDayReport> list = new ArrayList<>();

        while(endCal.after(startCal.getTime())){
            startCal.add(Calendar.DATE, 1);
            Date d =startCal.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String df = simpleDateFormat.format(d);

            ReserveVenueProjectDayReport dayReport = new ReserveVenueProjectDayReport();
            HashMap map = new HashMap();
            map.put("dateSQL", "and b.cons_date='" + df + "'");
            dayReport.setSqlMap(map);
            dayReport.setReserveProject(project);
            dayReport.setReserveVenue(reserveVenue);
            List<ReserveVenueProjectDayReport> dayReportList = dao.dayReport(dayReport);
            if (dayReportList == null) {
                continue;
            }
            for (ReserveVenueProjectDayReport report : dayReportList) {

                Double storedCardSum = report.getFieldBillStoredCard();
                Double cashSum = report.getFieldBillCash();
                Double bankCardSum = report.getFieldBillBankCard();
                Double weiXinSum = report.getFieldBillWeiXin();
                Double aliPaySum = report.getFieldBillAliPay();
                //Double dueSum = report.getFieldBillDue();//欠账
                Double otherSum = report.getFieldBillOther();

                if (storedCardSum == 0.0 && cashSum == 0.0 && bankCardSum == 0.0 && weiXinSum == 0.0 && aliPaySum == 0.0 && otherSum == 0.0) {
                    //销售额为0
                } else {
                    list.add(report);
                }
            }
        }
        return list;
    }

    public List<ReserveVenueProjectIntervalReport> intervalReports(ReserveVenueProjectIntervalReport intervalReport) {

        List<ReserveVenueProjectIntervalReport> list = dao.intervalReports(intervalReport);
        for (ReserveVenueProjectIntervalReport mr : list) {
            List<ReserveVenueProjectDayReport> dayReports = this.dayReport(intervalReport);
            mr.setDayReportList(dayReports);
        }
        return list;
    }
}