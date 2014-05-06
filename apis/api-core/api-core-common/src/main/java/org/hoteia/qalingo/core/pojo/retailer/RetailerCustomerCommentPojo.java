/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.retailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;

public class RetailerCustomerCommentPojo {

    private Long id;
    private String comment;
    private Long retailerCustomerCommentId;
    private CustomerPojo customer;
    private Long retailerId;
    private Date dateCreate;
    private Date dateUpdate;

    private List<RetailerCustomerCommentPojo> customerComments = new ArrayList<RetailerCustomerCommentPojo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRetailerCustomerCommentId() {
        return retailerCustomerCommentId;
    }

    public void setRetailerCustomerCommentId(Long retailerCustomerCommentId) {
        this.retailerCustomerCommentId = retailerCustomerCommentId;
    }

    public CustomerPojo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPojo customer) {
        this.customer = customer;
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public List<RetailerCustomerCommentPojo> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(List<RetailerCustomerCommentPojo> customerComments) {
        this.customerComments = customerComments;
    }
    
}