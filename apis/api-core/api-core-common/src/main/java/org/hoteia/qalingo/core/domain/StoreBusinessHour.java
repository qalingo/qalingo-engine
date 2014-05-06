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
@Table(name = "TECO_STORE_BUSINESS_HOUR")
public class StoreBusinessHour extends AbstractAddress {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 1719908138165530372L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "MONDAY")
    private boolean monday;

    @Column(name = "TUESDAY")
    private boolean tuesday;

    @Column(name = "WEDNESDAY")
    private boolean wednesday;

    @Column(name = "THURSDAY")
    private boolean thursday;

    @Column(name = "FRIDAY")
    private boolean friday;

    @Column(name = "SATURDAY")
    private boolean saturday;

    @Column(name = "SUNDAY")
    private boolean sunday;

    @Column(name = "START_HOUR")
    private String startHour;

    @Column(name = "END_HOUR")
    private String endHour;
    
    @Column(name = "CLOSING_DATE_START")
    private String closingDateStart;

    @Column(name = "CLOSING_DATE_END")
    private String closingDateEnd;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public StoreBusinessHour() {
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

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getClosingDateStart() {
        return closingDateStart;
    }

    public void setClosingDateStart(String closingDateStart) {
        this.closingDateStart = closingDateStart;
    }

    public String getClosingDateEnd() {
        return closingDateEnd;
    }

    public void setClosingDateEnd(String closingDateEnd) {
        this.closingDateEnd = closingDateEnd;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
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
        StoreBusinessHour other = (StoreBusinessHour) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (storeId == null) {
            if (other.storeId != null)
                return false;
        } else if (!storeId.equals(other.storeId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StoreBusinessHour [id=" + id + ", version=" + version + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday
                + ", saturday=" + saturday + ", sunday=" + sunday + ", startHour=" + startHour + ", endHour=" + endHour + ", closingDateStart=" + closingDateStart + ", closingDateEnd="
                + closingDateEnd + ", storeId=" + storeId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}