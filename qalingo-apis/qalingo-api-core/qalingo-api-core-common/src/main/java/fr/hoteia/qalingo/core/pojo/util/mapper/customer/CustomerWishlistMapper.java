package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerWishlist;
import fr.hoteia.qalingo.core.pojo.customer.CustomerWishlistPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
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
