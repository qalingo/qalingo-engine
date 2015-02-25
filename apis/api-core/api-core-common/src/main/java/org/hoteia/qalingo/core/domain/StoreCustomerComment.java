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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TECO_STORE_CUSTOMER_COMMENT")
public class StoreCustomerComment extends AbstractEntity<StoreCustomerComment> {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 1424510337043858148L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
    @Column(name = "TITLE")
    private String title;
    
    @Column(name="COMMENT")
    private String comment;

	@Column(name="STORE_CUSTOMER_COMMENT_ID")
	private Long storeCustomerCommentId;
	
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Customer.class)
    @JoinColumn(name = "CUSTOMER_ID", insertable = true, updatable = true)
    private Customer customer;

    @Column(name="MARKET_AREA_ID")
    private Long marketAreaId;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Store.class)
    @JoinColumn(name = "STORE_ID", insertable = true, updatable = true)
    private Store store;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="STORE_CUSTOMER_COMMENT_ID")
	private Set<StoreCustomerComment> customerComments = new HashSet<StoreCustomerComment>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public StoreCustomerComment() {
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

	public Long getStoreCustomerCommentId() {
    	return storeCustomerCommentId;
    }

	public void setRetailerCustomerCommentId(Long storeCustomerCommentId) {
    	this.storeCustomerCommentId = storeCustomerCommentId;
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

	public Store getStore() {
        return store;
    }
	
	public void setStore(Store store) {
        this.store = store;
    }

	public Set<StoreCustomerComment> getCustomerComments() {
    	return customerComments;
    }

	public void setCustomerComments(Set<StoreCustomerComment> customerComments) {
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
        StoreCustomerComment other = (StoreCustomerComment) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "StoreCustomerComment [id=" + id + ", comment=" + comment + ", storeCustomerCommentId=" + storeCustomerCommentId + ", dateCreate=" + dateCreate
                + ", dateUpdate=" + dateUpdate + "]";
    }

}