package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCardStatementsDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.ReserveMemberDayReport;
import com.bra.modules.reserve.entity.form.ReserveMemberIntervalReport;
import com.bra.modules.reserve.entity.form.SearchForm;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * reserveService
 * @author jiangxingqi
 * @version 2016-01-16
 */
@Service
@Transactional(readOnly = true)
public class ReserveCardStatementsService extends CrudService<ReserveCardStatementsDao, ReserveCardStatements> {

	@Autowired
	private ReserveMemberService reserveMemberService;

	public ReserveCardStatements get(String id) {
		return super.get(id);
	}

	public List<ReserveCardStatements> findList(ReserveCardStatements reserveCardStatements) {
		return super.findList(reserveCardStatements);
	}

	public Page<ReserveCardStatements> findPage(Page<ReserveCardStatements> page, ReserveCardStatements reserveCardStatements) {
		return super.findPage(page, reserveCardStatements);
	}

	/**
	 * 个人消费明细
	 * @param page
	 * @param reserveCardStatements
     * @return
     */
	public Page<ReserveCardStatements> findPersonalStatementsPage(Page<ReserveCardStatements> page, ReserveCardStatements reserveCardStatements) {
		reserveCardStatements.setPage(page);
		List<ReserveCardStatements> list = dao.findPersonalStatements(reserveCardStatements);
		page.setList(list);
		return page;
	}
	/**
	 * 个人消费明细
	 * @param reserveCardStatements
	 * @return
	 */
	public List<ReserveCardStatements> findPersonalStatementsList(ReserveCardStatements reserveCardStatements) {
		List<ReserveCardStatements> list = dao.findPersonalStatements(reserveCardStatements);
		return list;
	}

	public List<ReserveMemberIntervalReport> memberIncomeCollectReport( ReserveMemberIntervalReport reserveMemberIntervalReport) {
		if (reserveMemberIntervalReport != null) {
			if (reserveMemberIntervalReport.getSqlMap().get("dsf") == null)
				reserveMemberIntervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
		}
		return dao.memberIncomeCollectReport(reserveMemberIntervalReport);
	}

	public List<ReserveMemberIntervalReport> memberIncomeIntervalReport( ReserveMemberIntervalReport reserveMemberIntervalReport) {
		if (reserveMemberIntervalReport != null) {
			if (reserveMemberIntervalReport.getSqlMap().get("dsf") == null)
				reserveMemberIntervalReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
		}
		List<ReserveMemberIntervalReport> intervalReports=dao.memberIncomeIntervalReport(reserveMemberIntervalReport);//场馆区间收入
		for(ReserveMemberIntervalReport intervalReport:intervalReports){
			List<ReserveMemberDayReport> list=this.memberIncomeDayReport(intervalReport);//场馆日收入
			intervalReport.setDayReports(list);
		}
		return intervalReports;
	}
	//会员收入日报表
	public List<ReserveMemberDayReport> memberIncomeDayReport( ReserveMemberIntervalReport reserveMemberIntervalReport) {
		List<ReserveMemberDayReport> list=new ArrayList<>();
		ReserveMemberDayReport reserveMemberDayReport=new ReserveMemberDayReport();
		if (reserveMemberDayReport != null) {
			if (reserveMemberDayReport.getSqlMap().get("dsf") == null)
				reserveMemberDayReport.getSqlMap().put("dsf", AuthorityUtils.getDsf("v.id"));
		}
		ReserveVenue venue=reserveMemberIntervalReport.getReserveVenue();
		reserveMemberDayReport.setReserveVenue(venue);//场馆
		ReserveProject project=reserveMemberIntervalReport.getReserveProject();
		reserveMemberDayReport.setReserveProject(project);//项目
		ReserveStoredcardMemberSet storedCard = reserveMemberIntervalReport.getStoredcardMemberSet();
		reserveMemberDayReport.setStoredcardMemberSet(storedCard);//储值卡类型

		Date startDate=reserveMemberIntervalReport.getStartDate();
		Date endDate=reserveMemberIntervalReport.getEndDate();

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		while(endCal.after(startCal)|| endCal.equals(startCal)){
			Date day =startCal.getTime();
			reserveMemberDayReport.setDay(day);
			List<ReserveMemberDayReport> dayReports=dao.memberIncomeDayReport(reserveMemberDayReport);
			if(dayReports==null){
				continue;
			}
			for (ReserveMemberDayReport report : dayReports) {
				Double bill=report.getBill();
				if (bill!=null && bill != 0.0) {
					list.add(report);
				}
			}
			startCal.add(Calendar.DATE,1);
		}
		return list;
	}
	@Transactional(readOnly = false)
	public void save(ReserveCardStatements reserveCardStatements) {
		super.save(reserveCardStatements);
	}

	@Transactional(readOnly = false)
	public void delete(ReserveCardStatements reserveCardStatements) {
		reserveCardStatements=this.get(reserveCardStatements);
		if(reserveCardStatements!=null){
			if("1".equals(reserveCardStatements.getTransactionType())){
				ReserveMember member = reserveCardStatements.getReserveMember();
				member=reserveMemberService.get(member);
				Double remainder = member.getRemainder();
				remainder-=reserveCardStatements.getTransactionVolume();
				member.setRemainder(remainder);
				reserveMemberService.save(member);//余额应减去充值金额
			}
			if("7".equals(reserveCardStatements.getTransactionType())){
				ReserveMember member = reserveCardStatements.getReserveMember();
				member=reserveMemberService.get(member);
				Double remainder = member.getRemainder();
				Integer residue=member.getResidue();
				remainder-=reserveCardStatements.getTransactionVolume();
				residue-=reserveCardStatements.getTransactionNum();
				member.setRemainder(remainder);
				member.setResidue(residue);
				reserveMemberService.save(member);//余额应减去充值金额
			}
			super.delete(reserveCardStatements);
		}
	}

	public BigDecimal rechargeOfToday(ReserveCardStatements reserveCardStatements) {
		return dao.rechargeOfToday(reserveCardStatements);
	}

	public BigDecimal rechargeOfMonth(ReserveCardStatements reserveCardStatements) {
		return dao.rechargeOfMonth(reserveCardStatements);
	}

	public List<Map<String,Object>> rechargeOfChart(ReserveCardStatements reserveCardStatements) {
		return dao.rechargeOfChart(reserveCardStatements);
	}
	/**
	 * APP 高管总收入 同时也是场馆收益统计
	 * @param  reserveCardStatements
	 * @return
	 */
	public List<Map<String,Object>> venueIncomeReport(ReserveCardStatements reserveCardStatements) {
		return dao.venueIncomeReport(reserveCardStatements);
	}

	public List<Map<String,Object>> fieldIncome(SearchForm searchForm) {
		return dao.fieldIncome(searchForm);
	}

	public List<Map<String,Object>> storeIncome(SearchForm searchForm) {
		return dao.storeIncome(searchForm);
	}
	/**
	 * APP 高管 商品收入
	 * @param searchForm
	 * @return
	 */
	public List<Map<String,Object>> commIncome(Map searchForm) {
		return dao.commIncome(searchForm);
	}
	public List<ReserveCommodity> commSell(SearchForm searchForm) {
		return dao.commSell(searchForm);
	}

	public List<Map<String,Object>> ticketIncome(SearchForm searchForm){ return dao.ticketIncome(searchForm);}

}