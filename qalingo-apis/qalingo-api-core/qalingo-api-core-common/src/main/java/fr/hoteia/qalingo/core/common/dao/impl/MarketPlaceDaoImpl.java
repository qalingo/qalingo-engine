/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.MarketPlaceDao;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;

@Transactional
@Repository("marketPlaceDao")
public class MarketPlaceDaoImpl extends AbstractGenericDaoImpl implements MarketPlaceDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public MarketPlace getDefaultMarketPlace() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM MarketPlace WHERE isDefault = true";
		Query query = session.createQuery(sql);
		MarketPlace marketPlace = (MarketPlace) query.uniqueResult();
		return marketPlace;
	}
	
	public MarketPlace getMarketPlaceById(Long marketPlaceId) {
		return em.find(MarketPlace.class, marketPlaceId);
	}
	
	public MarketPlace getMarketPlaceByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM MarketPlace WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		MarketPlace marketPlace = (MarketPlace) query.uniqueResult();
		return marketPlace;
	}
	
	public List<MarketPlace> findMarketPlaces() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM MarketPlace ORDER BY code";
		Query query = session.createQuery(sql);
		List<MarketPlace> marketPlaces = (List<MarketPlace>) query.list();
		return marketPlaces;
	}

	public List<MarketPlace> findByExample(MarketPlace marketPlaceExample) {
		return super.findByExample(marketPlaceExample);
	}

	public void saveOrUpdateMarketPlace(MarketPlace marketPlace) {
		if(marketPlace.getDateCreate() == null){
			marketPlace.setDateCreate(new Date());
		}
		marketPlace.setDateUpdate(new Date());
		if(marketPlace.getId() == null){
			em.persist(marketPlace);
		} else {
			em.merge(marketPlace);
		}
	}

	public void deleteMarketPlace(MarketPlace marketPlace) {
		em.remove(marketPlace);
	}

}
