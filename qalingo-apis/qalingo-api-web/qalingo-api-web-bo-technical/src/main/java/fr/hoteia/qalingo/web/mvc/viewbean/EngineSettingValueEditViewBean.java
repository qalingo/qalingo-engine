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

public class EngineSettingValueEditViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5810747930459888841L;
	
	private String formSubmitUrl;
	private String submitLabel;

	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}
	
	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}
	
	public String getSubmitLabel() {
		return submitLabel;
	}
	
	public void setSubmitLabel(String submitLabel) {
		this.submitLabel = submitLabel;
	}

}