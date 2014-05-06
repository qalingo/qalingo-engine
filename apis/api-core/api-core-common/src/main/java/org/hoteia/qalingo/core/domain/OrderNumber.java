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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="TECO_ORDER_NUMBER")
public class OrderNumber extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3979521859173438793L;
	
	public static final String ORDER_STATUS_PENDING = "PENDING";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="LAST_ORDER_NUMBER")
	private Integer lastOrderNumber;

	public OrderNumber(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getLastOrderNumber() {
		return lastOrderNumber;
	}
	
	public void setLastOrderNumber(Integer lastOrderNumber) {
		this.lastOrderNumber = lastOrderNumber;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastOrderNumber == null) ? 0 : lastOrderNumber.hashCode());
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
        OrderNumber other = (OrderNumber) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastOrderNumber == null) {
            if (other.lastOrderNumber != null)
                return false;
        } else if (!lastOrderNumber.equals(other.lastOrderNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderNumber [id=" + id + ", version=" + version + ", lastOrderNumber=" + lastOrderNumber + "]";
    }

}