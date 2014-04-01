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
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.GroupRoleDao;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("groupRoleDao")
public class GroupRoleDaoImpl extends AbstractGenericDaoImpl implements GroupRoleDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerGroup getCustomerGroupById(final Long customerGroupId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", customerGroupId));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        customerGroup.setFetchPlan(fetchPlan);
        return customerGroup;
	}
	
	public CustomerGroup getCustomerGroupByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("code", code));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        customerGroup.setFetchPlan(fetchPlan);
        return customerGroup;
	}
	
	public CustomerGroup saveOrUpdateCustomerGroup(CustomerGroup customerGroup) {
		if(customerGroup.getDateCreate() == null){
			customerGroup.setDateCreate(new Date());
		}
		customerGroup.setDateUpdate(new Date());
        if (customerGroup.getId() != null) {
            if(em.contains(customerGroup)){
                em.refresh(customerGroup);
            }
            CustomerGroup mergedCustomerGroup = em.merge(customerGroup);
            em.flush();
            return mergedCustomerGroup;
        } else {
            em.persist(customerGroup);
            return customerGroup;
        }
	}

	public void deleteCustomerGroup(CustomerGroup customerGroup) {
		em.remove(customerGroup);
	}
	
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.defaultCustomerGroupFetchPlan());
        }
    }

}