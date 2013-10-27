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
import java.util.Locale;

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
import fr.hoteia.qalingo.core.pojo.RequestData;
import fr.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
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
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsOptionViewBean;
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

	CommonViewBean buildCommonViewBean(RequestData requestData) throws Exception;
	
	HeaderCartViewBean buildHeaderCartViewBean(RequestData requestData) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(RequestData requestData) throws Exception;

	List<FooterMenuViewBean> buildFooterMenuViewBeans(RequestData requestData) throws Exception;
	
	FollowUsViewBean buildFollowUsViewBean(RequestData requestData) throws Exception;
	
	FollowUsOptionViewBean buildFollowOption(RequestData requestData, Locale locale, String followType) throws Exception;
	
	LegalTermsViewBean buildLegalTermsViewBean(RequestData requestData) throws Exception;

	OurCompanyViewBean buildOurCompanyViewBean(RequestData requestData) throws Exception;
	
	FaqViewBean buildFaqViewBean(RequestData requestData) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(RequestData requestData) throws Exception;

	ConditionsViewBean buildConditionsViewBean(RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildRetailerViewBeansForTheMarketArea(RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildRetailerViewBeans(RequestData requestData, List<Retailer> retailers) throws Exception;

	RetailerViewBean buildRetailerViewBean(RequestData requestData, Retailer currentRetailer) throws Exception;

	List<CutomerMenuViewBean> buildCutomerMenuViewBeans(RequestData requestData) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(RequestData requestData) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(RequestData requestData, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;

	MarketAreaViewBean buildMarketAreaViewBean(RequestData requestData, MarketArea marketArea) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(RequestData requestData, Localization localization) throws Exception;
	
	StoreLocatorViewBean buildStoreLocatorViewBean(RequestData requestData, List<Store> stores) throws Exception;
	
	StoreViewBean buildStoreViewBean(RequestData requestData, Store store) throws Exception;
	
	CustomerViewBean buildCustomerViewBean(RequestData requestData, Customer customer) throws Exception ;

	CustomerWishlistViewBean buildCustomerWishlistViewBean(RequestData requestData, Customer customer) throws Exception;

	CustomerProductCommentsViewBean buildCustomerProductCommentsViewBean(RequestData requestData, Customer customer) throws Exception;
	
	CustomerProductCommentViewBean buildCustomerProductCommentViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, 
										ProductMarketing productMarketing, ProductSku productSku, CustomerProductComment customerProductComment) throws Exception;
	
	CustomerAddressListViewBean buildCustomerAddressListViewBean(RequestData requestData, Customer customer) throws Exception;
	
	CustomerAddressViewBean buildCustomeAddressViewBean(RequestData requestData, CustomerAddress customerAddress) throws Exception;
	
	ProductBrandViewBean buildProductBrandViewBean(RequestData requestData, ProductBrand productBrand) throws Exception;

	ProductBrandViewBean buildProductBrandViewBean(RequestData requestData, ProductBrand productBrand, List<ProductMarketing> productMarketings) throws Exception;

	ProductCategoryViewBean buildMasterProductCategoryViewBean(RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;

	ProductCategoryViewBean buildProductCategoryViewBean(RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, ProductMarketing productMarketing) throws Exception;

	CartViewBean buildCartViewBean(RequestData requestData, Cart cart) throws Exception;

	List<OrderViewBean> buildOrderViewBeans(RequestData requestData,  List<Order> orders) throws Exception;
	
	OrderViewBean buildOrderViewBean(RequestData requestData, Order order) throws Exception;


	OrderItemViewBean buildOrderItemViewBean(RequestData requestData, OrderItem orderItem) throws Exception;
	
	ProductCrossLinkViewBean buildProductCrossLinkViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, 
														   ProductMarketing productMarketing) throws Exception;
	
	ProductSkuViewBean buildProductSkuViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, 
												ProductMarketing productMarketing, ProductSku productSku) throws Exception;
	
	// SEARCH
	
	SearchViewBean buildSearchViewBean(RequestData requestData) throws Exception;

	List<SearchProductItemViewBean> buildSearchProductItemViewBeans(RequestData requestData, ProductResponseBean productResponseBean) throws Exception;
	
	SearchProductItemViewBean buildSearchProductItemViewBean(RequestData requestData, ProductSkuSolr productSolr) throws Exception;
	
	List<SearchFacetViewBean> buildSearchFacetViewBeans(RequestData requestData, ProductResponseBean productResponseBean) throws Exception;
	
	SearchFacetViewBean buildSearchFacetViewBean(RequestData requestData, FacetField facetField) throws Exception;

}