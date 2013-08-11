/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CustomerGroupDao;
import fr.hoteia.qalingo.core.domain.CustomerGroup;

@Transactional
@Repository("customerGroupDao")
public class CustomerGroupDaoImpl extends AbstractGenericDaoImpl implements CustomerGroupDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CustomerGroup getCustomerGroupById(Long customerGroupId) {
		return em.find(CustomerGroup.class, customerGroupId);
	}
	
	public CustomerGroup getCustomerGroupByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CustomerGroup WHERE code = :code";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		CustomerGroup customerGroup = (CustomerGroup) query.uniqueResult();
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

}
