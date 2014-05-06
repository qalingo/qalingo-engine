/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

@Repository("currencyReferentialDao")
public class CurrencyReferentialDaoImpl extends AbstractGenericDaoImpl implements CurrencyReferentialDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CurrencyReferential getCurrencyReferentialById(final Long currencyReferentialId, Object... params) {
        Criteria criteria = createDefaultCriteria(CurrencyReferential.class);
        criteria.add(Restrictions.eq("id", currencyReferentialId));
        CurrencyReferential currencyReferential = (CurrencyReferential) criteria.uniqueResult();
        return currencyReferential;
	}

	public CurrencyReferential getCurrencyReferentialByCode(final String currencyReferentialCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CurrencyReferential.class);
        criteria.add(Restrictions.eq("code", currencyReferentialCode));
        CurrencyReferential currencyReferential = (CurrencyReferential) criteria.uniqueResult();
        return currencyReferential;
	}
	
	public List<CurrencyReferential> findCurrencyReferentials(Object... params) {
        Criteria criteria = createDefaultCriteria(CurrencyReferential.class);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<CurrencyReferential> users = criteria.list();
        return users;
	}

	public CurrencyReferential saveOrUpdateCurrencyReferential(final CurrencyReferential currencyReferential) {
		if(currencyReferential.getDateCreate() == null){
			currencyReferential.setDateCreate(new Date());
		}
		currencyReferential.setDateUpdate(new Date());
        if (currencyReferential.getId() != null) {
            if(em.contains(currencyReferential)){
                em.refresh(currencyReferential);
            }
            CurrencyReferential mergedCurrencyReferential = em.merge(currencyReferential);
            em.flush();
            return mergedCurrencyReferential;
        } else {
            em.persist(currencyReferential);
            return currencyReferential;
        }
	}

	public void deleteCurrencyReferential(final CurrencyReferential currencyReferential) {
		em.remove(currencyReferential);
	}

}