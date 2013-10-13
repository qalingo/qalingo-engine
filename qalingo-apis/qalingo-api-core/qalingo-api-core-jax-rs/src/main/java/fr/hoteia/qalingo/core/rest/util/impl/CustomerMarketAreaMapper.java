package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.rest.pojo.CustomerMarketAreaPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerOptinPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerProductCommentPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerWishlistPojo;
import fr.hoteia.qalingo.core.rest.util.PojoMapper;

@Component("customerMarketAreaMapper")
public class CustomerMarketAreaMapper extends AbstractPojoMapper<CustomerMarketArea, CustomerMarketAreaPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "optins", "wishlistProducts", "productComments" };

    @Autowired @Qualifier("customerOptinMapper") private PojoMapper<CustomerOptin, CustomerOptinPojo> customerOptinMapper;
    @Autowired @Qualifier("customerWishlistMapper") private PojoMapper<CustomerWishlist, CustomerWishlistPojo> customerWishlistMapper;
    @Autowired @Qualifier("customerProductCommentMapper") private PojoMapper<CustomerProductComment, CustomerProductCommentPojo> customerProductCommentMapper;

    @Override
    public Class<CustomerMarketArea> getObjectType() {
        return CustomerMarketArea.class;
    }

    @Override
    public Class<CustomerMarketAreaPojo> getPojoType() {
        return CustomerMarketAreaPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CustomerMarketAreaPojo jsonPojo, final CustomerMarketArea object) {
        // TODO Auto-generated method stub
        super.mapAdditionalPropertiesFromPojo(jsonPojo, object);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CustomerMarketArea object, final CustomerMarketAreaPojo jsonPojo) {
        // TODO Auto-generated method stub
        super.mapAdditionalPropertiesToPojo(object, jsonPojo);
    }

}
