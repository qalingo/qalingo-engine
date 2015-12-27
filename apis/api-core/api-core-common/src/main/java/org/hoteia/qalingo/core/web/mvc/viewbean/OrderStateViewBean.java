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

public class OrderStateViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5892416934330014330L;

    private String state;
    private String stateLabel;
    private String technicalComment;
    private String userComment;

	public OrderStateViewBean() {
	}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getStateLabel() {
        return stateLabel;
    }
    
    public void setStateLabel(String stateLabel) {
        this.stateLabel = stateLabel;
    }

    public String getTechnicalComment() {
        return technicalComment;
    }

    public void setTechnicalComment(String technicalComment) {
        this.technicalComment = technicalComment;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

}