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

import fr.hoteia.qalingo.core.dao.ProductCategoryDao;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.service.ProductCategoryService;

@Service("ProductCategoryService")
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	// MASTER
	public ProductCategoryMaster getMasterProductCategoryById(final String rawProductCategoryId) {
		long productCategoryId = -1;
		try {
			productCategoryId = Long.parseLong(rawProductCategoryId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productCategoryDao.getMasterProductCategoryById(productCategoryId);
	}
	
	public ProductCategoryMaster getMasterProductCategoryByCode(final String productCategoryCode) {
		return productCategoryDao.getMasterProductCategoryByCode(productCategoryCode);
	}
	
	public ProductCategoryMaster getMasterProductCategoryByCode(final Long marketAreaId, final Long retailerId, final String productCategoryCode) {
		return productCategoryDao.getMasterProductCategoryByCode(marketAreaId, retailerId, productCategoryCode);
	}
	
	public List<ProductCategoryMaster> findRootProductCategories() {
		List<ProductCategoryMaster> categories = productCategoryDao.findRootProductCategories();
		return orderList(categories);
	}
	
	public List<ProductCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId) {
		return productCategoryDao.findMasterCategoriesByMarketIdAndRetailerId(marketAreaId, retailerId);
	}
	
	public void saveOrUpdateProductCategory(ProductCategoryMaster productCategory) {
		productCategoryDao.saveOrUpdateProductCategory(productCategory);
	}

	public void deleteProductCategory(ProductCategoryMaster productCategory) {
		productCategoryDao.deleteProductCategory(productCategory);
	}
	
	// VIRTUAL
	public ProductCategoryVirtual getVirtualProductCategoryById(final String rawProductCategoryId) {
		long productCategoryId = -1;
		try {
			productCategoryId = Long.parseLong(rawProductCategoryId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productCategoryDao.getVirtualProductCategoryById(productCategoryId);
	}
	
	public ProductCategoryVirtual getVirtualProductCategoryByCode(final String productCategoryCode) {
		return productCategoryDao.getVirtualProductCategoryByCode(productCategoryCode);
	}
	
	public ProductCategoryVirtual getVirtualProductCategoryByCode(final Long marketAreaId, final Long retailerId, final String productCategoryCode) {
		return productCategoryDao.getVirtualProductCategoryByCode(marketAreaId, retailerId, productCategoryCode);
	}

	public ProductCategoryVirtual getDefaultVirtualProductCategoryByProductMarketing(final Long marketAreaId, final Long retailerId, final ProductMarketing productMarketing) {
		List<ProductCategoryVirtual> categories = productCategoryDao.findProductCategoriesByProductMarketingId(marketAreaId, retailerId, productMarketing.getId());
		ProductCategoryVirtual productCategoryVirtual = null;
		if(categories != null){
			for (Iterator<ProductCategoryVirtual> iterator = categories.iterator(); iterator.hasNext();) {
				ProductCategoryVirtual productCategoryVirtualIterator = (ProductCategoryVirtual) iterator.next();
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
	
	public List<ProductCategoryVirtual> findRootProductCategories(final Long marketAreaId, final Long retailerId) {
		List<ProductCategoryVirtual> categories = productCategoryDao.findRootProductCategories(marketAreaId, retailerId);
		return orderList(marketAreaId, categories);
	}
	
	public List<ProductCategoryVirtual> findVirtualCategories(final Long marketAreaId, final Long retailerId) {
		List<ProductCategoryVirtual> categories = productCategoryDao.findProductCategories(marketAreaId, retailerId);
		return orderList(marketAreaId, categories);
	}

	public List<ProductCategoryVirtual> findVirtualCategoriesByProductMarketingId(final Long marketAreaId, final Long retailerId, final Long productMarketingId) {
		return productCategoryDao.findProductCategoriesByProductMarketingId(marketAreaId, retailerId, productMarketingId);
	}
	
	public void saveOrUpdateProductCategory(ProductCategoryVirtual productCategory) {
		productCategoryDao.saveOrUpdateProductCategory(productCategory);
	}

	public void deleteProductCategory(ProductCategoryVirtual productCategory) {
		productCategoryDao.deleteProductCategory(productCategory);
	}

	protected List<ProductCategoryVirtual> orderList(final Long marketAreaId, final List<ProductCategoryVirtual> categories){
		List<ProductCategoryVirtual> sortedObjects = new LinkedList<ProductCategoryVirtual>(categories);
		Collections.sort(sortedObjects, new Comparator<ProductCategoryVirtual>() {
			@Override
			public int compare(ProductCategoryVirtual o1, ProductCategoryVirtual o2) {
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
	
	protected List<ProductCategoryMaster> orderList(final List<ProductCategoryMaster> categories){
		List<ProductCategoryMaster> sortedObjects = new LinkedList<ProductCategoryMaster>(categories);
		Collections.sort(sortedObjects, new Comparator<ProductCategoryMaster>() {
			@Override
			public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
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
