/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ProductCategoryDao;
import fr.hoteia.qalingo.core.common.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.common.domain.ProductMarketing;
import fr.hoteia.qalingo.core.common.service.ProductCategoryService;

@Service("ProductCategoryService")
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	public ProductCategoryVirtual getProductCategoryById(final String rawProductCategoryId) {
		long productCategoryId = -1;
		try {
			productCategoryId = Long.parseLong(rawProductCategoryId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productCategoryDao.getProductCategoryById(productCategoryId);
	}
	
	public ProductCategoryVirtual getProductCategoryByCode(final Long marketAreaId, final Long retailerId, final String productCategoryCode) {
		return productCategoryDao.getProductCategoryByCode(marketAreaId, retailerId, productCategoryCode);
	}

	public ProductCategoryVirtual getDefaultProductCategoryByProductMarketing(final Long marketAreaId, final Long retailerId, final ProductMarketing productMarketing) {
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

//	public List<ProductCategory> findProductCategory(ProductCategory criteria) {
//		return ProductCategoryDao.findByExample(criteria);
//	}
	
	public List<ProductCategoryVirtual> findProductCategoriesByProductMarketingId(final Long marketAreaId, final Long retailerId, final Long productMarketingId) {
		return productCategoryDao.findProductCategoriesByProductMarketingId(marketAreaId, retailerId, productMarketingId);
	}
	
	public List<ProductCategoryVirtual> findProductCategories(final Long marketAreaId, final Long retailerId) {
		List<ProductCategoryVirtual> categories = productCategoryDao.findProductCategories(marketAreaId, retailerId);
		return orderList(marketAreaId, categories);
	}

	public void saveOrUpdateProductCategory(ProductCategoryVirtual productCategory) {
		productCategoryDao.saveOrUpdateProductCategory(productCategory);
	}

	public void deleteProductCategory(ProductCategoryVirtual productCategory) {
		productCategoryDao.deleteProductCategory(productCategory);
	}

	protected List<ProductCategoryVirtual> orderList(final Long marketAreaId, final List<ProductCategoryVirtual> categories){
		List<ProductCategoryVirtual> sortedCategories = new LinkedList<ProductCategoryVirtual>(categories);
		Collections.sort(sortedCategories, new Comparator<ProductCategoryVirtual>() {
			@Override
			public int compare(ProductCategoryVirtual o1, ProductCategoryVirtual o2) {
				return o1.getOrder(marketAreaId) - o2.getOrder(marketAreaId);				
			}
		});
		return sortedCategories;
	}
}
