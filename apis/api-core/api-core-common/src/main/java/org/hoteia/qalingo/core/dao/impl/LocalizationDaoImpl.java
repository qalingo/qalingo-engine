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
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.LocalizationDao;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("localizationDao")
public class LocalizationDaoImpl extends AbstractGenericDaoImpl implements LocalizationDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Localization getLocalizationById(final Long localizationId) {
//		return em.find(Localization.class, localizationId);
        Criteria criteria = getSession().createCriteria(Localization.class);
        criteria.add(Restrictions.eq("id", localizationId));
        Localization localization = (Localization) criteria.uniqueResult();
        return localization;
	}

	public Localization getLocalizationByCode(final String code) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM Localization WHERE upper(code) = upper(:code)";
//		Query query = session.createQuery(sql);
//		query.setString("code", code);
//		Localization localization = (Localization) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(Localization.class);
        criteria.add(Restrictions.eq("code", code));
        Localization localization = (Localization) criteria.uniqueResult();
        return localization;
	}
	
//	public List<Localization> findByExample(Localization localizationExample) {
//		return super.findByExample(localizationExample);
//	}

	public List<Localization> findLocalizations() {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM Localization ORDER BY language";
//		Query query = session.createQuery(sql);
//		List<Localization> localizations = (List<Localization>) query.list();
	    
        Criteria criteria = getSession().createCriteria(Localization.class);
        
        criteria.addOrder(Order.asc("language"));

        @SuppressWarnings("unchecked")
        List<Localization> localizations = criteria.list();
        
		return localizations;
	}
	
    public List<Localization> findLocalizationsByMarketAreaCode(final String marketAreaCode) {
        Criteria criteria = getSession().createCriteria(MarketArea.class);
        
        criteria.add(Restrictions.eq("code", marketAreaCode));
        MarketArea marketArea = (MarketArea) criteria.uniqueResult();

        List<Localization> localizations = new ArrayList<Localization>(marketArea.getLocalizations());
        return localizations;
    }
	
	public void saveOrUpdateLocalization(Localization localization) {
		if(localization.getId() == null){
			em.persist(localization);
		} else {
			em.merge(localization);
		}
	}

	public void deleteLocalization(Localization localization) {
		em.remove(localization);
	}

}