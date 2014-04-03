/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;

public interface RetailerService {

	// RETAILER
	
    Retailer getRetailerById(Long retailerId, Object... params);
    
	Retailer getRetailerById(String retailerId, Object... params);

    Retailer getRetailerByCode(String retailerCode, Object... params);

    Retailer getRetailerByCode(Long marketAreaId, Long retailerId, String retailerCode, Object... params);

    List<Retailer> findAllRetailers(Object... params);

	List<Retailer> findRetailers(Long marketAreaId, Long retailerId, Object... params);

    List<Retailer> findRetailersByMarketAreaCode(String marketAreaCode, Object... params);

	List<Retailer> findRetailersByTag(Long marketAreaId, Long retailerId, String tag, Object... params);

	List<Retailer> findRetailersByTags(Long marketAreaId, Long retailerId, List<String> tags, Object... params);

	List<Retailer> findLastRetailers(Long marketAreaId, Long retailerId, int maxResults, Object... params);
	
	List<Retailer> findBestRetailersByQualityOfService(Long marketAreaId, Long retailerId, int maxResults, Object... params);
	
	List<Retailer> findBestRetailersByQualityPrice(Long marketAreaId, Long retailerId, int maxResults, Object... params);

	List<Retailer> findRetailersByText(Long marketAreaId, Long retailerId, String searchTxt, Object... params);

	void saveOrUpdateRetailer(Retailer retailer);
	
	void deleteRetailer(Retailer retailer);
	
	String getRetailerLogoFilePath(String logo);
	
	String getRetailerLogoWebPath(String logo) throws Exception;
	
    // RETAILER COMMENT/RATE
	
	void saveOrUpdateRetailerCustomerRate(RetailerCustomerRate retailerCustomerRate);
	
	void deleteRetailerCustomerRate(RetailerCustomerRate retailerCustomerRate);
	
	void saveOrUpdateRetailerCustomerComment(RetailerCustomerComment retailerCustomerComment);
	
	void deleteRetailerCustomerComment(RetailerCustomerComment retailerCustomerComment);
	
	// STORE
	
    Store getStoreById(Long storeId, Object... params);
    
	Store getStoreById(String storeId, Object... params);

	Store getStoreByCode(String storeCode, Object... params);

	List<Store> findStores(Object... params);
	
	List<Store> findStoresByRetailerId(Long retailerId, Object... params);
	
	void saveOrUpdateStore(Store store);
	
	void deleteStore(Store store);

}