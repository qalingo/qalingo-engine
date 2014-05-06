/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

    // MASTER CATALOG
    
    public CatalogMaster getMasterCatalogById(final Long masterCatalogId, Object... params) {
        return catalogDao.getMasterCatalogById(masterCatalogId, params);
    }

    public CatalogMaster getMasterCatalogById(final String rawMasterCatalogId, Object... params) {
        long masterCatalogId = -1;
        try {
            masterCatalogId = Long.parseLong(rawMasterCatalogId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getMasterCatalogById(masterCatalogId, params);
    }
    
    public List<CatalogMaster> findAllCatalogMasters(Object... params) {
        return catalogDao.findAllCatalogMasters(params);
    }

    public void saveOrUpdateCatalogMaster(final CatalogMaster catalogMaster) {
        catalogDao.saveOrUpdateCatalogMaster(catalogMaster);
    }

    public void deleteCatalogMaster(final CatalogMaster catalogMaster) {
        catalogDao.deleteCatalogMaster(catalogMaster);
    }
    
    // VIRTUAL CATALOG
    
    public CatalogVirtual getVirtualCatalogById(final Long virtualCatalogId, Object... params) {
        return catalogDao.getVirtualCatalogById(virtualCatalogId, params);
    }

    public CatalogVirtual getVirtualCatalogById(final String rawVirtualCatalogId, Object... params) {
        long virtualCatalogId = -1;
        try {
            virtualCatalogId = Long.parseLong(rawVirtualCatalogId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getVirtualCatalogById(virtualCatalogId, params);
    }

    public CatalogVirtual getVirtualCatalogbyMarketAreaId(final Long marketAreaId, Object... params) {
        return catalogDao.getVirtualCatalogByMarketAreaId(marketAreaId, params);
    }

}