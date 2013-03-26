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

import fr.hoteia.qalingo.core.domain.ProductMarketing;

public interface ProductMarketingService {

	ProductMarketing getProductMarketingById(String productMarketingId);

//	ProductMarketing getProductMarketingByCode(String productMarketingCode);

	ProductMarketing getProductMarketingByCode(Long marketAreaId, Long retailerId, String productMarketingCode);
	
//	List<ProductMarketing> findProductMarketing(ProductMarketing criteria);
	
	List<ProductMarketing> findProductMarketings(Long marketAreaId, Long retailerId);

	List<ProductMarketing> findProductMarketingsByBrandId(Long marketAreaId, Long retailerId, Long brandId);

	void saveOrUpdateProductMarketing(ProductMarketing productMarketing);
	
	void deleteProductMarketing(ProductMarketing productMarketing);

}
