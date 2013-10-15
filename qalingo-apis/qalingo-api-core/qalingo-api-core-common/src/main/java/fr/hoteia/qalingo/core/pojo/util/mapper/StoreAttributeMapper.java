package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryTypeAttribute;
import fr.hoteia.qalingo.core.domain.StoreAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryTypeAttributePojo;
import fr.hoteia.qalingo.core.pojo.StoreAttributePojo;
import org.springframework.stereotype.Component;

@Component("storeAttributeMapper")
public class StoreAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<StoreAttribute, StoreAttributePojo> {
    @Override
    public Class<StoreAttribute> getObjectType() {
        return StoreAttribute.class;
    }

    @Override
    public Class<StoreAttributePojo> getPojoType() {
        return StoreAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final StoreAttributePojo jsonPojo, final StoreAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final StoreAttribute object, final StoreAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
