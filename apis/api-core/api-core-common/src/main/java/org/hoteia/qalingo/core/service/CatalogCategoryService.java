/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;

public interface CatalogCategoryService {

	// MASTER
    
    CatalogCategoryMaster getMasterCatalogCategoryById(Long catalogCategoryId, Object... params);

    CatalogCategoryMaster getMasterCatalogCategoryById(String catalogCategoryId, Object... params);

    CatalogCategoryMaster getMasterCatalogCategoryByCode(String catalogCategoryCode, String catalogMasterCode, Object... params);

    List<CatalogCategoryMaster> findRootMasterCatalogCategoriesByCatalogCode(String catalogMasterCode, Object... params);

    List<CatalogCategoryMaster> findAllMasterCatalogCategoriesByCatalogCode(String catalogMasterCode, Object... params);

//    List<CatalogCategoryMaster> findMasterCategories(Object... params);

    List<CatalogCategoryMaster> findMasterCategoriesByProductSkuId(Long productSkuId, Object... params);

//    List<CatalogCategoryMaster> findMasterCategoriesByProductMarketingId(Long productMarketingId, Object... params);

    List<CatalogCategoryMaster> orderCategoryMasterList(List<CatalogCategoryMaster> categories);

    CatalogCategoryMaster saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory);

    void deleteCatalogCategory(CatalogCategoryMaster catalogCategory);

	// VIRTUAL
	
    CatalogCategoryVirtual getVirtualCatalogCategoryById(Long catalogCategoryId, Object... params);
    
	CatalogCategoryVirtual getVirtualCatalogCategoryById(String catalogCategoryId, Object... params);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode, String catalogVirtualCode, String catalogMasterCode, Object... params);

	CatalogCategoryVirtual getDefaultVirtualCatalogCategoryByProductSkuId(Long productskuId, Object... params);
	
	List<CatalogCategoryVirtual> findRootVirtualCatalogCategoriesByCatalogCode(String catalogVirtualCode, Object... params);

    List<CatalogCategoryVirtual> findAllVirtualCatalogCategoriesByCatalogCode(String catalogVirtualCode, Object... params);

//	List<CatalogCategoryVirtual> findVirtualCategories(Object... params);

	List<CatalogCategoryVirtual> findVirtualCategoriesByProductSkuId(Long productSkuId, Object... params);

//    List<CatalogCategoryVirtual> findVirtualCategoriesByProductMarketingId(Long productMarketingId, Object... params);

	List<CatalogCategoryVirtual> orderCategoryVirtualList(List<CatalogCategoryVirtual> categories);

	CatalogCategoryVirtual saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory);
	
	void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory);

}