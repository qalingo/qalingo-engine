package org.hoteia.qalingo.core.pojo.customer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AbstractPojoResponse;

public class WishlistPojoResponse extends AbstractPojoResponse {

    private List<CustomerWishlistPojo> wishlists = new ArrayList<CustomerWishlistPojo>();
    
    public List<CustomerWishlistPojo> getWishlists() {
        return wishlists;
    }
    
    public void setWishlists(List<CustomerWishlistPojo> wishlists) {
        this.wishlists = wishlists;
    }
    
}
