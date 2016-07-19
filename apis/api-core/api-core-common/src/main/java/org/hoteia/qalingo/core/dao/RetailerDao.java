/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerCustomerComment;
import org.hoteia.qalingo.core.domain.RetailerCustomerRate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.StoreCustomerComment;
import org.hoteia.qalingo.core.domain.StoreCustomerRate;
import org.hoteia.qalingo.core.domain.bean.GeolocatedStore;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.retailer.FetchPlanGraphRetailer;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("retailerDao")
public class RetailerDao extends AbstractGenericDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserDao userDao;
    
    @Autowired
    protected ProductDao productDao;
    
    // RETAILER

    public Retailer getRetailerById(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", retailerId));

        Retailer retailer = (Retailer) criteria.uniqueResult();
        if (retailer != null) {
            retailer.setFetchPlan(fetchPlan);
        }
        return retailer;
    }

    public Retailer getRetailerByCode(final String retailerCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(retailerCode)));

        Retailer retailer = (Retailer) criteria.uniqueResult();
        if (retailer != null) {
            retailer.setFetchPlan(fetchPlan);
        }
        return retailer;
    }

    public Retailer getRetailerByCompanyCode(final String companyCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        FetchPlan fetchPlan = handleSpecificRetailerFetchMode(criteria, params);

        criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("company.code", companyCode));

        Retailer retailer = (Retailer) criteria.uniqueResult();
        if (retailer != null) {
            retailer.setFetchPlan(fetchPlan);
        }
        return retailer;
    }

    public Long getMaxRetailerId() {
        Criteria criteria = createDefaultCriteria(Retailer.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long) criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
    }

    public List<Retailer> findAllRetailers(Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findAllRetailersByCountry(String countryCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);
        criteria.createAlias("addresses", "addresse", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("addresse.countryCode", countryCode));

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    @Deprecated
    public List<Retailer> findRetailersByMarketAreaCode(final String marketAreaCode, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);

        criteria.add(Restrictions.eq("code", handleCodeValue(marketAreaCode)));
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();

        List<Retailer> retailers = new ArrayList<Retailer>(marketArea.getRetailers());
        return retailers;
    }

    @Deprecated
    public List<Retailer> findRetailers(Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findRetailersByTags(final List<String> tags, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.createAlias("retailerTags", "tag", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.in("tag.code", tags));

        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findLastRetailers(int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findBestRetailersByQualityOfService(int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("qualityOfService"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findBestRetailersByQualityPrice(int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.addOrder(Order.desc("ratioQualityPrice"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public List<Retailer> findRetailersByText(final String text, Object... params) {
        Criteria criteria = createDefaultCriteria(Retailer.class);

        handleSpecificRetailerFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.like("code", text, MatchMode.ANYWHERE), Restrictions.like("name", text, MatchMode.ANYWHERE), Restrictions.like("description", text, MatchMode.ANYWHERE)));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Retailer> retailers = criteria.list();
        return retailers;
    }

    public Retailer saveOrUpdateRetailer(final Retailer retailer) {
        if (retailer.getDateCreate() == null) {
            retailer.setDateCreate(new Date());
        }
        if (StringUtils.isEmpty(retailer.getCode())) {
            retailer.setCode(CoreUtil.generateEntityCode());
        }
        retailer.setDateUpdate(new Date());
        if (retailer.getId() != null) {
            if (em.contains(retailer)) {
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

    public Retailer updateRetailer(final Retailer retailer) {
        retailer.setDateUpdate(new Date());
        Retailer mergedRetailer = em.merge(retailer);
        return mergedRetailer;
    }

    public void deleteRetailer(final Retailer retailer) {
    	em.remove(em.contains(retailer) ? retailer : em.merge(retailer));
    }

    // RETAILER COMMENT/RATE

    @SuppressWarnings("unchecked")
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByRetailerId(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(RetailerCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.id", retailerId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByRetailerIdAndMarketAreaId(final Long retailerId, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(RetailerCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.id", retailerId));
        criteria.add(Restrictions.eq("marketAreaId", marketAreaId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<RetailerCustomerComment> findRetailerCustomerCommentsByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(RetailerCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customerId));
        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<RetailerCustomerRate> findRetailerCustomerRatesByRetailerId(final Long retailerId, final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(RetailerCustomerRate.class);
        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.id", retailerId));
        criteria.add(Restrictions.eq("type", type));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    public RetailerCustomerRate saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate productMarketingCustomerRate) {
        if (productMarketingCustomerRate.getDateCreate() == null) {
            productMarketingCustomerRate.setDateCreate(new Date());
        }
        productMarketingCustomerRate.setDateUpdate(new Date());
        if (productMarketingCustomerRate.getId() != null) {
            if (em.contains(productMarketingCustomerRate)) {
                em.refresh(productMarketingCustomerRate);
            }
            RetailerCustomerRate mergedRetailerCustomerRate = em.merge(productMarketingCustomerRate);
            em.flush();
            return mergedRetailerCustomerRate;
        } else {
            em.persist(productMarketingCustomerRate);
            return productMarketingCustomerRate;
        }
    }

    public void deleteRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
        em.remove(em.contains(retailerCustomerRate) ? retailerCustomerRate : em.merge(retailerCustomerRate));
    }

    public RetailerCustomerComment saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment customerComment) {
        if (customerComment.getDateCreate() == null) {
            customerComment.setDateCreate(new Date());
        }
        customerComment.setDateUpdate(new Date());
        if (customerComment.getId() != null) {
            if (em.contains(customerComment)) {
                em.refresh(customerComment);
            }
            RetailerCustomerComment mergedRetailerCustomerComment = em.merge(customerComment);
            em.flush();
            return mergedRetailerCustomerComment;
        } else {
            em.persist(customerComment);
            return customerComment;
        }
    }

    public void deleteRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
        em.remove(em.contains(retailerCustomerComment) ? retailerCustomerComment : em.merge(retailerCustomerComment));
    }

    // STORE

    public Store getStoreById(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        FetchPlan fetchPlan = handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", storeId));

        criteria.addOrder(Order.asc("code"));

        Store store = (Store) criteria.uniqueResult();
        if (store != null) {
            store.setFetchPlan(fetchPlan);
        }
        return store;
    }

    public Store getStoreByCode(final String storeCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        FetchPlan fetchPlan = handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(storeCode)));

        criteria.addOrder(Order.asc("code"));

        Store store = (Store) criteria.uniqueResult();
        if (store != null) {
            store.setFetchPlan(fetchPlan);
        }
        return store;
    }

    public Store findStoreByEmail(final String email, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        FetchPlan fetchPlan = handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("email", email));

        criteria.addOrder(Order.asc("code"));

        try {
            Store store = (Store) criteria.uniqueResult();
            if (store != null) {
                store.setFetchPlan(fetchPlan);
            }
            return store;

        } catch (NonUniqueResultException e) {
            logger.error("NonUniqueResultException: store email='" + email + "'");

            @SuppressWarnings("unchecked")
            List<Store> stores = criteria.list();
            return stores.get(0);

        } catch (Exception e) {
            logger.error("Can't find Store by email: '" + email + "'", e);
        }
        return null;
    }

    public Long getMaxStoreId() {
        Criteria criteria = createDefaultCriteria(Store.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long) criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
    }

    public Long countStore() {
        Criteria criteria = createDefaultCriteria(Store.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public List<Store> findAllStores(int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }
    
    public List<Store> findStoreByAddress(final String address, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        handleStoreSpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("address1", address));
        
        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }
    
    public List<Store> findStoreByAddressAndPostalCode(final String address, final String postalCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);
        handleStoreSpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("address1", address));
        criteria.add(Restrictions.eq("postalCode", postalCode));
        
        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }
    
    public List<Store> findAllStoresByCountry(final String countryCode, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("countryCode", countryCode));
        
        criteria.addOrder(Order.asc("name"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }
    
    
    public List<Store> findAllStoresByCountryAndType(final String countryCode, final String type, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("countryCode", countryCode));
        criteria.add(Restrictions.like("type", "%" + type + "%"));
        
        criteria.addOrder(Order.asc("name"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

    public List<Store> findB2CStores(List<String> types, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("b2c", true));

        if (types != null && !types.isEmpty()) {
            Disjunction disjunction = Restrictions.or();
            for (String type : types) {
                disjunction.add(Restrictions.like("type", type, MatchMode.ANYWHERE));
            }
            criteria.add(disjunction);
        }

        criteria.addOrder(Order.asc("code"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

    public List<Store> findB2BStores(List<String> types, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("b2b", true));

        if (types != null && !types.isEmpty()) {
            Disjunction disjunction = Restrictions.or();
            for (String type : types) {
                disjunction.add(Restrictions.like("type", "%" + type + "%"));
            }
            criteria.add(disjunction);
        }

        criteria.addOrder(Order.asc("code"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

    public List<Long> findAllStoreIds(Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        criteria.setProjection(Projections.property("id"));

        @SuppressWarnings("unchecked")
        List<Long> storeIds = criteria.list();
        return storeIds;
    }
    
    public List<Long> findAllStoreIdsByCountry(final String countryCode, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        criteria.add(Restrictions.eq("countryCode", countryCode));

        criteria.setProjection(Projections.property("id"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> storeIds = criteria.list();
        return storeIds;
    }

    public List<Long> findStoreWithoutLatitudeLongitude(int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        criteria.add(Restrictions.or(Restrictions.isNull("latitude"), Restrictions.isNull("longitude")));
        criteria.setProjection(Projections.property("id"));

        if (maxResults != 0) {
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<Long> storeIds = criteria.list();
        return storeIds;
    }

    public List<Long> findStoreIdsByCompanyId(final Long companyId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        criteria.createAlias("companyStoreRels", "companyStoreRels", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("companyStoreRels.pk.company.id", companyId));
        
        criteria.setProjection(Projections.property("id"));

        @SuppressWarnings("unchecked")
        List<Long> storeIds = criteria.list();
        return storeIds;
    }

    public List<Long> findStoreIdsByRetailerId(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.id", retailerId));
        criteria.setProjection(Projections.property("id"));

        @SuppressWarnings("unchecked")
        List<Long> storeIds = criteria.list();
        return storeIds;
    }

    public List<Store> findStoresByRetailerId(final Long retailerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.id", retailerId));

        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

    public List<Store> findStoresByRetailerCode(final String retailerCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Store.class);

        handleStoreSpecificFetchMode(criteria, params);

        criteria.createAlias("retailer", "retailer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("retailer.code", retailerCode));

        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Store> stores = criteria.list();
        return stores;
    }

    public List<GeolocatedStore> findB2CStoresByGeoloc(final String countryCode, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        return findB2CStoresByGeoloc(countryCode, null, types, latitude, longitude, distance, maxResults, params);
    }

    public List<GeolocatedStore> findB2CStoresByGeoloc(final String countryCode, final Long productBrandId, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        if (StringUtils.isNotEmpty(latitude)
                && StringUtils.isNotEmpty(longitude)) {
            Float latitudeFloat = new Float(latitude);
            Float longitudeFloat = new Float(longitude);
            StringBuilder queryString = buildSQLStoresByGeoloc(countryCode, productBrandId, types, latitude, longitude, distance, maxResults, params);
            queryString.append("AND is_b2c = :b2c ");
            if (distance != null) {
                queryString.append("HAVING distance <= :distanceValue ");
            } else {
                queryString.append("HAVING distance IS NOT null ");
            }
            queryString.append("ORDER BY distance ASC");

            Query query = createNativeQuery(queryString.toString());
            query.setParameter("latitude", latitudeFloat);
            query.setParameter("longitude", longitudeFloat);
            if (distance != null) {
                query.setParameter("distanceValue", distance);
            }
            query.setParameter("b2c", true);
            if (StringUtils.isNotEmpty(countryCode)) {
                query.setParameter("countryCode", countryCode);
            }
            if(productBrandId != null){
                query.setParameter("productBrandId", productBrandId);
            }
            if (types != null && !types.isEmpty()) {
                int count = 1;
                for (String type : types) {
                    query.setParameter("type" + count, "%" + type + "%");
                    count++;
                }
            }
            query.setParameter("active", true);
            query.setMaxResults(maxResults);
            query.unwrap(SQLQuery.class).addScalar("id", LongType.INSTANCE).addScalar("code", StringType.INSTANCE).addScalar("distance", DoubleType.INSTANCE);

            @SuppressWarnings("unchecked")
            List<Object[]> objects = query.getResultList();
            List<GeolocatedStore> stores = new ArrayList<GeolocatedStore>();
            for (Object[] object : objects) {
                GeolocatedStore geolocatedStore = new GeolocatedStore();
                geolocatedStore.setId((Long) object[0]);
                geolocatedStore.setCode((String) object[1]);
                geolocatedStore.setDistance((Double) object[2]);
                stores.add(geolocatedStore);
            }
            return stores;
        }
        return null;
    }

    public List<GeolocatedStore> findB2BStoresByGeoloc(final String countryCode, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        return findB2BStoresByGeoloc(countryCode, null, types, latitude, longitude, distance, maxResults, params);
    }

    public List<GeolocatedStore> findB2BStoresByGeoloc(final String countryCode, final Long productBrandId, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        if (StringUtils.isNotEmpty(latitude)
                && StringUtils.isNotEmpty(longitude)) {
            Float latitudeFloat = new Float(latitude);
            Float longitudeFloat = new Float(longitude);
            StringBuilder queryString = buildSQLStoresByGeoloc(countryCode, productBrandId, types, latitude, longitude, distance, maxResults, params);
            queryString.append("AND is_b2b = :b2b ");
            
            if (distance != null) {
                queryString.append("HAVING distance <= :distanceValue ");
            } else {
                queryString.append("HAVING distance IS NOT null ");
            }
            queryString.append("ORDER BY distance ASC");

            Query query = createNativeQuery(queryString.toString());
            query.setParameter("latitude", latitudeFloat);
            query.setParameter("longitude", longitudeFloat);
            query.setParameter("countryCode", countryCode);
            if (distance != null) {
                query.setParameter("distanceValue", distance);
            }
            query.setParameter("b2b", true);
            if (StringUtils.isNotEmpty(countryCode)) {
                query.setParameter("countryCode", countryCode);
            }
            if(productBrandId != null){
                query.setParameter("productBrandId", productBrandId);
            }
            if (types != null && !types.isEmpty()) {
                int count = 1;
                for (String type : types) {
                    query.setParameter("type" + count, "%" + type + "%");
                    count++;
                }
            }
            query.setParameter("active", true);
            query.setMaxResults(maxResults);
            query.unwrap(SQLQuery.class).addScalar("id", LongType.INSTANCE).addScalar("code", StringType.INSTANCE).addScalar("distance", DoubleType.INSTANCE);

            @SuppressWarnings("unchecked")
            List<Object[]> objects = query.getResultList();
            List<GeolocatedStore> stores = new ArrayList<GeolocatedStore>();
            for (java.lang.Object[] object : objects) {
                GeolocatedStore geolocatedStore = new GeolocatedStore();
                geolocatedStore.setId((Long) object[0]);
                geolocatedStore.setCode((String) object[1]);
                geolocatedStore.setDistance((Double) object[2]);
                stores.add(geolocatedStore);
            }
            return stores;
        }
        return null;
    }

    protected StringBuilder buildSQLStoresByGeoloc(final String countryCode, final Long productBrandId, final List<String> types, final String latitude, final String longitude, final String distance, int maxResults, Object... params) {
        StringBuilder queryString = new StringBuilder("SELECT store.id, store.code, ((ACOS(SIN(:latitude * PI() / 180) * SIN(latitude * PI() / 180) + COS(:latitude * PI() / 180) * COS(latitude * PI() / 180) * COS((:longitude - longitude) * PI() / 180)) * 180 / PI()) * 60 * 1.1515) AS distance ");
        queryString.append("FROM teco_store store ");
        if(productBrandId != null){
            queryString.append("left join teco_product_brand_store_rel pbsrel on pbsrel.store_id = store.id ");
        }
        queryString.append("WHERE is_active = :active ");
        if (StringUtils.isNotEmpty(countryCode)) {
            queryString.append("AND country_code = :countryCode ");
        }
        if(productBrandId != null){
            queryString.append("AND pbsrel.product_brand_id = :productBrandId ");
        }
        if (types != null && !types.isEmpty()) {
            queryString.append("AND (");
            int count = 1;
            for (String ignored : types) {
                if (count == 1) {
                    queryString.append("type like :type").append(count).append(" ");
                } else {
                    queryString.append("OR type like :type").append(count).append(" ");
                }
                count++;
            }
            queryString.append(") ");
        }
        return queryString;
    }
    
    public Store saveOrUpdateStore(final Store store) {
        if (store.getDateCreate() == null) {
            store.setDateCreate(new Date());
        }
        store.setDateUpdate(new Date());
        if (StringUtils.isEmpty(store.getCode())) {
            store.setCode(CoreUtil.generateEntityCode());
        }
        if (store.getId() != null) {
            if (em.contains(store)) {
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

    public Store updateStore(final Store store) {
        store.setDateUpdate(new Date());
        if (StringUtils.isEmpty(store.getCode())) {
            store.setCode(CoreUtil.generateEntityCode());
        }
        return em.merge(store);
    }

    public void deleteStore(final Store store) {
    	em.remove(em.contains(store) ? store : em.merge(store));
    }

    // STORE COMMENT/RATE

    @SuppressWarnings("unchecked")
    public List<StoreCustomerComment> findStoreCustomerCommentsByStoreId(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(StoreCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("store.id", storeId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<StoreCustomerComment> findStoreCustomerCommentsByStoreIdAndMarketAreaId(final Long storeId, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(StoreCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("store.id", storeId));
        criteria.add(Restrictions.eq("marketAreaId", marketAreaId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<StoreCustomerComment> findStoreCustomerCommentsByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(StoreCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customerId));
        criteria.createAlias("store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<StoreCustomerRate> findStoreCustomerRatesByStoreId(final Long storeId, final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(StoreCustomerRate.class);
        criteria.createAlias("store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("store.id", storeId));
        criteria.add(Restrictions.eq("type", type));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }

    public StoreCustomerRate saveOrUpdateStoreCustomerRate(final StoreCustomerRate productMarketingCustomerRate) {
        if (productMarketingCustomerRate.getDateCreate() == null) {
            productMarketingCustomerRate.setDateCreate(new Date());
        }
        productMarketingCustomerRate.setDateUpdate(new Date());
        if (productMarketingCustomerRate.getId() != null) {
            if (em.contains(productMarketingCustomerRate)) {
                em.refresh(productMarketingCustomerRate);
            }
            StoreCustomerRate mergedStoreCustomerRate = em.merge(productMarketingCustomerRate);
            em.flush();
            return mergedStoreCustomerRate;
        } else {
            em.persist(productMarketingCustomerRate);
            return productMarketingCustomerRate;
        }
    }

    public void deleteStoreCustomerRate(final StoreCustomerRate storeCustomerRate) {
        em.remove(em.contains(storeCustomerRate) ? storeCustomerRate : em.merge(storeCustomerRate));
    }

    public StoreCustomerComment saveOrUpdateStoreCustomerComment(final StoreCustomerComment customerComment) {
        if (customerComment.getDateCreate() == null) {
            customerComment.setDateCreate(new Date());
        }
        customerComment.setDateUpdate(new Date());
        if (customerComment.getId() != null) {
            if (em.contains(customerComment)) {
                em.refresh(customerComment);
            }
            StoreCustomerComment mergedStoreCustomerComment = em.merge(customerComment);
            em.flush();
            return mergedStoreCustomerComment;
        } else {
            em.persist(customerComment);
            return customerComment;
        }
    }

    public void deleteStoreCustomerComment(final StoreCustomerComment storeCustomerComment) {
        em.remove(em.contains(storeCustomerComment) ? storeCustomerComment : em.merge(storeCustomerComment));
    }

    protected FetchPlan handleSpecificRetailerFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphRetailer.defaultRetailerFetchPlan());
        }
    }

    protected FetchPlan handleStoreSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphRetailer.defaultStoreFetchPlan());
        }
    }

}