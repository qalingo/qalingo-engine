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
//		return em.find(CatalogCategoryMaster.class, catalogCategoryId);.
        Criteria criteria = getSession().createCriteria(CatalogCategoryMaster.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
        return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String catalogCategoryCode) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM CatalogCategoryMaster WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", catalogCategoryCode);
//		CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(CatalogCategoryMaster.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final Long marketAreaId, final String catalogCategoryCode) {
//		Session session = (Session) em.getDelegate();
//		initCategoryMasterFilter(session, marketAreaId, retailerId);
//		String sql = "FROM CatalogCategoryMaster WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", catalogCategoryCode);
//		CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(CatalogCategoryMaster.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryMaster> findRootCatalogCategories() {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM CatalogCategoryMaster WHERE defaultParentCatalogCategory is null";
//		Query query = session.createQuery(sql);
//		List<CatalogCategoryMaster> categories = (List<CatalogCategoryMaster>) query.list();
        Criteria criteria = getSession().createCriteria(CatalogCategoryMaster.class);

        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.eq("defaultParentCatalogCategory", null));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId) {
//		Session session = (Session) em.getDelegate();
//		initCategoryVirtualFilter(session, marketAreaId, retailerId);
//		String sql = "SELECT catalogCategoryMaster FROM CatalogCategoryMaster catalogCategoryMaster";
//		Query query = session.createQuery(sql);
//		List<CatalogCategoryMaster> categories = (List<CatalogCategoryMaster>) query.list();
        Criteria criteria = getSession().createCriteria(CatalogCategoryMaster.class);

        addDefaultFetch(criteria);
        
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
//		return em.find(CatalogCategoryVirtual.class, catalogCategoryId);
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
        return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final String catalogCategoryCode) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM CatalogCategoryVirtual WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", catalogCategoryCode);
//		CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final Long marketAreaId, final String catalogCategoryCode) {
//		Session session = (Session) em.getDelegate();
//		initCategoryVirtualFilter(session, marketAreaId, retailerId);
//		String sql = "FROM CatalogCategoryVirtual WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", catalogCategoryCode);
//		CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);
        
        addDefaultFetch(criteria);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryVirtual> findRootCatalogCategories(final Long marketAreaId) {
//		Session session = (Session) em.getDelegate();
//		initCategoryVirtualFilter(session, marketAreaId, retailerId);
//		String sql = "FROM CatalogCategoryVirtual WHERE defaultParentCatalogCategory is null";
//		Query query = session.createQuery(sql);
//		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);

        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.eq("defaultParentCatalogCategory", null));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategories(final Long marketAreaId) {
//		Session session = (Session) em.getDelegate();
//		initCategoryVirtualFilter(session, marketAreaId, retailerId);
//		String sql = "FROM CatalogCategoryVirtual";
//		Query query = session.createQuery(sql);
//		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);
        
        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategoriesByProductMarketingId(final Long marketAreaId, final Long productMarketingId) {
//		Session session = (Session) em.getDelegate();
//		initCategoryVirtualFilter(session, marketAreaId, retailerId);
//		String sql = "SELECT catalogCategoryVirtual FROM CatalogCategoryVirtual catalogCategoryVirtual, IN (catalogCategoryVirtual.productMarketings) AS productMarketing WHERE productMarketing.id = :productMarketingId";
//		Query query = session.createQuery(sql);
//		query.setLong("productMarketingId", productMarketingId);
//		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
        Criteria criteria = getSession().createCriteria(CatalogCategoryVirtual.class);

        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.eq("productMarketing.code", productMarketingId));
        
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
	
    private void addDefaultFetch(Criteria criteria) {
        criteria.createAlias("defaultParentCatalogCategory", "defaultParentCatalogCategory", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("defaultParentCatalogCategory", FetchMode.JOIN);
        
        criteria.createAlias("catalogCategoryGlobalAttributes", "catalogCategoryGlobalAttributes", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("catalogCategoryGlobalAttributes", FetchMode.JOIN);
        
        criteria.createAlias("catalogCategoryMarketAreaAttributes", "catalogCategoryMarketAreaAttributes", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("catalogCategoryMarketAreaAttributes", FetchMode.JOIN);
        
        criteria.createAlias("catalogCategories", "catalogCategories", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("catalogCategories", FetchMode.JOIN);
        
        criteria.createAlias("productMarketings", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("productMarketings", FetchMode.JOIN);
        
        criteria.createAlias("assetsIsGlobal", "assetsIsGlobal", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("assetsIsGlobal", FetchMode.JOIN);

        criteria.createAlias("assetsByMarketArea", "assetsByMarketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("assetsByMarketArea", FetchMode.JOIN);
    }

}