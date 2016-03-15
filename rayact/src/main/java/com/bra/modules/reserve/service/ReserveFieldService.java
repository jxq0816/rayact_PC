package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.JsonUtils;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.dao.ReserveFieldDao;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveFieldHolidayPriceSet;
import com.bra.modules.reserve.entity.ReserveFieldPriceSet;
import com.bra.modules.reserve.entity.ReserveFieldRelation;
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
        super.save(reserveField);
    }

    @Transactional(readOnly = false)
    public void save(ReserveField reserveField, RoutinePrice routinePrice, HolidayPrice holidayPrice, AttMainForm attMainForm) {
        super.save(reserveField);
        super.updateAttMain(reserveField, attMainForm);

        //常规价格保存
        List<ReserveFieldPriceSet> fieldPriceSetList = routinePrice.getFieldPriceSetList();
        if (!Collections3.isEmpty(fieldPriceSetList)) {
            for (ReserveFieldPriceSet fieldPriceSet : fieldPriceSetList) {
                fieldPriceSet.setReserveVenue(reserveField.getReserveVenue());
                fieldPriceSet.setReserveField(reserveField);
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