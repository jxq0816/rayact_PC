package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 11Service
 * @author 11
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueConsItemService extends CrudService<ReserveVenueConsItemDao, ReserveVenueConsItem> {

	@Autowired
	private ReserveFieldRelationService reserveFieldRelationService;
	@Autowired
	private ReserveFieldService reserveFieldService;

	public ReserveVenueConsItem get(String id) {
		return super.get(id);
	}

	public List<ReserveVenueConsItem> findVenueConsByMoblie(ReserveVenueConsItem reserveVenueCons){
		return dao.findVenueConsByMoblie(reserveVenueCons);
	}
	public List<ReserveVenueConsItem> findList(ReserveVenueConsItem reserveVenueCons) {
		return super.findList(reserveVenueCons);
	}
	/*
	相关场地预订状态的查询
	 */
	public List<ReserveVenueConsItem> findRelationList(ReserveVenueConsItem item) {
		//操作类型(1:已预定,2:锁场,3:已取消,4:已结算)
		List<ReserveVenueConsItem> reserveVenueConsItemList=new ArrayList<>();
		reserveVenueConsItemList.addAll(super.findList(item));//本场地的预订状态
		ReserveField field=item.getReserveField();
		field=reserveFieldService.get(field);
		//查询相关父场地的预订状态
		ReserveFieldRelation reserveFieldParentRelation=new ReserveFieldRelation();
		reserveFieldParentRelation.setChildField(field);
		List<ReserveFieldRelation> parentRelationList = reserveFieldRelationService.findList(reserveFieldParentRelation);
		for(ReserveFieldRelation  relation: parentRelationList){
			ReserveField parentFiled=relation.getParentField();
			item.setReserveField(parentFiled);
			reserveVenueConsItemList.addAll(super.findList(item));
		}
		//查询相关子场地的预订状态
		ReserveFieldRelation reserveFieldChildRelation=new ReserveFieldRelation();
		reserveFieldChildRelation.setParentField(field);
		List<ReserveFieldRelation> childRelationList = reserveFieldRelationService.findList(reserveFieldChildRelation);
		for(ReserveFieldRelation  relation: childRelationList){
			ReserveField childFiled=relation.getChildField();
			item.setReserveField(childFiled);
			reserveVenueConsItemList.addAll(super.findList(item));
		}
		return reserveVenueConsItemList;
	}
	
	public Page<ReserveVenueConsItem> findPage(Page<ReserveVenueConsItem> page, ReserveVenueConsItem reserveVenueCons) {
		return super.findPage(page, reserveVenueCons);
	}
	
	@Transactional(readOnly = false)
	public void save(ReserveVenueConsItem reserveVenueCons) {
		super.save(reserveVenueCons);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReserveVenueConsItem reserveVenueCons) {
		super.delete(reserveVenueCons);
	}

	public List<ReserveVenueConsItem> findListByDate(ReserveVenueConsItem reserveVenueConsItem){
		return dao.findListByDate(reserveVenueConsItem);
	}
}