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

    private String startHour;
    private String endHour;

    private String dayKey;
    private String dayLabel;
    
    private String comment;

    private boolean closed;
    private boolean off;

    private Integer ranking;
    
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

    public String getDayKey() {
        return dayKey;
    }

    public void setDayKey(String dayKey) {
        this.dayKey = dayKey;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isOff() {
        return off;
    }

    public void setOff(boolean off) {
        this.off = off;
    }
    
    public Integer getRanking() {
        return ranking;
    }
    
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

}