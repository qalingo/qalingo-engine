/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;

public interface WebBackofficeService {

	void updateUser(User user, UserForm userForm);
	
	void createProductCategory(MarketArea currentMarketArea, Localization currentLocalization, ProductCategoryMaster parentProductCategory, ProductCategoryMaster productCategory, ProductCategoryForm productCategoryForm) throws UniqueConstraintCodeCategoryException;

	void updateProductCategory(MarketArea currentMarketArea, Retailer currentRetailer, Localization currentLocalization, ProductCategoryMaster productCategory, ProductCategoryForm productCategoryForm) throws UniqueConstraintCodeCategoryException;
	
	void createProductCategory(MarketArea currentMarketArea, Localization currentLocalization, ProductCategoryVirtual productCategory, ProductCategoryForm productCategoryForm);

	void updateProductCategory(MarketArea currentMarketArea, Retailer currentRetailer, Localization currentLocalization, ProductCategoryVirtual productCategory, ProductCategoryForm productCategoryForm);
	
	void updateProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);
	
	void createProductMarketing(ProductMarketing productMarketing, ProductMarketingForm productMarketingForm);
	
	void updateProductSku(ProductSku productSku, ProductSkuForm productSkuForm);
	
	void createProductSku(ProductSku productSku, ProductSkuForm productSkuForm);
}
