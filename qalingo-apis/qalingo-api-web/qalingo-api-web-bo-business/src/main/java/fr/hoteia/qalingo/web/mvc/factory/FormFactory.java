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

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.mvc.form.ProductMarketingForm;
import fr.hoteia.qalingo.web.mvc.form.ProductSkuForm;
import fr.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
public interface FormFactory {

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryMaster productCategory) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryMaster parentProductCategory, CatalogCategoryMaster productCategory) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryVirtual productCategory) throws Exception;

	ProductCategoryForm buildProductCategoryForm(HttpServletRequest request, CatalogCategoryVirtual parentProductCategory, CatalogCategoryVirtual productCategory) throws Exception;

	ProductMarketingForm buildProductMarketingForm(HttpServletRequest request, ProductMarketing productMarketing) throws Exception;
	
	ProductSkuForm buildProductSkuForm(HttpServletRequest request, ProductSku productSku) throws Exception;
	
	UserForm buildUserForm(HttpServletRequest request, User user) throws Exception;

}
