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

public class BrandViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 1947200664388789049L;
	
	protected String businessName;
	protected String code;
	protected String brandDetailsUrl;
	protected String brandLineDetailsUrl;

	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getBrandDetailsUrl() {
		return brandDetailsUrl;
	}

	public void setBrandDetailsUrl(String brandDetailsUrl) {
		this.brandDetailsUrl = brandDetailsUrl;
	}
	
	public String getBrandLineDetailsUrl() {
		return brandLineDetailsUrl;
	}

	public void setBrandLineDetailsUrl(String brandLineDetailsUrl) {
		this.brandLineDetailsUrl = brandLineDetailsUrl;
	}
}
