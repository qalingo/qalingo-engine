package org.hoteia.qalingo.core.pojo.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CustomerMarketAreaPojo {

    private Long id;
    private int version;
    private String phone;
    private String fax;
    private String mobile;
    private Long marketAreaId;
    private Collection<CustomerOptinPojo> optins = new ArrayList<CustomerOptinPojo>();
    private Collection<CustomerWishlistPojo> wishlistProducts = new ArrayList<CustomerWishlistPojo>();
    private Collection<CustomerProductCommentPojo> productComments = new ArrayList<CustomerProductCommentPojo>();
    private Date dateCreate;
    private Date dateUpdate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(final Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

    public Collection<CustomerOptinPojo> getOptins() {
        return optins;
    }

    public void setOptins(final Collection<CustomerOptinPojo> optins) {
        this.optins = new ArrayList<CustomerOptinPojo>(optins);
    }

    public Collection<CustomerWishlistPojo> getWishlistProducts() {
        return wishlistProducts;
    }

    public void setWishlistProducts(final Collection<CustomerWishlistPojo> wishlistProducts) {
        this.wishlistProducts = new ArrayList<CustomerWishlistPojo>(wishlistProducts);
    }

    public Collection<CustomerProductCommentPojo> getProductComments() {
        return productComments;
    }

    public void setProductComments(final Collection<CustomerProductCommentPojo> productComments) {
        this.productComments = new ArrayList<CustomerProductCommentPojo>(productComments);
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}
