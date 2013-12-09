/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;

public interface RetailerDao {

	// RETAILER
	
	Retailer getRetailerById(Long retailerId);

    Retailer getRetailerByCode(String retailerCode);

    Retailer getRetailerByCode(Long marketAreaId, Long retailerId, String retailerCode);

    List<Retailer> findAllRetailers();
	 
    List<Retailer> findRetailersByMarketAreaCode(String marketAreaCode);
    
	List<Retailer> findRetailers(Long marketAreaId, Long retailerId);

	List<Retailer> findRetailersByTags(Long marketAreaId, Long retailerId, List<String> tags);

	List<Retailer> findLastRetailers(Long marketAreaId, Long retailerId, int maxResults);
	
	List<Retailer> findBestRetailersByQualityOfService(Long marketAreaId, Long retailerId, int maxResults);
	
	List<Retailer> findBestRetailersByQualityPrice(Long marketAreaId, Long retailerId, int maxResults);

	List<Retailer> findRetailersByText(Long marketAreaId, Long retailerId, String searchTxt);
	
	void saveOrUpdateRetailer(Retailer retailer);

	void deleteRetailer(Retailer retailer);
	
	void saveOrUpdateRetailerCustomerRate(RetailerCustomerRate retailerCustomerRate);
	
	void deleteRetailerCustomerRate(RetailerCustomerRate retailerCustomerRate);
	
	void saveOrUpdateRetailerCustomerComment(RetailerCustomerComment retailerCustomerComment);
	
	void deleteRetailerCustomerComment(RetailerCustomerComment retailerCustomerComment);
	
	// STORE
	
	Store getStoreById(Long storeId);

	Store getStoreByCode(String storeCode);
	 
	List<Store> findStores();
	
	void saveOrUpdateStore(Store store);

	void deleteStore(Store store);

}