package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.ProductMarketingAttributePojo;
import fr.hoteia.qalingo.core.pojo.ProductSkuAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("productSkuAttributeMapper")
public class ProductSkuAttributeMapper extends AbstractPojoMapper<ProductSkuAttribute, ProductSkuAttributePojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "attributeDefinition" };

    @Autowired @Qualifier("attributeDefinitionMapper") private PojoMapper<AttributeDefinition, AttributeDefinitionPojo> attributeDefinitionMapper;

    @Override
    public Class<ProductSkuAttribute> getObjectType() {
        return ProductSkuAttribute.class;
    }

    @Override
    public Class<ProductSkuAttributePojo> getPojoType() {
        return ProductSkuAttributePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductSkuAttributePojo jsonPojo, final ProductSkuAttribute object) {
        AttributeDefinition attributeDefinition = attributeDefinitionMapper.fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductSkuAttribute object, final ProductSkuAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = attributeDefinitionMapper.toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
