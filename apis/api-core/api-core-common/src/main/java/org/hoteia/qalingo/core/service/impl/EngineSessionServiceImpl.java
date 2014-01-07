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

import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("engineSessionService")
@Transactional
public class EngineSessionServiceImpl implements EngineSessionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EngineSessionDao engineSessionDao;

    // ECO SESSION

    public EngineEcoSession getEngineEcoSessionById(final Long engineSessionId) {
        return engineSessionDao.getEngineEcoSessionById(engineSessionId);
    }

    public EngineEcoSession getEngineEcoSessionById(final String rawEngineSessionId) {
        long engineSessionId = -1;
        try {
            engineSessionId = Long.parseLong(rawEngineSessionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineEcoSessionById(engineSessionId);
    }
    
    public EngineEcoSession getEngineEcoSessionByEngineSessionGuid(final String engineSessionGuid) {
        return engineSessionDao.getEngineEcoSessionByEngineSessionGuid(engineSessionGuid);
    }

    public EngineEcoSession saveOrUpdateEngineEcoSession(final EngineEcoSession engineEcoSession) {
        return engineSessionDao.saveOrUpdateEngineEcoSession(engineEcoSession);
    }

    public void deleteEngineEcoSession(final EngineEcoSession engineEcoSession) {
        engineSessionDao.deleteEngineEcoSession(engineEcoSession);
    }

    // BO SESSION

    public EngineBoSession getEngineBoSessionById(final Long engineSessionId) {
        return engineSessionDao.getEngineBoSessionById(engineSessionId);
    }

    public EngineBoSession getEngineBoSessionById(final String rawEngineSessionId) {
        long engineSessionId = -1;
        try {
            engineSessionId = Long.parseLong(rawEngineSessionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineBoSessionById(engineSessionId);
    }
    
    public EngineBoSession getEngineBoSessionByEngineSessionGuid(final String engineSessionGuid) {
        return engineSessionDao.getEngineBoSessionByEngineSessionGuid(engineSessionGuid);
    }

    public EngineBoSession saveOrUpdateEngineBoSession(final EngineBoSession engineBoSession) {
        return engineSessionDao.saveOrUpdateEngineBoSession(engineBoSession);
    }

    public void deleteEngineBoSession(final EngineBoSession engineBoSession) {
        engineSessionDao.deleteEngineBoSession(engineBoSession);
    }
    
}