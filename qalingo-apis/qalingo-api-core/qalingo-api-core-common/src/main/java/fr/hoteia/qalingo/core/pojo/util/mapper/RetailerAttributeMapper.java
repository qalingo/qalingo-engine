package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.RetailerAttribute;
import fr.hoteia.qalingo.core.domain.StoreAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.RetailerAttributePojo;
import fr.hoteia.qalingo.core.pojo.StoreAttributePojo;

public class RetailerAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<RetailerAttribute, RetailerAttributePojo> {
    @Override
    public Class<RetailerAttribute> getObjectType() {
        return RetailerAttribute.class;
    }

    @Override
    public Class<RetailerAttributePojo> getPojoType() {
        return RetailerAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final RetailerAttributePojo jsonPojo, final RetailerAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final RetailerAttribute object, final RetailerAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
