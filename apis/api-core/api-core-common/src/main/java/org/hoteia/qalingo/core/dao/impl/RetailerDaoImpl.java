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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.RetailerDao;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("retailerDao")
public class RetailerDaoImpl extends AbstractGenericDaoImpl implements RetailerDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// RETAILER

	public Retailer getRetailerById(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", retailerId));
        Retailer retailer = (Retailer) criteria.uniqueResult();
        retailer.setFetchPlan(fetchPlan);
        return retailer;
	}

    public Retailer getRetailerByCode(final String retailerCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", retailerCode));
        Retailer retailer = (Retailer) criteria.uniqueResult();
        retailer.setFetchPlan(fetchPlan);
        return retailer;
    }

	public Retailer getRetailerByCode(final Long marketAreaId, final Long retailerId, final String retailerCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", retailerCode));
        Retailer retailer = (Retailer) criteria.uniqueResult();
        retailer.setFetchPlan(fetchPlan);
		return retailer;
	}
	
    public List<Retailer> findAllRetailers(Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }
    
    public List<Retailer> findRetailersByMarketAreaCode(final String marketAreaCode, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);
        
        criteria.add(Restrictions.eq("code", marketAreaCode));
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();

        List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
        return retailers;
  }

    public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByTags(final Long marketAreaId, final Long retailerId, final List<String> tags, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.createAlias("retailerTags", "tag", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.in("tag.code", tags));
        
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}

	public List<Retailer> findLastRetailers(final Long marketAreaId, final Long retailerId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityOfService(final Long marketAreaId, final Long retailerId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("qualityOfService"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityPrice(final Long marketAreaId, final Long retailerId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("ratioQualityPrice"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByText(final Long marketAreaId, final Long retailerId, final String searchTxt, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + searchTxt + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("name", "%" + searchTxt + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + searchTxt + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public Retailer saveOrUpdateRetailer(final Retailer retailer) {
		if(retailer.getDateCreate() == null){
			retailer.setDateCreate(new Date());
		}
        if (StringUtils.isEmpty(retailer.getCode())) {
            retailer.setCode(UUID.randomUUID().toString());
        }
		retailer.setDateUpdate(new Date());
        if (retailer.getId() != null) {
            if(em.contains(retailer)){
                em.refresh(retailer);
            }
            Retailer mergedRetailer = em.merge(retailer);
            em.flush();
            return mergedRetailer;
        } else {
            em.persist(retailer);
            return retailer;
        }
	}

	public void deleteRetailer(final Retailer retailer) {
		em.remove(retailer);
	}
	
    // RETAILER COMMENT/RATE
	
    public RetailerCustomerRate saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        if (retailerCustomerRate.getDateCreate() == null) {
            retailerCustomerRate.setDateCreate(new Date());
        }
        retailerCustomerRate.setDateUpdate(new Date());
        if (retailerCustomerRate.getId() != null) {
            if(em.contains(retailerCustomerRate)){
                em.refresh(retailerCustomerRate);
            }
            RetailerCustomerRate mergedRetailerCustomerRate = em.merge(retailerCustomerRate);
            em.flush();
            return mergedRetailerCustomerRate;
        } else {
            em.persist(retailerCustomerRate);
            return retailerCustomerRate;
        }
    }

	public void deleteRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
		em.remove(retailerCustomerRate);
	}
	
	public RetailerCustomerComment saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
		if(retailerCustomerComment.getDateCreate() == null){
			retailerCustomerComment.setDateCreate(new Date());
		}
		retailerCustomerComment.setDateUpdate(new Date());
        if (retailerCustomerComment.getId() != null) {
            if(em.contains(retailerCustomerComment)){
                em.refresh(retailerCustomerComment);
            }
            RetailerCustomerComment mergedRetailerCustomerComment = em.merge(retailerCustomerComment);
            em.flush();
            return mergedRetailerCustomerComment;
        } else {
            em.persist(retailerCustomerComment);
            return retailerCustomerComment;
        }
	}

	public void deleteRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
		em.remove(retailerCustomerComment);
	}
	
	// STORE
	
	public Store getStoreById(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        FetchPlan fetchPlan = handleSpecificStoreFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("code"));
        
        criteria.add(Restrictions.eq("id", storeId));
        Store store = (Store) criteria.uniqueResult();
        store.setFetchPlan(fetchPlan);
        return store;
	}

	public Store getStoreByCode(final String storeCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        FetchPlan fetchPlan = handleSpecificStoreFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("code"));
        
        criteria.add(Restrictions.eq("code", storeCode));
        Store store = (Store) criteria.uniqueResult();
        store.setFetchPlan(fetchPlan);
		return store;
	}
	
	public List<Store> findStores(Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        handleSpecificStoreFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
		return stores;
	}
	
    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleSpecificStoreFetchMode(criteria, params);

        criteria.add(Restrictions.eq("retailerId", retailerId));
        
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

	public Store saveOrUpdateStore(final Store store) {
		if(store.getDateCreate() == null){
			store.setDateCreate(new Date());
		}
		store.setDateUpdate(new Date());
        if (store.getId() != null) {
            if(em.contains(store)){
                em.refresh(store);
            }
            Store mergedStore = em.merge(store);
            em.flush();
            return mergedStore;
        } else {
            em.persist(store);
            return store;
        }
	}

	public void deleteStore(final Store store) {
		em.remove(store);
	}
	
    protected FetchPlan handleSpecificRetailerFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultRetailerFetchPlan());
        }
    }
    
    protected FetchPlan handleSpecificStoreFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultStoreFetchPlan());
        }
    }

}