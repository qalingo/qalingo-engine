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

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;

public interface ProductCatalogService {

	CatalogMaster getProductCatalogById(String productCatalogId);
	
	CatalogVirtual getCatalogVirtualByCode(Long marketAreaId, Long retailerId, String catalogVirtualCode);
	
	void saveOrUpdateProductCatalog(CatalogMaster productCatalog);
	
	void deleteProductCatalog(CatalogMaster productCatalog);

}
