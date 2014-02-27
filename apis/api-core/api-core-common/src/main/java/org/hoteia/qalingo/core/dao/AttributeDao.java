/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.AttributeDefinition;

public interface AttributeDao {

	AttributeDefinition getAttributeDefinitionById(Long attributeId, Object... params);

	AttributeDefinition getAttributeDefinitionByCode(String code, Object... params);
	
	List<AttributeDefinition> findAttributeDefinitions(Object... params);
	
	List<AttributeDefinition> findAttributeDefinitionsByObjectType(int objectType, Object... params);

	AttributeDefinition saveOrUpdateAttributeDefinition(AttributeDefinition attributeDefinition);

	void deleteAttributeDefinition(AttributeDefinition attributeDefinition);

}
