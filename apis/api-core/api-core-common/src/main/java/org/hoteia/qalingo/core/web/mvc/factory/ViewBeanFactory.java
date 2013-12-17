/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.List;
import java.util.Locale;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.CustomerProductComment;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ConditionsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CurrencyReferentialViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressListViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductCommentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductCommentsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerWishlistViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CutomerMenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FaqViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsOptionViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FollowUsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.FooterMenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.HeaderCartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LegalTermsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketPlaceViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OurCompanyViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductAssociationLinkViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;

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
	
	List<RetailerViewBean> buildRetailerViewBeansByMarketArea(RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildRetailerViewBeans(RequestData requestData, List<Retailer> retailers) throws Exception;

	RetailerViewBean buildRetailerViewBean(RequestData requestData, Retailer currentRetailer) throws Exception;

	List<CutomerMenuViewBean> buildCutomerMenuViewBeans(RequestData requestData) throws Exception;
	
	List<MarketPlaceViewBean> buildMarketPlaceViewBeans(RequestData requestData) throws Exception;
	
	MarketPlaceViewBean buildMarketPlaceViewBean(RequestData requestData, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildMarketViewBeans(RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildMarketAreaViewBeans(RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;

	MarketAreaViewBean buildMarketAreaViewBean(RequestData requestData, MarketArea marketArea) throws Exception;

	List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(RequestData requestData, Localization localization) throws Exception;
	
    List<CurrencyReferentialViewBean> buildCurrenciesViewBeansByMarketArea(RequestData requestData) throws Exception;

    List<CurrencyReferentialViewBean> buildCurrencyReferentialViewBeans(RequestData requestData, List<CurrencyReferential> currencyReferentials) throws Exception;

    CurrencyReferentialViewBean buildCurrencyReferentialViewBean(RequestData requestData, CurrencyReferential currencyReferential) throws Exception;

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

	CatalogCategoryViewBean buildMasterProductCategoryViewBean(RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;

	CatalogCategoryViewBean buildCatalogCategoryViewBean(RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;
	
	ProductMarketingViewBean buildProductMarketingViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, ProductMarketing productMarketing) throws Exception;

	CartViewBean buildCartViewBean(RequestData requestData, Cart cart) throws Exception;

	List<OrderViewBean> buildOrderViewBeans(RequestData requestData,  List<OrderCustomer> orders) throws Exception;
	
	OrderViewBean buildOrderViewBean(RequestData requestData, OrderCustomer order) throws Exception;


	OrderItemViewBean buildOrderItemViewBean(RequestData requestData, OrderItem orderItem) throws Exception;
	
	ProductAssociationLinkViewBean buildProductAssociationLinkViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, 
														   ProductMarketing productMarketing) throws Exception;
	
	ProductSkuViewBean buildProductSkuViewBean(RequestData requestData, CatalogCategoryVirtual productCategory, 
												ProductMarketing productMarketing, ProductSku productSku) throws Exception;
	
    DeliveryMethodViewBean buildDeliveryMethodViewBean(RequestData requestData, DeliveryMethod deliveryMethod) throws Exception;

    PaymentMethodViewBean buildPaymentMethodViewBean(RequestData requestData, AbstractPaymentGateway paymentGateway) throws Exception;
    
}