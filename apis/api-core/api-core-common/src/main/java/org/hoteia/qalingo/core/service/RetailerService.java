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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hoteia.qalingo.core.dao.RetailerDao;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.CompanyStoreRel;
import org.hoteia.qalingo.core.domain.Company_;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Retailer_;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.StoreCustomerRate;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.fetchplan.retailer.FetchPlanGraphRetailer;
import org.hoteia.qalingo.core.fetchplan.user.FetchPlanGraphUser;
import org.hoteia.qalingo.core.pojo.GeolocData;
import org.hoteia.qalingo.core.pojo.GeolocatedStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("retailerService")
@Transactional
public class RetailerService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected RetailerDao retailerDao;
    
    @Autowired
    protected ProductService productService;

    @Autowired
    protected UserService userService;
    
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
    
    public List<Retailer> findAllRetailersByCountry(final String countryCode, Object... params) {
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
    
    public List<Long> findAllStoreIds(Object... params) {
        return retailerDao.findAllStoreIds(params);
    }
    
    public List<Long> findAllStoreIdsByCountry(final String countryCode, int maxResults, Object... params) {
        return retailerDao.findAllStoreIdsByCountry(countryCode, maxResults, params);
    }
    
    public List<Long> findStoreWithoutLatitudeLongitude(int maxResults, Object... params) {
        return retailerDao.findStoreWithoutLatitudeLongitude(maxResults, params);
    }
    
    public List<Long> findStoreIdsByCompanyId(final Long companyId, Object... params) {
        return retailerDao.findStoreIdsByCompanyId(companyId, params);
    }
    
    public List<Long> findStoreIdsByUserId(final Long userId, Object... params) {
        return retailerDao.findStoreIdsByUserId(userId, params);
    }

    public List<Long> findStoreIdsByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoreIdsByRetailerId(retailerId, params);
    }
    
    public List<Store> findAllStores(Object... params) {
        return retailerDao.findAllStores(0, params);
    }
    
    public List<Store> findStoreByAddress(final String address, Object... params) {
        return retailerDao.findStoreByAddress(address, params);
    }
    
    public List<Store> findStoreByAddressAndPostalCode(final String address, final String postalCode, Object... params) {
        return retailerDao.findStoreByAddressAndPostalCode(address, postalCode, params);
    }
    
    public List<Store> findAllStoresByCountry(final String countryCode, int maxResults, Object... params) {
        return retailerDao.findAllStoresByCountry(countryCode, maxResults, params);
    }
    
    public List<Store> findAllStoresByCountryAndType(final String countryCode, final String type, int maxResults, Object... params) {
        return retailerDao.findAllStoresByCountryAndType(countryCode, type, maxResults, params);
    }
    
    public List<Store> findStoresWithMax(int maxResults, Object... params) {
        return retailerDao.findAllStores(maxResults, params);
    }

    public List<Store> findB2CStores(int maxResults, Object... params) {
        return retailerDao.findB2CStores(null, maxResults, params);
    }
    
    public List<Store> findB2CStoresByType(final List<String> types, int maxResults, Object... params) {
        return retailerDao.findB2CStores(types, maxResults, params);
    }
    
    public List<Store> findB2BStores(int maxResults, Object... params) {
        return retailerDao.findB2BStores(null, maxResults, params);
    }
    
    public List<Store> findB2BStoresByType(final List<String> types, int maxResults, Object... params) {
        return retailerDao.findB2BStores(types, maxResults, params);
    }
    
    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        return retailerDao.findStoresByRetailerId(retailerId, params);
    }
    
    public List<Store> findStoresByRetailerCode(final String retailerCode, Object... params) {
        return retailerDao.findStoresByRetailerCode(retailerCode, params);
    }
    
    public List<GeolocatedStore> findB2CStoresByGeoloc(final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(null, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndCountry(final String countryCode, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(countryCode, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndProductBrand(final Long productBrandId, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(null, productBrandId, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndCountryAndProductBrand(final String countryCode, final Long productBrandId, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(countryCode, productBrandId, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndCountryAndType(final String countryCode, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(countryCode, types, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2CStoresByGeolocAndCountryAndTypeAndProductBrand(final String countryCode, final Long productBrandId, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2CStoresByGeoloc(countryCode, productBrandId, types, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2BStoresByGeolocAndCountry(final String countryCode, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2BStoresByGeoloc(countryCode, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2BStoresByGeoloc(final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2BStoresByGeoloc(null, null, latitude, longitude, distance, maxResults, params);
        return geolocatedStores;
    }
    
    public List<GeolocatedStore> findB2BStoresByGeolocAndCountryAndType(final String countryCode, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        List<GeolocatedStore> geolocatedStores = retailerDao.findB2BStoresByGeoloc(countryCode, types, latitude, longitude, distance, maxResults, params);
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

    public List<GeolocatedStore> findRandomB2CStores(final MarketArea marketArea, final GeolocData geolocData, int maxStoreList, int distance){
        List<GeolocatedStore> storeRandom = new ArrayList<GeolocatedStore>();
        if(marketArea.getGeolocCountryCode() != null){
            List<String> types = new ArrayList<String>();
            types.add("OPTICIAN");
            storeRandom = findB2CStoresByGeolocAndCountryAndType(marketArea.getGeolocCountryCode(), types, geolocData.getLatitude(), geolocData.getLongitude(), Integer.toString(distance), maxStoreList);
        } else {
            if(StringUtils.isNotEmpty(geolocData.getLatitude()) 
                    && StringUtils.isNotEmpty(geolocData.getLongitude())){
                List<String> types = new ArrayList<String>();
                types.add("OPTICIAN");
                storeRandom = findB2CStoresByGeolocAndCountryAndType(null, types, geolocData.getLatitude(), geolocData.getLongitude(), Integer.toString(distance), maxStoreList);
            }
        }
        return storeRandom;
    }
    
    public void deleteStoreAndRetailer(final Store store) {
    	logger.debug("Start deleting Store: " + store.toString());
    	
		List<Long> brandIds = productService.findProductBrandIdsByStoreId(store.getId(), 0);
		if(brandIds != null
				&& !brandIds.isEmpty()){
			for (Long brandId : brandIds) {
		    	logger.debug("ProductBrandStoreRel exist, productBrandId: " + brandId);
				ProductBrand productBrand = productService.getProductBrandById(brandId);
		        logger.debug("Delete Brand/Store: " + productBrand.toString());
				productService.deleteProductBrandStoreRel(productBrand, store);
			}
		}

		// RETAILER : DELETE ONLY IF ONE FOR ONE
        Retailer retailer = getRetailerById(store.getRetailer().getId(), FetchPlanGraphRetailer.fullRetailerFetchPlan());
		if(retailer.getStores() != null
				&& retailer.getStores().size() == 1){
	        logger.debug("Delete Retailer: " + retailer.toString());
			deleteRetailer(retailer);
		}
		
        // COMPANY/USER : DELETE ONLY IF ONE FOR ONE
        Set<CompanyStoreRel> companyStoreRels = new HashSet<CompanyStoreRel>();
        for (CompanyStoreRel companyStoreRel : companyStoreRels) {
            Company company = userService.getCompanyById(companyStoreRel.getCompany().getId(), FetchPlanGraphUser.fullCompanyFetchPlan());
            if (company.getStores().size() == 1) {
                List<User> users = company.getUsers();
                if (users != null) {
                    logger.debug("There is users, users: " + users.size());
                    for (User user : users) {
                        User reloadUser = userService.getUserById(user.getId(), FetchPlanGraphUser.fullUserFetchPlan());
                        if (reloadUser.getCompanyUserRels().size() == 1) {
                            logger.debug("Delete User: " + reloadUser.toString());
                            userService.deleteUser(user);
                        }
                    }
                }
                logger.debug("Delete Company: " + company.toString());
                userService.deleteCompany(company);
            }
        }
        
        deleteStore(store);
    }
    
    public ByteArrayOutputStream generateExcelBrandRetailers(ProductBrand productBrand) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("points de vente " + productBrand.getName());

        int rowPosition = 0;
        Row row = sheet.createRow((short) rowPosition++);
        int cellPosition = 0;

        Cell cell = row.createCell(cellPosition++);
        cell.setCellValue("NOM POINT DE VENTE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("SIRET POINT DE VENTE");
        
        cell = row.createCell(cellPosition++);
        cell.setCellValue("NOM SOCIETE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("SIRET SOCIETE");
        
        cell = row.createCell(cellPosition++);
        cell.setCellValue("ADRESSE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("ADRESSE+");
        
        cell = row.createCell(cellPosition++);
        cell.setCellValue("CODE POSTAL");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("VILLE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("PAYS");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("PHONE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("FAX");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("EMAIL");
        
        cell = row.createCell(cellPosition++);
        cell.setCellValue("CONTACT PRENOM");
        
        cell = row.createCell(cellPosition++);
        cell.setCellValue("CONTACT NOM");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("CONTACT MOBILE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("CONTACT EMAIL");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("INTERNAL BRAND CODE");

        cell = row.createCell(cellPosition++);
        cell.setCellValue("COLOR OPTICAL CODE");

        List<Long> storeIds = productService.findStoreIdsByBrandId(productBrand.getId(), 0);
        for (Long storeId : storeIds) {
            Store store = getStoreById(storeId, FetchPlanGraphRetailer.fullStoreFetchPlan());

            row = sheet.createRow((short) rowPosition++);
            cellPosition = 0;

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getName());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getLegalGuid());

            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null){
                cell.setCellValue(store.getCompany().getName());
            }

            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null){
                cell.setCellValue(store.getCompany().getLegalGuid());
            }

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getAddress1());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getAddress2());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getPostalCode());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getCity());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getCountryCode());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getPhone());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getFax());

            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getEmail());

            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null && store.getCompany().getDefaultUser() != null){
                cell.setCellValue(store.getCompany().getDefaultUser().getFirstname());
            }
            
            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null && store.getCompany().getDefaultUser() != null){
                cell.setCellValue(store.getCompany().getDefaultUser().getLastname());
            }

            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null && store.getCompany().getDefaultUser() != null){
                cell.setCellValue(store.getCompany().getDefaultUser().getMobile());
            }

            cell = row.createCell(cellPosition++);
            if(store.getCompany() != null && store.getCompany().getDefaultUser() != null){
                cell.setCellValue(store.getCompany().getDefaultUser().getEmail());
            }

            cell = row.createCell(cellPosition++);
            // ??
            
            cell = row.createCell(cellPosition++);
            cell.setCellValue(store.getCode());
        }

        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        workbook.write(outByteStream);
        outByteStream.close();
        return outByteStream;
    }
}