/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectFieldDayReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectFieldIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueProjectIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveVenueTotalIntervalReport;

import java.util.List;
import java.util.Map;

/**
 * 场馆管理DAO接口
 * @author 肖斌
 * @version 2015-12-29
 */
@MyBatisDao
public interface ReserveVenueDao extends CrudDao<ReserveVenue> {
    //场馆详情
    Map getForApp(ReserveVenue reserveVenue);
    /*场馆图片查询*/
    List<Map> findImgPathList(ReserveVenue reserveVenue);
    /*场馆基本信息查询*/
    List<Map> findListForApp(ReserveVenue reserveVenue);
    /*场馆有哪些项目*/
    List<Map> findProjectListOfVenueForApp(ReserveVenue reserveVenue);
    /*总收入统计*/
    List<ReserveVenueTotalIntervalReport> totalIncomeReport(ReserveVenueTotalIntervalReport intervalTotalReport);
    /*查询 场馆下的项目*/
    List<ReserveVenueProjectIntervalReport> findVenueProjectList(ReserveVenueProjectIntervalReport reserveVenueProjectIntervalReport);

    List<ReserveVenueProjectFieldDayReport> dayReport(ReserveVenueProjectFieldDayReport dayReport);
   /* 场地包场收入*/
    List<ReserveVenueProjectIntervalReport> reserveVenueProjectBlockIntervalReport(ReserveVenueProjectIntervalReport reserveVenueProjectIntervalReport);
    /* 场地散客收入*/
    List<ReserveVenueProjectIntervalReport> reserveVenueProjectDividedIntervalReport(ReserveVenueProjectIntervalReport reserveVenueProjectIntervalReport);

    List<ReserveVenueProjectFieldIntervalReport> reserveVenueProjectFieldIntervalReport(ReserveVenueProjectIntervalReport reserveVenueProjectIntervalReport);
}