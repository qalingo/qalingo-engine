/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;

public interface CatalogCategoryDao {

	// MASTER
    
	CatalogCategoryMaster getMasterCatalogCategoryById(Long catalogCategoryId, Object... params);
	
	CatalogCategoryMaster getMasterCatalogCategoryByCode(String catalogCategoryCode, Object... params);

	List<CatalogCategoryMaster> findRootMasterCatalogCategories(Object... params);
	
	List<CatalogCategoryMaster> findMasterCategories(Object... params);
	
	CatalogCategoryMaster saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory);

	void deleteCatalogCategory(CatalogCategoryMaster catalogCategory);

	// VIRTUAL
	
	CatalogCategoryVirtual getVirtualCatalogCategoryById(Long catalogCategoryId, Object... params);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode, Object... params);

	List<CatalogCategoryVirtual> findRootVirtualCatalogCategories(Object... params);

	List<CatalogCategoryVirtual> findCatalogCategories(Object... params);

	List<CatalogCategoryVirtual> findCatalogCategoriesByProductSkuId(Long productSkuId, Object... params);
	
	CatalogCategoryVirtual saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory);

	void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory);
	
}