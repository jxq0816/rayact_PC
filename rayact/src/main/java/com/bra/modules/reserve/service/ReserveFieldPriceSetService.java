package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.Collections3;
import com.bra.modules.reserve.dao.ReserveFieldPriceSetDao;
import com.bra.modules.reserve.entity.ReserveFieldPriceSet;
import com.bra.modules.reserve.entity.form.TimePrice;
import com.bra.modules.reserve.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 场地价格Service
 *
 * @author 肖斌
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class ReserveFieldPriceSetService extends CrudService<ReserveFieldPriceSetDao, ReserveFieldPriceSet> {

    @Autowired
    private ReserveFieldPriceSetDao dao;

    public ReserveFieldPriceSet get(String id) {
        return super.get(id);
    }

    public List<ReserveFieldPriceSet> findList(ReserveFieldPriceSet reserveFieldPriceSet) {
        return super.findList(reserveFieldPriceSet);
    }

    public Page<ReserveFieldPriceSet> findPage(Page<ReserveFieldPriceSet> page, ReserveFieldPriceSet reserveFieldPriceSet) {
        return super.findPage(page, reserveFieldPriceSet);
    }

    @Transactional(readOnly = false)
    public void save(ReserveFieldPriceSet reserveFieldPriceSet) {
        super.save(reserveFieldPriceSet);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveFieldPriceSet reserveFieldPriceSet) {
        super.delete(reserveFieldPriceSet);
    }


    @Transactional(readOnly = false)
    public void physicalDelete(ReserveFieldPriceSet reserveFieldPriceSet) {
        dao.physicalDelete(reserveFieldPriceSet);
    }


    public List<ReserveFieldPriceSet> findListByField(ReserveFieldPriceSet reserveFieldPriceSet) {

        //查询 一：周一至周五 1：散客 2：会员 二：周六 1：散客 2：会员 三：周日 1：散客 2：会员 对应的价格设置
        List<ReserveFieldPriceSet> fieldPriceSetList = super.findList(reserveFieldPriceSet);

        if (Collections3.isEmpty(fieldPriceSetList)) {//如果数据库中没有相关数据
            fieldPriceSetList = Lists.newArrayList();
            Map<String, String> weekMap = Maps.newConcurrentMap();
            weekMap.put("1", "周一至周五");
            weekMap.put("2", "周六");
            weekMap.put("3", "周日");

            Map<String, String> memberMap = Maps.newConcurrentMap();
            memberMap.put("1", "散客");
            memberMap.put("2", "会员");
            //memberMap.put("3", "团体");

            ReserveFieldPriceSet fieldPriceSet;
            for (String weekKey : weekMap.keySet()) {
                for (String memberKey : memberMap.keySet()) {
                    fieldPriceSet = new ReserveFieldPriceSet();
                    //fieldPriceSet.setVenueId(reserveFieldPriceSet.getVenueId());
                    //fieldPriceSet.setFieldId(reserveFieldPriceSet.getFieldId());
                    fieldPriceSet.setWeek(weekKey);
                    fieldPriceSet.setConsType(memberKey);
                    fieldPriceSet.setTimePriceList(getTimePrice());
                    fieldPriceSetList.add(fieldPriceSet);
                }
            }
        }
        return fieldPriceSetList;
    }

    private List<TimePrice> getTimePrice() {
        List<TimePrice> timePriceList = Lists.newArrayList();
        try {
            List<String> times = TimeUtils.getTimeSpacList("06:00:00", "24:00:00", 60);
            TimePrice timePrice;
            for (String time : times) {
                timePrice = new TimePrice();
                timePrice.setTime(time);
                timePriceList.add(timePrice);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("时间获取异常");
        }
        return timePriceList;
    }

}