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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("localizationDao")
public class LocalizationDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Localization getLocalizationById(final Long localizationId, Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        criteria.add(Restrictions.eq("id", localizationId));
        Localization localization = (Localization) criteria.uniqueResult();
        return localization;
	}

	public Localization getLocalizationByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        criteria.add(Restrictions.eq("code", handleCodeValue(code)));
        Localization localization = (Localization) criteria.uniqueResult();
        return localization;
	}

    public Localization getDefaultLocalizationByCountryCode(final String countryCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        criteria.add(Restrictions.eq("country", handleCodeValue(countryCode)));
        criteria.add(Restrictions.eq("isDefault", true));
        
        Localization localization = (Localization) criteria.uniqueResult();
        return localization;
    }
    
    public List<Localization> getLocalizationByCountryCode(final String countryCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        criteria.add(Restrictions.eq("country", handleCodeValue(countryCode)));
        
        @SuppressWarnings("unchecked")
        List<Localization> localizations = criteria.list();
        return localizations;
    }
	
	public List<Localization> findLocalizations(Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        
        criteria.addOrder(Order.asc("language"));

        @SuppressWarnings("unchecked")
        List<Localization> localizations = criteria.list();
		return localizations;
	}
	
    public List<Localization> findActiveLocalizations(Object... params) {
        Criteria criteria = createDefaultCriteria(Localization.class);
        
        criteria.add(Restrictions.eq("active", true));
        
        criteria.addOrder(Order.asc("language"));

        @SuppressWarnings("unchecked")
        List<Localization> localizations = criteria.list();
        return localizations;
    }
	
    public List<Localization> findLocalizationsByMarketAreaCode(final String marketAreaCode, Object... params) {
        Criteria criteria = createDefaultCriteria(MarketArea.class);
        
        criteria.add(Restrictions.eq("code", handleCodeValue(marketAreaCode)));
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();

        List<Localization> localizations = new ArrayList<Localization>(marketArea.getLocalizations());
        return localizations;
    }
	
	public Localization saveOrUpdateLocalization(Localization localization) {
        if (localization.getId() != null) {
            if(em.contains(localization)){
                em.refresh(localization);
            }
            Localization mergedLocalization = em.merge(localization);
            em.flush();
            return mergedLocalization;
        } else {
            em.persist(localization);
            return localization;
        }
	}

	public void deleteLocalization(Localization localization) {
		em.remove(em.contains(localization) ? localization : em.merge(localization));
	}

}