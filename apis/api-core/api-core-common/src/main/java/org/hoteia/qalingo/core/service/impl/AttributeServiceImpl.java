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

import org.hoteia.qalingo.core.dao.AttributeDao;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("attributeService")
@Transactional
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeDao attributeDao;

    public AttributeDefinition getAttributeDefinitionById(final Long attributeId) {
        return attributeDao.getAttributeDefinitionById(attributeId);
    }

    public AttributeDefinition getAttributeDefinitionById(final String rawAttributeId) {
        long attributeId = -1;
        try {
            attributeId = Long.parseLong(rawAttributeId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getAttributeDefinitionById(attributeId);
    }

    public AttributeDefinition getAttributeDefinitionByCode(final String code) {
        return attributeDao.getAttributeDefinitionByCode(code);
    }

    public List<AttributeDefinition> findAttributeDefinitions() {
        return attributeDao.findAttributeDefinitions();
    }

    public List<AttributeDefinition> findCatalogCategoryAttributeDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_CATALOG_CATEGORY);
    }

    public List<AttributeDefinition> findProductMarketingAttributeDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_MARKETING);
    }

    public List<AttributeDefinition> findProductSkuAttributeDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_SKU);
    }

    public List<AttributeDefinition> findCustomerAttributeDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_CUSTOMER);
    }

    public List<AttributeDefinition> findStoreDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_STORE);
    }

    public List<AttributeDefinition> findPaymentGatewayDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PAYMENT_GATEWAY);
    }

    public List<AttributeDefinition> findMarketAreaAttributeDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_MARKET_AREA);
    }

    public List<AttributeDefinition> findTaxDefinitions() {
        return attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_TAX);
    }

    public void saveOrUpdateAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.saveOrUpdateAttributeDefinition(attributeDefinition);
    }

    public void deleteAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.deleteAttributeDefinition(attributeDefinition);
    }

}