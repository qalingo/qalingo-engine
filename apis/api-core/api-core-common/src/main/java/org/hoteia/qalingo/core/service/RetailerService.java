/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.hoteia.qalingo.core.dao.RetailerDao;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.StoreCustomerRate;
import org.hoteia.qalingo.core.domain.bean.GeolocatedStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("retailerService")
@Transactional
public class RetailerService {

    @Autowired
    protected RetailerDao retailerDao;

    @Autowired
    protected EngineSettingService engineSettingService;
    
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

    public Retailer getRetailerByCompanyCode(final String companyCode, Object... params) {
        return retailerDao.getRetailerByCompanyCode(companyCode, params);
    }
    
    public Long getMaxRetailerId() {
        return retailerDao.getMaxRetailerId();
    }
    
    @Deprecated
    public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId, Object... params) {
        return retailerDao.findRetailers(marketAreaId, retailerId, params);
    }

    public List<Retailer> findAllRetailers(Object... params) {
        return retailerDao.findAllRetailers(params);
    }
    
    public List<Retailer> findAllRetailersByCountry(String countryCode, Object... params) {
        return retailerDao.findAllRetailersByCountry(countryCode, params);
    }

    @Deprecated
    public List<Retailer> findRetailersByMarketAreaCode(final String marketAreaCode, Object... params) {
        return retailerDao.findRetailersByMarketAreaCode(marketAreaCode, params);
    }

    public List<Retailer> findRetailersByTag(final String tag, Object... params) {
        List<String> tags = new ArrayList<String>();
        tags.add(tag);
        return retailerDao.findRetailersByTags(tags, params);
    }

    public List<Retailer> findRetailersByTags(final List<String> tags, Object... params) {
        return retailerDao.findRetailersByTags(tags, params);
    }

    public List<Retailer> findLastRetailers(final int maxResults, Object... params) {
        return retailerDao.findLastRetailers(maxResults, params);
    }

    public List<Retailer> findBestRetailersByQualityOfService(final int maxResults, Object... params) {
        return retailerDao.findBestRetailersByQualityOfService(maxResults, params);
    }

    public List<Retailer> findBestRetailersByQualityPrice(final int maxResults, Object... params) {
        return retailerDao.findBestRetailersByQualityPrice(maxResults, params);
    }

    public List<Retailer> findRetailersByText(final String searchTxt, Object... params) {
        return retailerDao.findRetailersByText(searchTxt, params);
    }

    public Retailer saveOrUpdateRetailer(final Retailer retailer) {
        return retailerDao.saveOrUpdateRetailer(retailer);
    }

    public Retailer updateRetailer(final Retailer retailer) {
        return retailerDao.updateRetailer(retailer);
    }
    
    public void deleteRetailer(final Retailer retailer) {
        retailerDao.deleteRetailer(retailer);
    }
    
    public String buildRetailerLogoFilePath(final Retailer retailer, final String logo) {
          String assetfileRootPath = engineSettingService.getSettingAssetFileRootPath().getDefaultValue();
          if (assetfileRootPath.endsWith("/")) {
              assetfileRootPath = assetfileRootPath.substring(0, assetfileRootPath.length() - 1);
          }
          
          String retailerLogoFilePath = engineSettingService.getSettingAssetRetailerAndStoreFilePath().getDefaultValue();
          if (retailerLogoFilePath.endsWith("/")) {
              retailerLogoFilePath = retailerLogoFilePath.substring(0, retailerLogoFilePath.length() - 1);
          }
          if (!retailerLogoFilePath.startsWith("/")) {
              retailerLogoFilePath = "/" + retailerLogoFilePath;
          }
          String absoluteFolderPath = new StringBuilder(assetfileRootPath).append(retailerLogoFilePath).append("/retailer-logo/").append(retailer.getCode()).append("/").toString();
          String absoluteFilePath = new StringBuilder(absoluteFolderPath).append(logo).toString();
          return FilenameUtils.separatorsToSystem(absoluteFilePath);
    }
    
    public String buildRetailerLogoWebPath(final String logo) throws Exception {
        EngineSetting engineSetting = engineSettingService.getSettingAssetRetailerAndStoreFilePath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        String retailerLogoWebPathPrefix = buildRootAssetWebPath() + prefixPath + "/retailer-logo/";
        String retailerLogoWebPath = retailerLogoWebPathPrefix + logo;
        return retailerLogoWebPath;
    }
    
    protected String buildRootAssetWebPath() throws Exception {
        EngineSetting engineSetting = engineSettingService.getSettingAssetWebRootPath();
        String prefixPath = "";
        if (engineSetting != null) {
            prefixPath = engineSetting.getDefaultValue();
        }
        if (prefixPath.endsWith("/")) {
            prefixPath = prefixPath.substring(0, prefixPath.length() - 1);
        }
        return prefixPath;
    }
    
    // RETAILER COMMENT/RATE

    public RetailerCustomerRate saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate customerRate) {
        return retailerDao.saveOrUpdateRetailerCustomerRate(customerRate);
    }

    public void deleteRetailerCustomerRate(final RetailerCustomerRate customerRate) {
        retailerDao.deleteRetailerCustomerRate(customerRate);
    }
    
    public RetailerCustomerComment saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment customerRate) {
        return retailerDao.saveOrUpdateRetailerCustomerComment(customerRate);
    }
    
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByRetailerId(final Long productMarketingId, Object... params){
        List<RetailerCustomerComment> customerComments = retailerDao.findRetailerCustomerCommentsByRetailerId(productMarketingId, params);
        return customerComments;
    }
    
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByRetailerIdAndMarketAreaId(final Long productMarketingId, final Long marketAreaId, Object... params){
        List<RetailerCustomerComment> customerComments = retailerDao.findRetailerCustomerCommentsByRetailerIdAndMarketAreaId(productMarketingId, marketAreaId, params);
        return customerComments;
    }
    
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByCustomerId(final Long customerId, Object... params){
        List<RetailerCustomerComment> customerComments = retailerDao.findRetailerCustomerCommentsByCustomerId(customerId, params);
        return customerComments;
    }
    
    public List<RetailerCustomerRate> findRetailerCustomerRatesByRetailerId(final Long productMarketingId, final String type, Object... params) {
        List<RetailerCustomerRate> customerRates = retailerDao.findRetailerCustomerRatesByRetailerId(productMarketingId, type, params);
        return customerRates;
    }
    
    public void deleteRetailerCustomerComment(final RetailerCustomerComment customerRate) {
        retailerDao.deleteRetailerCustomerComment(customerRate);
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
    
    public Store findStoreByEmail(final String email, Object... params) {
        return retailerDao.findStoreByEmail(email, params);
    }

    public Long countStore() {
        return retailerDao.countStore();
    }
    
    public Long getMaxStoreId() {
        return retailerDao.getMaxStoreId();
    }
    
    public List<Store> findAllStores(Object... params) {
        return retailerDao.findAllStores(0, params);
    }
    
    public List<Long> findAllStoreIds(Object... params) {
        return retailerDao.findAllStoreIds(params);
    }
    
    public List<Store> findStoresWithMax(int maxResults, Object... params) {
        return retailerDao.findAllStores(maxResults, params);
    }

    public List<Store> findB2CStores(int maxResults, Object... params) {
        return retailerDao.findB2CStores(maxResults, params);
    }
    
    public List<Store> findB2BStores(int maxResults, Object... params) {
        return retailerDao.findB2BStores(maxResults, params);
    }
    
    public List<Long> findStoreIdsByCompanyId(final Long companyId, Object... params) {
        return retailerDao.findStoreIdsByCompanyId(companyId, params);
    }

    public List<Long> findStoreIdsByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoreIdsByRetailerId(retailerId, params);
    }
    
    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoresByRetailerId(retailerId, params);
    }
    
    public List<Store> findStoresByRetailerCode(final String retailerCode, Object... params) {
        return retailerDao.findStoresByRetailerCode(retailerCode, params);
    }
    
    public List<Long> findShopStoresByCountryCode(final String countryCode, Object... params) {
        return retailerDao.findShopStoresByCountryCode(countryCode, params);
    }
    
    public List<GeolocatedStore> findB2CStoresByGeoloc(final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2BStoresByGeoloc(final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2BStoresByGeoloc(latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndCountry(final String countryCode, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeolocAndCountry(countryCode, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2BStoresByGeolocAndCountry(final String countryCode, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2BStoresByGeolocAndCountry(countryCode, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }

    public Store saveOrUpdateStore(final Store store) {
        return retailerDao.saveOrUpdateStore(store);
    }

    public Store updateStore(final Store store) {
        return retailerDao.updateStore(store);
    }

    public void deleteStore(final Store store) {
        retailerDao.deleteStore(store);
    }
    
    // STORE COMMENT/RATE

    public StoreCustomerRate saveOrUpdateStoreCustomerRate(final StoreCustomerRate customerRate) {
        return retailerDao.saveOrUpdateStoreCustomerRate(customerRate);
    }

    public void deleteStoreCustomerRate(final StoreCustomerRate customerRate) {
        retailerDao.deleteStoreCustomerRate(customerRate);
    }
    
    public StoreCustomerComment saveOrUpdateStoreCustomerComment(final StoreCustomerComment customerRate) {
        return retailerDao.saveOrUpdateStoreCustomerComment(customerRate);
    }
    
    public List<StoreCustomerComment> findStoreCustomerCommentsByStoreId(final Long productMarketingId, Object... params){
        List<StoreCustomerComment> customerComments = retailerDao.findStoreCustomerCommentsByStoreId(productMarketingId, params);
        return customerComments;
    }
    
    public List<StoreCustomerComment> findStoreCustomerCommentsByStoreIdAndMarketAreaId(final Long storeId, final Long marketAreaId, Object... params){
        List<StoreCustomerComment> customerComments = retailerDao.findStoreCustomerCommentsByStoreIdAndMarketAreaId(storeId, marketAreaId, params);
        return customerComments;
    }
    
    public List<StoreCustomerComment> findStoreCustomerCommentsByCustomerId(final Long customerId, Object... params){
        List<StoreCustomerComment> customerComments = retailerDao.findStoreCustomerCommentsByCustomerId(customerId, params);
        return customerComments;
    }
    
    public List<StoreCustomerRate> findStoreCustomerRatesByStoreId(final Long productMarketingId, final String type, Object... params) {
        List<StoreCustomerRate> customerRates = retailerDao.findStoreCustomerRatesByStoreId(productMarketingId, type, params);
        return customerRates;
    }
    
    public void deleteStoreCustomerComment(final StoreCustomerComment customerRate) {
        retailerDao.deleteStoreCustomerComment(customerRate);
    }

}