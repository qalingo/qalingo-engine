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
import org.hoteia.qalingo.core.dao.CatalogCategoryDao;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("catalogCategoryDao")
public class CatalogCategoryDaoImpl extends AbstractGenericDaoImpl implements CatalogCategoryDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// MASTER
	
	public CatalogCategoryMaster getMasterCatalogCategoryById(final Long catalogCategoryId) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
        return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String catalogCategoryCode) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final Long marketAreaId, final String catalogCategoryCode) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryMaster> findRootCatalogCategories() {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);

        addDefaultCatalogCategoryFetch(criteria);
        
        criteria.add(Restrictions.isNull("defaultParentCatalogCategory"));
        criteria.addOrder(Order.asc("id"));        

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);

        addDefaultCatalogCategoryFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
		return categories;
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory) {
		
		// TODO : Denis : child object dates ?
		
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
		if(catalogCategory.getId() == null){
			em.persist(catalogCategory);
		} else {
			em.merge(catalogCategory);
		}
	}

	public void deleteCatalogCategory(CatalogCategoryMaster catalogCategory) {
		em.remove(catalogCategory);
	}
	
	// VIRTUAL
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryById(final Long catalogCategoryId) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
        return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final String catalogCategoryCode) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final Long marketAreaId, final String catalogCategoryCode) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        addDefaultCatalogCategoryFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryVirtual> findRootCatalogCategories(final Long marketAreaId) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);

        addDefaultCatalogCategoryFetch(criteria);
        
        criteria.add(Restrictions.isNull("defaultParentCatalogCategory"));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategories(final Long marketAreaId) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        addDefaultCatalogCategoryFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategoriesByProductMarketingCode(final Long marketAreaId, final String productMarketingCode) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);

        addDefaultCatalogCategoryFetch(criteria);
        
        criteria.createAlias("productMarketings", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productMarketing.code", productMarketingCode));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory) {
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
		if(catalogCategory.getId() == null){
			em.persist(catalogCategory);
		} else {
			em.merge(catalogCategory);
		}
	}

	public void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory) {
		em.remove(catalogCategory);
	}
	
    private void addDefaultCatalogCategoryFetch(Criteria criteria) {
        
        criteria.setFetchMode("categoryMaster", FetchMode.JOIN);

        criteria.setFetchMode("defaultParentCatalogCategory", FetchMode.JOIN);

        criteria.setFetchMode("catalogCategoryAttributes", FetchMode.JOIN);

        criteria.setFetchMode("catalogCategories", FetchMode.JOIN);

        criteria.createAlias("catalogCategories.catalogCategoryAttributes", "catalogSubCategoryAttributes", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("catalogSubCategoryAttributes", FetchMode.JOIN);

        criteria.setFetchMode("productMarketings", FetchMode.JOIN);

        criteria.createAlias("productMarketings.productMarketingAttributes", "productMarketingAttributes", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("productMarketingAttributes", FetchMode.JOIN);

        criteria.setFetchMode("assets", FetchMode.JOIN);
        
    }

}