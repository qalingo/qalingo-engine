/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class SearchViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6712030542227057407L;
	
	private String textLabel;
    
	public String getTextLabel() {
		return textLabel;
	}
	
	public void setTextLabel(String textLabel) {
		this.textLabel = textLabel;
	}
	
}