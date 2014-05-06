/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;


public class StoreBusinessHourViewBean extends AbstractViewBean {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4198236235307654160L;

    private String closingDateStart;
    private String closingDateEnd;

    private OperationHourViewBean monday;
    private OperationHourViewBean tuesday;
    private OperationHourViewBean wednesday;
    private OperationHourViewBean thursday;
    private OperationHourViewBean friday;
    private OperationHourViewBean saturday;
    private OperationHourViewBean sunday;

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

    public OperationHourViewBean getMonday() {
        return monday;
    }

    public void setMonday(OperationHourViewBean monday) {
        this.monday = monday;
    }

    public OperationHourViewBean getTuesday() {
        return tuesday;
    }

    public void setTuesday(OperationHourViewBean tuesday) {
        this.tuesday = tuesday;
    }

    public OperationHourViewBean getWednesday() {
        return wednesday;
    }

    public void setWednesday(OperationHourViewBean wednesday) {
        this.wednesday = wednesday;
    }

    public OperationHourViewBean getThursday() {
        return thursday;
    }

    public void setThursday(OperationHourViewBean thursday) {
        this.thursday = thursday;
    }

    public OperationHourViewBean getFriday() {
        return friday;
    }

    public void setFriday(OperationHourViewBean friday) {
        this.friday = friday;
    }

    public OperationHourViewBean getSaturday() {
        return saturday;
    }

    public void setSaturday(OperationHourViewBean saturday) {
        this.saturday = saturday;
    }

    public OperationHourViewBean getSunday() {
        return sunday;
    }

    public void setSunday(OperationHourViewBean sunday) {
        this.sunday = sunday;
    }

    @Override
    public String toString() {
        return "StoreBusinessHourViewBean [closingDateStart=" + closingDateStart + ", closingDateEnd=" + closingDateEnd + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday=" + wednesday
                + ", thursday=" + thursday + ", friday=" + friday + ", saturday=" + saturday + ", sunday=" + sunday + "]";
    }

}