package fr.hoteia.qalingo.core.pojo.util.mapper.catalog;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryTypeAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryTypeAttributePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractAttributeDefinitionContainingPojoMapper;
import org.springframework.stereotype.Component;

@Component("catalogCategoryTypeAttributeMapper")
public class CatalogCategoryTypeAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<CatalogCategoryTypeAttribute, CatalogCategoryTypeAttributePojo> {

    @Override
    public Class<CatalogCategoryTypeAttribute> getObjectType() {
        return CatalogCategoryTypeAttribute.class;
    }

    @Override
    public Class<CatalogCategoryTypeAttributePojo> getPojoType() {
        return CatalogCategoryTypeAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CatalogCategoryTypeAttributePojo jsonPojo, final CatalogCategoryTypeAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CatalogCategoryTypeAttribute object, final CatalogCategoryTypeAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
