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

import fr.hoteia.qalingo.core.common.domain.CatalogMaster;
import fr.hoteia.qalingo.core.common.domain.CatalogVirtual;

public interface ProductCatalogService {

	CatalogMaster getProductCatalogById(String productCatalogId);
	
	CatalogVirtual getCatalogVirtualByCode(Long marketAreaId, Long retailerId, String catalogVirtualCode);
	
	List<CatalogMaster> findProductCatalog(CatalogMaster criteria);
	
	void saveOrUpdateProductCatalog(CatalogMaster productCatalog);
	
	void deleteProductCatalog(CatalogMaster productCatalog);

}
