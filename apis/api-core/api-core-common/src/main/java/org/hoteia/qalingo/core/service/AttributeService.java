/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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

    List<AttributeDefinition> findMarketAreaAttributeDefinitions();

    void saveOrUpdateAttributeDefinition(AttributeDefinition attributeDefinition);
    
    void deleteAttributeDefinition(AttributeDefinition attributeDefinition);

    // SPECIFIC
    
	List<AttributeDefinition> findCatalogCategoryAttributeDefinitions();

    List<AttributeDefinition> findProductMarketingAttributeDefinitions();

	List<AttributeDefinition> findProductSkuAttributeDefinitions();

	List<AttributeDefinition> findCustomerAttributeDefinitions();

    List<AttributeDefinition> findStoreDefinitions();

    List<AttributeDefinition> findPaymentGatewayGlobalAttributeDefinitions();
    
    List<AttributeDefinition> findPaymentGatewayMarketAreaAttributeDefinitions();

    List<AttributeDefinition> findTaxDefinitions();

}
