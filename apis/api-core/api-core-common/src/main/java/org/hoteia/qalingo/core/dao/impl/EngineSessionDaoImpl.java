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
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("engineSessionDao")
public class EngineSessionDaoImpl extends AbstractGenericDaoImpl implements EngineSessionDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ECO SESSION

    public EngineEcoSession getEngineEcoSessionById(final Long engineSessionId) {
        Criteria criteria = createDefaultCriteria(EngineEcoSession.class);

        addDefaultEngineEcoSessionFetch(criteria);

        criteria.add(Restrictions.eq("id", engineSessionId));
        EngineEcoSession engineSession = (EngineEcoSession) criteria.uniqueResult();
        
        return engineSession;
    }

    public EngineEcoSession getEngineEcoSessionByEngineSessionGuid(final String engineSessionGuid) {
        Criteria criteria = createDefaultCriteria(EngineEcoSession.class);

        addDefaultEngineEcoSessionFetch(criteria);

        criteria.add(Restrictions.eq("engineSessionGuid", engineSessionGuid));
        EngineEcoSession engineSession = (EngineEcoSession) criteria.uniqueResult();
        
        return engineSession;
    }

    public EngineEcoSession saveOrUpdateEngineEcoSession(EngineEcoSession engineSession) {
        if (engineSession.getEngineSessionGuid() == null) {
            engineSession.setEngineSessionGuid(UUID.randomUUID().toString());
        }
        if (engineSession.getDateCreate() == null) {
            engineSession.setDateCreate(new Date());
        }
        engineSession.setDateUpdate(new Date());
        if (engineSession.getId() != null) {
            if(em.contains(engineSession)){
                em.refresh(engineSession);
            }
            EngineEcoSession mergedEngineEcoSession = em.merge(engineSession);
            em.flush();
            return mergedEngineEcoSession;
        } else {
            em.persist(engineSession);
            return engineSession;
        }
    }

    public void deleteEngineEcoSession(EngineEcoSession engineSession) {
        em.remove(em.contains(engineSession) ? engineSession : em.merge(engineSession));
    }

    // BO SESSION

    public EngineBoSession getEngineBoSessionById(final Long engineSessionId) {
        Criteria criteria = createDefaultCriteria(EngineBoSession.class);

        addDefaultEngineBoSessionFetch(criteria);

        criteria.add(Restrictions.eq("id", engineSessionId));
        EngineBoSession engineSession = (EngineBoSession) criteria.uniqueResult();
        return engineSession;
    }
    
    public EngineBoSession getEngineBoSessionByEngineSessionGuid(final String engineSessionGuid) {
        Criteria criteria = createDefaultCriteria(EngineBoSession.class);

        addDefaultEngineBoSessionFetch(criteria);

        criteria.add(Restrictions.eq("engineSessionGuid", engineSessionGuid));
        EngineBoSession engineSession = (EngineBoSession) criteria.uniqueResult();
        return engineSession;
    }

    public EngineBoSession saveOrUpdateEngineBoSession(EngineBoSession engineSession) {
        if (engineSession.getEngineSessionGuid() == null) {
            engineSession.setEngineSessionGuid(UUID.randomUUID().toString());
        }
        if (engineSession.getDateCreate() == null) {
            engineSession.setDateCreate(new Date());
        }
        engineSession.setDateUpdate(new Date());
        return em.merge(engineSession);
    }

    public void deleteEngineBoSession(EngineBoSession engineSession) {
        em.remove(engineSession);
    }

    private void addDefaultEngineBoSessionFetch(Criteria criteria) {
    }

    private void addDefaultEngineEcoSessionFetch(Criteria criteria) {
        criteria.setFetchMode("carts", FetchMode.JOIN);
        
        criteria.createAlias("carts.cartItems", "cartItems", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("cartItems", FetchMode.JOIN);
        
        criteria.createAlias("carts.currency", "currency", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("currency", FetchMode.JOIN);
    }

}