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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.EngineSessionDao;
import fr.hoteia.qalingo.core.common.domain.EngineSession;

@Transactional
@Repository("engineSessionDao")
public class EngineSessionDaoImpl extends AbstractGenericDaoImpl implements EngineSessionDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public EngineSession getEngineSessionById(Long engineSessionId) {
		return em.find(EngineSession.class, engineSessionId);
	}

	public List<EngineSession> findByExample(EngineSession engineSessionExample) {
		return super.findByExample(engineSessionExample);
	}

	public void saveOrUpdateEngineSession(EngineSession engineSession) {
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

	public void deleteEngineSession(EngineSession engineSession) {
		em.remove(engineSession);
	}

}
