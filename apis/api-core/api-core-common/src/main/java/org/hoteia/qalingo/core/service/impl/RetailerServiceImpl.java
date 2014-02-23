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

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.dao.RetailerDao;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.service.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("retailerService")
@Transactional
public class RetailerServiceImpl implements RetailerService {

    @Autowired
    private RetailerDao retailerDao;

    // RETAILER

    public Retailer getRetailerById(final Long retailerId, Object... params) {
        return retailerDao.getRetailerById(retailerId, params);
    }

    public Retailer getRetailerById(final String rawRetailerId, Object... params) {
        long retailerId = -1;
        try {
            retailerId = Long.parseLong(rawRetailerId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return retailerDao.getRetailerById(retailerId, params);
    }

    public Retailer getRetailerByCode(final String retailerCode, Object... params) {
        return retailerDao.getRetailerByCode(retailerCode, params);
    }

    public Retailer getRetailerByCode(final Long marketAreaId, final Long retailerId, final String retailerCode, Object... params) {
        return retailerDao.getRetailerByCode(marketAreaId, retailerId, retailerCode, params);
    }

    public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId, Object... params) {
        return retailerDao.findRetailers(marketAreaId, retailerId, params);
    }

    public List<Retailer> findAllRetailers(Object... params) {
        return retailerDao.findAllRetailers(params);
    }

    public List<Retailer> findRetailersByMarketAreaCode(final String marketAreaCode, Object... params) {
        return retailerDao.findRetailersByMarketAreaCode(marketAreaCode, params);
    }

    public List<Retailer> findRetailersByTag(final Long marketAreaId, final Long retailerId, final String tag, Object... params) {
        List<String> tags = new ArrayList<String>();
        tags.add(tag);
        return retailerDao.findRetailersByTags(marketAreaId, retailerId, tags, params);
    }

    public List<Retailer> findRetailersByTags(final Long marketAreaId, final Long retailerId, final List<String> tags, Object... params) {
        return retailerDao.findRetailersByTags(marketAreaId, retailerId, tags, params);
    }

    public List<Retailer> findLastRetailers(final Long marketAreaId, final Long retailerId, final int maxResults, Object... params) {
        return retailerDao.findLastRetailers(marketAreaId, retailerId, maxResults, params);
    }

    public List<Retailer> findBestRetailersByQualityOfService(final Long marketAreaId, final Long retailerId, final int maxResults, Object... params) {
        return retailerDao.findBestRetailersByQualityOfService(marketAreaId, retailerId, maxResults, params);
    }

    public List<Retailer> findBestRetailersByQualityPrice(final Long marketAreaId, final Long retailerId, final int maxResults, Object... params) {
        return retailerDao.findBestRetailersByQualityPrice(marketAreaId, retailerId, maxResults, params);
    }

    public List<Retailer> findRetailersByText(final Long marketAreaId, final Long retailerId, final String searchTxt, Object... params) {
        return retailerDao.findRetailersByText(marketAreaId, retailerId, searchTxt, params);
    }

    public void saveOrUpdateRetailer(final Retailer retailer) {
        retailerDao.saveOrUpdateRetailer(retailer);
    }

    public void deleteRetailer(final Retailer retailer) {
        retailerDao.deleteRetailer(retailer);
    }

    // RETAILER COMMENT/RATE

    public void saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        retailerDao.saveOrUpdateRetailerCustomerRate(retailerCustomerRate);
    }

    public void deleteRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        retailerDao.deleteRetailerCustomerRate(retailerCustomerRate);
    }

    public void saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
        retailerDao.saveOrUpdateRetailerCustomerComment(retailerCustomerComment);
    }

    public void deleteRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
        retailerDao.deleteRetailerCustomerComment(retailerCustomerComment);
    }

    // STORE

    public Store getStoreById(final Long storeId, Object... params) {
        return retailerDao.getStoreById(storeId, params);
    }
    
    public Store getStoreById(final String rawStoreId, Object... params) {
        long storeId = -1;
        try {
            storeId = Long.parseLong(rawStoreId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getStoreById(storeId, params);
    }

    public Store getStoreByCode(final String storeCode, Object... params) {
        return retailerDao.getStoreByCode(storeCode, params);
    }

    public List<Store> findStores(Object... params) {
        return retailerDao.findStores(params);
    }
    
    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoresByRetailerId(retailerId, params);
    }

    public void saveOrUpdateStore(final Store store) {
        retailerDao.saveOrUpdateStore(store);
    }

    public void deleteStore(final Store store) {
        retailerDao.deleteStore(store);
    }

}