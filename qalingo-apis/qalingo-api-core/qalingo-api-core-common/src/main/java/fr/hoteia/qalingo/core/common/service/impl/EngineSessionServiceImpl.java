/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.EngineSessionDao;
import fr.hoteia.qalingo.core.common.domain.EngineSession;
import fr.hoteia.qalingo.core.common.service.EngineSessionService;

@Service("engineSessionService")
@Transactional
public class EngineSessionServiceImpl implements EngineSessionService {

	@Autowired
	private EngineSessionDao engineSessionDao;

	public EngineSession getEngineSessionById(String rawEngineSessionId) {
		long engineSessionId = -1;
		try {
			engineSessionId = Long.parseLong(rawEngineSessionId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return engineSessionDao.getEngineSessionById(engineSessionId);
	}

	public List<EngineSession> findEngineSession(EngineSession criteria) {
		return engineSessionDao.findByExample(criteria);
	}

	public void saveOrUpdateEngineSession(EngineSession engineSession) {
		engineSessionDao.saveOrUpdateEngineSession(engineSession);
	}

	public void deleteEngineSession(EngineSession engineSession) {
		engineSessionDao.deleteEngineSession(engineSession);
	}

}
