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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.response.FacetField;

import fr.hoteia.qalingo.core.domain.Cart;
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
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ConditionsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ContactUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressFormViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressListViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerAddressViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerCreateAccountViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerProductCommentsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CustomerWishlistViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CutomerMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FaqViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FollowUsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.HeaderCartViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
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
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchFacetViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchProductItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreLocatorViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.StoreViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	HeaderCartViewBean buildHeaderCartViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(HttpServletRequest request, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(HttpServletRequest request, MarketPlace marketPlace, List<Market> markets, Localization localization) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(HttpServletRequest request, Market market, List<MarketArea> marketAreas, Localization localization) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization) throws Exception;

	List<RetailerViewBean> buildRetailerViewBeans(HttpServletRequest request, MarketArea marketArea, Localization localization) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	List<CutomerMenuViewBean> buildCutomerMenuViewBeans(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;
	
	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	ContactUsViewBean buildContactUsViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;

	FollowUsViewBean buildFollowUsViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	LegacyViewBean buildLegacyViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;

	OurCompanyViewBean buildOurCompanyViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	FaqViewBean buildFaqViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;

	StoreLocatorViewBean buildStoreLocatorViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, List<Store> stores) throws Exception;
	
	StoreViewBean buildStoreViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, Store store) throws Exception;
	
	CustomerViewBean buildCustomerDetailsAccountViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, Customer customer) throws Exception ;

	CustomerWishlistViewBean buildCustomerWishlistViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, Customer customer) throws Exception;

	CustomerProductCommentsViewBean buildCustomerProductCommentsViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, Customer customer) throws Exception;
	
	CustomerProductCommentViewBean buildCustomerProductCommentViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
			   Retailer retailer, ProductCategoryVirtual productCategory, ProductMarketing productMarketing, ProductSku productSku, CustomerProductComment customerProductComment) 
			   throws Exception;
	
	CustomerCreateAccountViewBean buildCustomerCreateAccountViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;

	CustomerAddressFormViewBean buildCustomerAddressFormViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;

	CustomerAddressListViewBean buildCustomerAddressListViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, Customer customer) throws Exception;
	
	CustomerAddressViewBean buildCustomeAddressViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, CustomerAddress customerAddress) throws Exception;
	
	ConditionsViewBean buildConditionsViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer) throws Exception;

	ProductBrandViewBean buildProductBrandViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, ProductBrand productBrand) throws Exception;

	ProductBrandViewBean buildProductBrandViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, ProductBrand productBrand, List<ProductMarketing> productMarketings) throws Exception;

	ProductCategoryViewBean buildMasterProductCategoryViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			 Localization localization, Retailer retailer, ProductCategoryVirtual productCategory) throws Exception;

	ProductCategoryViewBean buildProductCategoryViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, ProductCategoryVirtual productCategory) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, ProductCategoryVirtual productCategory, ProductMarketing productMarketing) throws Exception;

	CartViewBean buildCartViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, Cart cart) throws Exception;

	List<OrderViewBean> buildOrderViewBeans(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer,  List<Order> orders) throws Exception;
	
	OrderViewBean buildOrderViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
									 Localization localization, Retailer retailer, Order order) throws Exception;


	OrderItemViewBean buildOrderItemViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
													 Localization localization, Retailer retailer, OrderItem orderItem) throws Exception;
	
	ProductCrossLinkViewBean buildProductCrossLinkViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
																   Localization localization, Retailer retailer, ProductCategoryVirtual productCategory, ProductMarketing productMarketing) 
																   throws Exception;
	
	ProductSkuViewBean buildProductSkuViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization,
													   Retailer retailer, ProductCategoryVirtual productCategory, ProductMarketing productMarketing, ProductSku productSku) 
													   throws Exception;
	
	// SEARCH
	
	SearchViewBean buildSearchViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;

	QuickSearchViewBean buildQuickSearchViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer) throws Exception;

	List<SearchProductItemViewBean> buildSearchProductItemViewBeans(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, ProductResponseBean productResponseBean) throws Exception;
	
	SearchProductItemViewBean buildSearchProductItemViewBean(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, 
			Localization localization, Retailer retailer, ProductSolr productSolr) throws Exception;
	
	List<SearchFacetViewBean> buildSearchFacetViewBeans(HttpServletRequest request, ProductResponseBean productResponseBean) throws Exception;
	
	SearchFacetViewBean buildSearchFacetViewBean(HttpServletRequest request, FacetField facetField) throws Exception;
}
