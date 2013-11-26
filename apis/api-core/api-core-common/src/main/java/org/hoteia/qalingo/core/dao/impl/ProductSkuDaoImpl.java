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
import org.hoteia.qalingo.core.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productSkuDao")
public class ProductSkuDaoImpl extends AbstractGenericDaoImpl implements ProductSkuDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductSku getProductSkuById(final Long productSkuId) {
//		return em.find(ProductSku.class, productSkuId);
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", productSkuId));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        return productSku;
	}
	
//	public ProductSku getProductSkuByCode(Long marketAreaId, String productSkuCode) {
//		Session session = (Session) em.getDelegate();
//
//		session.enableFilter("filterProductSkuAttributeIsGlobal");
//		
//		session.enableFilter("filterProductSkuAttributeByMarketArea");
//		session.getEnabledFilter("filterProductSkuAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
//
//		String sql = "FROM ProductSku WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", productSkuCode);
//		ProductSku productSku = (ProductSku) query.uniqueResult();
//		return productSku;
//	}
	
	public ProductSku getProductSkuByCode(final Long marketAreaId, final Long retailerId, final String productSkuCode) {
//		Session session = (Session) em.getDelegate();
//		initProductSkuFilter(session, marketAreaId, retailerId);
//
//		String sql = "FROM ProductSku WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", productSkuCode);
//		ProductSku productSku = (ProductSku) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(User.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", productSkuCode));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
		return productSku;
	}
		
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final Long productMarkettingId) {
//		Session session = (Session) em.getDelegate();
//		initProductSkuFilter(session, marketAreaId, retailerId);
//
//		String sql = "FROM ProductSku WHERE productMarketing.id = :productMarkettingId";
//		Query query = session.createQuery(sql);
//		query.setLong("productMarkettingId", productMarkettingId);
//		List<ProductSku> productSkus = (List<ProductSku>) query.list();
        Criteria criteria = getSession().createCriteria(ProductSku.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("productMarkettingId", productMarkettingId));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
		return productSkus;
	}
	
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final String text) {
//		Session session = (Session) em.getDelegate();
//		initProductMarketingFilter(session, marketAreaId, retailerId);
//		String sql = "FROM ProductSku WHERE code like :text OR businessName like :text OR description like :text";
//		Query query = session.createQuery(sql);
//		query.setString("text", "%" + text + "%");
//		List<ProductSku> productSkus = (List<ProductSku>) query.list();
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
        criteria.setFetchMode("productSkuGlobalAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productSkuMarketAreaAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketing", FetchMode.JOIN); 
        criteria.setFetchMode("assetsIsGlobal", FetchMode.JOIN); 
        criteria.setFetchMode("assetsByMarketArea", FetchMode.JOIN); 
        criteria.setFetchMode("prices", FetchMode.JOIN); 
        criteria.setFetchMode("stocks", FetchMode.JOIN); 
        criteria.setFetchMode("retailers", FetchMode.JOIN); 
    }

}