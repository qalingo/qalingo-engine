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

import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.util.CoreUtil;

public abstract class AbstractGenericDao {  

	@PersistenceContext
	protected EntityManager em;

    public Session getSession() {
        return (Session) em.getDelegate();
    }
    
    protected String handleCodeValue(String code) {
        return CoreUtil.replaceSpaceAndDash(code);
    }
    
    protected Criteria createDefaultCriteria(Class<?> entityClass) {
        return createDefaultCriteria(entityClass, null);
    }
    
    protected Criteria createDefaultCriteria(Class<?> entityClass, String alias) {
        Criteria criteria = getSession().createCriteria(entityClass, alias);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }
    
    protected Query createSqlQuery(String queryString) {
        Query query = em.createNativeQuery(queryString);
        return query;
    }
    
    protected Query createSqlQuery(Class<?> entityClass, String queryString) {
        Query query = em.createNativeQuery(queryString, entityClass);
        return query;
    }
    
    protected Query createSqlQuery(String resultSetMapping, String queryString) {
        Query query = em.createNativeQuery(queryString, resultSetMapping);
        return query;
    }
    
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params){
        if (params != null) {
            FetchPlan globalFetchPlan = new FetchPlan(new ArrayList<SpecificFetchMode>());
            for (Object param : params) {
                if (param instanceof FetchPlan) {
                    FetchPlan fetchPlan = (FetchPlan) param;
                    for (Iterator<SpecificFetchMode> iterator = fetchPlan.getFetchModes().iterator(); iterator.hasNext();) {
                        SpecificFetchMode specificFetchMode = (SpecificFetchMode) iterator.next();
                        if(!globalFetchPlan.getFetchModes().contains(specificFetchMode)){
                            globalFetchPlan.getFetchModes().add(specificFetchMode);
                        }
                    }
                }
            }
            
            if(globalFetchPlan != null && globalFetchPlan.getFetchModes() != null){
                for (Iterator<SpecificFetchMode> iterator = globalFetchPlan.getFetchModes().iterator(); iterator.hasNext();) {
                    SpecificFetchMode specificFetchMode = (SpecificFetchMode) iterator.next();
                    if(specificFetchMode.getRequiredAlias() != null){
                        // TODO : Denis : check duplicate entry are manage or not
                        criteria.createAlias(specificFetchMode.getRequiredAlias().getAssocationPath(), specificFetchMode.getRequiredAlias().getAlias(), specificFetchMode.getRequiredAlias().getJoinType());
                    }
                    criteria.setFetchMode(specificFetchMode.getAssocationPath(), specificFetchMode.getFetchMode());
                }
                return globalFetchPlan;
            }
            
        }
        return null;
    }
	
}