/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.AttributeDefinition;

public interface AttributeService {

    AttributeDefinition getAttributeDefinitionById(Long attributeId);
    
	AttributeDefinition getAttributeDefinitionById(String attributeId);
	
	AttributeDefinition getAttributeDefinitionByCode(String code);
	
	List<AttributeDefinition> findAttributeDefinitions();

    void saveOrUpdateAttributeDefinition(AttributeDefinition attributeDefinition);
    
    void deleteAttributeDefinition(AttributeDefinition attributeDefinition);

    // SPECIFIC

    List<AttributeDefinition> findCatalogCategoryAllAttributeDefinitions();
    
    List<AttributeDefinition> findCatalogCategoryGlobalAttributeDefinitions();

    List<AttributeDefinition> findCatalogCategoryMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findProductMarketingAllAttributeDefinitions();
    
    List<AttributeDefinition> findProductMarketingGlobalAttributeDefinitions();

    List<AttributeDefinition> findProductMarketingMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findProductSkuAllAttributeDefinitions();
    
    List<AttributeDefinition> findProductSkuGlobalAttributeDefinitions();

    List<AttributeDefinition> findProductSkuMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findCustomerAllAttributeDefinitions();
    
    List<AttributeDefinition> findCustomerGlobalAttributeDefinitions();

    List<AttributeDefinition> findCustomerMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findStoreAllAttributeDefinitions();
    
    List<AttributeDefinition> findStoreGlobalAttributeDefinitions();

    List<AttributeDefinition> findStoreMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findPaymentGatewayAllAttributeDefinitions();
    
    List<AttributeDefinition> findPaymentGatewayGlobalAttributeDefinitions();
    
    List<AttributeDefinition> findPaymentGatewayMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findMarketAreaAllAttributeDefinitions();
    
    List<AttributeDefinition> findMarketAreaGlobalAttributeDefinitions();
    
    List<AttributeDefinition> findTaxAllAttributeDefinitions();
    
    List<AttributeDefinition> findTaxGlobalAttributeDefinitions();

    List<AttributeDefinition> findTaxMarketAreaAttributeDefinitions();

}
