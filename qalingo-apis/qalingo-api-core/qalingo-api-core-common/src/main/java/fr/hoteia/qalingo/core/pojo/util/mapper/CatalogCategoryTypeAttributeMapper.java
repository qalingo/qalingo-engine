package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.CatalogCategoryTypeAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryTypeAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("catalogCategoryTypeAttributeMapper")
public class CatalogCategoryTypeAttributeMapper extends AbstractPojoMapper<CatalogCategoryTypeAttribute, CatalogCategoryTypeAttributePojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "attributeDefinition" };

    @Autowired @Qualifier("attributeDefinitionMapper") private PojoMapper<AttributeDefinition, AttributeDefinitionPojo> attributeDefinitionMapper;

    @Override
    public Class<CatalogCategoryTypeAttribute> getObjectType() {
        return CatalogCategoryTypeAttribute.class;
    }

    @Override
    public Class<CatalogCategoryTypeAttributePojo> getPojoType() {
        return CatalogCategoryTypeAttributePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CatalogCategoryTypeAttributePojo jsonPojo, final CatalogCategoryTypeAttribute object) {
        AttributeDefinition attributeDefinition = attributeDefinitionMapper.fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CatalogCategoryTypeAttribute object, final CatalogCategoryTypeAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = attributeDefinitionMapper.toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
