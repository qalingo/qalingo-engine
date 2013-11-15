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
	CatalogCategoryMaster getMasterCatalogCategoryById(Long catalogCategoryId);
	
	CatalogCategoryMaster getMasterCatalogCategoryByCode(String catalogCategoryCode);

	CatalogCategoryMaster getMasterCatalogCategoryByCode(Long marketAreaId, Long retailerId, String catalogCategoryCode);

	List<CatalogCategoryMaster> findRootCatalogCategories();
	
	List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId);
	
	void saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory);

	void deleteCatalogCategory(CatalogCategoryMaster catalogCategory);

	// VIRTUAL
	CatalogCategoryVirtual getVirtualCatalogCategoryById(Long catalogCategoryId);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(Long marketAreaId, Long retailerId, String catalogCategoryCode);

	List<CatalogCategoryVirtual> findRootCatalogCategories(Long marketAreaId, Long retailerId);

	List<CatalogCategoryVirtual> findCatalogCategories(Long marketAreaId, Long retailerId);

	List<CatalogCategoryVirtual> findCatalogCategoriesByProductMarketingId(Long marketAreaId, Long retailerId, Long productMarketingId);
	
	void saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory);

	void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory);
}
