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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FollowUsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7433453971883109184L;

	private String submitUrlShortForm;
	private String submitUrlFullForm;
    
	List<FollowUsOptionViewBean> followOptions = new ArrayList<FollowUsOptionViewBean>();

	public String getSubmitUrlShortForm() {
    	return submitUrlShortForm;
    }

	public void setSubmitUrlShortForm(String submitUrlShortForm) {
    	this.submitUrlShortForm = submitUrlShortForm;
    }

	public String getSubmitUrlFullForm() {
    	return submitUrlFullForm;
    }

	public void setSubmitUrlFullForm(String submitUrlFullForm) {
    	this.submitUrlFullForm = submitUrlFullForm;
    }

	public List<FollowUsOptionViewBean> getFollowOptions() {
		return followOptions;
	}

	public void setFollowOptions(List<FollowUsOptionViewBean> followOptions) {
		this.followOptions = followOptions;
	}
	
}
