package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.ProductSkuAttribute;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import fr.hoteia.qalingo.core.pojo.ProductSkuAttributePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("productSkuAttributeMapper")
public class ProductSkuAttributeMapper extends AbstractAttributeDefinitionContainingPojoMapper<ProductSkuAttribute, ProductSkuAttributePojo> {

    @Override
    public Class<ProductSkuAttribute> getObjectType() {
        return ProductSkuAttribute.class;
    }

    @Override
    public Class<ProductSkuAttributePojo> getPojoType() {
        return ProductSkuAttributePojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductSkuAttributePojo jsonPojo, final ProductSkuAttribute object) {
        AttributeDefinition attributeDefinition = getAttributeDefinitionMapper().fromPojo(jsonPojo.getAttributeDefinition());
        object.setAttributeDefinition(attributeDefinition);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductSkuAttribute object, final ProductSkuAttributePojo jsonPojo) {
        AttributeDefinitionPojo attributeDefinition = getAttributeDefinitionMapper().toPojo(object.getAttributeDefinition());
        jsonPojo.setAttributeDefinition(attributeDefinition);
    }
}
