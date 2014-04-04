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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.MarketDao;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.market.FetchPlanGraphMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("marketDao")
public class MarketDaoImpl extends AbstractGenericDaoImpl implements MarketDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// MARKET PLACE
	
    public MarketPlace getDefaultMarketPlace(Object... params) {
        Criteria criteria = createDefaultCriteria(MarketPlace.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarketPlace(criteria, params);
        
        criteria.add(Restrictions.eq("isDefault", true));
        
        MarketPlace marketPlace = (MarketPlace) criteria.uniqueResult();
        if(marketPlace != null){
            marketPlace.setFetchPlan(fetchPlan);
        }
        return marketPlace;
    }
    
    public MarketPlace getMarketPlaceById(final Long marketPlaceId, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketPlace.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarketPlace(criteria, params);

        criteria.add(Restrictions.eq("id", marketPlaceId));
        
        MarketPlace marketPlace = (MarketPlace) criteria.uniqueResult();
        if(marketPlace != null){
            marketPlace.setFetchPlan(fetchPlan);
        }
        return marketPlace;
    }
    
    public MarketPlace getMarketPlaceByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketPlace.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarketPlace(criteria, params);

        criteria.add(Restrictions.eq("code", code));
        
        MarketPlace marketPlace = (MarketPlace) criteria.uniqueResult();
        if(marketPlace != null){
            marketPlace.setFetchPlan(fetchPlan);
        }
        return marketPlace;
    }
    
    public List<MarketPlace> findMarketPlaces(Object... params) {
        Criteria criteria = createDefaultCriteria(MarketPlace.class);

        handleSpecificFetchModeMarketPlace(criteria, params);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<MarketPlace> marketPlaces = criteria.list();
        return marketPlaces;
    }

    public MarketPlace saveOrUpdateMarketPlace(final MarketPlace marketPlace) {
        if(marketPlace.getDateCreate() == null){
            marketPlace.setDateCreate(new Date());
        }
        marketPlace.setDateUpdate(new Date());
        if (marketPlace.getId() != null) {
            if(em.contains(marketPlace)){
                em.refresh(marketPlace);
            }
            MarketPlace mergedMarketPlace = em.merge(marketPlace);
            em.flush();
            return mergedMarketPlace;
        } else {
            em.persist(marketPlace);
            return marketPlace;
        }
    }

    public void deleteMarketPlace(final MarketPlace marketPlace) {
        em.remove(marketPlace);
    }

    protected FetchPlan handleSpecificFetchModeMarketPlace(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphMarket.defaultMarketPlaceFetchPlan());
        }
    }
    
	// MARKET
	
	public Market getDefaultMarket(Object... params) {
        Criteria criteria = createDefaultCriteria(Market.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarket(criteria, params);
        
        criteria.add(Restrictions.eq("isDefault", true));
        
        Market market = (Market) criteria.uniqueResult();
        if(market != null){
            market.setFetchPlan(fetchPlan);
        }
		return market;
	}
	
	public Market getMarketById(final Long marketId, Object... params) {
        Criteria criteria = createDefaultCriteria(Market.class);

        FetchPlan fetchPlan = handleSpecificFetchModeMarket(criteria, params);

        criteria.add(Restrictions.eq("id", marketId));
        
        Market market = (Market) criteria.uniqueResult();
        if(market != null){
            market.setFetchPlan(fetchPlan);
        }
        return market;
	}

	public Market getMarketByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(Market.class);

        FetchPlan fetchPlan = handleSpecificFetchModeMarket(criteria, params);

        criteria.add(Restrictions.eq("code", code));
        
        Market market = (Market) criteria.uniqueResult();
        if(market != null){
            market.setFetchPlan(fetchPlan);
        }
		return market;
	}
	
	public List<Market> findMarkets(Object... params) {
        Criteria criteria = createDefaultCriteria(Market.class);
        
        handleSpecificFetchModeMarket(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Market> markets = criteria.list();
		return markets;
	}
	
    public List<Market> getMarketsByMarketPlaceCode(final String marketPlaceCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Market.class);

        handleSpecificFetchModeMarket(criteria, params);
        
        criteria.createAlias("marketPlace", "mp", JoinType.LEFT_OUTER_JOIN);
        criteria.add( Restrictions.eq("mp.code", marketPlaceCode));
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Market> markets = criteria.list();
        return markets;
    }

	public Market saveOrUpdateMarket(final Market market) {
		if(market.getDateCreate() == null){
			market.setDateCreate(new Date());
		}
		market.setDateUpdate(new Date());
        if (market.getId() != null) {
            if(em.contains(market)){
                em.refresh(market);
            }
            Market mergedMarket = em.merge(market);
            em.flush();
            return mergedMarket;
        } else {
            em.persist(market);
            return market;
        }
	}

	public void deleteMarket(final Market market) {
		em.remove(market);
	}
	
    protected FetchPlan handleSpecificFetchModeMarket(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphMarket.defaultMarketFetchPlan());
        }
    }
	
	// MARKET AREA

	public MarketArea getMarketAreaById(final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarketArea(criteria, params);

        criteria.add(Restrictions.eq("id", marketAreaId));
        
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();
        if(marketArea != null){
            marketArea.setFetchPlan(fetchPlan);
        }
        return marketArea;
	}
	
	public MarketArea getMarketAreaByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);
        
        FetchPlan fetchPlan = handleSpecificFetchModeMarketArea(criteria, params);
        
        criteria.add(Restrictions.eq("code", code));
        
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();
        if(marketArea != null){
            marketArea.setFetchPlan(fetchPlan);
        }
		return marketArea;
	}

    public List<MarketArea> getMarketAreaByGeolocCountryCode(final String countryCode, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);

        handleSpecificFetchModeMarketArea(criteria, params);

        criteria.add(Restrictions.eq("geolocCountryCode", countryCode));
        
        @SuppressWarnings("unchecked")
        List<MarketArea> marketAreas = criteria.list();
        return marketAreas;
    }

    public MarketArea saveOrUpdateMarketArea(final MarketArea marketArea) {
        if(marketArea.getDateCreate() == null){
            marketArea.setDateCreate(new Date());
        }
        marketArea.setDateUpdate(new Date());
        if (marketArea.getId() != null) {
            if(em.contains(marketArea)){
                em.refresh(marketArea);
            }
            MarketArea mergedMarketArea = em.merge(marketArea);
            em.flush();
            return mergedMarketArea;
        } else {
            em.persist(marketArea);
            return marketArea;
        }
    }

    public void deleteMarketArea(final MarketArea marketArea) {
        em.remove(marketArea);
    }
    
    protected FetchPlan handleSpecificFetchModeMarketArea(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphMarket.defaultMarketAreaFetchPlan());
        }
    }
	
}