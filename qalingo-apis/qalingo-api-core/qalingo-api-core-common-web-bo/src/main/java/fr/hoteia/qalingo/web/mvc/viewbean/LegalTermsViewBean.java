/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class LegalTermsViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4770655450241546076L;
	
	protected String warning;
	protected String copyright;
	
	public String getWarning() {
		return warning;
	}
	
	public void setWarning(String warning) {
		this.warning = warning;
	}
	
	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
}