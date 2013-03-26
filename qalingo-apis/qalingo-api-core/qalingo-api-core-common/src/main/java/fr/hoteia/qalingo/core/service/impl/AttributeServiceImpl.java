/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.AttributeDao;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.service.AttributeService;

@Service("attributeService")
@Transactional
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private AttributeDao attributeDao;

	public AttributeDefinition getAttributeDefinitionById(String rawAttributeId) {
		long AttributeId = -1;
		try {
			AttributeId = Long.parseLong(rawAttributeId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return attributeDao.getAttributeDefinitionById(AttributeId);
	}
	
	public AttributeDefinition getAttributeDefinitionByCode(final String code) {
		return attributeDao.getAttributeDefinitionByCode(code);
	}

	public List<AttributeDefinition> findAttributeDefinitions() {
		return attributeDao.findAttributeDefinitions();
	}
	
	public List<AttributeDefinition> findCatalogCategoryAttributeDefinitions() {
		return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_CATEGORY);
	}
	
	public List<AttributeDefinition> findProductMarketingAttributeDefinitions() {
		return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_MARKETING);
	}
	
	public List<AttributeDefinition> findProductSkuAttributeDefinitions() {
		return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_SKU);
	}

	public void saveOrUpdateAttributeDefinition(AttributeDefinition attributeDefinition) {
		attributeDao.saveOrUpdateAttributeDefinition(attributeDefinition);
	}

	public void deleteAttributeDefinition(AttributeDefinition attributeDefinition) {
		attributeDao.deleteAttributeDefinition(attributeDefinition);
	}

}
