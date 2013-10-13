package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.rest.pojo.AttributeDefinitionPojo;

@Component("attributeDefinitionMapper")
public class AttributeDefinitionMapper extends AbstractPojoMapper<AttributeDefinition, AttributeDefinitionPojo> {

    @Override
    public Class<AttributeDefinition> getObjectType() {
        return AttributeDefinition.class;
    }

    @Override
    public Class<AttributeDefinitionPojo> getPojoType() {
        return AttributeDefinitionPojo.class;
    }

}
