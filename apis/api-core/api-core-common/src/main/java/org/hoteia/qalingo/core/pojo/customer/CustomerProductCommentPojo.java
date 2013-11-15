package org.hoteia.qalingo.core.pojo.customer;

import java.util.Date;

public class CustomerProductCommentPojo {

    private Long id;
    private int version;
    private int position;
    private String comment;
    private String productSkuCode;
    private Long customerMarketAreaId;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(final String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Long getCustomerMarketAreaId() {
        return customerMarketAreaId;
    }

    public void setCustomerMarketAreaId(final Long customerMarketAreaId) {
        this.customerMarketAreaId = customerMarketAreaId;
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
