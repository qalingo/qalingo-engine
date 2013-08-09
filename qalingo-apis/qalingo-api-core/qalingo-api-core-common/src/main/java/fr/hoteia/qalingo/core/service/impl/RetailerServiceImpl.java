/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.RetailerDao;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.RetailerCustomerComment;
import fr.hoteia.qalingo.core.domain.RetailerCustomerRate;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.service.RetailerService;

@Service("retailerService")
@Transactional
public class RetailerServiceImpl implements RetailerService {

	@Autowired
	private RetailerDao retailerDao;

	// RETAILER
	public Retailer getRetailerById(final String rawRetailerId) {
		long retailerId = -1;
		try {
			retailerId = Long.parseLong(rawRetailerId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return retailerDao.getRetailerById(retailerId);
	}
	
	public Retailer getRetailerByCode(final Long marketAreaId, final Long retailerId, final String retailerCode) {
		return retailerDao.getRetailerByCode(marketAreaId, retailerId, retailerCode);
	}

	public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId) {
		return retailerDao.findRetailers(marketAreaId, retailerId);
	}
	
	public List<Retailer> findRetailersByTag(final Long marketAreaId, final Long retailerId, final String tag) {
		List<String> tags = new ArrayList<String>();
		tags.add(tag);
		return retailerDao.findRetailersByTags(marketAreaId, retailerId, tags);
	}
	
	public List<Retailer> findRetailersByTags(final Long marketAreaId, final Long retailerId, final List<String> tags) {
		return retailerDao.findRetailersByTags(marketAreaId, retailerId, tags);
	}
	
	public List<Retailer> findLastRetailers(Long marketAreaId, Long retailerId, int maxResults) {
		return retailerDao.findLastRetailers(marketAreaId, retailerId, maxResults);
	}
	
	public List<Retailer> findBestRetailersByQualityOfService(Long marketAreaId, Long retailerId, int maxResults) {
		return retailerDao.findBestRetailersByQualityOfService(marketAreaId, retailerId, maxResults);
	}
	
	public List<Retailer> findBestRetailersByQualityPrice(Long marketAreaId, Long retailerId, int maxResults) {
		return retailerDao.findBestRetailersByQualityPrice(marketAreaId, retailerId, maxResults);
	}
	
	public void saveOrUpdateRetailer(final Retailer retailer) {
		retailerDao.saveOrUpdateRetailer(retailer);
	}

	public void deleteRetailer(final Retailer retailer) {
		retailerDao.deleteRetailer(retailer);
	}
	
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
	
	public Store getStoreById(final String rawStoreId) {
		long storeId = -1;
		try {
			storeId = Long.parseLong(rawStoreId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return retailerDao.getStoreById(storeId);
	}
	
	public Store getStoreByCode(final String storeCode) {
		return retailerDao.getStoreByCode(storeCode);
	}

	public List<Store> findStores() {
		return retailerDao.findStores();
	}
	
	public void saveOrUpdateStore(final Store store) {
		retailerDao.saveOrUpdateStore(store);
	}

	public void deleteStore(final Store store) {
		retailerDao.deleteStore(store);
	}

}