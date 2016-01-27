package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCardStatementsDao;
import com.bra.modules.reserve.entity.ReserveCardStatements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * reserveService
 * @author jiangxingqi
 * @version 2016-01-16
 */
@Service
@Transactional(readOnly = true)
public class ReserveCardStatementsService extends CrudService<ReserveCardStatementsDao, ReserveCardStatements> {

	public ReserveCardStatements get(String id) {
		return super.get(id);
	}

	public List<ReserveCardStatements> findList(ReserveCardStatements reserveCardStatements) {
		return super.findList(reserveCardStatements);
	}

	public Page<ReserveCardStatements> findPage(Page<ReserveCardStatements> page, ReserveCardStatements reserveCardStatements) {
		return super.findPage(page, reserveCardStatements);
	}

	public List<Map<String,Object>> findListByStoredCardType( ReserveCardStatements reserveCardStatements) {
		return dao.findListByStoredCardType(reserveCardStatements);
	}

	public List<Map<String,Object>> findListByCommodityType(ReserveCardStatements reserveCardStatements) {
		List<Map<String, Object>> list = dao.findListByCommodityType(reserveCardStatements);
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


	@Transactional(readOnly = false)
	public void save(ReserveCardStatements reserveCardStatements) {
		super.save(reserveCardStatements);
	}

	@Transactional(readOnly = false)
	public void delete(ReserveCardStatements reserveCardStatements) {
		super.delete(reserveCardStatements);
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
}