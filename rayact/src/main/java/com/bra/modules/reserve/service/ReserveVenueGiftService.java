package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveVenueGiftDao;
import com.bra.modules.reserve.entity.ReserveVenueGift;
import com.bra.modules.reserve.entity.form.VenueGiftForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiaobin on 16/1/27.
 */
@Service
@Transactional(readOnly = true)
public class ReserveVenueGiftService extends CrudService<ReserveVenueGiftDao, ReserveVenueGift> {

    public ReserveVenueGift get(String id) {
        return super.get(id);
    }

    public List<ReserveVenueGift> findList(ReserveVenueGift reserveVenueGift) {
        return super.findList(reserveVenueGift);
    }

    public Page<ReserveVenueGift> findPage(Page<ReserveVenueGift> page, ReserveVenueGift reserveVenueGift) {
        return super.findPage(page, reserveVenueGift);
    }

    @Transactional(readOnly = false)
    public void save(ReserveVenueGift reserveVenueGift) {
        super.save(reserveVenueGift);
    }

    @Transactional(readOnly = false)
    public void delete(ReserveVenueGift reserveVenueGift) {
        super.delete(reserveVenueGift);
    }

    @Transactional(readOnly = false)
    public void saveVenueList(VenueGiftForm giftForm, String modelKey) {
        List<ReserveVenueGift> venueGiftList = giftForm.getGiftList();
        for (ReserveVenueGift gift : venueGiftList) {
            if(StringUtils.isBlank(gift.getModelId())){
                continue;
            }
            gift.setModelKey(modelKey);
            gift.preInsert();
            dao.insert(gift);
        }
    }

}
