/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.ProductMarketingDao;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.Asset;

@Transactional
@Repository("productMarketingDao")
public class ProductMarketingDaoImpl extends AbstractGenericDaoImpl implements ProductMarketingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductMarketing getProductMarketingById(final Long productMarketingId) {
		return em.find(ProductMarketing.class, productMarketingId);
	}

	public ProductMarketing getProductMarketingByCode(final Long marketAreaId, final Long retailerId, final String productMarketingCode) {
		Session session = (Session) em.getDelegate();
		initProductMarketingFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductMarketing WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productMarketingCode);
		ProductMarketing productMarketing = (ProductMarketing) query.uniqueResult();
		return productMarketing;
	}
	
	public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initProductMarketingFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductMarketing";
		Query query = session.createQuery(sql);
		List<ProductMarketing> productMarketings = (List<ProductMarketing>) query.list();
		return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final Long retailerId, final String text) {
		Session session = (Session) em.getDelegate();
		initProductMarketingFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductMarketing WHERE code like :text OR businessName like :text OR description like :text";
		Query query = session.createQuery(sql);
		query.setString("text", "%" + text + "%");
		List<ProductMarketing> productMarketings = (List<ProductMarketing>) query.list();
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
		return em.find(Asset.class, productMarketingAssetId);
	}

	public Asset getProductMarketingAssetByCode(final String assetCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Asset WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", assetCode);
		Asset productMarketingAsset = (Asset) query.uniqueResult();
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
}
