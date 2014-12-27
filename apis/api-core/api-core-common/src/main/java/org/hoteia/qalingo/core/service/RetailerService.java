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
    private RetailerDao retailerDao;

    @Autowired
    private EngineSettingService engineSettingService;
    
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

    public Long getMaxRetailerId() {
        return retailerDao.getMaxRetailerId();
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

    public RetailerCustomerRate saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        return retailerDao.saveOrUpdateRetailerCustomerRate(retailerCustomerRate);
    }

    public void deleteRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        retailerDao.deleteRetailerCustomerRate(retailerCustomerRate);
    }

    public RetailerCustomerComment saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
        return retailerDao.saveOrUpdateRetailerCustomerComment(retailerCustomerComment);
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

    public Long getMaxStoreId() {
        return retailerDao.getMaxStoreId();
    }
    
    public List<Store> findStores(Object... params) {
        return retailerDao.findStores(params);
    }
    
    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoresByRetailerId(retailerId, params);
    }
    
    public List<Store> findStoresByRetailerCode(final String retailerCode, Object... params) {
        return retailerDao.findStoresByRetailerCode(retailerCode, params);
    }
    
    public List<GeolocatedStore> findStoresByGeoloc(final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findStoresByGeoloc(latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }

    public List<GeolocatedStore> findStoresByGeolocAndCountry(final String countryCode, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findStoresByGeolocAndCountry(countryCode, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }

    public Store saveOrUpdateStore(final Store store) {
        return retailerDao.saveOrUpdateStore(store);
    }

    public void deleteStore(final Store store) {
        retailerDao.deleteStore(store);
    }
    
    // STORE COMMENT/RATE

    public StoreCustomerRate saveOrUpdateStoreCustomerRate(final StoreCustomerRate retailerCustomerRate) {
        return retailerDao.saveOrUpdateStoreCustomerRate(retailerCustomerRate);
    }

    public void deleteStoreCustomerRate(final StoreCustomerRate retailerCustomerRate) {
        retailerDao.deleteStoreCustomerRate(retailerCustomerRate);
    }

    public StoreCustomerComment saveOrUpdateStoreCustomerComment(final StoreCustomerComment retailerCustomerComment) {
        return retailerDao.saveOrUpdateStoreCustomerComment(retailerCustomerComment);
    }

    public void deleteStoreCustomerComment(final StoreCustomerComment retailerCustomerComment) {
        retailerDao.deleteStoreCustomerComment(retailerCustomerComment);
    }

}