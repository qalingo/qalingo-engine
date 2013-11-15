/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.service.EngineSessionService;

@Service("engineSessionService")
@Transactional
public class EngineSessionServiceImpl implements EngineSessionService {

	@Autowired
	private EngineSessionDao engineSessionDao;

	// ECO SESSION
	public EngineEcoSession getEngineEcoSessionById(String rawEngineSessionId) {
		long engineSessionId = -1;
		try {
			engineSessionId = Long.parseLong(rawEngineSessionId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSessionDao.getEngineEcoSessionById(engineSessionId);
	}

//	public List<EngineEcoSession> findEngineEcoSession(EngineEcoSession criteria) {
//		return engineSessionDao.findByExample(criteria);
//	}

	public void saveOrUpdateEngineEcoSession(EngineEcoSession engineEcoSession) {
		engineSessionDao.saveOrUpdateEngineEcoSession(engineEcoSession);
	}

	public void deleteEngineEcoSession(EngineEcoSession engineEcoSession) {
		engineSessionDao.deleteEngineEcoSession(engineEcoSession);
	}
	
	// BO SESSION
	public EngineBoSession getEngineBoSessionById(String rawEngineSessionId) {
		long engineSessionId = -1;
		try {
			engineSessionId = Long.parseLong(rawEngineSessionId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSessionDao.getEngineBoSessionById(engineSessionId);
	}

//	public List<EngineEcoSession> findEngineSession(EngineEcoSession criteria) {
//		return engineSessionDao.findByExample(criteria);
//	}

	public void saveOrUpdateEngineBoSession(EngineBoSession engineBoSession) {
		engineSessionDao.saveOrUpdateEngineBoSession(engineBoSession);
	}

	public void deleteEngineBoSession(EngineBoSession engineBoSession) {
		engineSessionDao.deleteEngineBoSession(engineBoSession);
	}

}
