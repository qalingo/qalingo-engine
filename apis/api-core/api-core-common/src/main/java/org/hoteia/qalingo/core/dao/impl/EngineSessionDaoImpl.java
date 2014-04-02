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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SetAttribute;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("engineSessionDao")
public class EngineSessionDaoImpl extends AbstractGenericDaoImpl implements EngineSessionDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ECO SESSION
//    public static volatile SetAttribute<EngineEcoSession, Cart> carts;
//    public static volatile SetAttribute<Cart, CartItem> cartItems;
//    public static volatile SetAttribute<Cart, CurrencyReferential> cartCurrency;

    public EngineEcoSession getEngineEcoSessionById(final Long engineSessionId, Object... params) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<EngineEcoSession> criteriaQuery = builder.createQuery(EngineEcoSession.class);
        Root<EngineEcoSession> root = criteriaQuery.from( EngineEcoSession.class );
        
        criteriaQuery.where(builder.equal(root.get("id"), engineSessionId));

        EngineEcoSession engineSession = (EngineEcoSession) em.createQuery(criteriaQuery).getSingleResult();
        
        return engineSession;
    }

    public EngineEcoSession getEngineEcoSessionByEngineSessionGuid(final String engineSessionGuid, Object... params) {
      CriteriaBuilder builder = em.getCriteriaBuilder();
      CriteriaQuery<EngineEcoSession> criteriaQuery = builder.createQuery(EngineEcoSession.class);
      Root<EngineEcoSession> root = criteriaQuery.from( EngineEcoSession.class );
      
      criteriaQuery.where(builder.equal(root.get("engineSessionGuid"), engineSessionGuid));

      EngineEcoSession engineSession = (EngineEcoSession) em.createQuery(criteriaQuery).getSingleResult();
      
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

    public EngineBoSession getEngineBoSessionById(final Long engineSessionId, Object... params) {
        Criteria criteria = createDefaultCriteria(EngineBoSession.class);

        criteria.add(Restrictions.eq("id", engineSessionId));
        EngineBoSession engineSession = (EngineBoSession) criteria.uniqueResult();
        return engineSession;
    }
    
    public EngineBoSession getEngineBoSessionByEngineSessionGuid(final String engineSessionGuid, Object... params) {
        Criteria criteria = createDefaultCriteria(EngineBoSession.class);

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

}