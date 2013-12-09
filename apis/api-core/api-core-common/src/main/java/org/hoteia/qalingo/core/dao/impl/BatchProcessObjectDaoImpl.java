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
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.BatchProcessObjectDao;
import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.enumtype.BatchProcessObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("batchProcessObjectDao")
public class BatchProcessObjectDaoImpl extends AbstractGenericDaoImpl implements BatchProcessObjectDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BatchProcessObject getBatchProcessObjectById(final Long batchProcessObjectId) {
	    
        Criteria criteria = getSession().createCriteria(BatchProcessObject.class);
        criteria.add(Restrictions.eq("id", batchProcessObjectId));
        BatchProcessObject batchProcessObject = (BatchProcessObject) criteria.uniqueResult();
        return batchProcessObject;
        
//		return em.find(BatchProcessObject.class, batchProcessObjectId);
	}

	public List<BatchProcessObject> findBatchProcessObjects() {
        Criteria criteria = getSession().createCriteria(BatchProcessObject.class);

        @SuppressWarnings("unchecked")
        List<BatchProcessObject> batchProcessObjects = criteria.list();
        
        criteria.addOrder(Order.asc("id"));
        
//		String sql = "FROM BatchProcessObject";
//		Query query = session.createQuery(sql);
//		List<BatchProcessObject> batchProcessObjects = (List<BatchProcessObject>) query.list();
        
		return batchProcessObjects;
	}
	
	public List<BatchProcessObject> findBatchProcessObjectsByTypeObject(BatchProcessObjectType typeObject) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM BatchProcessObject WHERE typeObject = :typeObject";
//		Query query = session.createQuery(sql);
//		query.setParameter("typeObject", typeObject);
//		List<BatchProcessObject> batchProcessObjects = (List<BatchProcessObject>) query.list();
//		return batchProcessObjects;
		
        Criteria criteria = getSession().createCriteria(BatchProcessObject.class);

        criteria.add(Restrictions.eq("typeObject", typeObject));
        
        criteria.addOrder(Order.asc("attributeType"));
        criteria.addOrder(Order.asc("objectType"));

        criteria.addOrder(Order.asc("id"));
        
        @SuppressWarnings("unchecked")
        List<BatchProcessObject> batchProcessObjects = criteria.list();
        return batchProcessObjects;
	}
	
	public void saveOrUpdateBatchProcessObject(BatchProcessObject batchProcessObject) {
		if(batchProcessObject.getDateCreate() == null){
			batchProcessObject.setDateCreate(new Date());
		}
		batchProcessObject.setDateUpdate(new Date());
		if(batchProcessObject.getId() == null){
			em.persist(batchProcessObject);
		} else {
			em.merge(batchProcessObject);
		}
	}

	public void deleteBatchProcessObject(BatchProcessObject batchProcessObject) {
		em.remove(batchProcessObject);
	}

}