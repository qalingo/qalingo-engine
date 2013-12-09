/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.ProductSkuDao;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productSkuDao")
public class ProductSkuDaoImpl extends AbstractGenericDaoImpl implements ProductSkuDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductSku getProductSkuById(final Long productSkuId) {
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", productSkuId));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        return productSku;
	}
	
	public ProductSku getProductSkuByCode(final Long marketAreaId, final Long retailerId, final String productSkuCode) {
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", productSkuCode));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
		return productSku;
	}
		
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final Long productMarkettingId) {
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("productMarkettingId", productMarkettingId));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
		return productSkus;
	}
	
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final String text) {
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("businessName", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
		return productSkus;
	}
	
	public void saveOrUpdateProductSku(final ProductSku productSku) {
		if(productSku.getDateCreate() == null){
			productSku.setDateCreate(new Date());
		}
		productSku.setDateUpdate(new Date());
		if(productSku.getId() == null){
			em.persist(productSku);
		} else {
			em.merge(productSku);
		}
	}

	public void deleteProductSku(final ProductSku productSku) {
		em.remove(productSku);
	}
	
    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("productSkuAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketing", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN); 
        criteria.setFetchMode("prices", FetchMode.JOIN); 
        criteria.setFetchMode("stocks", FetchMode.JOIN); 
        criteria.setFetchMode("retailers", FetchMode.JOIN); 
    }

}