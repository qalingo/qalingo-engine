package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.pojo.CustomerWishlistPojo;
import org.springframework.stereotype.Component;

@Component("customerWishlistMapper")
public class CustomerWishlistMapper extends AbstractPojoMapper<CustomerWishlist, CustomerWishlistPojo> {

    @Override
    public Class<CustomerWishlist> getObjectType() {
        return CustomerWishlist.class;
    }

    @Override
    public Class<CustomerWishlistPojo> getPojoType() {
        return CustomerWishlistPojo.class;
    }

}
