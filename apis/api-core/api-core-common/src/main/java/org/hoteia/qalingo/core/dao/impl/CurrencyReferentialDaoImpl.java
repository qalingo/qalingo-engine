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
import org.hoteia.qalingo.core.dao.CurrencyReferentialDao;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("currencyReferentialDao")
public class CurrencyReferentialDaoImpl extends AbstractGenericDaoImpl implements CurrencyReferentialDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CurrencyReferential getCurrencyReferentialById(final Long currencyReferentialId) {
//		return em.find(CurrencyReferential.class, currencyReferentialId);
	    
        Criteria criteria = getSession().createCriteria(CurrencyReferential.class);
        criteria.add(Restrictions.eq("id", currencyReferentialId));
        CurrencyReferential currencyReferential = (CurrencyReferential) criteria.uniqueResult();
        return currencyReferential;
	}

	public CurrencyReferential getCurrencyReferentialByCode(final String currencyReferentialCode) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM CurrencyReferential WHERE code = :currencyReferentialCode";
//		Query query = session.createQuery(sql);
//		query.setString("currencyReferentialCode", currencyReferentialCode);
//		CurrencyReferential currencyReferential = (CurrencyReferential) query.uniqueResult();
//		return currencyReferential;
		
        Criteria criteria = getSession().createCriteria(CurrencyReferential.class);
        criteria.add(Restrictions.eq("code", currencyReferentialCode));
        CurrencyReferential currencyReferential = (CurrencyReferential) criteria.uniqueResult();
        return currencyReferential;
	}
	
	public List<CurrencyReferential> findCurrencyReferentials() {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM CurrencyReferential ORDER BY code";
//		Query query = session.createQuery(sql);
//		List<CurrencyReferential> currencyReferentials = (List<CurrencyReferential>) query.list();
//		return currencyReferentials;
		
        Criteria criteria = getSession().createCriteria(CurrencyReferential.class);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<CurrencyReferential> users = criteria.list();
        return users;
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