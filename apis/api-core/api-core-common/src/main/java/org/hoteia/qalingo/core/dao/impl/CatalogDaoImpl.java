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
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.CatalogDao;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("catalogDao")
public class CatalogDaoImpl extends AbstractGenericDaoImpl implements CatalogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    public CatalogMaster getProductCatalogById(final Long catalogMasterId) {
        Criteria criteria = getSession().createCriteria(CatalogMaster.class);
        addDefaultCatalogFetch(criteria);
        criteria.add(Restrictions.eq("id", catalogMasterId));
        
        CatalogMaster catalogMaster = (CatalogMaster) criteria.uniqueResult();
        return catalogMaster;
	}

	public CatalogVirtual getCatalogVirtual(final Long marketAreaId) {
        Criteria criteria = getSession().createCriteria(CatalogVirtual.class);
        addDefaultCatalogFetch(criteria);
        criteria.setFetchMode("catalogMaster", FetchMode.JOIN);
        criteria.createAlias("marketArea", "ma", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("ma.id", marketAreaId));

        CatalogVirtual catalogVirtual = (CatalogVirtual) criteria.uniqueResult();
		return catalogVirtual;
	}
	
    public List<CatalogMaster> findAllCatalogMasters() {
        Criteria criteria = getSession().createCriteria(CatalogMaster.class);
        addDefaultCatalogFetch(criteria);
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogMaster> catalogMasters = criteria.list();
        return catalogMasters;
    }
	
	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		if(catalogMaster.getDateCreate() == null){
			catalogMaster.setDateCreate(new Date());
		}
		catalogMaster.setDateUpdate(new Date());
		if(catalogMaster.getId() == null){
			em.persist(catalogMaster);
		} else {
			em.merge(catalogMaster);
		}
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		em.remove(catalogMaster);
	}
	
    private void addDefaultCatalogFetch(Criteria criteria) {
      
        criteria.setFetchMode("catalogCategories", FetchMode.JOIN);

        criteria.createAlias("catalogCategories.catalogCategoryAttributes", "catalogCategoryAttributes", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("catalogCategoryAttributes", FetchMode.JOIN);

    }

}