/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.response.FacetField;

import fr.hoteia.qalingo.core.domain.Cart;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Order;
import fr.hoteia.qalingo.core.domain.OrderItem;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FaqViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.HeaderCartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OrderViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.OurCompanyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductBrandViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCrossLinkViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchFacetViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchProductItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	HeaderCartViewBean buildHeaderCartViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;

	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;
	
	FollowUsViewBean buildFollowUsViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	LegalTermsViewBean buildLegalTermsViewBean(HttpServletRequest request, RequestData requestData) throws Exception;

	OurCompanyViewBean buildOurCompanyViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	FaqViewBean buildFaqViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, RequestData requestData) throws Exception;

	ConditionsViewBean buildConditionsViewBean(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildRetailerViewBeansForTheMarketArea(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildRetailerViewBeans(HttpServletRequest request, RequestData requestData, List<Retailer> retailers) throws Exception;

	RetailerViewBean buildRetailerViewBean(HttpServletRequest request, RequestData requestData, Retailer currentRetailer) throws Exception;

	List<CutomerMenuViewBean> buildCutomerMenuViewBeans(HttpServletRequest request, RequestData requestData) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(HttpServletRequest request) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(HttpServletRequest request, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(HttpServletRequest request, RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(HttpServletRequest request, Market market, List<MarketArea> marketAreas) throws Exception;

	MarketAreaViewBean buildMarketAreaViewBean(HttpServletRequest request, MarketArea marketArea) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(HttpServletRequest request, RequestData requestData, Localization localization) throws Exception;
	
	StoreLocatorViewBean buildStoreLocatorViewBean(HttpServletRequest request, RequestData requestData, List<Store> stores) throws Exception;
	
	StoreViewBean buildStoreViewBean(HttpServletRequest request, RequestData requestData, Store store) throws Exception;
	
	CustomerViewBean buildCustomerViewBean(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception ;

	CustomerWishlistViewBean buildCustomerWishlistViewBean(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;

	CustomerProductCommentsViewBean buildCustomerProductCommentsViewBean(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;
	
	CustomerProductCommentViewBean buildCustomerProductCommentViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory, 
										ProductMarketing productMarketing, ProductSku productSku, CustomerProductComment customerProductComment) throws Exception;
	
	CustomerAddressListViewBean buildCustomerAddressListViewBean(HttpServletRequest request, RequestData requestData, Customer customer) throws Exception;
	
	CustomerAddressViewBean buildCustomeAddressViewBean(HttpServletRequest request, RequestData requestData, CustomerAddress customerAddress) throws Exception;
	
	ProductBrandViewBean buildProductBrandViewBean(HttpServletRequest request, RequestData requestData, ProductBrand productBrand) throws Exception;

	ProductBrandViewBean buildProductBrandViewBean(HttpServletRequest request,RequestData requestData, ProductBrand productBrand, List<ProductMarketing> productMarketings) throws Exception;

	ProductCategoryViewBean buildMasterProductCategoryViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;

	ProductCategoryViewBean buildProductCategoryViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory, ProductMarketing productMarketing) throws Exception;

	CartViewBean buildCartViewBean(HttpServletRequest request, RequestData requestData, Cart cart) throws Exception;

	List<OrderViewBean> buildOrderViewBeans(HttpServletRequest request, RequestData requestData,  List<Order> orders) throws Exception;
	
	OrderViewBean buildOrderViewBean(HttpServletRequest request, RequestData requestData, Order order) throws Exception;


	OrderItemViewBean buildOrderItemViewBean(HttpServletRequest request, RequestData requestData, OrderItem orderItem) throws Exception;
	
	ProductCrossLinkViewBean buildProductCrossLinkViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory, ProductMarketing productMarketing) 
																   throws Exception;
	
	ProductSkuViewBean buildProductSkuViewBean(HttpServletRequest request, RequestData requestData, CatalogCategoryVirtual productCategory, 
												ProductMarketing productMarketing, ProductSku productSku) throws Exception;
	
	// SEARCH
	
	SearchViewBean buildSearchViewBean(HttpServletRequest request, RequestData requestData) throws Exception;

	List<SearchProductItemViewBean> buildSearchProductItemViewBeans(HttpServletRequest request, RequestData requestData, ProductResponseBean productResponseBean) throws Exception;
	
	SearchProductItemViewBean buildSearchProductItemViewBean(HttpServletRequest request, RequestData requestData, ProductSolr productSolr) throws Exception;
	
	List<SearchFacetViewBean> buildSearchFacetViewBeans(HttpServletRequest request, ProductResponseBean productResponseBean) throws Exception;
	
	SearchFacetViewBean buildSearchFacetViewBean(HttpServletRequest request, FacetField facetField) throws Exception;

}