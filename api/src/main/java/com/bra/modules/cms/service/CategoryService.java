package com.bra.modules.cms.service;

import com.bra.common.service.CrudService;
import com.bra.modules.cms.dao.CategoryDao;
import com.bra.modules.cms.eneity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2016/2/25.
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends CrudService<CategoryDao,Category> {
}
