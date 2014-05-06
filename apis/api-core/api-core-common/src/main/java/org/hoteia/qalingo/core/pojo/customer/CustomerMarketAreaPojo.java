/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CustomerMarketAreaPojo {

    private Long id;
    private int version;
    private String phone;
    private String fax;
    private String mobile;
    private Long marketAreaId;
    private List<CustomerOptinPojo> optins = new ArrayList<CustomerOptinPojo>();
    private List<CustomerWishlistPojo> wishlistProducts = new ArrayList<CustomerWishlistPojo>();
    private List<CustomerProductCommentPojo> productComments = new ArrayList<CustomerProductCommentPojo>();
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

    public List<CustomerOptinPojo> getOptins() {
        return optins;
    }

    public void setOptins(final List<CustomerOptinPojo> optins) {
        this.optins = optins;
    }

    public List<CustomerWishlistPojo> getWishlistProducts() {
        return wishlistProducts;
    }

    public void setWishlistProducts(final List<CustomerWishlistPojo> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }

    public List<CustomerProductCommentPojo> getProductComments() {
        return productComments;
    }

    public void setProductComments(final List<CustomerProductCommentPojo> productComments) {
        this.productComments = productComments;
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