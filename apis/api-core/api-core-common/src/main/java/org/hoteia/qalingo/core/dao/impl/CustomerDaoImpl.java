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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerDao;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAttribute;
import org.hoteia.qalingo.core.domain.CustomerCredential;
import org.hoteia.qalingo.core.exception.CustomerAttributeException;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractGenericDaoImpl implements CustomerDao {

	public Customer getCustomerById(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", customerId));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public Customer getCustomerByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", code));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public Customer getCustomerByPermalink(final String permalink, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("permalink", permalink));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}

	public Customer getCustomerByLoginOrEmail(final String usernameOrEmail, Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.eq("login", usernameOrEmail), Restrictions.eq("email", usernameOrEmail)));
        Customer customer = (Customer) criteria.uniqueResult();
        if(customer != null){
            customer.setFetchPlan(fetchPlan);
        }
        return customer;
	}
	
	public List<Customer> findCustomers(Object... params) {
        Criteria criteria = createDefaultCriteria(Customer.class);
        
        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));

        @SuppressWarnings("unchecked")
        List<Customer> customers = criteria.list();
		return customers;
	}
	
	public Customer saveOrUpdateCustomer(final Customer customer) throws Exception {
		if(customer.getDateCreate() == null){
			customer.setDateCreate(new Date());
			customer.setActive(true);
			customer.setValidated(false);
		}
		customer.setDateUpdate(new Date());
        if(StringUtils.isEmpty(customer.getCode())){
            customer.setCode(UUID.randomUUID().toString());
        }

		if(customer.getPermalink() == null){
			customer.setPermalink(UUID.randomUUID().toString());
		}
		
		for (Iterator<CustomerAttribute> iterator = customer.getAttributes().iterator(); iterator.hasNext();) {
			CustomerAttribute customerAttribute = (CustomerAttribute) iterator.next();
			// ATTRIBUTE DEFINITION CAN'T BE NULL
	        if(customerAttribute.getAttributeDefinition() == null){
	        	throw new CustomerAttributeException("Attribute Definition can't be null!");
	        }
			// MARKET AREA CAN'T BE NULL IF ATTRIBUTE IS NOT GLOBAL
	        if(!customerAttribute.getAttributeDefinition().isGlobal()
	        		&& customerAttribute.getMarketAreaId() == null){
	        	throw new CustomerAttributeException("Market Area can't be null if Attribute is not global!");
	        }
        }
        if (customer.getId() != null) {
            if(em.contains(customer)){
                em.refresh(customer);
            }
            Customer mergedCustomer = em.merge(customer);
            em.flush();
            return mergedCustomer;
        } else {
            em.persist(customer);
            return customer;
        }
	}

	public void deleteCustomer(final Customer customer) {
		em.remove(customer);
	}
	
	// CREDENTIAL
	
	public CustomerCredential saveOrUpdateCustomerCredential(final CustomerCredential customerCredential) throws Exception {
		if(customerCredential.getDateCreate() == null){
			customerCredential.setDateCreate(new Date());
			if(StringUtils.isEmpty(customerCredential.getResetToken())){
				customerCredential.setResetToken(UUID.randomUUID().toString());
			}
		}
		customerCredential.setDateUpdate(new Date());
        if (customerCredential.getId() != null) {
            if(em.contains(customerCredential)){
                em.refresh(customerCredential);
            }
            CustomerCredential mergedCustomerCredential = em.merge(customerCredential);
            em.flush();
            return mergedCustomerCredential;
        } else {
            em.persist(customerCredential);
            return customerCredential;
        }
	}
	
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCustomer.defaultCustomerFetchPlan());
        }
    }

}