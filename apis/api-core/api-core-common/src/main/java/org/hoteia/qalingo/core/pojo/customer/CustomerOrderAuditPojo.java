/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.customer;

import java.util.Date;

public class CustomerOrderAuditPojo {

    private Long id;
    private int version;
    private Long customerId;
    private String dayProductAudit;
    private String weekProductAudit;
    private String monthProductAudit;
    private String calendarYearProductAudit;
    private String specificYearProductAudit;
    private String dayOrderAmountAudit;
    private String weekOrderAmountAudit;
    private String monthOrderAmountAudit;
    private String calendarYearOrderAmountAudit;
    private String specificYearOrderAmountAudit;
    private Date dateCreate;
    private Date dateUpdate;

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