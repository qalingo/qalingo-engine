package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class OperationHourViewBean extends AbstractViewBean implements	Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6884778015370439951L;
	
    private String startHour;
    private String endHour;

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
	
}
