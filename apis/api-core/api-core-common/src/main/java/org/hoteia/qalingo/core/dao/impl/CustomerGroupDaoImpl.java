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

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerGroupDao;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("customerGroupDao")
public class CustomerGroupDaoImpl extends AbstractGenericDaoImpl implements CustomerGroupDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerGroup getCustomerGroupById(final Long customerGroupId) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.eq("id", customerGroupId));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        return customerGroup;
	}
	
	public CustomerGroup getCustomerGroupByCode(final String code) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        addDefaultFetch(criteria);
        
        criteria.add(Restrictions.eq("code", code));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        return customerGroup;
	}
	
	public void saveOrUpdateCustomerGroup(CustomerGroup customerGroup) {
		if(customerGroup.getDateCreate() == null){
			customerGroup.setDateCreate(new Date());
		}
		customerGroup.setDateUpdate(new Date());
		if(customerGroup.getId() == null){
			em.persist(customerGroup);
		} else {
			em.merge(customerGroup);
		}
	}

	public void deleteCustomerGroup(CustomerGroup customerGroup) {
		em.remove(customerGroup);
	}
	
    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("customerRoles", FetchMode.JOIN); 
    }

}