package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
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
public class CustomerOrderAudit implements Serializable {

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

}