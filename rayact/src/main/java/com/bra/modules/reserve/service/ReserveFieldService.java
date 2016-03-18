package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveFieldDao;
import com.bra.modules.reserve.entity.*;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.web.form.HolidayPrice;
import com.bra.modules.reserve.web.form.RoutinePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 场地管理Service
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldService extends CrudService<ReserveFieldDao, ReserveField> {


    @Autowired
    private ReserveTimeIntervalService reserveTimeIntervalService;
    @Autowired
    private ReserveFieldPriceSetService reserveFieldPriceSetService;
    @Autowired
    private ReserveFieldHolidayPriceSetService reserveFieldHolidayPriceSetService;
    @Autowired
    private ReserveFieldRelationService relationService;
    @Autowired
    private ReserveFieldDao dao;

    public ReserveField get(String id) {
        ReserveField reserveField = super.get(id);
        return reserveField;
    }

    public List<ReserveField> findList(ReserveField reserveField) {
        List<ReserveField> list=super.findList(reserveField);
        return list;
    }
    //查找全场
    public List<ReserveField> findFullList(ReserveField reserveField) {
        List<ReserveField> list=super.findList(reserveField);
        List<ReserveField> fullList=new ArrayList<ReserveField>();
        for(ReserveField field:list){
            ReserveFieldRelation relation=new ReserveFieldRelation();
            relation.setChildField(field);
            List<ReserveFieldRelation> relations=relationService.findList(relation);//查找该场地是不是半场
            field.setReserveFieldRelationList(relations);//设置子半场
            if(relations==null||relations.size()==0){//
                fullList.add(field);
            }
        }
        return fullList;
    }

    public Page<ReserveField> findPage(Page<ReserveField> page, ReserveField reserveField) {
        return super.findPage(page, reserveField);
    }

    @Transactional(readOnly = false)
    public void save(ReserveField reserveField) {
        //全场与半场的关系保存
        ReserveFieldRelation relation = new ReserveFieldRelation();
        relation.setChildField(reserveField);//设置该厂为子半场
        ReserveField parentField = reserveField.getReserveParentField();
        if(StringUtils.isNotEmpty(parentField.getId())){//如果选择了父场地
            List<ReserveFieldRelation> relationDBList = relationService.findList(relation);//数据库查询是否已有关系
            if(relationDBList.size()==0){//没有则新建
                ReserveFieldRelation fieldRelation = new ReserveFieldRelation();
                fieldRelation.setChildField(reserveField);
                fieldRelation.setParentField(parentField);
                relationService.save(fieldRelation);
            }
            else {//已有则更新
                ReserveFieldRelation relationDB=relationDBList.get(0);
                relationDB.setParentField(parentField);
                relationService.save(relationDB);
            }
        }else{//如果没有选择父场地，则将数据库中已有的关系删除
            List<ReserveFieldRelation> relationDBList = relationService.findList(relation);//数据库查询是否已有关系
            if(relationDBList.size()!=0){//已有则删除
                ReserveFieldRelation relationDB=relationDBList.get(0);
                relationService.delete(relationDB);
            }
        }
        //如果不分时令，将系统原有分时令的价格信息删除
        if("0".equals(reserveField.getIsTimeInterval())){
            ReserveFieldPriceSet set=new ReserveFieldPriceSet();
            set.setReserveField(reserveField);
            List<ReserveTimeInterval> timeIntervalList = reserveTimeIntervalService.findList(new ReserveTimeInterval());
            for(ReserveTimeInterval i:timeIntervalList){
                for(ReserveFieldPriceSet j:reserveFieldPriceSetService.findList(set)){
                    j.setReserveTimeInterval(i);
                    reserveFieldPriceSetService.physicalDelete(j);//删除
                }
            }
        }
        super.save(reserveField);
    }

    @Transactional(readOnly = false)
    public void savePrice(ReserveField reserveField, RoutinePrice routinePrice, HolidayPrice holidayPrice, AttMainForm attMainForm) {
        super.updateAttMain(reserveField, attMainForm);

        //常规价格保存
        List<ReserveFieldPriceSet> fieldPriceSetList = routinePrice.getFieldPriceSetList();
        ReserveTimeInterval timeInterval=routinePrice.getReserveTimeInterval();
        if (!Collections3.isEmpty(fieldPriceSetList)) {
            for (ReserveFieldPriceSet fieldPriceSet : fieldPriceSetList) {
                fieldPriceSet.setReserveVenue(reserveField.getReserveVenue());
                fieldPriceSet.setReserveField(reserveField);
                fieldPriceSet.setReserveTimeInterval(timeInterval);//设置时令
                fieldPriceSet.setConsJson(getTimeJson(fieldPriceSet.getTimePriceList()));
                reserveFieldPriceSetService.save(fieldPriceSet);
            }
        }
        //按日期价格保存
        List<ReserveFieldHolidayPriceSet> holidayPriceSetList = holidayPrice.getFieldHolidayPriceSetList();
        if (!Collections3.isEmpty(holidayPriceSetList)) {
            for (ReserveFieldHolidayPriceSet holidayPriceSet : holidayPriceSetList) {
                if (holidayPriceSet.getStartDate() == null) {
                    continue;
                }
                holidayPriceSet.setReserveVenue(reserveField.getReserveVenue());
                holidayPriceSet.setReserveField(reserveField);
                holidayPriceSet.setConsJson(JsonUtils.writeObjectToJson(holidayPriceSet.getUserTypePriceList()));
                reserveFieldHolidayPriceSetService.save(holidayPriceSet);
            }
        }
    }


    private String getTimeJson(List<TimePrice> timePriceList) {
        return JsonUtils.writeObjectToJson(timePriceList);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveField reserveField) {
        super.delete(reserveField);
    }

}