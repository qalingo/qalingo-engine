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

import fr.hoteia.qalingo.core.dao.ProductBrandDao;
import fr.hoteia.qalingo.core.domain.ProductBrand;

@Transactional
@Repository("productBrandDao")
public class ProductBrandDaoImpl extends AbstractGenericDaoImpl implements ProductBrandDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ProductBrand getProductBrandById(final Long productBrandId) {
		return em.find(ProductBrand.class, productBrandId);
	}

	public ProductBrand getProductBrandByCode(final Long marketAreaId, final String productBrandCode) {
		Session session = (Session) em.getDelegate();
		session.enableFilter("marketArea").setParameter("marketAreaId", marketAreaId);
		String sql = "FROM ProductBrand WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productBrandCode);
		ProductBrand productBrand = (ProductBrand) query.uniqueResult();
		return productBrand;
	}
	
	public void saveOrUpdateProductBrand(final ProductBrand productBrand) {
		if(productBrand.getDateCreate() == null){
			productBrand.setDateCreate(new Date());
		}
		productBrand.setDateUpdate(new Date());
		if(productBrand.getId() == null){
			em.persist(productBrand);
		} else {
			em.merge(productBrand);
		}
	}

	public void deleteProductBrand(final ProductBrand productBrand) {
		em.remove(productBrand);
	}

}
