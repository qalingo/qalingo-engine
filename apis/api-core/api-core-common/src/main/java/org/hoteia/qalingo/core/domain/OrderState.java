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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TECO_ORDER_STATE")
public class OrderState extends AbstractEntity<OrderState> implements DomainEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 1602080387919993090L;

    public final static String ORDER_STATE_PENDING      = "PENDING";
    public final static String ORDER_STATE_SHIPPING     = "SHIPPING";
    public final static String ORDER_STATE_CANCELED     = "CANCELED";
    public final static String ORDER_STATE_REFUNDED     = "REFUNDED";
    public final static String ORDER_STATE_CLOSED       = "CLOSED";
    public final static String ORDER_STATE_FINALIZED       = "FINALIZED";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;

	@Column(name="STATE")
	private String state;

	@Column(name="TECHNICAL_COMMENT")
	private String technicalComment;

    @Column(name = "USER_COMMENT")
    private String userComment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public OrderState(){
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTechnicalComment() {
        return technicalComment;
    }
    
    public void setTechnicalComment(String technicalComment) {
        this.technicalComment = technicalComment;
    }
    
    public String getUserComment() {
        return userComment;
    }
    
    public void setUserComment(String userComment) {
        this.userComment = userComment;
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
        result = prime * result + ((state == null) ? 0 : state.hashCode());
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
        OrderState other = (OrderState) obj;
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
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderState [id=" + id + ", state=" + state + ", technicalComment=" + technicalComment + ", userComment=" + userComment + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }

}