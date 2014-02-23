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

	CatalogCategoryMaster getMasterCatalogCategoryByCode(Long marketAreaId, String catalogCategoryCode, Object... params);

	List<CatalogCategoryMaster> findRootCatalogCategories(Object... params);
	
	List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(Long marketAreaId, Object... params);
	
	CatalogCategoryMaster saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory);

	void deleteCatalogCategory(CatalogCategoryMaster catalogCategory);

	// VIRTUAL
	
	CatalogCategoryVirtual getVirtualCatalogCategoryById(Long catalogCategoryId, Object... params);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode, Object... params);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(Long marketAreaId, String catalogCategoryCode, Object... params);

	List<CatalogCategoryVirtual> findRootCatalogCategories(Long marketAreaId, Object... params);

	List<CatalogCategoryVirtual> findCatalogCategories(Long marketAreaId, Object... params);

	List<CatalogCategoryVirtual> findCatalogCategoriesByProductMarketingCode(Long marketAreaId, String productMarketingCode, Object... params);
	
	CatalogCategoryVirtual saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory);

	void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory);
	
}