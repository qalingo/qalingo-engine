/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Order;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Shipping;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.web.mvc.form.AssetForm;
import org.hoteia.qalingo.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.web.mvc.form.OrderForm;
import org.hoteia.qalingo.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.web.mvc.form.RuleForm;
import org.hoteia.qalingo.web.mvc.form.ShippingForm;
import org.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
public interface FormFactory {

	CatalogCategoryForm buildCatalogCategoryForm(HttpServletRequest request) throws Exception;

	CatalogCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryMaster catalogCategory) throws Exception;

	CatalogCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryMaster parentProductCategory, CatalogCategoryMaster catalogCategory) throws Exception;

	CatalogCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryVirtual catalogCategory) throws Exception;

	CatalogCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryVirtual parentProductCategory, CatalogCategoryVirtual catalogCategory) throws Exception;

	ProductMarketingForm buildProductMarketingForm(HttpServletRequest request, ProductMarketing productMarketing) throws Exception;
	
	AssetForm buildProductMarketingAssetForm(HttpServletRequest request, Asset productMarketingAsset) throws Exception;
	
	ProductSkuForm buildProductSkuForm(HttpServletRequest request, ProductSku productSku) throws Exception;
	
	CustomerForm buildCustomerForm(HttpServletRequest request, Customer customer) throws Exception;
	
	OrderForm buildOrderForm(HttpServletRequest request, Order order) throws Exception;
	
	RuleForm buildRuleForm(HttpServletRequest request, AbstractRuleReferential promotion) throws Exception;
	
	ShippingForm buildShippingForm(HttpServletRequest request, Shipping shipping) throws Exception;
	
	RetailerForm buildRetailerForm(HttpServletRequest request, Retailer retailer) throws Exception;

	UserForm buildUserForm(HttpServletRequest request, User user) throws Exception;

}
