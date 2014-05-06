/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.CatalogCategoryDao;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("catalogCategoryDao")
public class CatalogCategoryDaoImpl extends AbstractGenericDaoImpl implements CatalogCategoryDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// MASTER
	
	public CatalogCategoryMaster getMasterCatalogCategoryById(final Long catalogCategoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMasterCategoryMode(criteria, params);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
        if(catalogCategory != null){
            catalogCategory.setFetchPlan(fetchPlan);
        }
        return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final String catalogCategoryCode, final String catalogMasterCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMasterCategoryMode(criteria, params);

        criteria.add(Restrictions.eq("code", catalogCategoryCode));
        
        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogMasterCode));
        
        CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) criteria.uniqueResult();
        if(catalogCategory != null){
            catalogCategory.setFetchPlan(fetchPlan);
        }
		return catalogCategory;
	}
	
	public List<CatalogCategoryMaster> findRootMasterCatalogCategoriesByCatalogCode(final String catalogMasterCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        handleSpecificFetchMasterCategoryMode(criteria, params);
        
        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogMasterCode));
        criteria.add(Restrictions.isNull("parentCatalogCategory"));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
		return categories;
	}
	
    public List<CatalogCategoryMaster> findAllMasterCatalogCategoriesByCatalogCode(final String catalogMasterCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);
        handleSpecificFetchMasterCategoryMode(criteria, params);
        
        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogMasterCode));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
        return categories;
    }
	
    public List<CatalogCategoryMaster> findMasterCategoriesByProductSkuId(final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryMaster.class);

        handleSpecificFetchVirtualCategoryMode(criteria, params);
        
        criteria.createAlias("catalogCategoryProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.productSku.id", productSkuId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryMaster> categories = criteria.list();
        return categories;
    }
    
	public CatalogCategoryMaster saveOrUpdateCatalogCategory(final CatalogCategoryMaster catalogCategory) {
		
		// TODO : Denis : child object dates ?
		
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
        if (catalogCategory.getId() != null) {
            if(em.contains(catalogCategory)){
                em.refresh(catalogCategory);
            }
            CatalogCategoryMaster mergedCatalogCategoryMaster = em.merge(catalogCategory);
            em.flush();
            return mergedCatalogCategoryMaster;
        } else {
            em.persist(catalogCategory);
            return catalogCategory;
        }
	}

	public void deleteCatalogCategory(final CatalogCategoryMaster catalogCategory) {
		em.remove(catalogCategory);
	}

    protected FetchPlan handleSpecificFetchMasterCategoryMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCategory.defaultMasterCatalogCategoryFetchPlan());
        }
    }

	// VIRTUAL
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryById(final Long catalogCategoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchVirtualCategoryMode(criteria, params);

        criteria.add(Restrictions.eq("id", catalogCategoryId));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
        if(catalogCategory != null){
            catalogCategory.setFetchPlan(fetchPlan);
        }
        return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByVirtualCategoryCode(final String catalogCategoryCode, final String catalogVirtualCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchVirtualCategoryMode(criteria, params);

        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogVirtualCode));
        
        criteria.add(Restrictions.eq("code", catalogCategoryCode));

        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
        if(catalogCategory != null){
            catalogCategory.setFetchPlan(fetchPlan);
        }
		return catalogCategory;
	}
	
    public CatalogCategoryVirtual getVirtualCatalogCategoryByMasterCategoryCode(final String catalogCategoryCode, final String catalogVirtualCode, final String catalogMasterCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
        
        FetchPlan fetchPlan = handleSpecificFetchVirtualCategoryMode(criteria, params);

        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogVirtualCode));

        criteria.createAlias("categoryMaster", "categoryMaster", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("categoryMaster.code", catalogCategoryCode));

        criteria.createAlias("categoryMaster.catalog", "catalogMaster", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogMaster.code", catalogMasterCode));
        
        CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) criteria.uniqueResult();
        if(catalogCategory != null){
            catalogCategory.setFetchPlan(fetchPlan);
        }
        return catalogCategory;
    }
    
	public List<CatalogCategoryVirtual> findRootVirtualCatalogCategoriesByCatalogCode(final String catalogVirtualCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);

        handleSpecificFetchVirtualCategoryMode(criteria, params);
        
        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogVirtualCode));
        criteria.add(Restrictions.isNull("parentCatalogCategory"));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
    public List<CatalogCategoryVirtual> findAllVirtualCatalogCategoriesByCatalogCode(final String catalogVirtualCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);

        handleSpecificFetchVirtualCategoryMode(criteria, params);
        
        criteria.createAlias("catalog", "catalog", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalog.code", catalogVirtualCode));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
        return categories;
    }
	
//	public List<CatalogCategoryVirtual> findVirtualCategories(Object... params) {
//        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
//        
//        handleSpecificFetchVirtualCategoryMode(criteria, params);
//        
//        criteria.addOrder(Order.asc("id"));
//
//        @SuppressWarnings("unchecked")
//        List<CatalogCategoryVirtual> categories = criteria.list();
//		return categories;
//	}
	
	public List<CatalogCategoryVirtual> findVirtualCategoriesByProductSkuId(final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);

        handleSpecificFetchVirtualCategoryMode(criteria, params);
        
        criteria.createAlias("catalogCategoryProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.productSku.id", productSkuId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CatalogCategoryVirtual> categories = criteria.list();
		return categories;
	}
	
//    public List<CatalogCategoryVirtual> findVirtualCategoriesByProductMarketingId(final Long productMarketingId, Object... params) {
//        Criteria criteria = createDefaultCriteria(CatalogCategoryVirtual.class);
//
//        handleSpecificFetchVirtualCategoryMode(criteria, params);
//
//        criteria.createAlias("catalogCategoryProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
//        criteria.createAlias("productSku", "catalogCategoryProductSkuRel.pk.productSku", JoinType.LEFT_OUTER_JOIN);
//        criteria.add(Restrictions.eq("productSku.productMarketing.id", productMarketingId));
//
//        criteria.addOrder(Order.asc("id"));
//
//        @SuppressWarnings("unchecked")
//        List<CatalogCategoryVirtual> categories = criteria.list();
//        return categories;
//    }
	
	public CatalogCategoryVirtual saveOrUpdateCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
        if (catalogCategory.getId() != null) {
            if(em.contains(catalogCategory)){
                em.refresh(catalogCategory);
            }
            CatalogCategoryVirtual mergedCatalogCategoryVirtual = em.merge(catalogCategory);
            em.flush();
            return mergedCatalogCategoryVirtual;
        } else {
            em.persist(catalogCategory);
            return catalogCategory;
        }
	}

	public void deleteCatalogCategory(final CatalogCategoryVirtual catalogCategory) {
		em.remove(catalogCategory);
	}
	
	protected FetchPlan handleSpecificFetchVirtualCategoryMode(Criteria criteria, Object... params){
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCategory.defaultVirtualCatalogCategoryFetchPlan());
        }
	}
	
}