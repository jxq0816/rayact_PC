package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.modules.reserve.dao.ReserveMenuDao;
import com.bra.modules.reserve.entity.ReserveMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单配置Service
 * @author jiangxingqi
 * @version 2016-07-05
 */
@Service
@Transactional(readOnly = true)
public class ReserveMenuService extends CrudService<ReserveMenuDao, ReserveMenu> {

	public ReserveMenu get(String id) {
		return super.get(id);
	}

	public List<ReserveMenu> findList(ReserveMenu reserveMenu) {
		return super.findList(reserveMenu);
	}

	public Page<ReserveMenu> findPage(Page<ReserveMenu> page, ReserveMenu reserveMenu) {
		return super.findPage(page, reserveMenu);
	}

	@Transactional(readOnly = false)
	public void save(ReserveMenu reserveMenu) {
		super.save(reserveMenu);
	}

	@Transactional(readOnly = false)
	public void delete(ReserveMenu reserveMenu) {
		super.delete(reserveMenu);
	}

}