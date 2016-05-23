package com.bra.modules.cms.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.ActivityDao;
import com.bra.modules.cms.entity.Activity;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 活动Service
 * @author ddt
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class ActivityService extends CrudService<ActivityDao, Activity> {

	public Activity get(String id) {
		return super.get(id);
	}
	
	public List<Activity> findList(Activity activity) {
		return super.findList(activity);
	}
	
	public Page<Activity> findPage(Page<Activity> page, Activity activity) {
		return super.findPage(page, activity);
	}
	
	@Transactional(readOnly = false)
	public void save(Activity activity) {
		super.save(activity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Activity activity) {
		super.delete(activity);
	}

	@Transactional(readOnly = false)
	public void save(Activity activity, AttMainForm attMainForm) {
		if (activity.getContent() != null) {
			activity.setContent(StringEscapeUtils.unescapeHtml4(
					activity.getContent()));
		}
		super.save(activity);
		updateAttMain(activity, attMainForm);
	}
	
}