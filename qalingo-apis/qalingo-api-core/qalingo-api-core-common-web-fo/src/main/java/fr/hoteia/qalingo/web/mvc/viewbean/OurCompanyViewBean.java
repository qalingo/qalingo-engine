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

public class OurCompanyViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -2683788708377450252L;
	
	protected String pageTitle;
	protected String textHtml;

	protected String warning;
	
	public String getPageTitle() {
		return pageTitle;
	}
	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public String getTextHtml() {
		return textHtml;
	}
	
	public void setTextHtml(String textHtml) {
		this.textHtml = textHtml;
	}
	
	public String getWarning() {
		return warning;
	}
	
	public void setWarning(String warning) {
		this.warning = warning;
	}
	
}
