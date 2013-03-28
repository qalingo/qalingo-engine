/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;

public interface CatalogCategoryService {

	// MASTER
	CatalogCategoryMaster getMasterCatalogCategoryById(String catalogCategoryId);

	CatalogCategoryMaster getMasterCatalogCategoryByCode(String catalogCategoryCode);

	CatalogCategoryMaster getMasterCatalogCategoryByCode(Long marketAreaId, Long retailerId, String catalogCategoryCode);

	List<CatalogCategoryMaster> findRootCatalogCategories();

	List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId);
	
	void saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory);
	
	void deleteCatalogCategory(CatalogCategoryMaster catalogCategory);

	// VIRTUAL
	
	CatalogCategoryVirtual getVirtualCatalogCategoryById(String catalogCategoryId);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode);

	CatalogCategoryVirtual getVirtualCatalogCategoryByCode(Long marketAreaId, Long retailerId, String catalogCategoryCode);

	CatalogCategoryVirtual getDefaultVirtualCatalogCategoryByProductMarketing(Long marketAreaId, Long retailerId, ProductMarketing productMarketing);
	
	List<CatalogCategoryVirtual> findRootCatalogCategories(Long marketAreaId, Long retailerId);

	List<CatalogCategoryVirtual> findVirtualCategories(Long marketAreaId, Long retailerId);

	List<CatalogCategoryVirtual> findVirtualCategoriesByProductMarketingId(Long marketAreaId, Long retailerId, Long productMarketingId);
	
	void saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory);
	
	void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory);

}
