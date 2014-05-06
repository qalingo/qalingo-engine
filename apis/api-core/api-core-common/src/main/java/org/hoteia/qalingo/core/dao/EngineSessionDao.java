/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;

public interface EngineSessionDao {

	// ECO SESSION
    
	EngineEcoSession getEngineEcoSessionById(Long engineSessionId, Object... params);

	EngineEcoSession getEngineEcoSessionByEngineSessionGuid(String engineSessionGuid, Object... params);
	
	EngineEcoSession saveOrUpdateEngineEcoSession(EngineEcoSession engineSession);

	void deleteEngineEcoSession(EngineEcoSession engineSession);
	
	// BO SESSION
	
	EngineBoSession getEngineBoSessionById(Long engineBoSessionId, Object... params);
	
    EngineBoSession getEngineBoSessionByEngineSessionGuid(String engineSessionGuid, Object... params);
	
    EngineBoSession saveOrUpdateEngineBoSession(EngineBoSession engineBoSession);
	
	void deleteEngineBoSession(EngineBoSession engineBoSession);

}