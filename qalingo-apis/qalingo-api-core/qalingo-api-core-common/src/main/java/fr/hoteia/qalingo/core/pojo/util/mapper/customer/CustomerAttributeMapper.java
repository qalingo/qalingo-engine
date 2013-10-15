package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.customer.CustomerAttributePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractAttributeDefinitionContainingPojoMapper;
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
