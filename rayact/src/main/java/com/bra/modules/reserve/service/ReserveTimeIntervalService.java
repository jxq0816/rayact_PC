package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveTimeIntervalDao;
import com.bra.modules.reserve.entity.ReserveTimeInterval;
import com.bra.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 时令Service
 *
 * @author jiangxingqi
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class ReserveTimeIntervalService extends CrudService<ReserveTimeIntervalDao, ReserveTimeInterval> {

    public ReserveTimeInterval get(String id) {
        return super.get(id);
    }

    public List<ReserveTimeInterval> findList(ReserveTimeInterval reserveTimeInterval) {
        return super.findList(reserveTimeInterval);
    }

    public Page<ReserveTimeInterval> findPage(Page<ReserveTimeInterval> page, ReserveTimeInterval reserveTimeInterval) {
        return super.findPage(page, reserveTimeInterval);
    }

    @Transactional(readOnly = false)
    public void save(ReserveTimeInterval reserveTimeInterval) {
        super.save(reserveTimeInterval);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveTimeInterval reserveTimeInterval) {
        super.delete(reserveTimeInterval);
    }

    //判断时间处于哪个时令
    public ReserveTimeInterval findTimeInterval(int month, int day) {

        List<ReserveTimeInterval> timeIntervalList = this.findList(new ReserveTimeInterval());
        for (ReserveTimeInterval j : timeIntervalList) {
            int endMonth = j.getEndMonth();
            int startMonth = j.getStartMonth();
            int startDate = j.getStartDate();
            int endDate = j.getEndDate();
            //不跨年 夏令：4-1至10-31
            if (startMonth <= month && month <= endMonth) {
                if (startDate <= day && day <= endDate) {
                    return j;
                }
            }
            // 冬令：11-1 至 3-31
            //跨年第一种
            if (startMonth > endMonth) {
                //年初 如：系统时间(1):1月25日 (2):3月18日,介于冬令
                if ((month < endMonth) || (month == endMonth && day <= endDate)) {
                    return j;
                }
                //年终 如：系统时间(1)12月12日（2）11月18日,介于冬令
                if ((month > startMonth) || (month == startMonth && day >= startDate)) {
                    return j;
                }
            }
            //A令：11-8 至 11-1
            //跨年第二种
            if (startMonth == endMonth && startDate > endDate) {
                if ((month == startMonth && day > endDate && day < startDate) == false) {
                    return j;
                }
            }
        }
        return null;
    }

    //判断时间处于哪个时令
    public ReserveTimeInterval findTimeIntervalMobile(int month, int day,User user) {
        ReserveTimeInterval op = new ReserveTimeInterval();
        op.setTenantId(user.getCompany().getId());
        List<ReserveTimeInterval> timeIntervalList = this.findList(op);
        for (ReserveTimeInterval j : timeIntervalList) {
            int endMonth = j.getEndMonth();
            int startMonth = j.getStartMonth();
            int startDate = j.getStartDate();
            int endDate = j.getEndDate();
            //不跨年 夏令：4-1至10-31
            if (startMonth <= month && month <= endMonth) {
                if (startDate <= day && day <= endDate) {
                    return j;
                }
            }
            // 冬令：11-1 至 3-31
            //跨年第一种
            if (startMonth > endMonth) {
                //年初 如：系统时间(1):1月25日 (2):3月18日,介于冬令
                if ((month < endMonth) || (month == endMonth && day <= endDate)) {
                    return j;
                }
                //年终 如：系统时间(1)12月12日（2）11月18日,介于冬令
                if ((month > startMonth) || (month == startMonth && day >= startDate)) {
                    return j;
                }
            }
            //A令：11-8 至 11-1
            //跨年第二种
            if (startMonth == endMonth && startDate > endDate) {
                if ((month == startMonth && day > endDate && day < startDate) == false) {
                    return j;
                }
            }
        }
        return null;
    }
}