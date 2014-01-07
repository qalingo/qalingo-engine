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
        result = prime * result + ((calendarYearOrderAmountAudit == null) ? 0 : calendarYearOrderAmountAudit.hashCode());
        result = prime * result + ((calendarYearProductAudit == null) ? 0 : calendarYearProductAudit.hashCode());
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((dayOrderAmountAudit == null) ? 0 : dayOrderAmountAudit.hashCode());
        result = prime * result + ((dayProductAudit == null) ? 0 : dayProductAudit.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastOrderDate == null) ? 0 : lastOrderDate.hashCode());
        result = prime * result + ((monthOrderAmountAudit == null) ? 0 : monthOrderAmountAudit.hashCode());
        result = prime * result + ((monthProductAudit == null) ? 0 : monthProductAudit.hashCode());
        result = prime * result + ((specificYearOrderAmountAudit == null) ? 0 : specificYearOrderAmountAudit.hashCode());
        result = prime * result + ((specificYearProductAudit == null) ? 0 : specificYearProductAudit.hashCode());
        result = prime * result + ((weekOrderAmountAudit == null) ? 0 : weekOrderAmountAudit.hashCode());
        result = prime * result + ((weekProductAudit == null) ? 0 : weekProductAudit.hashCode());
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
        if (calendarYearOrderAmountAudit == null) {
            if (other.calendarYearOrderAmountAudit != null)
                return false;
        } else if (!calendarYearOrderAmountAudit.equals(other.calendarYearOrderAmountAudit))
            return false;
        if (calendarYearProductAudit == null) {
            if (other.calendarYearProductAudit != null)
                return false;
        } else if (!calendarYearProductAudit.equals(other.calendarYearProductAudit))
            return false;
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
        if (dayOrderAmountAudit == null) {
            if (other.dayOrderAmountAudit != null)
                return false;
        } else if (!dayOrderAmountAudit.equals(other.dayOrderAmountAudit))
            return false;
        if (dayProductAudit == null) {
            if (other.dayProductAudit != null)
                return false;
        } else if (!dayProductAudit.equals(other.dayProductAudit))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastOrderDate == null) {
            if (other.lastOrderDate != null)
                return false;
        } else if (!lastOrderDate.equals(other.lastOrderDate))
            return false;
        if (monthOrderAmountAudit == null) {
            if (other.monthOrderAmountAudit != null)
                return false;
        } else if (!monthOrderAmountAudit.equals(other.monthOrderAmountAudit))
            return false;
        if (monthProductAudit == null) {
            if (other.monthProductAudit != null)
                return false;
        } else if (!monthProductAudit.equals(other.monthProductAudit))
            return false;
        if (specificYearOrderAmountAudit == null) {
            if (other.specificYearOrderAmountAudit != null)
                return false;
        } else if (!specificYearOrderAmountAudit.equals(other.specificYearOrderAmountAudit))
            return false;
        if (specificYearProductAudit == null) {
            if (other.specificYearProductAudit != null)
                return false;
        } else if (!specificYearProductAudit.equals(other.specificYearProductAudit))
            return false;
        if (weekOrderAmountAudit == null) {
            if (other.weekOrderAmountAudit != null)
                return false;
        } else if (!weekOrderAmountAudit.equals(other.weekOrderAmountAudit))
            return false;
        if (weekProductAudit == null) {
            if (other.weekProductAudit != null)
                return false;
        } else if (!weekProductAudit.equals(other.weekProductAudit))
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