package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.CustomerAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("customerAttributeMapper")
public class CustomerAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<CustomerAttribute, CustomerAttributePojo> {

    @Override
    public Class<CustomerAttribute> getObjectType() {
        return CustomerAttribute.class;
    }

    @Override
    public Class<CustomerAttributePojo> getPojoType() {
        return CustomerAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CustomerAttributePojo jsonPojo, final CustomerAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CustomerAttribute object, final CustomerAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }

}
