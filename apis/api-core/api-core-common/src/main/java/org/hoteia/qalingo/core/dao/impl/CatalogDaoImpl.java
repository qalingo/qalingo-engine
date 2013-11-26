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
@Repository("productCatalogDao")
public class CatalogDaoImpl extends AbstractGenericDaoImpl implements CatalogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<CatalogMaster> findAllCatalogMasters() {
//        return em.createQuery("SELECT cm FROM CatalogMaster cm").getResultList();
        Criteria criteria = getSession().createCriteria(CatalogMaster.class);

        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogMaster> catalogMasters = criteria.list();
        return catalogMasters;
    }

    public CatalogMaster getProductCatalogById(final Long catalogMasterId) {
        
        Criteria criteria = getSession().createCriteria(CatalogMaster.class);
        criteria.add(Restrictions.eq("id", catalogMasterId));
        
        addDefaultFetch(criteria);
        
        CatalogMaster catalogMaster = (CatalogMaster) criteria.uniqueResult();
        return catalogMaster;
        
//		return em.find(CatalogMaster.class, catalogMasterId);
	}

	public CatalogVirtual getCatalogVirtual(final Long marketAreaId, final Long retailerId) {
	    
	    initCatalogVirtual(getSession(), marketAreaId, retailerId);

        Criteria criteria = getSession().createCriteria(CatalogVirtual.class);
        
        criteria.setFetchMode("catalogMaster", FetchMode.JOIN);

        addDefaultFetch(criteria);

        criteria.createAlias("marketArea", "ma", JoinType.LEFT_OUTER_JOIN);
        criteria.add( Restrictions.eq("ma.id", marketAreaId));

        CatalogVirtual catalogVirtual = (CatalogVirtual) criteria.uniqueResult();
        
//		Session session = (Session) em.getDelegate();
//		initCatalogVirtual(session, marketAreaId, retailerId);
//		String sql = "SELECT cv FROM CatalogVirtual cv, MarketArea ma WHERE cv.id = ma.virtualCatalogId AND ma.id = :marketAreaId";
//		Query query = session.createQuery(sql);
//		query.setLong("marketAreaId", marketAreaId);
//		CatalogVirtual catalogVirtual = (CatalogVirtual) query.uniqueResult();
		
		return catalogVirtual;
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
	
    private void addDefaultFetch(Criteria criteria) {
      
        criteria.setFetchMode("catalogCategories", FetchMode.JOIN);

//        criteria.createAlias("catalogCategories.defaultParentCatalogCategory", "defaultParentCatalogCategory", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("defaultParentCatalogCategory", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.categoryMaster", "categoryMaster", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("categoryMaster", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.catalogCategoryGlobalAttributes", "catalogCategoryGlobalAttributes", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("catalogCategoryGlobalAttributes", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.catalogCategoryMarketAreaAttributes", "catalogCategoryMarketAreaAttributes", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("catalogCategoryMarketAreaAttributes", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.productMarketings", "productMarketings", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("productMarketings", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.assetsIsGlobal", "assetsIsGlobal", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("assetsIsGlobal", FetchMode.JOIN);
//
//        criteria.createAlias("catalogCategories.assetsByMarketArea", "assetsByMarketArea", JoinType.LEFT_OUTER_JOIN);
//        criteria.setFetchMode("assetsByMarketArea", FetchMode.JOIN);
    }

}