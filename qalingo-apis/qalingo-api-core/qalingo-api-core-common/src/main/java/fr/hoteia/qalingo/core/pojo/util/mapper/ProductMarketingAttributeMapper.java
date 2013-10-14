package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.ProductMarketingAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("productMarketingAttributeMapper")
public class ProductMarketingAttributeMapper extends AbstractPojoMapper<ProductMarketingAttribute, ProductMarketingAttributePojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "attributeDefinition" };

    @Autowired @Qualifier("attributeDefinitionMapper") private PojoMapper<AttributeDefinition, AttributeDefinitionPojo> attributeDefinitionMapper;

    @Override
    public Class<ProductMarketingAttribute> getObjectType() {
        return ProductMarketingAttribute.class;
    }

    @Override
    public Class<ProductMarketingAttributePojo> getPojoType() {
        return ProductMarketingAttributePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductMarketingAttributePojo jsonPojo, final ProductMarketingAttribute object) {
        AttributeDefinition attributeDefinition = attributeDefinitionMapper.fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductMarketingAttribute object, final ProductMarketingAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = attributeDefinitionMapper.toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
