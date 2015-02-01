/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.GeolocAddress;
import org.hoteia.qalingo.core.domain.GeolocCity;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("geolocDao")
public class GeolocDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// GEOLOC CITY
	
    public GeolocCity getGeolocCityByCityAndCountry(final String city, final String country, Object... params) {
        try {
            Criteria criteria = createDefaultCriteria(GeolocCity.class);

            FetchPlan fetchPlan = handleSpecificFetchMode(criteria);

            criteria.add(Restrictions.eq("city", city));
            criteria.add(Restrictions.eq("country", country));
            GeolocCity geolocCity = (GeolocCity) criteria.uniqueResult();
            if (geolocCity != null) {
                geolocCity.setFetchPlan(fetchPlan);
            }
            return geolocCity;
            
        } catch (Exception e) {
            logger.error("Can't load Geoloc by City: '" + city + "', and country: '" + country + "'", e);
        }
        return null;
    }
    
    public GeolocCity getGeolocCityByCountryWithNullCity(final String country, Object... params) {
        try {
            Criteria criteria = createDefaultCriteria(GeolocCity.class);
    
            FetchPlan fetchPlan = handleSpecificFetchMode(criteria);
    
            criteria.add(Restrictions.isNull("city"));
            criteria.add(Restrictions.eq("country", country));
            GeolocCity geolocCity = (GeolocCity) criteria.uniqueResult();
            if (geolocCity != null) {
                geolocCity.setFetchPlan(fetchPlan);
            }
            return geolocCity;
        } catch (Exception e) {
            logger.error("Can't load Geoloc by City as null, and country: '" + country + "'", e);
        }
        return null;
    }

    public GeolocCity saveOrUpdateGeolocCity(final GeolocCity geolocCity) {
        if (geolocCity.getDateCreate() == null) {
            geolocCity.setDateCreate(new Date());
        }
        geolocCity.setDateUpdate(new Date());
        if (geolocCity.getId() != null) {
            if (em.contains(geolocCity)) {
                em.refresh(geolocCity);
            }
            GeolocCity mergedGeolocCity = em.merge(geolocCity);
            em.flush();
            return mergedGeolocCity;
        } else {
            em.persist(geolocCity);
            return geolocCity;
        }
    }

    public void deleteGeolocCity(final GeolocCity geolocCity) {
        if (em.contains(geolocCity)) {
            em.remove(geolocCity);
        } else {
            em.remove(em.merge(geolocCity));
        }
    }
	    
	// GEOLOC ADDRESS
    
	public GeolocAddress getGeolocAddressByFormatedAddress(final String formatedAddress, Object... params) {
	    try {
            Criteria criteria = createDefaultCriteria(GeolocAddress.class);
            
            FetchPlan fetchPlan = handleSpecificFetchMode(criteria);
            
            criteria.add(Restrictions.eq("formatedAddress", formatedAddress));
            GeolocAddress geolocAddress = (GeolocAddress) criteria.uniqueResult();
            if(geolocAddress != null){
                geolocAddress.setFetchPlan(fetchPlan);
            }
            return geolocAddress;
        } catch (Exception e) {
            logger.error("Can't load GeolocAddress by Formated Address: '" + formatedAddress + "'", e);
        }
        return null;
	}
	
    public GeolocAddress getGeolocAddressByLatitudeAndLongitude(final String latitude, final String longitude, Object... params) {
        try {
            Criteria criteria = createDefaultCriteria(GeolocAddress.class);
    
            FetchPlan fetchPlan = handleSpecificFetchMode(criteria);
            criteria.add(Restrictions.eq("latitude", latitude));
            criteria.add(Restrictions.eq("longitude", longitude));
    
            GeolocAddress geolocAddress = (GeolocAddress) criteria.uniqueResult();
            if (geolocAddress != null) {
                geolocAddress.setFetchPlan(fetchPlan);
            }
            return geolocAddress;
        } catch (Exception e) {
            logger.error("Can't load GeolocAddress by latitude: '" + latitude + "', and longitude: '" + longitude + "'", e);
        }
        return null;
    }
	
	public GeolocAddress saveOrUpdateGeolocAddress(final GeolocAddress geolocAddress) {
		if(geolocAddress.getDateCreate() == null){
			geolocAddress.setDateCreate(new Date());
		}
		geolocAddress.setDateUpdate(new Date());
        if (geolocAddress.getId() != null) {
            if(em.contains(geolocAddress)){
                em.refresh(geolocAddress);
            }
            GeolocAddress mergedGeolocAddress = em.merge(geolocAddress);
            em.flush();
            return mergedGeolocAddress;
        } else {
            em.persist(geolocAddress);
            return geolocAddress;
        }
	}

	public void deleteGeolocAddress(final GeolocAddress geolocAddress) {
	    if(em.contains(geolocAddress)){
	        em.remove(geolocAddress);
	    } else {
	        em.remove(em.merge(geolocAddress));
	    }
	}
	
}