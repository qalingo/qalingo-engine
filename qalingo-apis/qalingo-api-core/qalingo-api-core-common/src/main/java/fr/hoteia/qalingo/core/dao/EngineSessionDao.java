/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao;

import fr.hoteia.qalingo.core.domain.EngineBoSession;
import fr.hoteia.qalingo.core.domain.EngineEcoSession;

public interface EngineSessionDao {

	// ECO SESSION
	EngineEcoSession getEngineEcoSessionById(Long engineSessionId);

//	List<EngineEcoSession> findByExample(EngineEcoSession engineSessionExample);

	void saveOrUpdateEngineEcoSession(EngineEcoSession engineSession);

	void deleteEngineEcoSession(EngineEcoSession engineSession);
	
	// BO SESSION
	EngineBoSession getEngineBoSessionById(Long engineBoSessionId);
	
//	List<EngineBoSession> findEngineBoSession(EngineBoSession criteria);
	
	void saveOrUpdateEngineBoSession(EngineBoSession engineBoSession);
	
	void deleteEngineBoSession(EngineBoSession engineBoSession);

}
