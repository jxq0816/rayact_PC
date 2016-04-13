package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveVenueConsItemDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		List<ReserveVenueConsItem> list=super.findList(reserveVenueCons);
		return list;
	}
	/*
	相关场地预订状态的查询
	 */
	public List<ReserveVenueConsItem> findRelationList(ReserveVenueConsItem item) {
		//操作类型(1:已预定,2:锁场,3:已取消,4:已结算)
		List<ReserveVenueConsItem> reserveVenueConsItemList=new ArrayList<>();
		List<ReserveVenueConsItem> selfList=super.findList(item);
		reserveVenueConsItemList.addAll(selfList);//本场地的预订状态
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

	public boolean checkReserveTime(Date consDate,ReserveField field,String startTime,String endTime){
		Boolean bool=true;
		startTime=TimeUtils.earlyMorningFormat(startTime);//将凌晨00：00 转化为24:00
		endTime=TimeUtils.earlyMorningFormat(endTime);
		//查询该场地，当天的已预订时间
		ReserveVenueConsItem reserveVenueConsItem=new  ReserveVenueConsItem();
		reserveVenueConsItem.setReserveField(field);
		reserveVenueConsItem.setConsDate(consDate);//设置预订日期
		List<ReserveVenueConsItem> list = this.findRelationList(reserveVenueConsItem);//查询该场地在预订date的预订状态
		for(ReserveVenueConsItem item:list){
			String start=item.getStartTime();//已预订开始时间
			String end=item.getEndTime();//已预订结束时间
			start= TimeUtils.earlyMorningFormat(start);
			end=TimeUtils.earlyMorningFormat(end);
			//第一个判断条件 假设 预订时间为15:30-16:00 已预订时间为15:30-16:30 startTime=start=15:30 startTime=start不可预订
			//第二个判断条件 假设 预订时间为15:30-16:00  已预订时间为15:00-15:30 startTime=end=15:30 时间段正好错开 可预订
			if(startTime.compareTo(start)>=0 && startTime.compareTo(end)<0){//预订开始时间 介于 已预订区间
				bool=false;
				break;
			}
			//第一个判断条件 假设 预订时间为15:30-16:00  已预订时间为16:00-16:30 endTime=stat=16:00 时间段正好错开 可预订
			//第二个判断条件 假设 预订时间为15:30-16:30  已预订时间为16:00-16:30 endTime=end=16:30 不可预订
			if(endTime.compareTo(start)>0 && endTime.compareTo(end)<=0){//预订结束时间 介于 已预订区间
				bool=false;
				break;
			}
			if(startTime.compareTo(start)<0 && endTime.compareTo(end)>0){// 预订区间 包含 已预订区间
				bool=false;
				break;
			}
		}
		return bool;
	}

	public int getUsedVenueNum(ReserveVenueConsItem reserveVenueCons) {
		return dao.getUsedVenueNum(reserveVenueCons);
	}
	public List<Map<String,Object>> getUsedVenueNumByProject(ReserveVenueConsItem reserveVenueCons) {
		return dao.getUsedVenueNumByProject(reserveVenueCons);
	}
}