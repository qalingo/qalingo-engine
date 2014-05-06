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
import org.hoteia.qalingo.core.dao.AttributeDao;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("attributeDao")
public class AttributeDaoImpl extends AbstractGenericDaoImpl implements AttributeDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AttributeDefinition getAttributeDefinitionById(final Long attributeDefinitionId) {
        Criteria criteria = createDefaultCriteria(AttributeDefinition.class);
        criteria.add(Restrictions.eq("id", attributeDefinitionId));
        AttributeDefinition attributeDefinitions = (AttributeDefinition) criteria.uniqueResult();
        return attributeDefinitions;
    }

    public AttributeDefinition getAttributeDefinitionByCode(final String code) {
        Criteria criteria = createDefaultCriteria(AttributeDefinition.class);
        criteria.add(Restrictions.eq("code", code));
        AttributeDefinition attributeDefinition = (AttributeDefinition) criteria.uniqueResult();
        return attributeDefinition;
    }

    public List<AttributeDefinition> findAttributeDefinitions() {
        Criteria criteria = createDefaultCriteria(AttributeDefinition.class);

        criteria.addOrder(Order.asc("attributeType"));
        criteria.addOrder(Order.asc("objectType"));

        @SuppressWarnings("unchecked")
        List<AttributeDefinition> attributeDefinitions = criteria.list();
        return attributeDefinitions;
    }

    public List<AttributeDefinition> findAttributeDefinitionsByObjectType(int objectType) {
        Criteria criteria = createDefaultCriteria(AttributeDefinition.class);
        criteria.add(Restrictions.eq("objectType", objectType));

        criteria.addOrder(Order.asc("attributeType"));

        @SuppressWarnings("unchecked")
        List<AttributeDefinition> attributeDefinitions = criteria.list();
        return attributeDefinitions;
    }

    public AttributeDefinition saveOrUpdateAttributeDefinition(final AttributeDefinition attributeDefinition) {
        if (attributeDefinition.getDateCreate() == null) {
            attributeDefinition.setDateCreate(new Date());
        }
        attributeDefinition.setDateUpdate(new Date());
        if (attributeDefinition.getId() != null) {
            if(em.contains(attributeDefinition)){
                em.refresh(attributeDefinition);
            }
            AttributeDefinition mergedAttributeDefinition = em.merge(attributeDefinition);
            em.flush();
            return mergedAttributeDefinition;
        } else {
            em.persist(attributeDefinition);
            return attributeDefinition;
        }
    }

    public void deleteAttributeDefinition(final AttributeDefinition attributeDefinition) {
        em.remove(attributeDefinition);
    }

}