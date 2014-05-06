/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.Iterator;
import java.util.List;

import org.hoteia.qalingo.core.dao.CatalogCategoryDao;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogCategoryService")
@Transactional
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

    @Autowired
    private CatalogCategoryDao catalogCategoryDao;

    // MASTER

    public CatalogCategoryMaster getMasterCatalogCategoryById(final Long catalogCategoryId, Object... params) {
        return catalogCategoryDao.getMasterCatalogCategoryById(catalogCategoryId, params);
    }

    public CatalogCategoryMaster getMasterCatalogCategoryById(final String rawCatalogCategoryId, Object... params) {
        long catalogCategoryId = -1;
        try {
            catalogCategoryId = Long.parseLong(rawCatalogCategoryId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMasterCatalogCategoryById(catalogCategoryId, params);
    }

    public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String catalogCategoryCode, final String catalogMasterCode, Object... params) {
        return catalogCategoryDao.getMasterCatalogCategoryByCode(catalogCategoryCode, catalogMasterCode, params);
    }

    public List<CatalogCategoryMaster> findRootMasterCatalogCategoriesByCatalogCode(final String catalogMasterCode, Object... params) {
        List<CatalogCategoryMaster> categories = catalogCategoryDao.findRootMasterCatalogCategoriesByCatalogCode(catalogMasterCode, params);
        return orderCategoryMasterList(categories);
    }
    
    public List<CatalogCategoryMaster> findAllMasterCatalogCategoriesByCatalogCode(final String catalogMasterCode, Object... params) {
        List<CatalogCategoryMaster> categories = catalogCategoryDao.findAllMasterCatalogCategoriesByCatalogCode(catalogMasterCode, params);
        return orderCategoryMasterList(categories);
    }

    public List<CatalogCategoryMaster> findMasterCategoriesByProductSkuId(final Long productSkuId, Object... params) {
        return catalogCategoryDao.findMasterCategoriesByProductSkuId(productSkuId, params);
    }
    
    public List<CatalogCategoryMaster> orderCategoryMasterList(final List<CatalogCategoryMaster> categories) {
        return categories;
    }
    
    public CatalogCategoryMaster saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory) {
        return catalogCategoryDao.saveOrUpdateCatalogCategory(catalogCategory);
    }

    public void deleteCatalogCategory(CatalogCategoryMaster catalogCategory) {
        catalogCategoryDao.deleteCatalogCategory(catalogCategory);
    }

    // VIRTUAL

    public CatalogCategoryVirtual getVirtualCatalogCategoryById(final Long catalogCategoryId, Object... params) {
        return catalogCategoryDao.getVirtualCatalogCategoryById(catalogCategoryId, params);
    }

    public CatalogCategoryVirtual getVirtualCatalogCategoryById(final String rawCatalogCategoryId, Object... params) {
        long catalogCategoryId = -1;
        try {
            catalogCategoryId = Long.parseLong(rawCatalogCategoryId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getVirtualCatalogCategoryById(catalogCategoryId, params);
    }

    public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final String catalogCategoryCode, final String catalogVirtualCode, final String catalogMasterCode, Object... params) {
        // FIRST TRY TO LOAD A VIRTUAL CATEGORY BY THE MASTER CATEGORY CODE 
        CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryDao.getVirtualCatalogCategoryByMasterCategoryCode(catalogCategoryCode, catalogVirtualCode, catalogMasterCode, params);
        if(catalogCategoryVirtual == null){
            // TRY TO FIND VIRTUAL CATAGORY WITH HIS OWN CODE
            catalogCategoryVirtual = catalogCategoryDao.getVirtualCatalogCategoryByVirtualCategoryCode(catalogCategoryCode, catalogVirtualCode, params);
        }
        return catalogCategoryVirtual;
    }

    public CatalogCategoryVirtual getDefaultVirtualCatalogCategoryByProductSkuId(final Long productSkuId, Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findVirtualCategoriesByProductSkuId(productSkuId, params);
        CatalogCategoryVirtual catalogCategoryVirtual = null;
        if (categories != null) {
            for (Iterator<CatalogCategoryVirtual> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual catalogCategoryVirtualIterator = (CatalogCategoryVirtual) iterator.next();
                if (catalogCategoryVirtualIterator.isDefault()) {
                    catalogCategoryVirtual = catalogCategoryVirtualIterator;
                }
            }
            if (categories.size() > 0 && catalogCategoryVirtual == null) {
                catalogCategoryVirtual = categories.iterator().next();
            }
        }
        return catalogCategoryVirtual;
    }

    public List<CatalogCategoryVirtual> findRootVirtualCatalogCategoriesByCatalogCode(final String catalogVirtualCode, Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findRootVirtualCatalogCategoriesByCatalogCode(catalogVirtualCode, params);
        return orderCategoryVirtualList(categories);
    }

    public List<CatalogCategoryVirtual> findAllVirtualCatalogCategoriesByCatalogCode(final String catalogVirtualCode, Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findAllVirtualCatalogCategoriesByCatalogCode(catalogVirtualCode, params);
        return orderCategoryVirtualList(categories);
    }
    
    public List<CatalogCategoryVirtual> findVirtualCategoriesByProductSkuId(final Long productSkuId, Object... params) {
        return catalogCategoryDao.findVirtualCategoriesByProductSkuId(productSkuId, params);
    }
    
    public List<CatalogCategoryVirtual> orderCategoryVirtualList(final List<CatalogCategoryVirtual> categories) {
        return categories;
    }

    public CatalogCategoryVirtual saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        return catalogCategoryDao.saveOrUpdateCatalogCategory(catalogCategory);
    }

    public void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        catalogCategoryDao.deleteCatalogCategory(catalogCategory);
    }

}