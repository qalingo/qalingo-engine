/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.AbstractRuleReferential;
import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.Shipping;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.web.mvc.form.AssetForm;
import fr.hoteia.qalingo.web.mvc.form.CustomerForm;
import fr.hoteia.qalingo.web.mvc.form.OrderForm;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.RetailerForm;
import fr.hoteia.qalingo.web.mvc.form.RuleForm;
import fr.hoteia.qalingo.web.mvc.form.ShippingForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
public interface FormFactory {

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryMaster productCategory) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryMaster parentProductCategory, CatalogCategoryMaster productCategory) throws Exception;

	ProductCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryVirtual productCategory) throws Exception;

	ProductCategoryForm buildCatalogCategoryForm(HttpServletRequest request, CatalogCategoryVirtual parentProductCategory, CatalogCategoryVirtual productCategory) throws Exception;

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
