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

import java.util.List;

import org.hoteia.qalingo.core.domain.UserConnectionLog;

public interface UserConnectionLogDao {

	UserConnectionLog getUserConnectionLogById(Long userConnectionLogId, Object... params);

	List<UserConnectionLog> findUserConnectionLogsByUserId(Long userId, Object... params);

	List<UserConnectionLog> findUserConnectionLogsByUserIdAndAppCode(Long userId, String appCode, Object... params);

	UserConnectionLog saveOrUpdateUserConnectionLog(UserConnectionLog userConnectionLog);

	void deleteUserConnectionLog(UserConnectionLog userConnectionLog);

}