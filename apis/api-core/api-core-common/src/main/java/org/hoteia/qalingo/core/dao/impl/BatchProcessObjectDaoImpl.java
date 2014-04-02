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

@Repository("batchProcessObjectDao")
public class BatchProcessObjectDaoImpl extends AbstractGenericDaoImpl implements BatchProcessObjectDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BatchProcessObject getBatchProcessObjectById(final Long batchProcessObjectId, Object... params) {
	    
        Criteria criteria = createDefaultCriteria(BatchProcessObject.class);
        criteria.add(Restrictions.eq("id", batchProcessObjectId));
        BatchProcessObject batchProcessObject = (BatchProcessObject) criteria.uniqueResult();
        return batchProcessObject;
	}

	public List<BatchProcessObject> findBatchProcessObjects(Object... params) {
        Criteria criteria = createDefaultCriteria(BatchProcessObject.class);
        criteria.addOrder(Order.asc("id"));
        
        @SuppressWarnings("unchecked")
        List<BatchProcessObject> batchProcessObjects = criteria.list();
		return batchProcessObjects;
	}
	
	public List<BatchProcessObject> findBatchProcessObjectsByTypeObject(final BatchProcessObjectType typeObject, Object... params) {
        Criteria criteria = createDefaultCriteria(BatchProcessObject.class);

        criteria.add(Restrictions.eq("typeObject", typeObject));
        
        criteria.addOrder(Order.asc("attributeType"));
        criteria.addOrder(Order.asc("objectType"));

        criteria.addOrder(Order.asc("id"));
        
        @SuppressWarnings("unchecked")
        List<BatchProcessObject> batchProcessObjects = criteria.list();
        return batchProcessObjects;
	}
	
	public BatchProcessObject saveOrUpdateBatchProcessObject(final BatchProcessObject batchProcessObject) {
		if(batchProcessObject.getDateCreate() == null){
			batchProcessObject.setDateCreate(new Date());
		}
		batchProcessObject.setDateUpdate(new Date());
        if (batchProcessObject.getId() != null) {
            if(em.contains(batchProcessObject)){
                em.refresh(batchProcessObject);
            }
            BatchProcessObject mergedBatchProcessObject = em.merge(batchProcessObject);
            em.flush();
            return mergedBatchProcessObject;
        } else {
            em.persist(batchProcessObject);
            return batchProcessObject;
        }
	}

	public void deleteBatchProcessObject(final BatchProcessObject batchProcessObject) {
		em.remove(batchProcessObject);
	}

}