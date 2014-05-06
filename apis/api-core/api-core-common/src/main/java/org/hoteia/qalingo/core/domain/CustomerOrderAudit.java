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
import javax.persistence.Version;

@Entity
@Table(name="TECO_CUSTOMER_ORDER_AUDIT")
public class CustomerOrderAudit extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -1712837783699928204L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Column(name="LAST_ORDER_DATE")
	private Date lastOrderDate;

	@Column(name="DAY_PRODUCT_AUDIT")
	private String dayProductAudit;
	
	@Column(name="WEEK_PRODUCT_AUDIT")
	private String weekProductAudit;
	
	@Column(name="MONTH_PRODUCT_AUDIT")
	private String monthProductAudit;
	
	@Column(name="CALENDAR_YEAR_PRODUCT_AUDIT")
	private String calendarYearProductAudit;

	@Column(name="SPECIFIC_YEAR_PRODUCT_AUDIT")
	private String specificYearProductAudit;
	
	@Column(name="DAY_ORDER_AMOUNT_AUDIT")
	private String dayOrderAmountAudit;
	
	@Column(name="WEEK_ORDER_AMOUNT_AUDIT")
	private String weekOrderAmountAudit;
	
	@Column(name="MONTH_ORDER_AMOUNT_AUDIT")
	private String monthOrderAmountAudit;
	
	@Column(name="CALENDAR_YEAR_ORDER_AMOUNT_AUDIT")
	private String calendarYearOrderAmountAudit;

	@Column(name="SPECIFIC_YEAR_ORDER_AMOUNT_AUDIT")
	private String specificYearOrderAmountAudit;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CustomerOrderAudit(){
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

	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Date getLastOrderDate() {
	    return lastOrderDate;
    }
	
	public void setLastOrderDate(Date lastOrderDate) {
	    this.lastOrderDate = lastOrderDate;
    }

	public String getDayProductAudit() {
    	return dayProductAudit;
    }

	public void setDayProductAudit(String dayProductAudit) {
    	this.dayProductAudit = dayProductAudit;
    }

	public String getWeekProductAudit() {
    	return weekProductAudit;
    }

	public void setWeekProductAudit(String weekProductAudit) {
    	this.weekProductAudit = weekProductAudit;
    }

	public String getMonthProductAudit() {
    	return monthProductAudit;
    }

	public void setMonthProductAudit(String monthProductAudit) {
    	this.monthProductAudit = monthProductAudit;
    }

	public String getCalendarYearProductAudit() {
    	return calendarYearProductAudit;
    }

	public void setCalendarYearProductAudit(String calendarYearProductAudit) {
    	this.calendarYearProductAudit = calendarYearProductAudit;
    }

	public String getSpecificYearProductAudit() {
    	return specificYearProductAudit;
    }

	public void setSpecificYearProductAudit(String specificYearProductAudit) {
    	this.specificYearProductAudit = specificYearProductAudit;
    }

	public String getDayOrderAmountAudit() {
    	return dayOrderAmountAudit;
    }

	public void setDayOrderAmountAudit(String dayOrderAmountAudit) {
    	this.dayOrderAmountAudit = dayOrderAmountAudit;
    }

	public String getWeekOrderAmountAudit() {
    	return weekOrderAmountAudit;
    }

	public void setWeekOrderAmountAudit(String weekOrderAmountAudit) {
    	this.weekOrderAmountAudit = weekOrderAmountAudit;
    }

	public String getMonthOrderAmountAudit() {
    	return monthOrderAmountAudit;
    }

	public void setMonthOrderAmountAudit(String monthOrderAmountAudit) {
    	this.monthOrderAmountAudit = monthOrderAmountAudit;
    }

	public String getCalendarYearOrderAmountAudit() {
    	return calendarYearOrderAmountAudit;
    }

	public void setCalendarYearOrderAmountAudit(String calendarYearOrderAmountAudit) {
    	this.calendarYearOrderAmountAudit = calendarYearOrderAmountAudit;
    }

	public String getSpecificYearOrderAmountAudit() {
    	return specificYearOrderAmountAudit;
    }

	public void setSpecificYearOrderAmountAudit(String specificYearOrderAmountAudit) {
    	this.specificYearOrderAmountAudit = specificYearOrderAmountAudit;
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
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        CustomerOrderAudit other = (CustomerOrderAudit) obj;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
            return false;
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
        return "CustomerOrderAudit [id=" + id + ", version=" + version + ", customerId=" + customerId + ", lastOrderDate=" + lastOrderDate + ", dayProductAudit=" + dayProductAudit
                + ", weekProductAudit=" + weekProductAudit + ", monthProductAudit=" + monthProductAudit + ", calendarYearProductAudit=" + calendarYearProductAudit + ", specificYearProductAudit="
                + specificYearProductAudit + ", dayOrderAmountAudit=" + dayOrderAmountAudit + ", weekOrderAmountAudit=" + weekOrderAmountAudit + ", monthOrderAmountAudit=" + monthOrderAmountAudit
                + ", calendarYearOrderAmountAudit=" + calendarYearOrderAmountAudit + ", specificYearOrderAmountAudit=" + specificYearOrderAmountAudit + ", dateCreate=" + dateCreate + ", dateUpdate="
                + dateUpdate + "]";
    }

}