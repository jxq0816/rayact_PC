package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveVenueDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectDayReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectMonthReport;
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

    //获得month月的日保表
    public List<ReserveVenueProjectDayReport> dayReport(ReserveVenue reserveVenue, ReserveProject project, Date month) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(month);//controller 默认传递本月
        int year = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int dayNumOfMonth = TimeUtils.getDaysByYearMonth(year, m);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置第一天


        ReserveField reserveField = new ReserveField();
        reserveField.setReserveVenue(reserveVenue);


        List<ReserveVenueProjectDayReport> list = new ArrayList<>();

        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
            Date d = cal.getTime();
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

    public List<ReserveVenueProjectMonthReport> monthReport(ReserveVenue reserveVenue, Date month) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(month);//controller 默认传递本月
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置第一天

        ReserveField reserveField = new ReserveField();
        reserveField.setReserveVenue(reserveVenue);

        Date d = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String df = simpleDateFormat.format(d);

        ReserveVenueProjectMonthReport monthReport = new ReserveVenueProjectMonthReport();
        HashMap map = new HashMap();
        map.put("monthSQL", "and DATE_FORMAT(b.cons_date,'%Y-%m')='" + df + "'");
        monthReport.setSqlMap(map);
        monthReport.setReserveVenue(reserveVenue);
        List<ReserveVenueProjectMonthReport> list = dao.monthReport(monthReport);
        for (ReserveVenueProjectMonthReport mr : list) {
            ReserveProject project = mr.getReserveProject();
            List<ReserveVenueProjectDayReport> dayReports = this.dayReport(reserveVenue, project, month);
            mr.setDayReportList(dayReports);
        }
        return list;
    }
}