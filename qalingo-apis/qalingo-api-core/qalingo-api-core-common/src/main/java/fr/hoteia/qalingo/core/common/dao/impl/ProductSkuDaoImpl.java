/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ProductSkuDao;
import fr.hoteia.qalingo.core.common.domain.ProductSku;

@Transactional
@Repository("productSkuDao")
public class ProductSkuDaoImpl extends AbstractGenericDaoImpl implements ProductSkuDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ProductSku getProductSkuById(Long productSkuId) {
		return em.find(ProductSku.class, productSkuId);
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
		Session session = (Session) em.getDelegate();
		initProductSkuFilter(session, marketAreaId, retailerId);

		String sql = "FROM ProductSku WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productSkuCode);
		ProductSku productSku = (ProductSku) query.uniqueResult();
		return productSku;
	}
		
	public List<ProductSku> findProductSkus(final Long marketAreaId, final Long retailerId, final Long productMarkettingId) {
		Session session = (Session) em.getDelegate();
		initProductSkuFilter(session, marketAreaId, retailerId);

		String sql = "FROM ProductSku WHERE productMarketing.id = :productMarkettingId";
		Query query = session.createQuery(sql);
		query.setLong("productMarkettingId", productMarkettingId);
		List<ProductSku> productSkus = (List<ProductSku>) query.list();
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

}
