package com.bra.modules.cms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.AttitudeDao;
import com.bra.modules.cms.entity.Attitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 点赞Service
 * @author ddt
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class AttitudeService extends CrudService<AttitudeDao, Attitude> {
	@Autowired
	private AttitudeDao attitudeDao;

	public Attitude get(String id) {
		return super.get(id);
	}
	
	public List<Attitude> findList(Attitude attitude) {
		return super.findList(attitude);
	}
	
	public Page<Attitude> findPage(Page<Attitude> page, Attitude attitude) {
		return super.findPage(page, attitude);
	}
	
	@Transactional(readOnly = false)
	public void save(Attitude attitude) {
		super.save(attitude);
	}
	
	@Transactional(readOnly = false)
	public void delete(Attitude attitude) {
		super.delete(attitude);
	}

	public String getCount(Attitude attitude){
		return JSONArray.toJSONString(attitudeDao.getCount(attitude),SerializerFeature.WriteMapNullValue);
	}
	
}