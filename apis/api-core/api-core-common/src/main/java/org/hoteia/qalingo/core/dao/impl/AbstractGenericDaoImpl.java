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

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public abstract class AbstractGenericDaoImpl {  

	@PersistenceContext
	protected EntityManager em;

    public Session getSession() {
        return (Session) em.getDelegate();
    }
    
    protected Criteria createDefaultCriteria(Class<?> entityClass) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }
    
    @SuppressWarnings("unchecked")
    protected void handleSpecificFetchMode(Criteria criteria, Object... params){
        if (params != null) {
            for (Object param : params) {
                if (param instanceof List) {
                    List<SpecificFetchMode> specificFetchModes = (List<SpecificFetchMode>) param;
                    for (Iterator<SpecificFetchMode> iterator = specificFetchModes.iterator(); iterator.hasNext();) {
                        SpecificFetchMode specificFetchMode = (SpecificFetchMode) iterator.next();
                        if(specificFetchMode.getRequiredAlias() != null){
                            criteria.createAlias(specificFetchMode.getRequiredAlias().getAssocationPath(), specificFetchMode.getRequiredAlias().getAlias(), specificFetchMode.getRequiredAlias().getJoinType());
                        }
                        criteria.setFetchMode(specificFetchMode.getAssocationPath(), specificFetchMode.getFetchMode());
                    }
                }
            }
        }
    }
	
}