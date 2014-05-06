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

public class ShoppingCartDetailsViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8746622591612218802L;

	String totalItems;

	String totalAmountWithTaxes;

	String loginUrl;

	String updateCartUrl;
	String previousCheckoutUrl;
	String detailsUrl;
	String authUrl;
	String paymentUrl;
	String confirmationUrl;
	
	List<ProductMarketingViewBean> productDetailsList = new ArrayList<ProductMarketingViewBean>();

	public String getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}

	public String getTotalAmountWithTaxes() {
		return totalAmountWithTaxes;
	}

	public void setTotalAmountWithTaxes(String totalAmountWithTaxes) {
		this.totalAmountWithTaxes = totalAmountWithTaxes;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	public String getUpdateCartUrl() {
		return updateCartUrl;
	}

	public void setUpdateCartUrl(String updateCartUrl) {
		this.updateCartUrl = updateCartUrl;
	}
	
	public String getPreviousCheckoutUrl() {
		return previousCheckoutUrl;
	}

	public void setPreviousCheckoutUrl(String previousCheckoutUrl) {
		this.previousCheckoutUrl = previousCheckoutUrl;
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	
	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	
	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	
	public String getConfirmationUrl() {
		return confirmationUrl;
	}

	public void setConfirmationUrl(String confirmationUrl) {
		this.confirmationUrl = confirmationUrl;
	}

	public List<ProductMarketingViewBean> getProductDetailsList() {
		return productDetailsList;
	}

	public void setProductDetailsList(List<ProductMarketingViewBean> productDetailsList) {
		this.productDetailsList = productDetailsList;
	}
	
}
