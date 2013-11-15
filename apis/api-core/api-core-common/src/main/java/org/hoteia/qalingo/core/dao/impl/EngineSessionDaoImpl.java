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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;

@Transactional
@Repository("engineSessionDao")
public class EngineSessionDaoImpl extends AbstractGenericDaoImpl implements EngineSessionDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// ECO SESSION
	public EngineEcoSession getEngineEcoSessionById(Long engineSessionId) {
		return em.find(EngineEcoSession.class, engineSessionId);
	}

//	public List<EngineEcoSession> findByExample(EngineEcoSession engineSessionExample) {
//		return super.findByExample(engineSessionExample);
//	}

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
	public EngineBoSession getEngineBoSessionById(Long engineSessionId) {
		return em.find(EngineBoSession.class, engineSessionId);
	}

//	public List<EngineBoSession> findByExample(EngineBoSession engineSessionExample) {
//		return super.findByExample(engineSessionExample);
//	}

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

}
