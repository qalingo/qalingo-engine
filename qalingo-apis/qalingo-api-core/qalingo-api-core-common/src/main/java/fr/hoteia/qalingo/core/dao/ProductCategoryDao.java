/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao;

import java.util.List;

import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;

public interface ProductCategoryDao {

	// MASTER
	ProductCategoryMaster getMasterProductCategoryById(Long productCategoryId);
	
	ProductCategoryMaster getMasterProductCategoryByCode(String productCategoryCode);

	ProductCategoryMaster getMasterProductCategoryByCode(Long marketAreaId, Long retailerId, String productCategoryCode);

	List<ProductCategoryMaster> findRootProductCategories();
	
	List<ProductCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId);
	
	void saveOrUpdateProductCategory(ProductCategoryMaster productCategory);

	void deleteProductCategory(ProductCategoryMaster productCategory);

	// VIRTUAL
	ProductCategoryVirtual getVirtualProductCategoryById(Long productCategoryId);

	ProductCategoryVirtual getVirtualProductCategoryByCode(String productCategoryCode);

	ProductCategoryVirtual getVirtualProductCategoryByCode(Long marketAreaId, Long retailerId, String productCategoryCode);

	List<ProductCategoryVirtual> findRootProductCategories(Long marketAreaId, Long retailerId);

	List<ProductCategoryVirtual> findProductCategories(Long marketAreaId, Long retailerId);

	List<ProductCategoryVirtual> findProductCategoriesByProductMarketingId(Long marketAreaId, Long retailerId, Long productMarketingId);
	
	void saveOrUpdateProductCategory(ProductCategoryVirtual productCategory);

	void deleteProductCategory(ProductCategoryVirtual productCategory);
}
