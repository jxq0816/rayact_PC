package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveCommoditySellDetailDao;
import com.bra.modules.reserve.entity.ReserveCommoditySellDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品销售明细Service
 *
 * @author jiangxingqi
 * @version 2016-01-12
 */
@Service
@Transactional(readOnly = true)
public class ReserveCommoditySellDetailService extends CrudService<ReserveCommoditySellDetailDao, ReserveCommoditySellDetail> {

    public ReserveCommoditySellDetail get(String id) {
        return super.get(id);
    }

    public List<ReserveCommoditySellDetail> findList(ReserveCommoditySellDetail reserveCommoditySellDetail) {
        return super.findList(reserveCommoditySellDetail);
    }

    public Page<ReserveCommoditySellDetail> findPage(Page<ReserveCommoditySellDetail> page, ReserveCommoditySellDetail reserveCommoditySellDetail) {
        return super.findPage(page, reserveCommoditySellDetail);
    }

    public Page<ReserveCommoditySellDetail> findSellDetailList(Page<ReserveCommoditySellDetail> page, ReserveCommoditySellDetail reserveCommoditySellDetail) {
        reserveCommoditySellDetail.setPage(page);
        page.setList(dao.findSellDetailList(reserveCommoditySellDetail));
        return page;
    }

    public List<ReserveCommoditySellDetail> findSellDetailList(ReserveCommoditySellDetail reserveCommoditySellDetail) {
        return dao.findSellDetailList(reserveCommoditySellDetail);
    }

    @Transactional(readOnly = false)
    public void save(ReserveCommoditySellDetail reserveCommoditySellDetail) {
        super.save(reserveCommoditySellDetail);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveCommoditySellDetail reserveCommoditySellDetail) {
        super.delete(reserveCommoditySellDetail);
    }

}