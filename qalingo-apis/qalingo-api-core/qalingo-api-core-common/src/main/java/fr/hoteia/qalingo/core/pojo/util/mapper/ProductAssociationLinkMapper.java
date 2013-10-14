package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.ProductAssociationLink;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.pojo.ProductAssociationLinkPojo;
import fr.hoteia.qalingo.core.pojo.ProductMarketingPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("productAssociationLinkMapper")
public class ProductAssociationLinkMapper extends AbstractPojoMapper<ProductAssociationLink, ProductAssociationLinkPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "productMarketing" };

    @Autowired @Qualifier("productMarketingMapper") private PojoMapper<ProductMarketing, ProductMarketingPojo> productMarketingMapper;

    @Override
    public Class<ProductAssociationLink> getObjectType() {
        return ProductAssociationLink.class;
    }

    @Override
    public Class<ProductAssociationLinkPojo> getPojoType() {
        return ProductAssociationLinkPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final ProductAssociationLinkPojo jsonPojo, final ProductAssociationLink object) {
        ProductMarketing productMarketing = productMarketingMapper.fromPojo(jsonPojo.getProductMarketing());
        object.setProductMarketing(productMarketing);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final ProductAssociationLink object, final ProductAssociationLinkPojo jsonPojo) {
        ProductMarketingPojo productMarketingPojo = productMarketingMapper.toPojo(object.getProductMarketing());
        jsonPojo.setProductMarketing(productMarketingPojo);
    }
}
