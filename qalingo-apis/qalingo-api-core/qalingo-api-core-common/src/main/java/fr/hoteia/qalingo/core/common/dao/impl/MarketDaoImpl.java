/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

import fr.hoteia.qalingo.core.common.dao.MarketDao;
import fr.hoteia.qalingo.core.common.domain.Market;

@Transactional
@Repository("marketDao")
public class MarketDaoImpl extends AbstractGenericDaoImpl implements MarketDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Market getDefaultMarket() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Market WHERE isDefault = true";
		Query query = session.createQuery(sql);
		Market market = (Market) query.uniqueResult();
		return market;
	}
	
	public Market getMarketById(Long marketId) {
		return em.find(Market.class, marketId);
	}

	public Market getMarketByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Market WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		Market market = (Market) query.uniqueResult();
		return market;
	}
	
	public List<Market> findByExample(Market marketExample) {
		return super.findByExample(marketExample);
	}
	
	public List<Market> findMarkets() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Market ORDER BY code";
		Query query = session.createQuery(sql);
		List<Market> markets = (List<Market>) query.list();
		return markets;
	}

	public void saveOrUpdateMarket(Market market) {
		if(market.getDateCreate() == null){
			market.setDateCreate(new Date());
		}
		market.setDateUpdate(new Date());
		if(market.getId() == null){
			em.persist(market);
		} else {
			em.merge(market);
		}
	}

	public void deleteMarket(Market market) {
		em.remove(market);
	}

}
