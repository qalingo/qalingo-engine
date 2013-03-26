/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Company;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.LocalizationService;
import fr.hoteia.qalingo.core.service.ProductCategoryService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.StoreService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.factory.ModelAndViewFactory;
import fr.hoteia.qalingo.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Service("modelAndViewFactory")
public class ModelAndViewFactoryImpl implements ModelAndViewFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected StoreService storeService;
	
	@Autowired
    protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected LocalizationService localizationService;
	
	@Autowired
	protected ProductCategoryService productCategoryService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected FormFactory formFactory;
	
	/**
     * 
     */
	public void initCommonModelAndView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView) throws Exception {
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		// COMMON
		CommonViewBean commonViewBean = viewBeanFactory.buildCommonViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer);
		modelAndView.addObject(Constants.COMMON_VIEW_BEAN, commonViewBean);

		// QUICK SEARCH
		QuickSearchViewBean quickSearchViewBean = viewBeanFactory.buildQuickSearchViewBean(request, currentLocalization);
		modelAndView.addObject(Constants.QUICK_SEARCH_VIEW_BEAN, quickSearchViewBean);
		
		// HEADER
		modelAndView.addObject(Constants.MENUS_VIEW_BEAN, viewBeanFactory.buildMenuViewBeans(request, currentLocalization));
		modelAndView.addObject(Constants.MORE_PAGE_MENUS_VIEW_BEAN, viewBeanFactory.buildMorePageMenuViewBeans(request, currentLocalization));

		// ALL MARKETPLACES
		List<MarketPlaceViewBean> marketPlaceViewBeans = viewBeanFactory.buildMarketPlaceViewBeans(request, currentLocalization);
		modelAndView.addObject(Constants.MARKET_PLACES_VIEW_BEAN, marketPlaceViewBeans);
		
		// MARKETS FOR THE CURRENT MARKETPLACE
		Set<Market> marketList = currentMarketPlace.getMarkets();
		modelAndView.addObject(Constants.MARKETS_VIEW_BEAN, viewBeanFactory.buildMarketViewBeans(request, currentMarketPlace, new ArrayList<Market>(marketList), currentLocalization));
		
		// MARKET AREAS FOR THE CURRENT MARKET
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		modelAndView.addObject(Constants.MARKET_AREAS_VIEW_BEAN, viewBeanFactory.buildMarketAreaViewBeans(request, currentMarket, new ArrayList<MarketArea>(marketAreaList), currentLocalization));
		
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		modelAndView.addObject(Constants.MARKET_LANGUAGES_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, currentMarketArea, currentLocalization));

		// RETAILERS FOR THE CURRENT MARKET AREA
		modelAndView.addObject(Constants.RETAILERS_VIEW_BEAN, viewBeanFactory.buildRetailerViewBeans(request, currentMarketArea, currentLocalization));
		
		// LOCALIZATIONS
		Company company = requestUtil.getCurrentCompany(request);
		if(company != null){
			Set<Localization> localizations = company.getLocalizations();
			modelAndView.addObject(Constants.LANGUAGE_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, new ArrayList<Localization>(localizations)));
		} else {
			Localization defaultLocalization = localizationService.getLocalizationByCode("en");
			List<Localization> defaultLocalizations = new ArrayList<Localization>();
			defaultLocalizations.add(defaultLocalization);
			modelAndView.addObject(Constants.LANGUAGE_VIEW_BEAN, viewBeanFactory.buildLocalizationViewBeans(request, defaultLocalizations));
		}
		
		// LEGACY
		LegacyViewBean legacyViewBean = viewBeanFactory.buildLegacyViewBean(request, currentLocalization);
		modelAndView.addObject(Constants.LEGACY_VIEW_BEAN, legacyViewBean);

		// FOOTER
		modelAndView.addObject(Constants.FOOTER_MENUS_VIEW_BEAN, viewBeanFactory.buildFooterMenuViewBeans(request, currentLocalization));
	}
	
	/**
     * 
     */
	public void initLoginModelAndView(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		SecurityViewBean security = viewBeanFactory.buildSecurityViewBean(request, currentLocalization);
		modelAndView.addObject(Constants.SECURITY_VIEW_BEAN, security);
	}
	
	/**
     * 
     */
	public void initCatalogModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogMaster catalogMaster) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		List<ProductCategoryMaster> productCategories = productCategoryService.findRootProductCategories();

		CatalogViewBean catalogViewBean = viewBeanFactory.buildMasterCatalogViewBean(request, currentMarketArea, currentLocalization, catalogMaster, productCategories);
		
		modelAndView.addObject(Constants.CATALOG_VIEW_BEAN, catalogViewBean);
	}
	
	/**
     * 
     */
	public void initCatalogModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogVirtual catalogVirtual) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		List<ProductCategoryVirtual> productCategories = productCategoryService.findRootProductCategories(currentMarketArea.getId(), currentRetailer.getId());

		CatalogViewBean catalogViewBean = viewBeanFactory.buildVirtualCatalogViewBean(request, currentMarketArea, currentLocalization, catalogVirtual, productCategories);
		
		modelAndView.addObject(Constants.CATALOG_VIEW_BEAN, catalogViewBean);
	}
	
	/**
     * 
     */
	public void initProductMasterCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductCategoryMaster productCategory) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductCategoryViewBean productCategoryViewBean = viewBeanFactory.buildMasterProductCategoryViewBean(request, currentMarketArea, currentLocalization, productCategory, true);
		
		modelAndView.addObject(Constants.PRODUCT_CATEGORY_VIEW_BEAN, productCategoryViewBean);
	}
	
	/**
     * 
     */
	public void initProductVirtualCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductCategoryVirtual productCategory) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductCategoryViewBean productCategoryViewBean = viewBeanFactory.buildVirtualProductCategoryViewBean(request, currentMarketArea, currentLocalization, productCategory, true);
		
		modelAndView.addObject(Constants.PRODUCT_CATEGORY_VIEW_BEAN, productCategoryViewBean);
	}
	
	/**
     * 
     */
	public void initProductMarketingModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductMarketing productMarketing) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductMarketingViewBean productMarketingViewBean = viewBeanFactory.buildProductMarketingViewBean(request, currentMarketArea, currentLocalization, productMarketing, true);
		
		modelAndView.addObject(Constants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
	}
	
	/**
     * 
     */
	public void initProductSkuModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductSku productSku) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductSkuViewBean productSkuViewBean = viewBeanFactory.buildProductSkuViewBean(request, currentMarketArea, currentLocalization, productSku);
		
		modelAndView.addObject(Constants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);
	}

}
