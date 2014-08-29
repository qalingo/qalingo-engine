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

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.UserGroup;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("groupRoleDao")
public class GroupRoleDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// CUSTOMER GROUP
	
	public CustomerGroup getCustomerGroupById(final Long customerGroupId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        FetchPlan fetchPlan = handleSpecificCustomerGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", customerGroupId));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        if(customerGroup != null){
            customerGroup.setFetchPlan(fetchPlan);
        }
        return customerGroup;
	}
	
	public CustomerGroup getCustomerGroupByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerGroup.class);
        
        FetchPlan fetchPlan = handleSpecificCustomerGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("code", code));
        CustomerGroup customerGroup = (CustomerGroup) criteria.uniqueResult();
        if(customerGroup != null){
            customerGroup.setFetchPlan(fetchPlan);
        }
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
	
    protected FetchPlan handleSpecificCustomerGroupFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.defaultCustomerGroupFetchPlan());
        }
    }
    
    // USER GROUP
    
    public UserGroup getUserGroupById(final Long userGroupId, Object... params) {
        Criteria criteria = createDefaultCriteria(UserGroup.class);
        
        FetchPlan fetchPlan = handleSpecificUserGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", userGroupId));
        UserGroup userGroup = (UserGroup) criteria.uniqueResult();
        if(userGroup != null){
            userGroup.setFetchPlan(fetchPlan);
        }
        return userGroup;
    }
    
    public UserGroup getUserGroupByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(UserGroup.class);
        
        FetchPlan fetchPlan = handleSpecificUserGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("code", code));
        UserGroup userGroup = (UserGroup) criteria.uniqueResult();
        if(userGroup != null){
            userGroup.setFetchPlan(fetchPlan);
        }
        return userGroup;
    }
    
    public UserGroup saveOrUpdateUserGroup(UserGroup userGroup) {
        if(userGroup.getDateCreate() == null){
            userGroup.setDateCreate(new Date());
        }
        userGroup.setDateUpdate(new Date());
        if (userGroup.getId() != null) {
            if(em.contains(userGroup)){
                em.refresh(userGroup);
            }
            UserGroup mergedUserGroup = em.merge(userGroup);
            em.flush();
            return mergedUserGroup;
        } else {
            em.persist(userGroup);
            return userGroup;
        }
    }

    public void deleteUserGroup(UserGroup userGroup) {
        em.remove(userGroup);
    }
    
    protected FetchPlan handleSpecificUserGroupFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.defaultCustomerGroupFetchPlan());
        }
    }

}