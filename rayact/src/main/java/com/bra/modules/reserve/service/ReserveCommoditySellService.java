package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCommoditySellDao;
import com.bra.modules.reserve.dao.ReserveCommoditySellDetailDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.ReserveCommodityDayReport;
import com.bra.modules.reserve.entity.form.ReserveCommodityIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品销售主表Service
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommoditySellService extends CrudService<ReserveCommoditySellDao, ReserveCommoditySell> {

	@Autowired
	private ReserveCommoditySellDetailDao sellDetailDao;

	public ReserveCommoditySell get(String id) {
		return super.get(id);
	}

	public List<ReserveCommoditySell> findList(ReserveCommoditySell reserveCommoditySell) {
		return super.findList(reserveCommoditySell);
	}

	public BigDecimal sellOfMonth(ReserveCardStatements reserveCardStatements){
		return dao.findSellOfMonth(reserveCardStatements);
	}

	public BigDecimal sellOfToday(ReserveCardStatements reserveCardStatements){
		return dao.sellOfToday(reserveCardStatements);
	}

	public List<Map<String, Object>> sellOfChart(ReserveCardStatements reserveCardStatements){
		return dao.sellOfChart(reserveCardStatements);
	}

	public Page<ReserveCommoditySell> findPage(Page<ReserveCommoditySell> page, ReserveCommoditySell reserveCommoditySell) {
		return super.findPage(page, reserveCommoditySell);
	}

	@Transactional(readOnly = false)
	public void save(ReserveCommoditySell reserveCommoditySell) {
		super.save(reserveCommoditySell);
	}

	@Transactional(readOnly = false)
	public void delete(ReserveCommoditySell reserveCommoditySell) {
		super.delete(reserveCommoditySell);
	}

	public ReserveCommoditySellReport sellReport(ReserveCommoditySell reserveCommoditySell){
		ReserveCommoditySellDetail reserveCommoditySellDetail=new ReserveCommoditySellDetail();
		reserveCommoditySellDetail.setReserveCommoditySell(reserveCommoditySell);
		List<ReserveCommoditySellDetail> sellDetailList=sellDetailDao.findSellDetailList(reserveCommoditySellDetail);
		ReserveCommoditySellReport sellReport=new ReserveCommoditySellReport();
		sellReport.setSellDetailList(sellDetailList);

		Double sum=0.0;
		for(ReserveCommoditySellDetail sellDetail:sellDetailList){
			double detailSum=sellDetail.getDetailSum();
			sum+=detailSum;
		}
		sellReport.setTotalSum(sum);
		return sellReport;
	}

	public List<ReserveCommodityIntervalReport> commodityIncomeCollectReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport) {
		List<ReserveCommodityIntervalReport> list = dao.commodityIncomeCollectReport(reserveCommodityIntervalReport);
		return list;
	}
	public List<ReserveCommodityIntervalReport> reserveCommodityIncomeIntervalReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport){
		List<ReserveCommodityIntervalReport> intervalReports=dao.reserveCommodityIncomeIntervalReport(reserveCommodityIntervalReport);

		for(ReserveCommodityIntervalReport intervalReport:intervalReports){
			List<ReserveCommodityDayReport> list=this.reserveCommodityIncomeDayReport(intervalReport);
			intervalReport.setDayReportList(list);
		}
		return intervalReports;
	}

	public List<ReserveCommodityDayReport> reserveCommodityIncomeDayReport(ReserveCommodityIntervalReport intervalReport){
		List<ReserveCommodityDayReport> list=new ArrayList<>();

		ReserveCommodityDayReport reserveCommodityDayReport=new ReserveCommodityDayReport();
		ReserveVenue venue=intervalReport.getReserveVenue();
		ReserveCommodityType commodityType= intervalReport.getReserveCommodityType();
		ReserveCommodity reserveCommodity=intervalReport.getReserveCommodity();

		reserveCommodityDayReport.setReserveVenue(venue);
		reserveCommodityDayReport.setReserveCommodity(reserveCommodity);
		reserveCommodityDayReport.setReserveCommodityType(commodityType);

		Date startDate=intervalReport.getStartDate();
		Date endDate=intervalReport.getEndDate();

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		while(endCal.after(startCal)|| endCal.equals(startCal)){
			Date day =startCal.getTime();
			reserveCommodityDayReport.setDay(day);
			List<ReserveCommodityDayReport> dayReports=dao.reserveCommodityIncomeDayReport(reserveCommodityDayReport);
			if(dayReports==null){
				continue;
			}
			for (ReserveCommodityDayReport report : dayReports) {
				Double bill=report.getBill();
				if (bill != 0.0) {
					list.add(report);
				}
			}
			startCal.add(Calendar.DATE,1);
		}
		return list;
	}
}