/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ProductCatalogDao;
import fr.hoteia.qalingo.core.common.domain.CatalogMaster;
import fr.hoteia.qalingo.core.common.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.common.service.ProductCatalogService;

@Service("catalogMasterService")
@Transactional
public class ProductCatalogServiceImpl implements ProductCatalogService {

	@Autowired
	private ProductCatalogDao catalogMasterDao;

	public CatalogMaster getProductCatalogById(final String rawProductCatalogId) {
		long catalogMasterId = -1;
		try {
			catalogMasterId = Long.parseLong(rawProductCatalogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return catalogMasterDao.getProductCatalogById(catalogMasterId);
	}
	
	public CatalogVirtual getCatalogVirtualByCode(final Long marketAreaId, final Long retailerId, final String catalogVirtualCode) {
		return catalogMasterDao.getCatalogVirtualByCode(marketAreaId, retailerId, catalogVirtualCode);
	}

	public List<CatalogMaster> findProductCatalog(final CatalogMaster criteria) {
		return catalogMasterDao.findByExample(criteria);
	}

	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		catalogMasterDao.saveOrUpdateProductCatalog(catalogMaster);
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		catalogMasterDao.deleteProductCatalog(catalogMaster);
	}

}
