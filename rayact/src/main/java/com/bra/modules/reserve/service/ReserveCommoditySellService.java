package com.bra.modules.reserve.service;

import com.bra.common.persistence.ConstantEntity;
import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCommoditySellDao;
import com.bra.modules.reserve.dao.ReserveCommoditySellDetailDao;
import com.bra.modules.reserve.dao.ReserveMemberDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.ReserveCommodityDayReport;
import com.bra.modules.reserve.entity.form.ReserveCommodityIntervalReport;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import com.bra.modules.reserve.utils.AuthorityUtils;
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
	@Autowired
	private ReserveCardStatementsService statementsService;
	@Autowired
	private ReserveCommodityService reserveCommodityService;

	@Autowired
	private ReserveMemberDao reserveMemberDao;

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
		ReserveCommoditySellDetail detail=new ReserveCommoditySellDetail();
		detail.setReserveCommoditySell(reserveCommoditySell);
		List<ReserveCommoditySellDetail> detailList = sellDetailDao.findList(detail);
		for(ReserveCommoditySellDetail i:detailList){
			ReserveCommodity commodity = i.getReserveCommodity();//买的啥
			int cnt=i.getNum();//买了多少
			commodity=reserveCommodityService.get(commodity);
			commodity.setRepertoryNum(commodity.getRepertoryNum()+cnt);//入库
			reserveCommodityService.save(commodity);
			sellDetailDao.delete(i);//明细删除
			ReserveCardStatements statement = i.getReserveCardStatement();
			if(statement!=null){
				statementsService.delete(i.getReserveCardStatement());//流水删除
			}
		}

		String payType=reserveCommoditySell.getPayType();
		if(ConstantEntity.STOREDCARD.equals(payType)){
			ReserveMember member = reserveCommoditySell.getReserveMember();
			member=reserveMemberDao.get(member);
			Double totalSum=reserveCommoditySell.getTotalSum();
			member.setRemainder(member.getRemainder()+totalSum);
			reserveMemberDao.update(member);//金额退回
		}

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
		if (reserveCommodityIntervalReport != null) {
			if (reserveCommodityIntervalReport.getSqlMap().get("dsf") == null)
				reserveCommodityIntervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
		}
		List<ReserveCommodityIntervalReport> list = dao.commodityIncomeCollectReport(reserveCommodityIntervalReport);
		return list;
	}
	public List<ReserveCommodityIntervalReport> reserveCommodityIncomeIntervalReport(ReserveCommodityIntervalReport reserveCommodityIntervalReport){
		if (reserveCommodityIntervalReport != null) {
			if (reserveCommodityIntervalReport.getSqlMap().get("dsf") == null)
				reserveCommodityIntervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("d.id"));
		}
		List<ReserveCommodityIntervalReport> intervalReports=dao.reserveCommodityIncomeIntervalReport(reserveCommodityIntervalReport);

		for(ReserveCommodityIntervalReport intervalReport:intervalReports){
			List<ReserveCommodityDayReport> list=this.reserveCommodityIncomeDayReport(intervalReport);
			intervalReport.setDayReportList(list);
		}
		return intervalReports;
	}

	public List<ReserveCommodityDayReport> reserveCommodityIncomeDayReport(ReserveCommodityIntervalReport intervalReport){
		if (intervalReport != null) {
			if (intervalReport.getSqlMap().get("dsf") == null)
				intervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("d.id"));
		}

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