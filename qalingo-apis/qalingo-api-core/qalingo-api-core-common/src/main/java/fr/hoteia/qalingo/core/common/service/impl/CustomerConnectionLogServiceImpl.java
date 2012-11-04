/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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

import fr.hoteia.qalingo.core.common.dao.CustomerConnectionLogDao;
import fr.hoteia.qalingo.core.common.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.common.service.CustomerConnectionLogService;

@Service("customerConnectionLogService")
@Transactional
public class CustomerConnectionLogServiceImpl implements CustomerConnectionLogService {

	@Autowired
	private CustomerConnectionLogDao customerConnectionLogDao;

	public CustomerConnectionLog getCustomerConnectionLogById(String rawCustomerConnectionLogId) {
		long customerConnectionLogId = -1;
		try {
			customerConnectionLogId = Long.parseLong(rawCustomerConnectionLogId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return customerConnectionLogDao.getCustomerConnectionLogById(customerConnectionLogId);
	}

	public List<CustomerConnectionLog> findCustomerConnectionLog(CustomerConnectionLog criteria) {
		return customerConnectionLogDao.findByExample(criteria);
	}

	public void saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		customerConnectionLogDao.saveOrUpdateCustomerConnectionLog(customerConnectionLog);
	}

	public void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		customerConnectionLogDao.deleteCustomerConnectionLog(customerConnectionLog);
	}

}
