package com.bra.modules.reserve.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.reserve.entity.ReserveField;
import com.bra.modules.reserve.entity.ReserveVenueConsItem;
import com.bra.modules.reserve.entity.form.ReserveVenueReport;
import com.bra.modules.reserve.utils.AuthorityUtils;
import com.bra.modules.reserve.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.reserve.dao.ReserveVenueDao;

/**
 * 场馆管理Service
 *
 * @author 肖斌
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueService extends CrudService<ReserveVenueDao, ReserveVenue> {

    @Autowired
    private ReserveVenueDao dao;

    @Autowired
    private ReserveFieldService reserveFieldService;

    public ReserveVenue get(String id) {
        ReserveVenue reserveVenue = super.get(id);
        return reserveVenue;
    }

    public List<ReserveVenue> findList(ReserveVenue reserveVenue) {
        if (reserveVenue != null) {
            if (reserveVenue.getSqlMap().get("dsf") == null)
                reserveVenue.getSqlMap().put("dsf", AuthorityUtils.getDsf("a.id"));
        }
        return super.findList(reserveVenue);
    }

    public Page<ReserveVenue> findPage(Page<ReserveVenue> page, ReserveVenue reserveVenue) {
        return super.findPage(page, reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue) {
        super.save(reserveVenue);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenue reserveVenue, AttMainForm attMainForm) {
        super.save(reserveVenue);
        updateAttMain(reserveVenue, attMainForm);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenue reserveVenue) {
        super.delete(reserveVenue);
    }



    public List<ReserveVenueReport> report(ReserveVenue reserveVenue, Date month) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(month);//controller 默认传递本月
        int year=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH);
        int dayNumOfMonth=TimeUtils.getDaysByYearMonth(year,m);
        cal.set(Calendar.DAY_OF_MONTH,1);//设置第一天


        ReserveField reserveField=new ReserveField();
        reserveField.setReserveVenue(reserveVenue);
        List<ReserveField> reserveFieldList=reserveFieldService.findList(reserveField);

        List<ReserveVenueReport> list=new ArrayList<>();
        for(ReserveField field:reserveFieldList){
            for(int i = 0 ;  i < dayNumOfMonth; i++,cal.add(Calendar.DATE,1)){
                Date d=cal.getTime();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String df=simpleDateFormat.format(d);

                ReserveVenueConsItem reserveVenueConsItem=new ReserveVenueConsItem();
                HashMap map=new HashMap();
                map.put("dateSQL","and b.cons_date='"+df+"'");
                reserveVenueConsItem.setSqlMap(map);
                reserveVenueConsItem.setReserveField(field);
                reserveVenueConsItem.setReserveVenue(reserveVenue);
                List<ReserveVenueReport> reserveVenueReportList= dao.report(reserveVenueConsItem);
                if(reserveVenueReportList==null){
                    continue;
                }
                list.addAll(reserveVenueReportList);
            }
        }
        return list;
    }
}