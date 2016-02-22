package com.bra.modules.reserve.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bra.modules.reserve.dao.ReserveCommoditySellDetailDao;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetail;
import com.bra.modules.reserve.entity.form.ReserveCommoditySellReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveCommoditySell;
import com.bra.modules.reserve.dao.ReserveCommoditySellDao;

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

	public List<Map<String,Object>> commodityIncomeRatioReport(ReserveCardStatements reserveCardStatements) {
		List<Map<String, Object>> list = dao.commodityIncomeRatioReport(reserveCardStatements);
		double total=0;
		for(Map<String,Object> map : list){
			double saleAmount=(double)map.get("saleAmount");
			total=total+saleAmount;
		}
		for(Map<String,Object> map : list){
			double saleAmount=(double)map.get("saleAmount");
			double rate=(saleAmount/total)*100;
			BigDecimal   rateBig   =   new   BigDecimal(rate);
			double  r= rateBig.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();//保留两位小数
			map.put("saleRate",r);
		}
		return list;
	}
}