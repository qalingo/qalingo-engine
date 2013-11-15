/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo.impl;

import static org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.pojo.store.StorePojo;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.pojo.StorePojoService;


@Service("storePojoService")
@Transactional(readOnly = true)
public class StorePojoServiceImpl implements StorePojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    private Mapper dozerBeanMapper;
    
    @Autowired 
    private RetailerService retailerService;

    @Override
    public List<StorePojo> getAllStores() {
        List<Store> stores = retailerService.findStores();
        logger.debug("Found {} stores", stores.size());
        return mapAll(dozerBeanMapper, stores, StorePojo.class);
    }

    @Override
    public StorePojo getStoreById(final String id) {
        Store store = retailerService.getStoreById(id);
        logger.debug("Found {} store for id {}", store, id);
        return store == null ? null : dozerBeanMapper.map(store, StorePojo.class);
    }

    @Override
    public void saveOrUpdate(final StorePojo storeJsonBean) {
        Store store = dozerBeanMapper.map(storeJsonBean, Store.class);
        logger.info("Saving store {}", store);
        retailerService.saveOrUpdateStore(store);
    }

}