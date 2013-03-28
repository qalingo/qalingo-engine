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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CatalogCategoryDao;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;

@Service("catalogCategoryService")
@Transactional
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

	@Autowired
	private CatalogCategoryDao productCategoryDao;

	// MASTER
	public CatalogCategoryMaster getMasterCatalogCategoryById(final String rawProductCategoryId) {
		long productCategoryId = -1;
		try {
			productCategoryId = Long.parseLong(rawProductCategoryId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productCategoryDao.getMasterCatalogCategoryById(productCategoryId);
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String productCategoryCode) {
		return productCategoryDao.getMasterCatalogCategoryByCode(productCategoryCode);
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final Long marketAreaId, final Long retailerId, final String productCategoryCode) {
		return productCategoryDao.getMasterCatalogCategoryByCode(marketAreaId, retailerId, productCategoryCode);
	}
	
	public List<CatalogCategoryMaster> findRootCatalogCategories() {
		List<CatalogCategoryMaster> categories = productCategoryDao.findRootCatalogCategories();
		return orderList(categories);
	}
	
	public List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId) {
		return productCategoryDao.findMasterCategoriesByMarketIdAndRetailerId(marketAreaId, retailerId);
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryMaster productCategory) {
		productCategoryDao.saveOrUpdateCatalogCategory(productCategory);
	}

	public void deleteCatalogCategory(CatalogCategoryMaster productCategory) {
		productCategoryDao.deleteCatalogCategory(productCategory);
	}
	
	// VIRTUAL
	public CatalogCategoryVirtual getVirtualCatalogCategoryById(final String rawProductCategoryId) {
		long productCategoryId = -1;
		try {
			productCategoryId = Long.parseLong(rawProductCategoryId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productCategoryDao.getVirtualCatalogCategoryById(productCategoryId);
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final String productCategoryCode) {
		return productCategoryDao.getVirtualCatalogCategoryByCode(productCategoryCode);
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final Long marketAreaId, final Long retailerId, final String productCategoryCode) {
		return productCategoryDao.getVirtualCatalogCategoryByCode(marketAreaId, retailerId, productCategoryCode);
	}

	public CatalogCategoryVirtual getDefaultVirtualCatalogCategoryByProductMarketing(final Long marketAreaId, final Long retailerId, final ProductMarketing productMarketing) {
		List<CatalogCategoryVirtual> categories = productCategoryDao.findCatalogCategoriesByProductMarketingId(marketAreaId, retailerId, productMarketing.getId());
		CatalogCategoryVirtual productCategoryVirtual = null;
		if(categories != null){
			for (Iterator<CatalogCategoryVirtual> iterator = categories.iterator(); iterator.hasNext();) {
				CatalogCategoryVirtual productCategoryVirtualIterator = (CatalogCategoryVirtual) iterator.next();
				if(productCategoryVirtualIterator.isDefault()){
					productCategoryVirtual = productCategoryVirtualIterator;
				}
			}
			if(categories.size() > 0
					&& productCategoryVirtual == null){
				productCategoryVirtual = categories.iterator().next();
			}
		}
		return productCategoryVirtual;
	}
	
	public List<CatalogCategoryVirtual> findRootCatalogCategories(final Long marketAreaId, final Long retailerId) {
		List<CatalogCategoryVirtual> categories = productCategoryDao.findRootCatalogCategories(marketAreaId, retailerId);
		return orderList(marketAreaId, categories);
	}
	
	public List<CatalogCategoryVirtual> findVirtualCategories(final Long marketAreaId, final Long retailerId) {
		List<CatalogCategoryVirtual> categories = productCategoryDao.findCatalogCategories(marketAreaId, retailerId);
		return orderList(marketAreaId, categories);
	}

	public List<CatalogCategoryVirtual> findVirtualCategoriesByProductMarketingId(final Long marketAreaId, final Long retailerId, final Long productMarketingId) {
		return productCategoryDao.findCatalogCategoriesByProductMarketingId(marketAreaId, retailerId, productMarketingId);
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryVirtual productCategory) {
		productCategoryDao.saveOrUpdateCatalogCategory(productCategory);
	}

	public void deleteCatalogCategory(CatalogCategoryVirtual productCategory) {
		productCategoryDao.deleteCatalogCategory(productCategory);
	}

	protected List<CatalogCategoryVirtual> orderList(final Long marketAreaId, final List<CatalogCategoryVirtual> categories){
		List<CatalogCategoryVirtual> sortedObjects = new LinkedList<CatalogCategoryVirtual>(categories);
		Collections.sort(sortedObjects, new Comparator<CatalogCategoryVirtual>() {
			@Override
			public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
				if(o1 != null
						&& o2 != null){
					Integer order1 = o1.getOrder(marketAreaId);
					Integer order2 = o2.getOrder(marketAreaId);
					if(order1 != null
							&& order2 != null){
						return order1.compareTo(order2);				
					} else {
						return o1.getId().compareTo(o2.getId());	
					}
				}
				return 0;
			}
		});
		return sortedObjects;
	}
	
	protected List<CatalogCategoryMaster> orderList(final List<CatalogCategoryMaster> categories){
		List<CatalogCategoryMaster> sortedObjects = new LinkedList<CatalogCategoryMaster>(categories);
		Collections.sort(sortedObjects, new Comparator<CatalogCategoryMaster>() {
			@Override
			public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
				if(o1 != null
						&& o2 != null){
					Integer order1 = o1.getOrder();
					Integer order2 = o2.getOrder();
					if(order1 != null
							&& order2 != null){
						return order1.compareTo(order2);				
					} else {
						return o1.getId().compareTo(o2.getId());	
					}
				}
				return 0;
			}
		});
		return sortedObjects;
	}
}
