package com.bra.modules.mechanism.service;

import com.bra.common.service.CrudService;
import com.bra.modules.mechanism.dao.CollectionDao;
import com.bra.modules.mechanism.entity.MCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2016/2/24.
 */
@Service
@Transactional(readOnly = true)
public class CollectionService extends CrudService<CollectionDao,MCollection> {

    //取消收藏
    @Transactional(readOnly = false)
    public void cancel(MCollection collection){
        dao.cancel(collection);
    }
    @Transactional(readOnly = false)
    public void add(MCollection collection){
        dao.cancel(collection);
        super.save(collection);
    }

    public MCollection findByModelIdKeyUser(String memberId,String modelId,String modelKey){
        return dao.findByModelIdKeyUser(new MCollection(memberId,modelId,modelKey));
    }
}
