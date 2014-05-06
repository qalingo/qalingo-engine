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

import java.util.List;

import org.hoteia.qalingo.core.domain.CustomerProductComment;

public interface CustomerProductCommentService {

    CustomerProductComment getCustomerProductCommentById(Long customerId, Object... params);

    CustomerProductComment getCustomerProductCommentById(String customerId, Object... params);

	List<CustomerProductComment> findCustomerProductCommentByCustomerId(Long customerId, Object... params);
	
	List<CustomerProductComment> findCustomerProductCommentByProductSkuId(Long productSkuId, Object... params);
	
	void saveOrUpdateCustomerProductComment(CustomerProductComment customer);
	
	void deleteCustomerProductComment(CustomerProductComment customer);

}