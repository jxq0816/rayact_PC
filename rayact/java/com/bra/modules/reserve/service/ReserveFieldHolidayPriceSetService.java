package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveFieldHolidayPriceSet;
import com.bra.modules.reserve.dao.ReserveFieldHolidayPriceSetDao;

/**
 * 场地价格设定（节假日价格）Service
 * @author 肖斌
 * @version 2016-01-08
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldHolidayPriceSetService extends CrudService<ReserveFieldHolidayPriceSetDao, ReserveFieldHolidayPriceSet> {

	public ReserveFieldHolidayPriceSet get(String id) {
		return super.get(id);
	}
	
	public List<ReserveFieldHolidayPriceSet> findList(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet) {
		return super.findList(reserveFieldHolidayPriceSet);
	}
	
	public Page<ReserveFieldHolidayPriceSet> findPage(Page<ReserveFieldHolidayPriceSet> page, ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet) {
		return super.findPage(page, reserveFieldHolidayPriceSet);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet) {
		super.save(reserveFieldHolidayPriceSet);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveFieldHolidayPriceSet reserveFieldHolidayPriceSet) {
		super.delete(reserveFieldHolidayPriceSet);
	}
	
}