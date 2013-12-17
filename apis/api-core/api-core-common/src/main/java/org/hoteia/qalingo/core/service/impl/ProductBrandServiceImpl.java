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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.ProductBrandDao;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.service.ProductBrandService;

@Service("productBrandService")
@Transactional
public class ProductBrandServiceImpl implements ProductBrandService {

	@Autowired
	private ProductBrandDao productBrandDao;

	public ProductBrand getProductBrandById(String rawProductBrandId) {
		long ProductBrandId = -1;
		try {
			ProductBrandId = Long.parseLong(rawProductBrandId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productBrandDao.getProductBrandById(ProductBrandId);
	}
	
	public ProductBrand getProductBrandByCode(final Long marketAreaId, final String productBrandCode) {
		return productBrandDao.getProductBrandByCode(marketAreaId, productBrandCode);
	}

	public void saveOrUpdateProductBrand(ProductBrand productBrand) {
		productBrandDao.saveOrUpdateProductBrand(productBrand);
	}

	public void deleteProductBrand(ProductBrand productBrand) {
		productBrandDao.deleteProductBrand(productBrand);
	}
	
	@Override
	public List<ProductBrand> findProductBrandsByCatalogCategoryCode(String categoryCode) {
		return productBrandDao.findProductBrandsByCatalogCategoryCode(categoryCode);
	}

}
