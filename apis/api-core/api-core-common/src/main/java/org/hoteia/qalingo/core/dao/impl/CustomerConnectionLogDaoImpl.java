/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

@Repository("customerConnectionLogDao")
public class CustomerConnectionLogDaoImpl extends AbstractGenericDaoImpl implements CustomerConnectionLogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerConnectionLog getCustomerConnectionLogById(final Long customerConnectionLogId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("id", customerConnectionLogId));
        CustomerConnectionLog customerConnectionLog = (CustomerConnectionLog) criteria.uniqueResult();
        return customerConnectionLog;
	}

	public List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerId(Long customerId, Object... params){
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("customerId", customerId));
        
        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<CustomerConnectionLog> customerConnectionLogs = criteria.list();
        return customerConnectionLogs;
	}
	
	public List<CustomerConnectionLog> findCustomerConnectionLogsByCustomerIdAndAppCode(final Long customerId, final String appCode, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerConnectionLog.class);
        criteria.add(Restrictions.eq("customerId", customerId));
        criteria.add(Restrictions.eq("appCode", appCode));
        
        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<CustomerConnectionLog> customerConnectionLogs = criteria.list();
        return customerConnectionLogs;
	}
	
	public CustomerConnectionLog saveOrUpdateCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
        if (customerConnectionLog.getId() != null) {
            if(em.contains(customerConnectionLog)){
                em.refresh(customerConnectionLog);
            }
            CustomerConnectionLog mergedCustomerConnectionLog = em.merge(customerConnectionLog);
            em.flush();
            return mergedCustomerConnectionLog;
        } else {
            em.persist(customerConnectionLog);
            return customerConnectionLog;
        }
	}

	public void deleteCustomerConnectionLog(CustomerConnectionLog customerConnectionLog) {
		em.remove(customerConnectionLog);
	}

}