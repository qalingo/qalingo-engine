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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.AttributeDao;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;

@Transactional
@Repository("attributeDao")
public class AttributeDaoImpl extends AbstractGenericDaoImpl implements AttributeDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public AttributeDefinition getAttributeDefinitionById(Long attributeDefinitionId) {
		return em.find(AttributeDefinition.class, attributeDefinitionId);
	}

	public AttributeDefinition getAttributeDefinitionByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AttributeDefinition WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		AttributeDefinition attributeDefinition = (AttributeDefinition) query.uniqueResult();
		return attributeDefinition;
	}
	
	public List<AttributeDefinition> findAttributeDefinitions() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AttributeDefinition ORDER BY attributeType, objectType";
		Query query = session.createQuery(sql);
		List<AttributeDefinition> attributeDefinitions = (List<AttributeDefinition>) query.list();
		return attributeDefinitions;
	}
	
	public List<AttributeDefinition> findAttributeDefinitionsByObjectType(int objectType) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AttributeDefinition WHERE objectType = :objectType ORDER BY attributeType";
		Query query = session.createQuery(sql);
		query.setInteger("objectType", objectType);
		List<AttributeDefinition> attributeDefinitions = (List<AttributeDefinition>) query.list();
		return attributeDefinitions;
	}
	
	public void saveOrUpdateAttributeDefinition(AttributeDefinition attributeDefinition) {
		if(attributeDefinition.getId() == null){
			em.persist(attributeDefinition);
		} else {
			em.merge(attributeDefinition);
		}
	}

	public void deleteAttributeDefinition(AttributeDefinition attributeDefinition) {
		em.remove(attributeDefinition);
	}

}
