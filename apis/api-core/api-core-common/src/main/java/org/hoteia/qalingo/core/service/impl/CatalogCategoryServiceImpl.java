/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

    public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String catalogCategoryCode, Object... params) {
        return catalogCategoryDao.getMasterCatalogCategoryByCode(catalogCategoryCode, params);
    }

    public List<CatalogCategoryMaster> findRootMasterCatalogCategories(Object... params) {
        List<CatalogCategoryMaster> categories = catalogCategoryDao.findRootMasterCatalogCategories(params);
        return orderCategoryMasterList(categories);
    }

    public List<CatalogCategoryMaster> findMasterCategories(Object... params) {
        return catalogCategoryDao.findMasterCategories(params);
    }

    public List<CatalogCategoryMaster> orderCategoryMasterList(final List<CatalogCategoryMaster> categories) {
        return categories;
    }
    
//    public List<CatalogCategoryMaster> orderCategoryMasterList(final Long marketAreaId, final List<CatalogCategoryMaster> categories) {
//        List<CatalogCategoryMaster> sortedObjects = new LinkedList<CatalogCategoryMaster>(categories);
//        Collections.sort(sortedObjects, new Comparator<CatalogCategoryMaster>() {
//            @Override
//            public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
//                if (o1 != null && o2 != null) {
//                    Integer order1 = o1.getOrder(marketAreaId);
//                    Integer order2 = o2.getOrder(marketAreaId);
//                    if (order1 != null && order2 != null) {
//                        return order1.compareTo(order2);
//                    } else {
//                        return o1.getId().compareTo(o2.getId());
//                    }
//                }
//                return 0;
//            }
//        });
//        return sortedObjects;
//    }

    public void saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory) {
        catalogCategoryDao.saveOrUpdateCatalogCategory(catalogCategory);
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

    public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final String catalogCategoryCode, Object... params) {
        return catalogCategoryDao.getVirtualCatalogCategoryByCode(catalogCategoryCode, params);
    }

    public CatalogCategoryVirtual getDefaultVirtualCatalogCategoryByProductSkuId(final Long productSkuId, Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findCatalogCategoriesByProductSkuId(productSkuId, params);
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

    public List<CatalogCategoryVirtual> findRootVirtualCatalogCategories(Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findRootVirtualCatalogCategories(params);
        return orderCategoryVirtualList(categories);
    }

    public List<CatalogCategoryVirtual> findVirtualCategories(Object... params) {
        List<CatalogCategoryVirtual> categories = catalogCategoryDao.findCatalogCategories(params);
        return orderCategoryVirtualList(categories);
    }

    public List<CatalogCategoryVirtual> findVirtualCategoriesByProductSkuId(final Long productSkuId, Object... params) {
        return catalogCategoryDao.findCatalogCategoriesByProductSkuId(productSkuId, params);
    }

    public List<CatalogCategoryVirtual> orderCategoryVirtualList(final List<CatalogCategoryVirtual> categories) {
        return categories;
    }
    
//    public List<CatalogCategoryVirtual> orderCategoryVirtualList(final Long marketAreaId, final List<CatalogCategoryVirtual> categories) {
//        List<CatalogCategoryVirtual> sortedObjects = new LinkedList<CatalogCategoryVirtual>(categories);
//        Collections.sort(sortedObjects, new Comparator<CatalogCategoryVirtual>() {
//            @Override
//            public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
//                if (o1 != null && o2 != null) {
//                    Integer order1 = o1.getOrder(marketAreaId);
//                    Integer order2 = o2.getOrder(marketAreaId);
//                    if (order1 != null && order2 != null) {
//                        return order1.compareTo(order2);
//                    } else {
//                        return o1.getId().compareTo(o2.getId());
//                    }
//                }
//                return 0;
//            }
//        });
//        return sortedObjects;
//    }

    public void saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        catalogCategoryDao.saveOrUpdateCatalogCategory(catalogCategory);
    }

    public void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        catalogCategoryDao.deleteCatalogCategory(catalogCategory);
    }

}