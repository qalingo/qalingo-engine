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

import org.hoteia.qalingo.core.domain.CustomerConnectionLog;

public interface CustomerConnectionLogDao {

	CustomerConnectionLog getCustomerConnectionLogById(Long customerConnectionLogId, Object... params);

	List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerId(Long customerId, Object... params);

	List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerIdAndAppCode(Long customerId, String appCode, Object... params);
	
	CustomerConnectionLog saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog);

	void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog);

}
