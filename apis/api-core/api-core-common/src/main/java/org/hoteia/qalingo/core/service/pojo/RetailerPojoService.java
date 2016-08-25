/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import static org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.mapAll;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.pojo.store.LightStorePojo;
import org.hoteia.qalingo.core.pojo.store.StorePojo;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.resolver.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("retailerPojoService")
@Transactional(readOnly = true)
public class RetailerPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired 
    protected RetailerService retailerService;

    @Autowired 
    protected ReferentialDataService referentialDataService;
    
    @Autowired 
    protected UrlService urlService;

    @Autowired 
    protected Mapper dozerBeanMapper;

    public RetailerPojo getRetailerById(final String retailerId) {
        final Retailer retailer = retailerService.getRetailerById(retailerId);
        logger.debug("Found {} retailer for id {}", retailer, retailerId);
        return retailer == null ? null : dozerBeanMapper.map(retailer, RetailerPojo.class);
    }
    
    public RetailerPojo getRetailerByCode(final String retailerCode) {
        final Retailer retailer = retailerService.getRetailerByCode(retailerCode);
        logger.debug("Found {} retailer for code {}", retailer, retailerCode);
        return retailer == null ? null : dozerBeanMapper.map(retailer, RetailerPojo.class);
    }
    
    public List<RetailerPojo> findAllRetailers() {
        List<Retailer> allRetailers = retailerService.findAllRetailers();
        logger.debug("Found {} retailers", allRetailers.size());
        return mapAll(dozerBeanMapper, allRetailers, RetailerPojo.class);
    }
    
    public List<RetailerPojo> findRetailersByMarketAreaCode(final String marketAreaCode) {
        List<Retailer> retailersByMarketAreaCode = retailerService.findRetailersByMarketAreaCode(marketAreaCode);
        logger.debug("Found {} retailers", retailersByMarketAreaCode.size());
        return mapAll(dozerBeanMapper, retailersByMarketAreaCode, RetailerPojo.class);
    }

    public List<StorePojo> getAllStores() {
        List<Store> stores = retailerService.findAllStores();
        logger.debug("Found {} stores", stores.size());
        return mapAll(dozerBeanMapper, stores, StorePojo.class);
    }
    
    public List<LightStorePojo> buildListLightStores(final RequestData requestData, final List<Store> stores) {
        List<LightStorePojo> lightStorePojos = new ArrayList<LightStorePojo>();
        for (final Store store : stores) {
            lightStorePojos.add(buildListLightStore(requestData, store));
        }
        return lightStorePojos;
    }
    
    public LightStorePojo buildListLightStore(final RequestData requestData, final Store store) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final LightStorePojo lightStorePojo = dozerBeanMapper.map(store, LightStorePojo.class);

        lightStorePojo.setI18nName(store.getI18nName(localizationCode));
        lightStorePojo.setI18nDescription(store.getI18nDescription(localizationCode));

        lightStorePojo.setAddressOnLine(referentialDataService.buildFullAddress(requestData.getLocale(), store.getAddress1(), store.getPostalCode(), 
                store.getCity(), store.getI18nCity(localizationCode), store.getCountryCode()));
        
        lightStorePojo.setDetailsUrl(urlService.generateUrl(FoUrls.STORE_DETAILS, requestData, store));
        
        return lightStorePojo;
    }
    
    public StorePojo getStoreById(final String id) {
        Store store = retailerService.getStoreById(id);
        logger.debug("Found {} store for id {}", store, id);
        return store == null ? null : dozerBeanMapper.map(store, StorePojo.class);
    }
    
    public StorePojo getStoreByCode(final String code) {
        Store store = retailerService.getStoreByCode(code);
        logger.debug("Found {} store for code {}", store, code);
        return store == null ? null : dozerBeanMapper.map(store, StorePojo.class);
    }

    public void saveOrUpdate(final StorePojo storeJsonBean) {
        Store store = dozerBeanMapper.map(storeJsonBean, Store.class);
        logger.info("Saving store {}", store);
        retailerService.saveOrUpdateStore(store);
    }
    
}