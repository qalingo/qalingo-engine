/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.ProductMarketing;

public interface ProductMarketingDao {

	ProductMarketing getProductMarketingById(Long productMarketingId);

//	ProductMarketing getProductMarketingByCode(String productMarketingCode);
	
	ProductMarketing getProductMarketingByCode(Long marketAreaId, Long retailerId, String productMarketingCode);

//	List<ProductMarketing> findByExample(ProductMarketing productMarketingExample);

	List<ProductMarketing> findProductMarketings(Long marketAreaId, Long retailerId);
	
	void saveOrUpdateProductMarketing(ProductMarketing productMarketing);

	void deleteProductMarketing(ProductMarketing productMarketing);

}
