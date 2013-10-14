package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.pojo.CustomerMarketAreaPojo;
import fr.hoteia.qalingo.core.pojo.CustomerOptinPojo;
import fr.hoteia.qalingo.core.pojo.CustomerProductCommentPojo;
import fr.hoteia.qalingo.core.pojo.CustomerWishlistPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

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
        mapOptinsPropertyFromPojo(jsonPojo, object);
        mapWishlistProductsPropertyFromPojo(jsonPojo, object);
        mapProductCommentsPropertyFromPojo(jsonPojo, object);
    }

    private void mapOptinsPropertyFromPojo(final CustomerMarketAreaPojo jsonPojo, final CustomerMarketArea object) {
        Collection<CustomerOptin> optins = customerOptinMapper.fromPojo(jsonPojo.getOptins());
        object.setOptins(new HashSet<CustomerOptin>(optins));
    }

    private void mapWishlistProductsPropertyFromPojo(final CustomerMarketAreaPojo jsonPojo, final CustomerMarketArea object) {
        Collection<CustomerWishlist> wishlist = customerWishlistMapper.fromPojo(jsonPojo.getWishlistProducts());
        object.setWishlistProducts(new HashSet<CustomerWishlist>(wishlist));
    }

    private void mapProductCommentsPropertyFromPojo(final CustomerMarketAreaPojo jsonPojo, final CustomerMarketArea object) {
        Collection<CustomerProductComment> marketAreas = customerProductCommentMapper.fromPojo(jsonPojo.getProductComments());
        object.setProductComments(new HashSet<CustomerProductComment>(marketAreas));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final CustomerMarketArea object, final CustomerMarketAreaPojo jsonPojo) {
        mapOptinsPropertyToPojo(object, jsonPojo);
        mapWishlistProductsPropertyToPojo(object, jsonPojo);
        mapProductCommentsPropertyToPojo(object, jsonPojo);
    }

    private void mapOptinsPropertyToPojo(final CustomerMarketArea object, final CustomerMarketAreaPojo jsonPojo) {
        Collection<CustomerOptinPojo> optins = customerOptinMapper.toPojo(object.getOptins());
        jsonPojo.setOptins(optins);
    }

    private void mapWishlistProductsPropertyToPojo(final CustomerMarketArea object, final CustomerMarketAreaPojo jsonPojo) {
        Collection<CustomerWishlistPojo> wishlist = customerWishlistMapper.toPojo(object.getWishlistProducts());
        jsonPojo.setWishlistProducts(wishlist);
    }

    private void mapProductCommentsPropertyToPojo(final CustomerMarketArea object, final CustomerMarketAreaPojo jsonPojo) {
        Collection<CustomerProductCommentPojo> productComments = customerProductCommentMapper.toPojo(object.getProductComments());
        jsonPojo.setProductComments(productComments);
    }

}
