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

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("engineSessionDao")
public class EngineSessionDaoImpl extends AbstractGenericDaoImpl implements EngineSessionDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// ECO SESSION
	
	public EngineEcoSession getEngineEcoSessionById(final Long engineSessionId) {
//		return em.find(EngineEcoSession.class, engineSessionId);
        Criteria criteria = getSession().createCriteria(EngineEcoSession.class);
        
        addDefaultEngineEcoSessionFetch(criteria);

        criteria.add(Restrictions.eq("id", engineSessionId));
        EngineEcoSession engineEcoSession = (EngineEcoSession) criteria.uniqueResult();
        return engineEcoSession;
	}

	public void saveOrUpdateEngineEcoSession(EngineEcoSession engineSession) {
		if(engineSession.getDateCreate() == null){
			engineSession.setDateCreate(new Date());
		}
		engineSession.setDateUpdate(new Date());
		if(engineSession.getId() == null){
			em.persist(engineSession);
		} else {
			em.merge(engineSession);
		}
	}

	public void deleteEngineEcoSession(EngineEcoSession engineSession) {
		em.remove(engineSession);
	}
	
	// BO SESSION
	
	public EngineBoSession getEngineBoSessionById(final Long engineSessionId) {
//		return em.find(EngineBoSession.class, engineSessionId);
        Criteria criteria = getSession().createCriteria(EngineBoSession.class);
        
        addDefaultEngineBoSessionFetch(criteria);
        
        criteria.add(Restrictions.eq("id", engineSessionId));
        EngineBoSession engineBoSession = (EngineBoSession) criteria.uniqueResult();
        return engineBoSession;
	}

	public void saveOrUpdateEngineBoSession(EngineBoSession engineSession) {
		if(engineSession.getDateCreate() == null){
			engineSession.setDateCreate(new Date());
		}
		engineSession.setDateUpdate(new Date());
		if(engineSession.getId() == null){
			em.persist(engineSession);
		} else {
			em.merge(engineSession);
		}
	}

	public void deleteEngineBoSession(EngineBoSession engineSession) {
		em.remove(engineSession);
	}
	
    private void addDefaultEngineBoSessionFetch(Criteria criteria) {
    }
    
    private void addDefaultEngineEcoSessionFetch(Criteria criteria) {
        criteria.setFetchMode("cart", FetchMode.JOIN); 
    }

}