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

import fr.hoteia.qalingo.core.dao.CustomerConnectionLogDao;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.service.CustomerConnectionLogService;
import fr.hoteia.qalingo.core.service.EngineSettingService;

@Service("customerConnectionLogService")
@Transactional
public class CustomerConnectionLogServiceImpl implements CustomerConnectionLogService {

	@Autowired
	private CustomerConnectionLogDao customerConnectionLogDao;

	@Autowired
	protected EngineSettingService engineSettingService;
	
	public CustomerConnectionLog getCustomerConnectionLogById(String rawCustomerConnectionLogId) {
		long customerConnectionLogId = -1;
		try {
			customerConnectionLogId = Long.parseLong(rawCustomerConnectionLogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return customerConnectionLogDao.getCustomerConnectionLogById(customerConnectionLogId);
	}

//	public List<CustomerConnectionLog> findCustomerConnectionLog(CustomerConnectionLog criteria) {
//		return customerConnectionLogDao.findByExample(criteria);
//	}

	public void saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		String maxConnectionToLog = engineSettingService.getEngineSettingDefaultValueByCode(EngineSettingService.ENGINE_SETTING_MAX_CUSTOMER_CONNECTION_LOG);
		final Long customerId = customerConnectionLog.getCustomerId();
		final String appCode = customerConnectionLog.getApp();
		List<CustomerConnectionLog> customerConnectionLogs  = customerConnectionLogDao.findCustomerConnectionLogsByCustomerIdAndAppCode(customerId, appCode);
		if(customerConnectionLogs.size() >= new Integer(maxConnectionToLog)){
			CustomerConnectionLog customerConnectionLogToUpdate = customerConnectionLogs.get(0);
			customerConnectionLogToUpdate.setAddress(customerConnectionLog.getAddress());
			customerConnectionLogToUpdate.setHost(customerConnectionLog.getHost());
			customerConnectionLogToUpdate.setLoginDate(customerConnectionLog.getLoginDate());
			customerConnectionLogDao.saveOrUpdateCustomerConnectionLog(customerConnectionLogToUpdate);
		} else {
			customerConnectionLogDao.saveOrUpdateCustomerConnectionLog(customerConnectionLog);
		}
	}

	public void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		customerConnectionLogDao.deleteCustomerConnectionLog(customerConnectionLog);
	}

}
