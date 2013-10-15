package fr.hoteia.qalingo.core.pojo.util.mapper.catalog;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryMasterAttributePojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryVirtualAttributePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractAttributeDefinitionContainingPojoMapper;
import org.springframework.stereotype.Component;

@Component("catalogCategoryVirtualAttributeMapper")
public class CatalogCategoryVirtualAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<CatalogCategoryVirtualAttribute, CatalogCategoryVirtualAttributePojo> {

    @Override
    public Class<CatalogCategoryVirtualAttribute> getObjectType() {
        return CatalogCategoryVirtualAttribute.class;
    }

    @Override
    public Class<CatalogCategoryVirtualAttributePojo> getPojoType() {
        return CatalogCategoryVirtualAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CatalogCategoryVirtualAttributePojo jsonPojo, final CatalogCategoryVirtualAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CatalogCategoryVirtualAttribute object, final CatalogCategoryVirtualAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
