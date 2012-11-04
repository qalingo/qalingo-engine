/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.CustomerDao;
import fr.hoteia.qalingo.core.common.domain.Customer;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;

@Transactional
@Repository("customerDao")
public class CustomerDaoImpl extends AbstractGenericDaoImpl implements CustomerDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Customer getCustomerById(final Long customerId) {
		return em.find(Customer.class, customerId);
	}

	public Customer getCustomerByLoginOrEmail(final String usernameOrEmail) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Customer WHERE (login = :usernameOrEmail OR email = :usernameOrEmail) AND active = true";
		Query query = session.createQuery(sql);
		query.setString("usernameOrEmail", usernameOrEmail);
		Customer customer = (Customer) query.uniqueResult();
		return customer;
	}
	
	public List<Customer> findCustomers() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Customer ORDER BY lastname";
		Query query = session.createQuery(sql);
		List<Customer> customers = (List<Customer>) query.list();
		return customers;
	}
	
	public List<Customer> findByExample(final Customer customerExample) {
		return super.findByExample(customerExample);
	}

	public void saveOrUpdateCustomer(final Customer customer) {
		if(customer.getDateCreate() == null){
			customer.setDateCreate(new Date());
		}
		customer.setDateUpdate(new Date());
		if(customer.getId() == null){
			em.persist(customer);
		} else {
			em.merge(customer);
		}
	}

	public void deleteCustomer(final Customer customer) {
		em.remove(customer);
	}

}
