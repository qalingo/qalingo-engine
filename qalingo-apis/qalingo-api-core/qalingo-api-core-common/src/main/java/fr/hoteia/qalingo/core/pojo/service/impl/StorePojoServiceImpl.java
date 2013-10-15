/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.pojo.service.impl;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.pojo.service.StorePojoService;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import fr.hoteia.qalingo.core.service.RetailerService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;


@Service("storePojoService")
@Transactional(readOnly = true)
public class StorePojoServiceImpl implements StorePojoService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private DozerBeanMapper mapper;
    @Autowired private RetailerService retailerService;

    @Override
    public List<StorePojo> getAllStores() {
        List<Store> stores = retailerService.findStores();
        return mapAll(mapper, stores, StorePojo.class);
    }

    @Override
    public StorePojo getStoreById(final String id) {
        Store store = retailerService.getStoreById(id);
        return store == null ? null : mapper.map(store, StorePojo.class);
    }

    @Override
    public void saveOrUpdate(final StorePojo storeJsonBean) {
        Store store = mapper.map(storeJsonBean, Store.class);
        retailerService.saveOrUpdateStore(store);
    }

}
