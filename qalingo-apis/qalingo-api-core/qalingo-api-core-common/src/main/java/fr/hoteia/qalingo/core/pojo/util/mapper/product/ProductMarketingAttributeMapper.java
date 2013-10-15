package fr.hoteia.qalingo.core.pojo.util.mapper.product;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.ProductMarketingAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.product.ProductMarketingAttributePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractAttributeDefinitionContainingPojoMapper;
import org.springframework.stereotype.Component;

@Component("productMarketingAttributeMapper")
public class ProductMarketingAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<ProductMarketingAttribute, ProductMarketingAttributePojo> {

    @Override
    public Class<ProductMarketingAttribute> getObjectType() {
        return ProductMarketingAttribute.class;
    }

    @Override
    public Class<ProductMarketingAttributePojo> getPojoType() {
        return ProductMarketingAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductMarketingAttributePojo jsonPojo, final ProductMarketingAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductMarketingAttribute object, final ProductMarketingAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
