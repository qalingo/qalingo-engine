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
import org.hoteia.qalingo.core.domain.Tax;
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
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.TaxViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildViewBeanCommon(RequestData requestData) throws Exception;
	
	HeaderCartViewBean buildViewBeanHeaderCart(RequestData requestData) throws Exception;
	
	List<MenuViewBean> buildListViewBeanMenu(RequestData requestData) throws Exception;

	List<FooterMenuViewBean> buildViewBeanFooterMenu(RequestData requestData) throws Exception;
	
	FollowUsViewBean buildViewBeanFollowUs(RequestData requestData) throws Exception;
	
	FollowUsOptionViewBean buildViewBeanFollowOption(RequestData requestData, Locale locale, String followType) throws Exception;
	
	LegalTermsViewBean buildViewBeanLegalTerms(RequestData requestData) throws Exception;

	OurCompanyViewBean buildViewBeanOurCompany(RequestData requestData) throws Exception;
	
	FaqViewBean buildViewBeanFaq(RequestData requestData) throws Exception;
	
	SecurityViewBean buildViewBeanSecurity(RequestData requestData) throws Exception;

	ConditionsViewBean buildViewBeanConditions(RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildListViewBeanRetailerByMarketArea(RequestData requestData) throws Exception;
	
	List<RetailerViewBean> buildListViewBeanRetailer(RequestData requestData, List<Retailer> retailers) throws Exception;

	RetailerViewBean buildViewBeanRetailer(RequestData requestData, Retailer currentRetailer) throws Exception;

	List<CutomerMenuViewBean> buildListViewBeanCutomerMenu(RequestData requestData) throws Exception;
	
	List<MarketPlaceViewBean> buildListViewBeanMarketPlace(RequestData requestData) throws Exception;
	
	MarketPlaceViewBean buildViewBeanMarketPlace(RequestData requestData, MarketPlace marketPlace) throws Exception;
	
	List<MarketViewBean> buildListViewBeanMarket(RequestData requestData, MarketPlace marketPlace, List<Market> markets) throws Exception;
	
	List<MarketAreaViewBean> buildListViewBeanMarketArea(RequestData requestData, Market market, List<MarketArea> marketAreas) throws Exception;

	MarketAreaViewBean buildViewBeanMarketArea(RequestData requestData, MarketArea marketArea) throws Exception;

	List<LocalizationViewBean> buildListViewBeanLocalizationByMarketArea(RequestData requestData, Localization localization) throws Exception;
	
    List<CurrencyReferentialViewBean> buildListViewBeanCurrenciesByMarketArea(RequestData requestData) throws Exception;

    List<CurrencyReferentialViewBean> buildListViewBeanCurrencyReferential(RequestData requestData, List<CurrencyReferential> currencyReferentials) throws Exception;

    CurrencyReferentialViewBean buildViewBeanCurrencyReferential(RequestData requestData, CurrencyReferential currencyReferential) throws Exception;

    List<StoreViewBean> buildListViewBeanStore(RequestData requestData, List<Store> stores) throws Exception;
	
	StoreViewBean buildViewBeanStore(RequestData requestData, Store store) throws Exception;
	
	CustomerViewBean buildViewBeanCustomer(RequestData requestData, Customer customer) throws Exception ;

	CustomerWishlistViewBean buildViewBeanCustomerWishlist(RequestData requestData, Customer customer) throws Exception;

	CustomerProductCommentsViewBean buildViewBeanCustomerProductComments(RequestData requestData, Customer customer) throws Exception;
	
	CustomerProductCommentViewBean buildViewBeanCustomerProductComment(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual, 
										ProductMarketing productMarketing, ProductSku productSku, CustomerProductComment customerProductComment) throws Exception;
	
	CustomerAddressListViewBean buildViewBeanCustomerAddressList(RequestData requestData, Customer customer) throws Exception;
	
	CustomerAddressViewBean buildViewBeanCustomeAddress(RequestData requestData, CustomerAddress customerAddress) throws Exception;
	
	ProductBrandViewBean buildViewBeanProductBrand(RequestData requestData, ProductBrand productBrand) throws Exception;

	ProductBrandViewBean buildViewBeanProductBrand(RequestData requestData, ProductBrand productBrand, List<ProductMarketing> productMarketings) throws Exception;

	CatalogCategoryViewBean buildViewBeanMastercatalogCategoryVirtual(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual) throws Exception;

	CatalogCategoryViewBean buildViewBeanCatalogCategory(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual) throws Exception;
	
	ProductMarketingViewBean buildViewBeanProductMarketing(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual, ProductMarketing productMarketing) throws Exception;

    ProductSkuViewBean buildViewBeanProductSku(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual, ProductMarketing productMarketing, ProductSku productSku) throws Exception;

    ProductAssociationLinkViewBean buildViewBeanProductAssociationLink(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual, 
            ProductMarketing productMarketing) throws Exception;

	CartViewBean buildViewBeanCart(RequestData requestData, Cart cart) throws Exception;

	List<OrderViewBean> buildListViewBeanOrder(RequestData requestData,  List<OrderCustomer> orders) throws Exception;
	
	OrderViewBean buildViewBeanOrder(RequestData requestData, OrderCustomer order) throws Exception;


	OrderItemViewBean buildViewBeanOrderItem(RequestData requestData, OrderItem orderItem) throws Exception;
	
    DeliveryMethodViewBean buildViewBeanDeliveryMethod(RequestData requestData, DeliveryMethod deliveryMethod) throws Exception;

    TaxViewBean buildViewBeanTax(RequestData requestData, Tax tax) throws Exception;

    PaymentMethodViewBean buildViewBeanPaymentMethod(RequestData requestData, AbstractPaymentGateway paymentGateway) throws Exception;
}