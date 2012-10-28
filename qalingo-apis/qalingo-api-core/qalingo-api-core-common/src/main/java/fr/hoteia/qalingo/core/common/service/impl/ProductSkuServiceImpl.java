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
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ProductSkuDao;
import fr.hoteia.qalingo.core.common.domain.ProductSku;
import fr.hoteia.qalingo.core.common.service.ProductSkuService;

@Service("productSkuService")
@Transactional
public class ProductSkuServiceImpl implements ProductSkuService {

	@Autowired
	private ProductSkuDao productSkuDao;

//	public ProductSku getProductSkuById(final String rawProductSkuId) {
//		long productSkuId = -1;
//		try {
//			productSkuId = Long.parseLong(rawProductSkuId);
//		} catch (NumberFormatException e) {
//			throw new IllegalArgumentException(e);
//		}
//		return productSkuDao.getProductSkuById(productSkuId);
//	}

//	public ProductSku getProductSkuByCode(String productSkuCode) {
//		return productSkuDao.getProductSkuByCode(productSkuCode);
//	}
	
//	public ProductSku getProductSkuByCode(final Long marketAreaId, final String productSkuCode) {
//		return productSkuDao.getProductSkuByCode(marketAreaId, productSkuCode);
//	}
	
	public ProductSku getProductSkuByCode(final Long marketAreaId, final Long retailerId, final String productSkuCode) {
		return productSkuDao.getProductSkuByCode(marketAreaId, retailerId, productSkuCode);
	}
	
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final Long productMarkettingId) {
		List<ProductSku> skus = productSkuDao.findProductSkus(marketAreaId, retailerId, productMarkettingId);
		return orderList(marketAreaId, skus);
	}
	
//	public List<ProductSku> findProductSku(ProductSku criteria) {
//		return productSkuDao.findByExample(criteria);
//	}

	public void saveOrUpdateProductSku(ProductSku productSku) {
		productSkuDao.saveOrUpdateProductSku(productSku);
	}

	public void deleteProductSku(ProductSku productSku) {
		productSkuDao.deleteProductSku(productSku);
	}
	
	protected List<ProductSku> orderList(final Long marketAreaId, final List<ProductSku> skus){
		List<ProductSku> sortedSkus = new LinkedList<ProductSku>(skus);
		Collections.sort(sortedSkus, new Comparator<ProductSku>() {
			@Override
			public int compare(ProductSku o1, ProductSku o2) {
				return o1.getOrder(marketAreaId) - o2.getOrder(marketAreaId);				
			}
		});
		return sortedSkus;
	}

}
