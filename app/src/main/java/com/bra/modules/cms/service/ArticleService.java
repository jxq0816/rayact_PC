/**
 * Copyright &copy; 2012-2014 <a href="https://github.com.bra.>JeeSite</a> All rights reserved.
 */
package com.bra.modules.cms.service;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.service.CrudService;
import com.bra.common.utils.CacheUtils;
import com.bra.common.utils.Collections3;
import com.bra.common.utils.MyBeanUtils;
import com.bra.common.utils.StringUtils;
import com.bra.modules.cms.dao.ArticleDao;
import com.bra.modules.cms.dao.ArticleDataDao;
import com.bra.modules.cms.dao.CategoryDao;
import com.bra.modules.cms.entity.Article;
import com.bra.modules.cms.entity.ArticleData;
import com.bra.modules.cms.entity.Category;
import com.bra.modules.cms.entity.CmsCollection;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.web.bean.AttMainForm;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章Service
 *
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {
    @Autowired
    private CmsCollectionService cmsCollectionService;
    @Autowired
    private ArticleDataDao articleDataDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = false)
    public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) CacheUtils.get("updateExpiredWeightDateByArticle");
        if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null
                && updateExpiredWeightDate.getTime() < new Date().getTime())) {
            dao.updateExpiredWeight(article);
            CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
        }
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		dc.createAlias("category", "category");
//		dc.createAlias("category.site", "category.site");
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (category == null) {
                category = new Category();
            }
            category.setParentIds(category.getId());
            category.setSite(category.getSite());
            article.setCategory(category);
        } else {
            article.setCategory(new Category());
        }
//		if (StringUtils.isBlank(page.getOrderBy())){
//			page.setOrderBy("a.weight,a.update_date desc");
//		}
//		return dao.find(page, dc);
        //	article.getSqlMap().put("dsf", dataScopeFilter(article.getCurrentUser(), "o", "u"));
        return super.findPage(page, article);

    }

    @Transactional(readOnly = false)
    public void save(Article article, AttMainForm attMainForm)throws Exception {
        List l = findListMapUn(article);
        if(l!=null&&l.size()>0){
         throw new Exception("标题重复");
        }
        article.setTitle(StringEscapeUtils.unescapeHtml4(
                article.getTitle()));
        if (article.getArticleData().getContent() != null) {
            article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
                    article.getArticleData().getContent()));
        }
        // 如果没有审核权限，则将当前内容改为待审核状态
        if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            article.setDelFlag(Article.DEL_FLAG_AUDIT);
        }
        // 如果栏目不需要审核，则将该内容设为发布状态
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (!Global.YES.equals(category.getIsAudit())) {
                article.setDelFlag(Article.DEL_FLAG_NORMAL);
            }
        }
        article.setUpdateBy(UserUtils.getUser());
        article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())) {
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }

        ArticleData articleData ;
        String attId = "";
        Map<String, Object> map = MyBeanUtils.describe(attMainForm);
        for (String key : map.keySet()) {
            if ("class".equals(key)) continue;
            List<AttMain> attMains = (List<AttMain>) map.get(key);
            if (Collections3.isEmpty(attMains)) continue;
            for (AttMain attMain : attMains) {
                if (StringUtils.isNotBlank(attMain.getId())) {
                    attId = attMain.getId();
                }
            }
        }
        if (StringUtils.isBlank(article.getId())) {
            article.preInsert();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            if(StringUtils.isNotBlank(attId))
                 article.setImage(com.bra.modules.sys.utils.StringUtils.ATTPATH+attId);
            dao.insert(article);
            articleDataDao.insert(articleData);
        } else {
            article.preUpdate();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            if(StringUtils.isNotBlank(attId))
                article.setImage(com.bra.modules.sys.utils.StringUtils.ATTPATH+attId);
            dao.update(article);
            articleDataDao.update(article.getArticleData());
        }
        updateAttMain(article,attMainForm);
    }

    @Transactional(readOnly = false)
    public void delete(Article article, Boolean isRe) {
//		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
        // 使用下面方法，以便更新索引。
        //Article article = dao.get(id);
        //article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
        //dao.insert(article);
        super.delete(article);
    }

    /**
     * 通过编号获取内容标题
     *
     * @return new Object[]{栏目Id,文章Id,文章标题}
     */
    public List<Object[]> findByIds(String ids) {
        if (ids == null) {
            return new ArrayList<Object[]>();
        }
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        Article e = null;
        for (int i = 0; (idss.length - i) > 0; i++) {
            e = dao.get(idss[i]);
            if (e != null && e.getCategory() != null)
                list.add(new Object[]{e.getCategory().getId(), e.getId(), StringUtils.abbr(e.getTitle(), 50)});
        }
        return list;
    }

    /**
     * 点击数加一
     */
    @Transactional(readOnly = false)
    public void updateHitsAddOne(String id) {
        dao.updateHitsAddOne(id);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    //FIXME 暂不提供检索功能
    public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate) {

        // 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
//		
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
//		if (StringUtils.isNotBlank(categoryId)){
//			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
//		}
//		
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
//			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}   

        //BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);

        // 设置排序（默认相识度排序）
        //FIXME 暂时不提供lucene检索
        //Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
        // 全文检索
        //dao.search(page, query, queryFilter, sort);
        // 关键字高亮
        //dao.keywordsHighlight(query, page.getList(), 30, "title");
        //dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");

        return page;
    }

    public List<Map<String,Object>> findListMap(Page page,Article article){
        article.setPage(page);
        List<Map<String,Object>> list =  articleDao.findListMap(article);
        for(Map<String,Object> node:list){
            String attId = String.valueOf(node.get("attId"));
            String id = String.valueOf(node.get("id"));
            User user = UserUtils.getUser();
            if(user!=null&&StringUtils.isNotBlank(user.getId())){
                CmsCollection cc = new CmsCollection();
                cc.setModelId(id);
                cc.setCreateBy(UserUtils.getUser());
                List<CmsCollection> tmp = cmsCollectionService.findList(cc);
                if(tmp!=null&&tmp.size()>0){
                    node.put("hasCollection",1);
                }else{
                    node.put("hasCollection",0);
                }
            }else{
                node.put("hasCollection",0);
            }
            if(StringUtils.isNotBlank(attId)){
                node.put("attUrl",com.bra.modules.sys.utils.StringUtils.ATTPATH + attId);
            }else{
                node.put("attUrl","");
            }
            node.remove("attId");
        }
        return list;
    }

    public List<Map<String,String>> findListMapUn(Article article){
        return articleDao.findListUn(article);
    }


}
