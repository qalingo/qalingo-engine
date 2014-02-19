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

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.RetailerDao;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("retailerDao")
public class RetailerDaoImpl extends AbstractGenericDaoImpl implements RetailerDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// RETAILER

	public Retailer getRetailerById(final Long retailerId) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);
        
        criteria.add(Restrictions.eq("id", retailerId));
        Retailer retailer = (Retailer) criteria.uniqueResult();
        return retailer;
	}

    public Retailer getRetailerByCode(final String retailerCode) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        addDefaultRetailerFetch(criteria);

        criteria.add(Restrictions.eq("code", retailerCode));
        Retailer retailer = (Retailer) criteria.uniqueResult();
        return retailer;
    }

	public Retailer getRetailerByCode(final Long marketAreaId, final Long retailerId, final String retailerCode) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        addDefaultRetailerFetch(criteria);

        criteria.add(Restrictions.eq("code", retailerCode));
        Retailer retailer = (Retailer) criteria.uniqueResult();
		return retailer;
	}
	
    public List<Retailer> findAllRetailers() {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }
    
    public List<Retailer> findRetailersByMarketAreaCode(final String marketAreaCode) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);

        criteria.add(Restrictions.eq("code", marketAreaCode));
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();

        List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
        return retailers;
  }

    public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByTags(final Long marketAreaId, final Long retailerId, final List<String> tags) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        addDefaultRetailerFetch(criteria);

        criteria.createAlias("retailerTags", "tag", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.in("tag.code", tags));
        
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}

	public List<Retailer> findLastRetailers(final Long marketAreaId, final Long retailerId, int maxResults) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.addOrder(Order.desc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityOfService(final Long marketAreaId, final Long retailerId, int maxResults) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.addOrder(Order.desc("qualityOfService"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityPrice(final Long marketAreaId, final Long retailerId, int maxResults) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.addOrder(Order.desc("ratioQualityPrice"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByText(final Long marketAreaId, final Long retailerId, final String searchTxt) {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        
        addDefaultRetailerFetch(criteria);

        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + searchTxt + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("businessName", "%" + searchTxt + "%")));
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
	
	public Store getStoreById(final Long storeId) {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        addDefaultStoreFetch(criteria);
        
        criteria.addOrder(Order.asc("code"));
        
        criteria.add(Restrictions.eq("id", storeId));
        Store store = (Store) criteria.uniqueResult();
        return store;
	}

	public Store getStoreByCode(final String storeCode) {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        addDefaultStoreFetch(criteria);
        
        criteria.addOrder(Order.asc("code"));
        
        criteria.add(Restrictions.eq("code", storeCode));
        Store store = (Store) criteria.uniqueResult();
		return store;
	}
	
	public List<Store> findStores() {
        Criteria criteria = createDefaultCriteria(Store.class);
        
        addDefaultStoreFetch(criteria);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
		return stores;
	}
	
    public List<Store> findStoresByRetailerId(final Long retailerId) {
        Criteria criteria = createDefaultCriteria(Store.class);

        addDefaultStoreFetch(criteria);

        criteria.add(Restrictions.eq("retailerId", retailerId));
        
        criteria.addOrder(Order.asc("businessName"));

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
	
    private void addDefaultRetailerFetch(Criteria criteria) {
        criteria.setFetchMode("links", FetchMode.JOIN); 
        criteria.setFetchMode("addresses", FetchMode.JOIN); 
        criteria.setFetchMode("stores", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN); 
        criteria.setFetchMode("retailerAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("customerRates", FetchMode.JOIN); 
        criteria.setFetchMode("customerComments", FetchMode.JOIN); 
        criteria.setFetchMode("retailerTags", FetchMode.JOIN); 
    }
    
    private void addDefaultStoreFetch(Criteria criteria) {
        criteria.setFetchMode("storeAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN); 
    }

}