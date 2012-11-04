/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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

import fr.hoteia.qalingo.core.common.dao.ProductMarketingDao;
import fr.hoteia.qalingo.core.common.domain.ProductMarketing;
import fr.hoteia.qalingo.core.common.service.ProductMarketingService;

@Service("productMarketingService")
@Transactional
public class ProductMarketingServiceImpl implements ProductMarketingService {

	@Autowired
	private ProductMarketingDao productMarketingDao;

	public ProductMarketing getProductMarketingById(final String rawProductMarketingId) {
		long productMarketingId = -1;
		try {
			productMarketingId = Long.parseLong(rawProductMarketingId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return productMarketingDao.getProductMarketingById(productMarketingId);
	}

//	public ProductMarketing getProductMarketingByCode(String productMarketingCode) {
//		return productMarketingDao.getProductMarketingByCode(productMarketingCode);
//	}
	
	public ProductMarketing getProductMarketingByCode(final Long marketAreaId, final Long retailerId, final String productMarketingCode) {
		return productMarketingDao.getProductMarketingByCode(marketAreaId, retailerId, productMarketingCode);
	}
	
//	public List<ProductMarketing> findProductMarketing(ProductMarketing criteria) {
//		return productMarketingDao.findByExample(criteria);
//	}
	
	public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final Long retailerId) {
		List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketings(marketAreaId, retailerId);
		return orderList(marketAreaId, productMarketings);
	}
	
	public List<ProductMarketing> findProductMarketingsByBrandId(final Long marketAreaId, final Long retailerId, final Long brandId) {
		
		// TODO 
		
		List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketings(marketAreaId, retailerId);
		return orderList(marketAreaId, productMarketings);
	}

	public void saveOrUpdateProductMarketing(ProductMarketing productMarketing) {
		productMarketingDao.saveOrUpdateProductMarketing(productMarketing);
	}

	public void deleteProductMarketing(ProductMarketing productMarketing) {
		productMarketingDao.deleteProductMarketing(productMarketing);
	}
	
	protected List<ProductMarketing> orderList(final Long marketAreaId, final List<ProductMarketing> productMarketings){
		if(productMarketings != null){
			List<ProductMarketing> sortedObjects = new LinkedList<ProductMarketing>(productMarketings);
			Collections.sort(sortedObjects, new Comparator<ProductMarketing>() {
				@Override
				public int compare(ProductMarketing o1, ProductMarketing o2) {
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
		return null;
	}

}
