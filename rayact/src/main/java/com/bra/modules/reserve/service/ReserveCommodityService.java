package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.IdGen;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveCommodityDao;
import com.bra.modules.reserve.entity.ReserveCommodity;
import com.bra.modules.reserve.utils.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品Service
 * @author jiangxingqi
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommodityService extends CrudService<ReserveCommodityDao, ReserveCommodity> {

	public ReserveCommodity get(String id) {
		return super.get(id);
	}

	public List<ReserveCommodity> checkCommodityId(ReserveCommodity commodity) {
		List<ReserveCommodity> list=dao.checkCommodityId(commodity);
		return list;
	}
	
	public List<ReserveCommodity> findList(ReserveCommodity commodity) {
		if (commodity != null) {
			if (commodity.getSqlMap().get("dsf") == null) {
				String dsf = AuthorityUtils.getDsf("v.id");
				commodity.getSqlMap().put("dsf", dsf);
			}
		}
		return super.findList(commodity);
	}
	
	public Page<ReserveCommodity> findPage(Page<ReserveCommodity> page, ReserveCommodity commodity) {
		commodity.setPage(page);
		page.setList(this.findList(commodity));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveCommodity commodity) {
		if(StringUtils.isEmpty(commodity.getCommodityId())){
			commodity.setCommodityId(IdGen.uuid());
		}
		super.save(commodity);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveCommodity commodity) {
		super.delete(commodity);
	}
	
}