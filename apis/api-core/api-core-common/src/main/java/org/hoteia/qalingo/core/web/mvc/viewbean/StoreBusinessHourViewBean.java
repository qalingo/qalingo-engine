/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;


public class StoreBusinessHourViewBean extends AbstractViewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4198236235307654160L;
	
	private String closingDateStart;
	private String closingDateEnd;
	private String startHour;
	private String endHour;
	private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
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
	@Override
	public String toString() {
		return "StoreBusinessHourViewBean [closingDateStart="
				+ closingDateStart + ", closingDateEnd=" + closingDateEnd
				+ ", startHour=" + startHour + ", endHour=" + endHour
				+ ", monday=" + monday + ", tuesday=" + tuesday
				+ ", wednesday=" + wednesday + ", thursday=" + thursday
				+ ", friday=" + friday + ", saturday=" + saturday + ", sunday="
				+ sunday + "]";
	}
	
    
    
    
}