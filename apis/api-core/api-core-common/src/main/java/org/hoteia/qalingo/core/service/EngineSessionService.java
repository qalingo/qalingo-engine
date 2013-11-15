/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;

public interface EngineSessionService {

	// ECO SESSION
	EngineEcoSession getEngineEcoSessionById(String engineEcoSessionId);
	
//	List<EngineEcoSession> findEngineSession(EngineEcoSession criteria);
	
	void saveOrUpdateEngineEcoSession(EngineEcoSession engineEcoSession);
	
	void deleteEngineEcoSession(EngineEcoSession engineEcoSession);
	
	// BO SESSION
	EngineBoSession getEngineBoSessionById(String engineBoSessionId);
	
//	List<EngineBoSession> findEngineBoSession(EngineBoSession criteria);
	
	void saveOrUpdateEngineBoSession(EngineBoSession engineBoSession);
	
	void deleteEngineBoSession(EngineBoSession engineBoSession);

}
