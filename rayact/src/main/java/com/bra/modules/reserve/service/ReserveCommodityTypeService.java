package com.bra.modules.reserve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveCommodityType;
import com.bra.modules.reserve.dao.ReserveCommodityTypeDao;

/**
 * 商品类别Service
 * @author jiangxingqi
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommodityTypeService extends CrudService<ReserveCommodityTypeDao, ReserveCommodityType> {

	public ReserveCommodityType get(String id) {
		return super.get(id);
	}
	
	public List<ReserveCommodityType> findList(ReserveCommodityType commodityType) {
		return super.findList(commodityType);
	}
	
	public Page<ReserveCommodityType> findPage(Page<ReserveCommodityType> page, ReserveCommodityType commodityType) {
		return super.findPage(page, commodityType);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCommodityType commodityType) {
		super.save(commodityType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCommodityType commodityType) {
		super.delete(commodityType);
	}
	
}