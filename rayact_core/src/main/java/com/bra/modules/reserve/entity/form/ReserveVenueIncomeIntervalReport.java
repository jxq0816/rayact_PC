package com.bra.modules.reserve.entity.form;


import com.bra.common.persistence.PayTypeEntity;

import java.util.Date;
import java.util.List;

/**
 * 场馆收入统计Entity
 *
 * @author jiangxingqi
 * @version 2015-12-29
 */
public class ReserveVenueIncomeIntervalReport extends PayTypeEntity<ReserveVenueIncomeIntervalReport> {

    private static final long serialVersionUID = 1L;


    private List<ReserveVenueProjectIntervalReport> projectIntervalReports;

    private Date startDate;//开始日期

    private Date endDate;//结束日期

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public List<ReserveVenueProjectIntervalReport> getProjectIntervalReports() {
        return projectIntervalReports;
    }

    public void setProjectIntervalReports(List<ReserveVenueProjectIntervalReport> projectIntervalReports) {
        this.projectIntervalReports = projectIntervalReports;
    }
}