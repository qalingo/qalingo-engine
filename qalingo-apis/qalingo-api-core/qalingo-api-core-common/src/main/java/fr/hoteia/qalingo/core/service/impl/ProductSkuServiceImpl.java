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
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.ProductSkuDao;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.service.ProductSkuService;

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
	
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final String text) {
		List<ProductSku> skus = productSkuDao.findProductSkus(marketAreaId, retailerId, text);
		return orderList(marketAreaId, skus);
	}

	public void saveOrUpdateProductSku(ProductSku productSku) {
		productSkuDao.saveOrUpdateProductSku(productSku);
	}

	public void deleteProductSku(ProductSku productSku) {
		productSkuDao.deleteProductSku(productSku);
	}
	
	protected List<ProductSku> orderList(final Long marketAreaId, final List<ProductSku> skus){
		List<ProductSku> sortedObjects = new LinkedList<ProductSku>(skus);
		Collections.sort(sortedObjects, new Comparator<ProductSku>() {
			@Override
			public int compare(ProductSku o1, ProductSku o2) {
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

}
