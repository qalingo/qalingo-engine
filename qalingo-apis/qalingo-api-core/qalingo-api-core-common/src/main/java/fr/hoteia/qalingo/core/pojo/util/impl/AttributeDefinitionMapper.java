package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import org.springframework.stereotype.Component;

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
