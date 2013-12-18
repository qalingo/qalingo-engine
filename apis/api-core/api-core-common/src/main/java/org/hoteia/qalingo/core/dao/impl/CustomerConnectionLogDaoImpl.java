/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerConnectionLogDao;
import org.hoteia.qalingo.core.domain.CustomerConnectionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("customerConnectionLogDao")
public class CustomerConnectionLogDaoImpl extends AbstractGenericDaoImpl implements CustomerConnectionLogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerConnectionLog getCustomerConnectionLogById(final Long customerConnectionLogId) {
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("id", customerConnectionLogId));
        CustomerConnectionLog customerConnectionLog = (CustomerConnectionLog) criteria.uniqueResult();
        return customerConnectionLog;
	}

	public List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerId(Long customerId){
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("customerId", customerId));
        
        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<CustomerConnectionLog> customerConnectionLogs = criteria.list();
        return customerConnectionLogs;
	}
	
	public List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerIdAndAppCode(final Long customerId, final String appCode) {
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("customerId", customerId));
        criteria.add(Restrictions.eq("appCode", appCode));
        
        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<CustomerConnectionLog> customerConnectionLogs = criteria.list();
        return customerConnectionLogs;
	}
	
	public void saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		if(customerConnectionLog.getId() == null){
			em.persist(customerConnectionLog);
		} else {
			em.merge(customerConnectionLog);
		}
	}

	public void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		em.remove(customerConnectionLog);
	}

}