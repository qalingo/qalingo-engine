/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CatalogDao;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.service.CatalogService;

import java.util.List;

@Service("catalogMasterService")
@Transactional
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogDao catalogMasterDao;

    @Override
    public List<CatalogMaster> findAllCatalogMasters() {
        return catalogMasterDao.findAllCatalogMasters();
    }

    public CatalogMaster getProductCatalogById(final String rawProductCatalogId) {
		long catalogMasterId = -1;
		try {
			catalogMasterId = Long.parseLong(rawProductCatalogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return catalogMasterDao.getProductCatalogById(catalogMasterId);
	}
	
	public CatalogVirtual getCatalogVirtual(final Long marketAreaId, final Long retailerId) {
		return catalogMasterDao.getCatalogVirtual(marketAreaId, retailerId);
	}

	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		catalogMasterDao.saveOrUpdateProductCatalog(catalogMaster);
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		catalogMasterDao.deleteProductCatalog(catalogMaster);
	}

}
