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

public class QuickSearchViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6712030542227057407L;
	
	private String textLabel;
	private String urlFormSubmit;
	private String labelSubmit;

	private String urlEngineSettingFormSubmit;
	private String urlUserFormSubmit;
	private String urlBatchFormSubmit;

	public String getTextLabel() {
		return textLabel;
	}
	
	public void setTextLabel(String textLabel) {
		this.textLabel = textLabel;
	}
	
	public String getUrlFormSubmit() {
		return urlFormSubmit;
	}
	
	public void setUrlFormSubmit(String urlFormSubmit) {
		this.urlFormSubmit = urlFormSubmit;
	}
	
	public String getLabelSubmit() {
		return labelSubmit;
	}
	
	public void setLabelSubmit(String labelSubmit) {
		this.labelSubmit = labelSubmit;
	}

	public String getUrlEngineSettingFormSubmit() {
		return urlEngineSettingFormSubmit;
	}

	public void setUrlEngineSettingFormSubmit(String urlEngineSettingFormSubmit) {
		this.urlEngineSettingFormSubmit = urlEngineSettingFormSubmit;
	}

	public String getUrlUserFormSubmit() {
		return urlUserFormSubmit;
	}

	public void setUrlUserFormSubmit(String urlUserFormSubmit) {
		this.urlUserFormSubmit = urlUserFormSubmit;
	}

	public String getUrlBatchFormSubmit() {
		return urlBatchFormSubmit;
	}

	public void setUrlBatchFormSubmit(String urlBatchFormSubmit) {
		this.urlBatchFormSubmit = urlBatchFormSubmit;
	}
	
}