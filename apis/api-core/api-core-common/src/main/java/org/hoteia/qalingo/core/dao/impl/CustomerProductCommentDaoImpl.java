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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CustomerProductCommentDao;
import org.hoteia.qalingo.core.domain.CustomerProductComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("customerProductCommentDao")
public class CustomerProductCommentDaoImpl extends AbstractGenericDaoImpl implements CustomerProductCommentDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomerProductComment getCustomerProductCommentById(final Long customerProductCommentId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerProductComment.class);
        criteria.add(Restrictions.eq("id", customerProductCommentId));
        CustomerProductComment customerProductComments = (CustomerProductComment) criteria.uniqueResult();
        return customerProductComments;
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerProductComment.class);
        criteria.add(Restrictions.eq("customerId", customerId));

        criteria.addOrder(Order.asc("id"));
        
        @SuppressWarnings("unchecked")
        List<CustomerProductComment> customerProductComments = criteria.list();
		return customerProductComments;
	}
	
	public List<CustomerProductComment> findCustomerProductCommentByProductSkuId(final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(CustomerProductComment.class);
        criteria.add(Restrictions.eq("productSkuId", productSkuId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<CustomerProductComment> customerProductComments = criteria.list();
		return customerProductComments;
	}

	public CustomerProductComment saveOrUpdateCustomerProductComment(final CustomerProductComment customerProductComment) {
		if(customerProductComment.getDateCreate() == null){
			customerProductComment.setDateCreate(new Date());
		}
		customerProductComment.setDateUpdate(new Date());
        if (customerProductComment.getId() != null) {
            if(em.contains(customerProductComment)){
                em.refresh(customerProductComment);
            }
            CustomerProductComment mergedCustomerProductComment = em.merge(customerProductComment);
            em.flush();
            return mergedCustomerProductComment;
        } else {
            em.persist(customerProductComment);
            return customerProductComment;
        }
	}

	public void deleteCustomerProductComment(final CustomerProductComment customerProductComment) {
		em.remove(customerProductComment);
	}

}