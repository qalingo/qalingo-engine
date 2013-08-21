package fr.hoteia.qalingo.web.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Company;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.EngineSettingWebAppContext;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.LocalizationService;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketAreaViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketPlaceViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MarketViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.RetailerViewBean;

/**
 * 
 * <p>
 * <a href="AbstractBackofficeQalingoController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractBackofficeQalingoController extends AbstractQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected BackofficeUrlService backofficeUrlService;

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected LocalizationService localizationService;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;

	@Autowired
    protected RequestUtil requestUtil;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initApp(final HttpServletRequest request, final Model model) throws Exception {
		final Locale locale  = requestUtil.getCurrentLocale(request);
		// APP NAME
		model.addAttribute(Constants.APP_NAME, getAppName(request));
		Object[] params = {StringUtils.capitalize(requestUtil.getApplicationName())};
		model.addAttribute(Constants.APP_NAME_HTML, getCommonMessage(ScopeCommonMessage.APP, "name_html", params, locale));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initConfig(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		model.addAttribute(ModelConstants.LOCALE_LANGUAGE_CODE, locale.getLanguage());
		model.addAttribute(ModelConstants.CONTEXT_PATH, request.getContextPath());
		model.addAttribute(ModelConstants.THEME, requestUtil.getCurrentTheme(request));
		Object[] params = {StringUtils.capitalize(requestUtil.getEnvironmentName())};
		model.addAttribute(ModelConstants.ENV_NAME, getSpecificMessage(ScopeWebMessage.COMMON, "header.env.name", params, locale));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initSeo(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String seoPageMetaAuthor = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_AUTHOR, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_AUTHOR, seoPageMetaAuthor);

		String seoPageMetaKeywords = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_KEYWORDS, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_KEYWORDS, seoPageMetaKeywords);

		String seoPageMetaDescription = getCommonMessage(ScopeCommonMessage.SEO, BoMessageKey.SEO_META_DESCRIPTION, locale);
        model.addAttribute(ModelConstants.SEO_PAGE_META_DESCIPRTION, seoPageMetaDescription);

        // DEFAULT EMPTY VALUE
        model.addAttribute(ModelConstants.SEO_PAGE_META_TITLE, getAppName(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.COMMON_VIEW_BEAN)
	protected CommonViewBean initCommon(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildCommonViewBean(request, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.URL_SUBMIT_QUICK_SEARCH)
	protected String initQuickSearch(final HttpServletRequest request, final Model model) throws Exception {
		return backofficeUrlService.generateUrl(BoUrls.GLOBAL_SEARCH, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initHeader(final HttpServletRequest request, final Model model) throws Exception {
		// HEADER
		model.addAttribute(ModelConstants.MENUS_VIEW_BEAN, viewBeanFactory.buildMenuViewBeans(request, requestUtil.getRequestData(request)));
		model.addAttribute(ModelConstants.MORE_PAGE_MENUS_VIEW_BEAN, viewBeanFactory.buildMorePageMenuViewBeans(request, requestUtil.getRequestData(request)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.LANGUAGES_VIEW_BEAN)
	protected List<LocalizationViewBean> initLocalizations(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS
		Company company = requestUtil.getCurrentCompany(request);
		if(company != null){
			Set<Localization> localizations = company.getLocalizations();
			return viewBeanFactory.buildLocalizationViewBeans(request, requestUtil.getRequestData(request), new ArrayList<Localization>(localizations));
		} else {
			Localization defaultLocalization = localizationService.getLocalizationByCode("en");
			List<Localization> defaultLocalizations = new ArrayList<Localization>();
			defaultLocalizations.add(defaultLocalization);
			return viewBeanFactory.buildLocalizationViewBeans(request, requestUtil.getRequestData(request), defaultLocalizations);
		}
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_PLACES_VIEW_BEAN)
	protected List<MarketPlaceViewBean> initAllPlaces(final HttpServletRequest request, final Model model) throws Exception {
		// ALL MARKETPLACES
		return viewBeanFactory.buildMarketPlaceViewBeans(request, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKETS_VIEW_BEAN)
	protected List<MarketViewBean> initMarkets(final HttpServletRequest request, final Model model) throws Exception {
		// MARKETS FOR THE CURRENT MARKETPLACE
		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		Set<Market> marketList = currentMarketPlace.getMarkets();
		return viewBeanFactory.buildMarketViewBeansByMarketPlace(request, requestUtil.getRequestData(request), currentMarketPlace, new ArrayList<Market>(marketList));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_AREAS_VIEW_BEAN)
	protected List<MarketAreaViewBean> initMarletAreas(final HttpServletRequest request, final Model model) throws Exception {
		// MARKET AREAS FOR THE CURRENT MARKET
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
		return viewBeanFactory.buildMarketAreaViewBeansByMarket(request, requestUtil.getRequestData(request), currentMarket, new ArrayList<MarketArea>(marketAreaList));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.MARKET_LANGUAGES_VIEW_BEAN)
	protected List<LocalizationViewBean> initMarketLocalizations(final HttpServletRequest request, final Model model) throws Exception {
		// LOCALIZATIONS FOR THE CURRENT MARKET AREA
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		return viewBeanFactory.buildLocalizationViewBeansByMarketArea(request, requestUtil.getRequestData(request), new ArrayList<Localization>(currentMarketArea.getLocalizations()));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.RETAILERS_VIEW_BEAN)
	protected List<RetailerViewBean> initRetailers(final HttpServletRequest request, final Model model) throws Exception {
		// RETAILERS FOR THE CURRENT MARKET AREA
		return viewBeanFactory.buildRetailerViewBeans(request, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN)
	protected LegalTermsViewBean initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildLegalTermsViewBean(request, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.FOOTER_MENUS_VIEW_BEAN)
	protected List<FooterMenuViewBean> initFooter(final HttpServletRequest request, final Model model) throws Exception {
		return viewBeanFactory.buildFooterMenuViewBeans(request, requestUtil.getRequestData(request));
	}
	
	/**
	 * 
	 */
	protected void overrideMainContentTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String title) throws Exception {
//		final Locale locale  = requestUtil.getCurrentLocale(request);
//		String mainContentTitleKey = BoMessageKey.MAIN_CONTENT_TITLE_PREFIX + pageKey;
//		try {
//			String mainContentTitle = getSpecificMessage(ScopeWebMessage.BREADCRUMB_AND_HEADER, mainContentTitleKey, locale);
//	        modelAndView.addObject(ModelConstants.MAIN_CONTENT_TITLE, mainContentTitle);
//		} catch (Exception e) {
//			// DOESN'T NEED WARNING OR ERROR LOG
//			LOG.debug("mainContentTitle with key header.title doesn't exist for:" + pageKey);
//		}
		if(StringUtils.isNotEmpty(title)){
			 modelAndView.addObject(ModelConstants.MAIN_CONTENT_TITLE, title);
		}
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	public void initWording(final HttpServletRequest request, final Model model) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		String contextName = requestUtil.getContextName();
		try {
			EngineSettingWebAppContext contextValue = EngineSettingWebAppContext.valueOf(contextName);
			model.addAttribute(ModelConstants.WORDING, coreMessageSource.loadWording(contextValue, locale));
	        
        } catch (Exception e) {
        	LOG.error("Context name, " + contextName + " can't be resolve by EngineSettingWebAppContext class.", e);
        }
	}
	
	/**
	 * 
	 */
	protected void overrideSeoTitle(final HttpServletRequest request, final ModelAndView modelAndView, final String title) throws Exception {
		final String fullTitle = getAppName(request) + " - " + title;
		if(StringUtils.isNotEmpty(fullTitle)){
	        modelAndView.addObject(ModelConstants.SEO_PAGE_META_TITLE, fullTitle);
		}
	}
	
	/**
	 * 
	 */
	protected String getMessageTitleKey(final String pageKey) throws Exception {
		return "page_title_" + pageKey;
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initBreadcrumAndHeaderContent(final HttpServletRequest request, final Model model) throws Exception {
        // DEFAULT EMPTY VALUE
        model.addAttribute(ModelConstants.MAIN_CONTENT_TITLE, "");
	}
	
	/**
	 * 
	 */
	@ModelAttribute(ModelConstants.USER_VIEW_BEAN)
	protected User initUser(final HttpServletRequest request, final Model model) throws Exception {
		final User user = requestUtil.getCurrentUser(request);
		return user;
	}
	
	/**
	 * @throws Exception  
	 * 
	 */
	protected String getUserId(final HttpServletRequest request) throws Exception {
		User user = requestUtil.getCurrentUser(request);
		if(user != null){
			Long userId = user.getId();
			if(userId != null){
				return userId.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getAppName(HttpServletRequest request) throws Exception {
		final Locale locale  = requestUtil.getCurrentLocale(request);
		Object[] params = {StringUtils.capitalize(requestUtil.getApplicationName())};
		String appName = getCommonMessage(ScopeCommonMessage.APP, "name_text", params, locale);
		return appName;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Localization getCurrentLocalization(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentLocalization(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected Locale getCurrentLocale(HttpServletRequest request) throws Exception {
		return getCurrentLocalization(request).getLocale();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentTheme(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentTheme(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentDevice(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentDevice(request);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected String getCurrentVelocityPath(HttpServletRequest request) throws Exception {
		return requestUtil.getCurrentVelocityWebPrefix(request);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, locale);
	}
	
	/**
	 * 
	 */
	protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale){
		return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, params, locale);
	}
}