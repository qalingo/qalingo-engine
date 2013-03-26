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

import fr.hoteia.qalingo.core.dao.CustomerProductCommentDao;
import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.service.CustomerProductCommentService;

@Service("customerProductCommentService")
@Transactional
public class CustomerProductCommentServiceImpl implements CustomerProductCommentService {

	@Autowired
	private CustomerProductCommentDao customerProductCommentDao;

	public CustomerProductComment getCustomerProductCommentById(final String rawCustomerProductCommentId) {
		long customerProductCommentId = -1;
		try {
			customerProductCommentId = Long.parseLong(rawCustomerProductCommentId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return customerProductCommentDao.getCustomerProductCommentById(customerProductCommentId);
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByCustomerId(final Long customerId) {
		return customerProductCommentDao.findCustomerProductCommentByCustomerId(customerId);
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByProductSkuId(final Long productSkuId) {
		return customerProductCommentDao.findCustomerProductCommentByProductSkuId(productSkuId);
	}
	
	public void saveOrUpdateCustomerProductComment(final CustomerProductComment customerProductComment) {
		customerProductCommentDao.saveOrUpdateCustomerProductComment(customerProductComment);
	}

	public void deleteCustomerProductComment(final CustomerProductComment customerProductComment) {
		customerProductCommentDao.deleteCustomerProductComment(customerProductComment);
	}

}
