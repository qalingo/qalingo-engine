/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import fr.hoteia.qalingo.core.domain.UserConnectionLog;

public interface UserConnectionLogService {

	UserConnectionLog getUserConnectionLogById(String userConnectionLogId);
	
//	List<UserConnectionLog> findUserConnectionLog(UserConnectionLog criteria);
	
	void saveOrUpdateUserConnectionLog(UserConnectionLog userConnectionLog);
	
	void deleteUserConnectionLog(UserConnectionLog userConnectionLog);

}
