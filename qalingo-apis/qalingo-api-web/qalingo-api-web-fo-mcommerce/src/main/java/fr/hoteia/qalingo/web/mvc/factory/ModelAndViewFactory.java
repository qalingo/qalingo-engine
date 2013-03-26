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
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;

public interface ModelAndViewFactory {

	void initCommonModelAndView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, String customerId) throws Exception;

	void initContactUsModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	void initSearchModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;

	void initFollowUsModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initOurCompanyModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initStoreLocatorModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initCustomerDetailsAccountModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;

	void initCustomerCreateAccountModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initCustomerAddAddressModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initCustomerAddressListModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;

	void initCustomerOrderListModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;
	
	void initCustomerOrderDetailsModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;
	
	void initCustomerWishListModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;

	void initCustomerProductCommentModelAndView(HttpServletRequest request, ModelAndView modelAndView, Customer customer) throws Exception;

	void initFaqModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	
	void initPageBrandLine(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, ProductBrand productBrand, String titleKeyPrefixSufix) throws Exception;

	void initPageBrandDetails(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, ProductBrand productBrand, String titleKeyPrefixSufix) throws Exception;

	void initPageProductCategory(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, ProductCategoryVirtual productCategory, String titleKeyPrefixSufix) throws Exception;
	
	void initPageProductMarketing(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, ProductCategoryVirtual productCategory, ProductMarketing productMarketing, String titleKeyPrefixSufix) throws Exception;

	void initMasterProductCategoryModelAndView(HttpServletRequest request, ModelAndView modelAndView, ProductCategoryVirtual productCategory) throws Exception;
	
	void initProductMarketingModelAndView(HttpServletRequest request, ModelAndView modelAndView, ProductCategoryVirtual productCategory, ProductMarketing productMarketing) throws Exception;
	
	 void initCartModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
	 
	 void initOrderModelAndView(HttpServletRequest request, ModelAndView modelAndView, Order order) throws Exception;
	 
	 void initLoginModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
}
