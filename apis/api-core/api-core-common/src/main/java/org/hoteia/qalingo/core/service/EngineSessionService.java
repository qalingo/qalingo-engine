/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;

public interface EngineSessionService {

    // ECO SESSION

    EngineEcoSession updateAndSynchronizeEngineEcoSession(EngineEcoSession engineEcoSessionWithTransientValues) throws Exception;
    
    void synchronizeEngineEcoSession(EngineEcoSession engineEcoSessionWithTransientValues, String ecoEngineSessionGuid) throws Exception;

    EngineEcoSession getEngineEcoSessionById(Long engineEcoSessionId, Object... params);

    EngineEcoSession getEngineEcoSessionById(String engineEcoSessionId, Object... params);

    EngineEcoSession getEngineEcoSessionByEngineSessionGuid(String jSessionId, Object... params);

    EngineEcoSession saveOrUpdateEngineEcoSession(EngineEcoSession engineEcoSession);

    void deleteEngineEcoSession(EngineEcoSession engineEcoSession);

    // BO SESSION

    EngineBoSession getEngineBoSessionById(Long engineBoSessionId, Object... params);

    EngineBoSession getEngineBoSessionById(String engineBoSessionId, Object... params);

    EngineBoSession getEngineBoSessionByEngineSessionGuid(String jSessionId, Object... params);

    EngineBoSession saveOrUpdateEngineBoSession(EngineBoSession engineBoSession);

    void deleteEngineBoSession(EngineBoSession engineBoSession);

}