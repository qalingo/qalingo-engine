/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.common.domain.ProductMarketing;

public interface ProductCategoryService {

	ProductCategoryVirtual getProductCategoryById(String productCategoryId);

	ProductCategoryVirtual getProductCategoryByCode(Long marketAreaId, Long retailerId, String productCategoryCode);

	ProductCategoryVirtual getDefaultProductCategoryByProductMarketing(Long marketAreaId, Long retailerId, ProductMarketing productMarketing);
	
//	List<ProductCategory> findProductCategory(ProductCategory criteria);
	
	List<ProductCategoryVirtual> findProductCategories(Long marketAreaId, Long retailerId);
	
	List<ProductCategoryVirtual> findProductCategoriesByProductMarketingId(Long marketAreaId, Long retailerId, Long productMarketingId);
	
	void saveOrUpdateProductCategory(ProductCategoryVirtual productCategory);
	
	void deleteProductCategory(ProductCategoryVirtual productCategory);

}
