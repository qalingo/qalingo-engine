package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.CustomerAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("customerAttributeMapper")
public class CustomerAttributeMapper extends AbstractPojoMapper<CustomerAttribute, CustomerAttributePojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "attributeDefinition" };

    @Autowired @Qualifier("attributeDefinitionMapper") private PojoMapper<AttributeDefinition, AttributeDefinitionPojo> attributeDefinitionMapper;

    @Override
    public Class<CustomerAttribute> getObjectType() {
        return CustomerAttribute.class;
    }

    @Override
    public Class<CustomerAttributePojo> getPojoType() {
        return CustomerAttributePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CustomerAttributePojo jsonPojo, final CustomerAttribute object) {
        AttributeDefinition attributeDefinition = attributeDefinitionMapper.fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CustomerAttribute object, final CustomerAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = attributeDefinitionMapper.toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }

}
