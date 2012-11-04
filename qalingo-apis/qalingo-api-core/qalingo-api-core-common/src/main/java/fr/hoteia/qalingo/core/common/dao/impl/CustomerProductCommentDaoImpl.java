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

import fr.hoteia.qalingo.core.common.dao.CustomerProductCommentDao;
import fr.hoteia.qalingo.core.common.domain.CustomerProductComment;

@Transactional
@Repository("customerProductCommentDao")
public class CustomerProductCommentDaoImpl extends AbstractGenericDaoImpl implements CustomerProductCommentDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CustomerProductComment getCustomerProductCommentById(final Long customerProductCommentId) {
		return em.find(CustomerProductComment.class, customerProductCommentId);
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByCustomerId(final Long customerId) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CustomerProductComment WHERE customerId = :customerId";
		Query query = session.createQuery(sql);
		query.setLong("customerId", customerId);
		List<CustomerProductComment> customerProductComments = (List<CustomerProductComment>) query.list();
		return customerProductComments;
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByProductSkuId(final Long productSkuId) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CustomerProductComment WHERE productSku.id = :productSkuId";
		Query query = session.createQuery(sql);
		query.setLong("productSkuId", productSkuId);
		List<CustomerProductComment> customerProductComments = (List<CustomerProductComment>) query.list();
		return customerProductComments;
	}

	public void saveOrUpdateCustomerProductComment(final CustomerProductComment customerProductComment) {
		if(customerProductComment.getDateCreate() == null){
			customerProductComment.setDateCreate(new Date());
		}
		customerProductComment.setDateUpdate(new Date());
		if(customerProductComment.getId() == null){
			em.persist(customerProductComment);
		} else {
			em.merge(customerProductComment);
		}
	}

	public void deleteCustomerProductComment(final CustomerProductComment customerProductComment) {
		em.remove(customerProductComment);
	}

}
