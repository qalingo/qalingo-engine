/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.hoteia.qalingo.core.dao.CatalogDao;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogService")
@Transactional
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogDao catalogDao;

    public CatalogMaster getProductCatalogById(final String rawProductCatalogId) {
		long catalogMasterId = -1;
		try {
			catalogMasterId = Long.parseLong(rawProductCatalogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return catalogDao.getProductCatalogById(catalogMasterId);
	}
	
	public CatalogVirtual getCatalogVirtual(final Long marketAreaId) {
		return catalogDao.getCatalogVirtual(marketAreaId);
	}

    public List<CatalogMaster> findAllCatalogMasters() {
        return catalogDao.findAllCatalogMasters();
    }
    
	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		catalogDao.saveOrUpdateProductCatalog(catalogMaster);
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		catalogDao.deleteProductCatalog(catalogMaster);
	}

}