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

import org.hoteia.qalingo.core.domain.CustomerConnectionLog;

public interface CustomerConnectionLogService {

    CustomerConnectionLog getCustomerConnectionLogById(Long customerConnectionLogId, Object... params);
    
	CustomerConnectionLog getCustomerConnectionLogById(String customerConnectionLogId, Object... params);
	
	void saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog);
	
	void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog);

}