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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.ProductBrandDao;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productBrandDao")
public class ProductBrandDaoImpl extends AbstractGenericDaoImpl implements ProductBrandDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductBrand getProductBrandById(final Long productBrandId) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        criteria.add(Restrictions.eq("id", productBrandId));
        ProductBrand productBrand = (ProductBrand) criteria.uniqueResult();
        return productBrand;
	}

	public ProductBrand getProductBrandByCode(final Long marketAreaId, final String productBrandCode) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        criteria.add(Restrictions.eq("code", productBrandCode));
        ProductBrand productBrand = (ProductBrand) criteria.uniqueResult();
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
	
    private void addDefaultProductBrandFetch(Criteria criteria) {
        
    }
	
}