package com.bra.modules.reserve.service;

import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.StringUtils;
import com.bra.modules.reserve.dao.ReserveMenuDao;
import com.bra.modules.reserve.entity.ReserveMenu;
import com.bra.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ReserveMenuDao reserveMenuDao;

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
		// 获取父节点实体
		reserveMenu.setParent(this.get(reserveMenu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = reserveMenu.getParentIds();

		// 设置新的父节点串
		reserveMenu.setParentIds(reserveMenu.getParent().getParentIds()+reserveMenu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(reserveMenu.getId())){
			reserveMenu.preInsert();
			reserveMenuDao.insert(reserveMenu);
		}else{
			reserveMenu.preUpdate();
			reserveMenuDao.update(reserveMenu);
		}

		// 更新子节点 parentIds
		ReserveMenu m = new ReserveMenu();
		m.setParentIds("%,"+reserveMenu.getId()+",%");
		List<ReserveMenu> list = reserveMenuDao.findByParentIdsLike(m);
		for (ReserveMenu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, reserveMenu.getParentIds()));
			reserveMenuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		super.save(reserveMenu);
	}

	@Transactional(readOnly = false)
	public void delete(ReserveMenu reserveMenu) {
		super.delete(reserveMenu);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(ReserveMenu reserveMenu) {
		reserveMenuDao.updateSort(reserveMenu);
		/*// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);*/
	}

}