/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
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

    // CATALOG CATEGORY
    public List<AttributeDefinition> findCatalogCategoryAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_CATALOG_CATEGORY);
        return sortAttributes(allAttributeDefinitions);
    }

    public List<AttributeDefinition> findCatalogCategoryGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findCatalogCategoryAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findCatalogCategoryMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findCatalogCategoryAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // PRODUCT MARKETING
    public List<AttributeDefinition> findProductMarketingAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_MARKETING);
        return sortAttributes(allAttributeDefinitions);
    }

    public List<AttributeDefinition> findProductMarketingGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findProductMarketingAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findProductMarketingMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findProductMarketingAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // PRODUCT SKU
    public List<AttributeDefinition> findProductSkuAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PRODUCT_SKU);
        return sortAttributes(allAttributeDefinitions);
    }

    public List<AttributeDefinition> findProductSkuGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findProductSkuAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findProductSkuMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findProductSkuAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // CUSTOMER
    public List<AttributeDefinition> findCustomerAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_CUSTOMER);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findCustomerGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findCustomerAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findCustomerMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findCustomerAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }

    // STORE
    public List<AttributeDefinition> findStoreAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_STORE);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findStoreGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findStoreAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findStoreMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findStoreAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // RETAILER
    public List<AttributeDefinition> findRetailerAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_RETAILER);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findRetailerGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findRetailerAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }

    public List<AttributeDefinition> findRetailerMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findRetailerAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // PAYMENT GATEWAY
    public List<AttributeDefinition> findPaymentGatewayAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_PAYMENT_GATEWAY);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findPaymentGatewayGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findPaymentGatewayAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }
    
    public List<AttributeDefinition> findPaymentGatewayMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findPaymentGatewayAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }
    
    // MARKET AREA
    public List<AttributeDefinition> findMarketAreaAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_MARKET_AREA);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findMarketAreaGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findMarketAreaAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }
    
    // TAX 
    public List<AttributeDefinition> findTaxAllAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = attributeDao.findAttributeDefinitionsByObjectType(AttributeDefinition.OBJECT_TYPE_TAX);
        return sortAttributes(allAttributeDefinitions);
    }
    
    public List<AttributeDefinition> findTaxGlobalAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findTaxAllAttributeDefinitions();
        return sortAttributes(getGlobalAttributeDefinitions(allAttributeDefinitions));
    }
    
    public List<AttributeDefinition> findTaxMarketAreaAttributeDefinitions() {
        List<AttributeDefinition> allAttributeDefinitions = findTaxAllAttributeDefinitions();
        return sortAttributes(getMarketAreaAttributeDefinitions(allAttributeDefinitions));
    }

    public void saveOrUpdateAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.saveOrUpdateAttributeDefinition(attributeDefinition);
    }

    public void deleteAttributeDefinition(final AttributeDefinition attributeDefinition) {
        attributeDao.deleteAttributeDefinition(attributeDefinition);
    }
    
    protected List<AttributeDefinition> getGlobalAttributeDefinitions(final List<AttributeDefinition> allAttributeDefinitions) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        for (Iterator<AttributeDefinition> iterator = allAttributeDefinitions.iterator(); iterator.hasNext();) {
            AttributeDefinition attributeDefinition = (AttributeDefinition) iterator.next();
            if(attributeDefinition.isGlobal()){
                attributeDefinitions.add(attributeDefinition);
            }
        }
        return sortAttributes(attributeDefinitions);
    }
    
    protected List<AttributeDefinition> getMarketAreaAttributeDefinitions(final List<AttributeDefinition> allAttributeDefinitions) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        for (Iterator<AttributeDefinition> iterator = allAttributeDefinitions.iterator(); iterator.hasNext();) {
            AttributeDefinition attributeDefinition = (AttributeDefinition) iterator.next();
            if(!attributeDefinition.isGlobal()){
                attributeDefinitions.add(attributeDefinition);
            }
        }
        return sortAttributes(attributeDefinitions);
    }
    
    protected List<AttributeDefinition> sortAttributes(List<AttributeDefinition> attributeDefinitions){
        if (attributeDefinitions != null) {
            List<AttributeDefinition> sortedObjects = new LinkedList<AttributeDefinition>(attributeDefinitions);
                Collections.sort(sortedObjects, new Comparator<AttributeDefinition>() {
                    @Override
                    public int compare(AttributeDefinition o1, AttributeDefinition o2) {
                        if (o1 != null && o2 != null) {
                            int compare = o1.getOrdering().compareTo(o2.getOrdering());
                            if(compare != 0){
                                return compare;
                            }
                            return o1.getCode().compareTo(o2.getCode());
                        }
                        return 0;
                    }
                });
            return sortedObjects;
        }
        return attributeDefinitions;
    }

}