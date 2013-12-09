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
import org.hoteia.qalingo.core.dao.ProductMarketingDao;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productMarketingDao")
public class ProductMarketingDaoImpl extends AbstractGenericDaoImpl implements ProductMarketingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductMarketing getProductMarketingById(final Long productMarketingId) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);
        
        criteria.add(Restrictions.eq("id", productMarketingId));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
        return productMarketing;
	}

	public ProductMarketing getProductMarketingByCode(final Long marketAreaId, final Long retailerId, final String productMarketingCode) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.add(Restrictions.eq("code", productMarketingCode));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
		return productMarketing;
	}
	
	public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final Long retailerId) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final Long retailerId, final String text) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("businessName", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}
	
	public void saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
		if(productMarketing.getDateCreate() == null){
			productMarketing.setDateCreate(new Date());
		}
		productMarketing.setDateUpdate(new Date());
		if(productMarketing.getId() == null){
			em.persist(productMarketing);
		} else {
			em.merge(productMarketing);
		}
	}

	public void deleteProductMarketing(final ProductMarketing productMarketing) {
		em.remove(productMarketing);
	}

	// ASSET
	
	public Asset getProductMarketingAssetById(final Long productMarketingAssetId) {
        Criteria criteria = getSession().createCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productMarketingAssetId));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
        return productMarketingAsset;
	}

	public Asset getProductMarketingAssetByCode(final String assetCode) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        criteria.add(Restrictions.eq("code", assetCode));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
		return productMarketingAsset;
	}
	
	public void saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
		if(productMarketingAsset.getDateCreate() == null){
			productMarketingAsset.setDateCreate(new Date());
		}
		productMarketingAsset.setDateUpdate(new Date());
		if(productMarketingAsset.getId() == null){
			em.persist(productMarketingAsset);
		} else {
			em.merge(productMarketingAsset);
		}
	}

	public void deleteProductMarketingAsset(final Asset productMarketingAsset) {
		em.remove(productMarketingAsset);
	}
	
    private void addDefaultProductMarketingFetch(Criteria criteria) {
        criteria.setFetchMode("productBrand", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketingType", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketingAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productSkus", FetchMode.JOIN); 
        criteria.setFetchMode("productAssociationLinks", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN); 
    }
	
}