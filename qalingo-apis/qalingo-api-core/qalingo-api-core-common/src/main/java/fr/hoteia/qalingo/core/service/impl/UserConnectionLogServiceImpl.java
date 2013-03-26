/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.UserConnectionLogDao;
import fr.hoteia.qalingo.core.domain.UserConnectionLog;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.UserConnectionLogService;

@Service("userConnectionLogService")
@Transactional
public class UserConnectionLogServiceImpl implements UserConnectionLogService {

	@Autowired
	private UserConnectionLogDao userConnectionLogDao;
	
	@Autowired
	protected EngineSettingService engineSettingService;

	public UserConnectionLog getUserConnectionLogById(String rawUserConnectionLogId) {
		long userConnectionLogId = -1;
		try {
			userConnectionLogId = Long.parseLong(rawUserConnectionLogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return userConnectionLogDao.getUserConnectionLogById(userConnectionLogId);
	}

//	public List<UserConnectionLog> findUserConnectionLog(UserConnectionLog criteria) {
//		return userConnectionLogDao.findByExample(criteria);
//	}

	public void saveOrUpdateUserConnectionLog(UserConnectionLog userConnectionLog) {
		String maxConnectionToLog = engineSettingService.getEngineSettingDefaultValueByCode(EngineSettingService.ENGINE_SETTING_MAX_USER_CONNECTION_LOG);
		final Long userId = userConnectionLog.getUserId();
		final String appCode = userConnectionLog.getApp();
		List<UserConnectionLog> userConnectionLogs  = userConnectionLogDao.findUserConnectionLogsByUserIdAndAppCode(userId, appCode);
		if(userConnectionLogs.size() >= new Integer(maxConnectionToLog)){
			UserConnectionLog userConnectionLogToUpdate = userConnectionLogs.get(0);
			userConnectionLogToUpdate.setAddress(userConnectionLog.getAddress());
			userConnectionLogToUpdate.setHost(userConnectionLog.getHost());
			userConnectionLogToUpdate.setLoginDate(userConnectionLog.getLoginDate());
			userConnectionLogDao.saveOrUpdateUserConnectionLog(userConnectionLogToUpdate);
		} else {
			userConnectionLogDao.saveOrUpdateUserConnectionLog(userConnectionLog);
		}
	}

	public void deleteUserConnectionLog(UserConnectionLog userConnectionLog) {
		userConnectionLogDao.deleteUserConnectionLog(userConnectionLog);
	}

}
