/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.service.TreeService;
import com.bra.modules.cms.dao.CategoryDao;
import com.bra.modules.cms.entity.Category;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.cms.entity.Site;
import com.bra.modules.cms.utils.CmsUtils;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.entity.Office;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 栏目Service
 *
 * @version 2013-5-31
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends TreeService<CategoryDao, Category> {
	@Autowired
	private PostMainService postMainService;
	@Autowired
	private CategoryDao categoryDao;

	public static final String CACHE_CATEGORY_LIST = "categoryList";
	
	private Category entity = new Category();
	
	@SuppressWarnings("unchecked")
	public List<Category> findByUser(boolean isCurrentSite, String module){
		
		//List<Category> list = (List<Category>) UserUtils.getCache(CACHE_CATEGORY_LIST);
		List<Category> list = null;
		if (list == null){
			User user = UserUtils.getUser();
			Category category = new Category();
			category.setOffice(new Office());
			category.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
			category.setSite(new Site());
			category.setParent(new Category());
			list = dao.findList(category);
			// 将没有父节点的节点，找到父节点
			Set<String> parentIdSet = Sets.newHashSet();
			for (Category e : list){
				if (e.getParent()!=null && StringUtils.isNotBlank(e.getParent().getId())){
					boolean isExistParent = false;
					for (Category e2 : list){
						if (e.getParent().getId().equals(e2.getId())){
							isExistParent = true;
							break;
						}
					}
					if (!isExistParent){
						parentIdSet.add(e.getParent().getId());
					}
				}
			}
			if (parentIdSet.size() > 0){
				//FIXME 暂且注释，用于测试
//				dc = dao.createDetachedCriteria();
//				dc.add(Restrictions.in("id", parentIdSet));
//				dc.add(Restrictions.eq("delFlag", Category.DEL_FLAG_NORMAL));
//				dc.addOrder(Order.asc("site.id")).addOrder(Order.asc("sort"));
//				list.addAll(0, dao.find(dc));
			}
			UserUtils.putCache(CACHE_CATEGORY_LIST, list);
		}
		
		if (isCurrentSite){
			List<Category> categoryList = Lists.newArrayList(); 
			for (Category e : list){
				if (Category.isRoot(e.getId()) || (e.getSite()!=null && e.getSite().getId() !=null 
						&& e.getSite().getId().equals(Site.getCurrentSiteId()))){
					if (StringUtils.isNotEmpty(module)){
						if (module.equals(e.getModule()) || "".equals(e.getModule())){
							categoryList.add(e);
						}
					}else{
						categoryList.add(e);
					}
				}
			}
			return categoryList;
		}
		return list;
	}

	public List<Category> findByParentId(String parentId, String siteId){
		Category parent = new Category();
		parent.setId(parentId);
		entity.setParent(parent);
		Site site = new Site();
		site.setId(siteId);
		entity.setSite(site);
		return dao.findByParentIdAndSiteId(entity);
	}
	
	public Page<Category> find(Page<Category> page, Category category) {
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		if (category.getSite()!=null && StringUtils.isNotBlank(category.getSite().getId())){
//			dc.createAlias("site", "site");
//			dc.add(Restrictions.eq("site.id", category.getSite().getId()));
//		}
//		if (category.getParent()!=null && StringUtils.isNotBlank(category.getParent().getId())){
//			dc.createAlias("parent", "parent");
//			dc.add(Restrictions.eq("parent.id", category.getParent().getId()));
//		}
//		if (StringUtils.isNotBlank(category.getInMenu()) && Category.SHOW.equals(category.getInMenu())){
//			dc.add(Restrictions.eq("inMenu", category.getInMenu()));
//		}
//		dc.add(Restrictions.eq(Category.FIELD_DEL_FLAG, Category.DEL_FLAG_NORMAL));
//		dc.addOrder(Order.asc("site.id")).addOrder(Order.asc("sort"));
//		return dao.find(page, dc);
//		page.setSpringPage(dao.findByParentId(category.getParent().getId(), page.getSpringPage()));
//		return page;
		category.setPage(page);
		category.setInMenu(Global.SHOW);
		page.setList(dao.findModule(category));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Category category,AttMainForm attMainForm) {
		category.setSite(new Site(Site.getCurrentSiteId()));
		if (StringUtils.isNotBlank(category.getViewConfig())){
            category.setViewConfig(StringEscapeUtils.unescapeHtml4(category.getViewConfig()));
        }
		boolean isNew = category.getIsNewRecord();
		super.save(category);
		if(isNew && "group".equals(category.getModule())){
			PostMain pm = new PostMain();
			pm.setGroup(category);
			pm.setSubject("你知道么？又有一个圈子建立了！");
			pm.setContent("用户 "+UserUtils.getUser().getName()+" 建立了一个新的圈子："+category.getName()+" ，欢迎大家前来关注发帖。圈子有你更精彩！");
			postMainService.save(pm);
		}
		UserUtils.removeCache(CACHE_CATEGORY_LIST);
		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
		updateAttMain(category, attMainForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(Category category) {
		super.delete(category);
		UserUtils.removeCache(CACHE_CATEGORY_LIST);
		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
	}
	
	/**
	 * 通过编号获取栏目列表
	 */
	public List<Category> findByIds(String ids) {
		List<Category> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		if (idss.length>0){
//			List<Category> l = dao.findByIdIn(idss);
//			for (String id : idss){
//				for (Category e : l){
//					if (e.getId().equals(id)){
//						list.add(e);
//						break;
//					}
//				}
//			}
			for(String id : idss){
				Category e = dao.get(id);
				if(null != e){
					//System.out.println("e.id:"+e.getId()+",e.name:"+e.getName());
					list.add(e);
				}
				//list.add(dao.get(id));
				
			}
		}
		return list;
	}

	public List<Map<String,Object>> getCate(Category cate){
		List<Map<String,String>> rtn =  categoryDao.getCate(cate);
		List<Map<String,Object>> real = new ArrayList<>();
		if(rtn!=null){
			for(Map node:rtn){
				if("null".equals(String.valueOf(node.get("attURL")))||com.bra.modules.sys.utils.StringUtils.isNull(String.valueOf(node.get("attURL")))){
					String attId = String.valueOf(node.get("attId"));
					if(com.bra.modules.sys.utils.StringUtils.isNull(attId)||"null".equals(attId)){
						node.put("attURL", null);
					}else{
						node.put("attURL", com.bra.modules.sys.utils.StringUtils.ATTPATH + attId);
					}
					node.remove("attId");
				}else{
					node.remove("attId");
				}
				String attbgId = String.valueOf(node.get("attbgId"));
				if(!"null".equals(attbgId)&&!com.bra.modules.sys.utils.StringUtils.isNull(attbgId)){
					node.put("attbgURL", com.bra.modules.sys.utils.StringUtils.ATTPATH + attbgId);
					node.remove("attbgId");
				}else{
					node.put("attbgURL", null);
					node.remove("attbgId");
				}
				real.add(node);
			}
		}
		return real;
	}

	public void saveApp(Category category, HttpServletResponse response){
		if (category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category("1"));
		}
		Category parent = get(category.getParent().getId());
		category.setParent(parent);
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(parent.getOffice());
		}

	}
}
