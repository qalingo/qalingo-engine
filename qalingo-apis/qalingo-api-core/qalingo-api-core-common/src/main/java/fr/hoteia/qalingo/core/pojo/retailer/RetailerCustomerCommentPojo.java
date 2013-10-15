package fr.hoteia.qalingo.core.pojo.retailer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import fr.hoteia.qalingo.core.domain.Customer;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asList;

public class RetailerCustomerCommentPojo {

    private Long id;
    private String comment;
    private Long retailerCustomerCommentId;
    private Customer customer;
    private Long retailerId;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<RetailerCustomerCommentPojo> customerComments = Collections.emptyList();

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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

    public Collection<RetailerCustomerCommentPojo> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Collection<RetailerCustomerCommentPojo> customerComments) {
        this.customerComments = asList(customerComments);
    }
}
