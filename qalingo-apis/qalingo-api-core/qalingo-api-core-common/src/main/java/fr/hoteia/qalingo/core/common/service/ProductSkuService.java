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

import fr.hoteia.qalingo.core.common.domain.ProductSku;

public interface ProductSkuService {

//	ProductSku getProductSkuById(String productSkuId);
	
//	ProductSku getProductSkuByCode(String productSkuCode);
	
//	ProductSku getProductSkuByCode(Long marketAreaId, String productSkuCode);
	
	ProductSku getProductSkuByCode(Long marketAreaId, Long retailerId, String productSkuCode);
	
//	List<ProductSku> findProductSku(ProductSku criteria);
	
	List<ProductSku> findProductSkus(Long marketAreaId, Long retailerId, Long productMarkettingId);
	
	void saveOrUpdateProductSku(ProductSku productSku);
	
	void deleteProductSku(ProductSku productSku);

}
