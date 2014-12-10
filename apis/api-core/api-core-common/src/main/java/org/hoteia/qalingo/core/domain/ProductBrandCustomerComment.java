/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TECO_PRODUCT_BRAND_CUSTOMER_COMMENT")
public class ProductBrandCustomerComment extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 1924519557043858148L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
    @Column(name = "TITLE")
    private String title;
    
    @Column(name="COMMENT")
    private String comment;

	@Column(name="PRODUCT_MARKETING_CUSTOMER_COMMENT_ID")
	private Long productMarketingCustomerCommentId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name="CUSTOMER_ID")
	private Customer customer;

    @Column(name="MARKET_AREA_ID")
    private Long marketAreaId;

    @Column(name="PRODUCT_MARKETING_ID")
    private Long productMarketingId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_CUSTOMER_COMMENT_ID")
	private Set<ProductBrandCustomerComment> customerComments = new HashSet<ProductBrandCustomerComment>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public ProductBrandCustomerComment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

	public Long getProductMarketingCustomerCommentId() {
        return productMarketingCustomerCommentId;
    }
	
	public void setProductMarketingCustomerCommentId(Long productMarketingCustomerCommentId) {
        this.productMarketingCustomerCommentId = productMarketingCustomerCommentId;
    }

	public Customer getCustomer() {
    	return customer;
    }

	public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
	
	public Long getMarketAreaId() {
        return marketAreaId;
    }
	
	public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }

	public Long getProductMarketingId() {
        return productMarketingId;
    }
	
	public void setProductMarketingId(Long productMarketingId) {
        this.productMarketingId = productMarketingId;
    }
	
	public Set<ProductBrandCustomerComment> getCustomerComments() {
    	return customerComments;
    }

	public void setCustomerComments(Set<ProductBrandCustomerComment> customerComments) {
    	this.customerComments = customerComments;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductBrandCustomerComment other = (ProductBrandCustomerComment) obj;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (marketAreaId == null) {
            if (other.marketAreaId != null)
                return false;
        } else if (!marketAreaId.equals(other.marketAreaId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductMarketingCustomerComment [id=" + id + ", comment=" + comment + ", productMarketingCustomerCommentId=" + productMarketingCustomerCommentId + ", marketAreaId=" + marketAreaId
                + ", productMarketingId=" + productMarketingId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}