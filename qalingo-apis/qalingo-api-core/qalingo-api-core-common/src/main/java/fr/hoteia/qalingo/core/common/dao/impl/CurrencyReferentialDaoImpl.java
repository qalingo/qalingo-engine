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

import fr.hoteia.qalingo.core.common.dao.CurrencyReferentialDao;
import fr.hoteia.qalingo.core.common.domain.CurrencyReferential;

@Transactional
@Repository("currencyReferentialDao")
public class CurrencyReferentialDaoImpl extends AbstractGenericDaoImpl implements CurrencyReferentialDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CurrencyReferential getCurrencyReferentialById(Long currencyReferentialId) {
		return em.find(CurrencyReferential.class, currencyReferentialId);
	}

	public CurrencyReferential getCurrencyReferentialByCode(String currencyReferentialCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CurrencyReferential WHERE code = :currencyReferentialCode";
		Query query = session.createQuery(sql);
		query.setString("currencyReferentialCode", currencyReferentialCode);
		CurrencyReferential currencyReferential = (CurrencyReferential) query.uniqueResult();
		return currencyReferential;
	}
	
	public List<CurrencyReferential> findCurrencyReferentials() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CurrencyReferential ORDER BY code";
		Query query = session.createQuery(sql);
		List<CurrencyReferential> currencyReferentials = (List<CurrencyReferential>) query.list();
		return currencyReferentials;
	}

	public void saveOrUpdateCurrencyReferential(CurrencyReferential currencyReferential) {
		if(currencyReferential.getDateCreate() == null){
			currencyReferential.setDateCreate(new Date());
		}
		currencyReferential.setDateUpdate(new Date());
		if(currencyReferential.getId() == null){
			em.persist(currencyReferential);
		} else {
			em.merge(currencyReferential);
		}
	}

	public void deleteCurrencyReferential(CurrencyReferential currencyReferential) {
		em.remove(currencyReferential);
	}

}
