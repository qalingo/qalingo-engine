/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.AttributeDao;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.service.AttributeService;

@Service("attributeService")
@Transactional
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeDao attributeDao;

    public AttributeDefinition getAttributeDefinitionById(final Long attributeId, Object... params) {
        return attributeDao.getAttributeDefinitionById(attributeId, params);
    }

    public AttributeDefinition getAttributeDefinitionById(final String rawAttributeId, Object... params) {
        long attributeId = -1;
        try {
            attributeId = Long.parseLong(rawAttributeId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getAttributeDefinitionById(attributeId, params);
    }

    public AttributeDefinition getAttributeDefinitionByCode(final String code, Object... params) {
        return attributeDao.getAttributeDefinitionByCode(code, params);
    }

    public List<AttributeDefinition> findAttributeDefinitions(Object... params) {
        return attributeDao.findAttributeDefinitions(params);
    }

    public List<AttributeDefinition> findCatalogCategoryAttributeDefinitions(Object... params) {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_CATEGORY);
    }

    public List<AttributeDefinition> findProductMarketingAttributeDefinitions(Object... params) {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_MARKETING);
    }

    public List<AttributeDefinition> findProductSkuAttributeDefinitions(Object... params) {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_SKU);
    }

    public void saveOrUpdateAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.saveOrUpdateAttributeDefinition(attributeDefinition);
    }

    public void deleteAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.deleteAttributeDefinition(attributeDefinition);
    }

}