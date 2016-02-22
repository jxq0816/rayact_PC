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