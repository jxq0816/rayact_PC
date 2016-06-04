package com.bra.modules.reserve.dao;

import com.bra.common.persistence.CrudDao;
import com.bra.common.persistence.annotation.MyBatisDao;
import com.bra.modules.reserve.entity.ReserveCommodity;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.form.ReserveMemberDayReport;
import com.bra.modules.reserve.entity.form.ReserveMemberIntervalReport;
import com.bra.modules.reserve.entity.form.SearchForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * reserveDAO接口
 * @author jiangxingqi
 * @version 2016-01-16
 */
@MyBatisDao
public interface ReserveCardStatementsDao extends CrudDao<ReserveCardStatements> {

     BigDecimal rechargeOfToday(ReserveCardStatements reserveCardStatements);

     BigDecimal rechargeOfMonth(ReserveCardStatements reserveCardStatements);

     List<ReserveMemberIntervalReport> memberIncomeCollectReport(ReserveMemberIntervalReport reserveMemberIntervalReport);

     List<ReserveMemberIntervalReport> memberIncomeIntervalReport(ReserveMemberIntervalReport reserveMemberIntervalReport);

     List<Map<String,Object>> rechargeOfChart(ReserveCardStatements reserveCardStatements);

     List<Map<String,Object>> venueIncomeReport(ReserveCardStatements reserveCardStatements);

     List<ReserveMemberDayReport> memberIncomeDayReport(ReserveMemberDayReport reserveMemberDayReport);

     List<Map<String,Object>> fieldIncome(SearchForm searchForm);

     List<Map<String,Object>> storeIncome(SearchForm searchForm);

     List<Map<String,Object>> commIncome(Map searchForm);

     List<ReserveCommodity> commSell(SearchForm searchForm);

     List<Map<String,Object>> ticketIncome(SearchForm searchForm);

     List<ReserveCardStatements> findPersonalStatements(ReserveCardStatements searchForm);

}