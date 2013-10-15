package fr.hoteia.qalingo.core.pojo.util.mapper.catalog;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryMasterAttributePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractAttributeDefinitionContainingPojoMapper;
import org.springframework.stereotype.Component;

@Component("catalogCategoryMasterAttributeMapper")
public class CatalogCategoryMasterAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<CatalogCategoryMasterAttribute, CatalogCategoryMasterAttributePojo> {

    @Override
    public Class<CatalogCategoryMasterAttribute> getObjectType() {
        return CatalogCategoryMasterAttribute.class;
    }

    @Override
    public Class<CatalogCategoryMasterAttributePojo> getPojoType() {
        return CatalogCategoryMasterAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CatalogCategoryMasterAttributePojo jsonPojo, final CatalogCategoryMasterAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CatalogCategoryMasterAttribute object, final CatalogCategoryMasterAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
