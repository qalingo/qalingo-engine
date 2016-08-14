/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.GeolocData;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CmsContentService;
import org.hoteia.qalingo.core.service.EngineSessionService;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.ReferentialDataService;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.CmsContentViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PageModelDataHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected FrontofficeViewBeanFactory frontofficeViewBeanFactory;

    @Autowired
    protected EngineSessionService engineSessionService;

    @Autowired
    protected ReferentialDataService referentialDataService;
    
    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected GeolocService geolocService;

	@Autowired
	protected CmsContentService cmsContentService;

    @Autowired
	protected UrlService urlService;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
	protected CoreMessageSource coreMessageSource;
    
    @Value("${cookie.prefix}")
    protected String cookiePrefix;
    
    @Value("${minify.assets}")
    protected String minifyAssets;
    
    @Value("${assets.version}")
    protected String assetsVersion;
    
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();

    public PageModelDataHandlerInterceptor() {
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.attributes.getName()));
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            final RequestData requestData = requestUtil.getRequestData(request);
            final GeolocData geolocData = requestData.getGeolocData();
            final MarketPlace currentMarketPlace = requestData.getMarketPlace();
            final MarketArea currentMarketArea = requestData.getMarketArea();
            final Localization currentLocalization = requestData.getMarketAreaLocalization();
            final Locale locale = requestData.getLocale();
            final Customer customer = requestData.getCustomer();
            final String currentUrl = requestUtil.getCurrentRequestUrl(request);
            
            if(modelAndView != null){
            	String requestParamMinifyAssets = request.getParameter("minifyAssets");
            	if(StringUtils.isNotEmpty(requestParamMinifyAssets)){
            		request.getSession().setAttribute("minifyAssets", requestParamMinifyAssets);
            	}
            	String sessionMinifyAssets = (String) request.getSession().getAttribute("minifyAssets");
            	if(StringUtils.isNotEmpty(sessionMinifyAssets)){
                    modelAndView.getModelMap().put("minifyAssets", BooleanUtils.toBoolean(sessionMinifyAssets));
            	} else {
                    modelAndView.getModelMap().put("minifyAssets", minifyAssets);
            	}
                
                modelAndView.getModelMap().put("assetsVersion", assetsVersion);
            	
            	// SANITY CHECK : Controle MarketArea
            	String referer = request.getHeader(Constants.REFERER);
            	
            	if(StringUtils.isEmpty(referer) || !referer.contains("coloroptical.com")){
            		// VISITOR COME FROM OTHER WEBSITE : check the target
            		if(geolocData != null && geolocData.getCountry() != null && currentMarketArea != null
            				&& StringUtils.isNotEmpty(geolocData.getCountry().getIsoCode())
            				&& !geolocData.getCountry().getIsoCode().equals(currentMarketArea.getGeolocCountryCode())){
            			// TARGET IS NOT THE GEOLOCATED MARKET AREA
            			 String targetUrl = request.getRequestURI().replace(request.getContextPath(), "");
            			 
//            			System.out.println("need redirect modal");
//            			System.out.println("targetUrl: " + targetUrl);
            			
            			MarketArea marketAreaGeoloc = null;
                        List<MarketArea> marketAreas = marketService.findMarketAreaOpenedByGeolocCountryCode(geolocData.getCountry().getIsoCode());
                        if (marketAreas != null) {
                            if (marketAreas.size() == 1) {
                                marketAreaGeoloc = marketAreas.get(0);
                            } else {
                                // WE HAVE MANY MARKET AREA FOR THE CURRENT COUNTRY CODE - WE SELECT THE DEFAULT MARKET PLACE ASSOCIATE
                                for (MarketArea marketAreaIt : marketAreas) {
                                    if (marketAreaIt.getMarket().getMarketPlace().isDefault()) {
                                        marketAreaGeoloc = marketAreaIt;
                                    }
                                }
                            }
                            
                            String context = "/" + marketAreaGeoloc.getCode() + "/" + marketAreaGeoloc.getDefaultLocalization().getCode() + "/";
                            String newSegmentl = urlService.getSeoSegmentMain(marketAreaGeoloc.getDefaultLocalization().getLocale(), true);
                            String[] splits = request.getRequestURI().split("/");
                            String target = "";
                            int count = 0;
                            for (String split : splits) {
                                if (count == (splits.length - 1)) {
                                    target = split;
                                }
                                count++;
                            }

                            String newUrl = context.toLowerCase() + newSegmentl + "/" + target;
                            String newAbsoluteUrl = urlService.buildAbsoluteUrl(requestData, newUrl);
                            logger.debug("Redirect to: " + newAbsoluteUrl);

                            try {
								// TEST TARGET URL
                        		URL obj = new URL(newAbsoluteUrl);
                        		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        		con.setRequestMethod("GET");
                        		con.setRequestProperty("User-Agent", request.getHeader("User-Agent"));
                        		int responseCode = con.getResponseCode();
                        		if(responseCode != 200){
                        			logger.debug("Url is not OK/200, url: " + newAbsoluteUrl);
                        			MarketAreaViewBean marketAreaGeolocViewBean = frontofficeViewBeanFactory.buildViewBeanMarketArea(requestData, currentMarketArea);
                        			newAbsoluteUrl = urlService.buildAbsoluteUrl(requestData, marketAreaGeolocViewBean.getChangeContextUrl());
                        			logger.debug("Switch URL target, new url: " + newAbsoluteUrl);
                        		}
                            	
							} catch (Exception e) {
								// TODO: handle exception
							}
                    		
                            modelAndView.getModelMap().put("newAbsoluteUrl", newAbsoluteUrl);
                            modelAndView.getModelMap().put("redirectMarketArea", true);
                            modelAndView.getModelMap().put("redirectMarketAreaCountry", referentialDataService.getCountryByLocale(geolocData.getCountry().getIsoCode(), locale));
                            modelAndView.getModelMap().put("targetMarketAreaCountry", referentialDataService.getCountryByLocale(currentMarketArea.getGeolocCountryCode(), locale));
                            
                        } else {
                            logger.debug("There is no MarketArea for this country:" + geolocData.getCountry().getIsoCode());
                        	
                        }
            		}
            	}
            	
            	boolean displayHttpsNavBarMessage = false;
            	if(currentUrl.contains("login")){
            		displayHttpsNavBarMessage = true;
            	}
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    Cookie navBarMessageCookie = null;
                    boolean found = false;
                    String navBarMessageName = cookiePrefix + "_NAV_BAR_MESSAGE";
                    for (Cookie cookie : cookies) {
                        if (navBarMessageName.equals(cookie.getName())) {
                            navBarMessageCookie = cookie;
                            found = true;
                            break;
                        }
                    }
                    if(found && navBarMessageCookie.getValue().equals("hide")){
                    	displayHttpsNavBarMessage = false;
                    }
                }
                modelAndView.getModelMap().put("displayHttpsNavBarMessage", displayHttpsNavBarMessage);
                
            	modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCommon(requestData));

                modelAndView.getModelMap().put("requestScheme", request.getScheme());

                if(geolocData != null && geolocData.getCity() != null && StringUtils.isNotEmpty(geolocData.getCity().getName())){
                    modelAndView.getModelMap().addAttribute("cityName", geolocData.getCity().getName());
                    modelAndView.getModelMap().addAttribute("isGeolocated", true);
                }

                if(customer != null){
                    modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanCustomer(requestData, customer));
                }

                if(requestData.getGeolocData() != null){
                    modelAndView.getModelMap().put(ModelConstants.GEOLOC_DATA_VIEW_BEAN, requestData.getGeolocData());
                }

                modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanLegalTerms(requestData));

                modelAndView.getModelMap().put(ModelConstants.HEADER_CART, frontofficeViewBeanFactory.buildViewBeanHeaderCart(requestData));

                // MARKETS FOR THE CURRENT MARKETPLACE
                Set<Market> marketList = currentMarketPlace.getMarkets();
                List<MarketAreaViewBean> allMarketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
                for (Market market : marketList) {
                    Set<MarketArea> marketAreaList = market.getMarketAreas();
                    for (MarketArea marketArea : marketAreaList) {
                        if (marketArea.isOpened()) {
                        	MarketArea reloadedMarketArea = marketService.getMarketAreaById(marketArea.getId());
                            MarketAreaViewBean marketAreaViewBean = frontofficeViewBeanFactory.buildViewBeanMarketArea(requestData, reloadedMarketArea);
                            if(currentMarketArea.getId().equals(reloadedMarketArea.getId())){
                            	marketAreaViewBean.setActive(true);
                            }
                            allMarketAreaViewBeans.add(marketAreaViewBean);
                        }
                    }
                }

                // MARKET AREAS FOR THE CURRENT MARKET
                modelAndView.getModelMap().put(ModelConstants.MARKET_AREAS_VIEW_BEAN, allMarketAreaViewBeans);

                // CURRENT MARKET AREA
                modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_VIEW_BEAN, frontofficeViewBeanFactory.buildViewBeanMarketArea(requestData, currentMarketArea));

                // LOCALIZATIONS FOR THE CURRENT MARKET AREA
                modelAndView.getModelMap().put("marketAreaLocalization", frontofficeViewBeanFactory.buildViewBeanLocalization(requestData, currentLocalization));
                modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_LANGUAGES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanLocalizationByMarketArea(requestData, currentLocalization));

                // RETAILERS FOR THE CURRENT MARKET AREA
                modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_RETAILERS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanRetailerByMarketArea(requestData));

                // CURRENCIES FOR THE CURRENT MARKET AREA
                modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_CURRENCIES_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanCurrenciesByMarketArea(requestData));

                // HEADER
                modelAndView.getModelMap().put(ModelConstants.HEADER_NAVS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanHeaderNav(requestData));
                modelAndView.getModelMap().put(ModelConstants.HEADER_MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanCmsMenus(requestData));

                // FOOTER
                modelAndView.getModelMap().put(ModelConstants.FOOTER_MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanFooterMenu(requestData));
                modelAndView.getModelMap().put(ModelConstants.FOOTER_NAVS_VIEW_BEAN, frontofficeViewBeanFactory.buildListViewBeanFooterNav(requestData));

                //TODO a remonter dans CommonViewBean
                modelAndView.getModelMap().put("checkoutShoppingCartUrl", urlService.generateUrl(FoUrls.CART_DETAILS, requestData));

        		final List<Long> lastCmsContentIds = cmsContentService.findLastActiveCmsContentIds(CmsContent.APP_NAME_FRONTOFFICE_B2C, "ARTICLE", currentMarketArea.getId(), currentLocalization.getId(), 20);
        		final List<CmsContent> reloadedCmsContents = new ArrayList<CmsContent>();
        		final Date now = new Date();
        		for (Iterator<Long> iterator = lastCmsContentIds.iterator(); iterator.hasNext();) {
        			Long cmsContentId = (Long) iterator.next();
        			if(reloadedCmsContents.size() == 9){
        				// WE HAVE LOAD 20 items WITHOUT PARAM PUBLISH DATE, WE FILTER AFTER 
        				// TODO : specific query to load 9 items already publish and active
        				break;
        			}
        			CmsContent reloadedCmsContent = cmsContentService.getCmsContentById(cmsContentId);
        			if(reloadedCmsContent.getDatePublish().getTime() < now.getTime()){
            			reloadedCmsContents.add(reloadedCmsContent);
        			}
        		}
    			final List<CmsContentViewBean> cmsContentViewBeans = frontofficeViewBeanFactory.buildListViewBeanCmsContent(requestData, reloadedCmsContents);
    			if(cmsContentViewBeans != null && !cmsContentViewBeans.isEmpty()){
                    modelAndView.getModelMap().put("footerLastArticles", cmsContentViewBeans);
    			}
                modelAndView.getModelMap().put("allArticleUrl", urlService.generateUrl(FoUrls.ARTICLES, requestData));

                // GEOLOC
//                if(requestData.getGeolocData() != null){
//                    modelAndView.getModelMap().put(ModelConstants.GEOLOC_REMOTE_ADDRESS, requestData.getGeolocData().getRemoteAddress());
//                    modelAndView.getModelMap().put(ModelConstants.GEOLOC_COUNTRY, requestData.getGeolocData().getCountry());
//                    modelAndView.getModelMap().put(ModelConstants.GEOLOC_CITY, requestData.getGeolocData().getCity());
//                }
                
        		// APP NAME
        		modelAndView.getModelMap().addAttribute(Constants.APP_NAME, requestUtil.getAppName(request));
        		Object[] appNameParams = {StringUtils.capitalize(requestUtil.getApplicationName())};
        		modelAndView.getModelMap().addAttribute(Constants.APP_NAME_HTML, coreMessageSource.getCommonMessage(ScopeCommonMessage.APP.getPropertyKey(), "name_html", appNameParams, locale));

        		modelAndView.getModelMap().addAttribute(ModelConstants.LOCALE_LANGUAGE_CODE, locale.getLanguage());
        		modelAndView.getModelMap().addAttribute(ModelConstants.CONTEXT_PATH, request.getContextPath());
        		modelAndView.getModelMap().addAttribute(ModelConstants.THEME, requestUtil.getCurrentTheme(request));
        		Object[] params = {StringUtils.capitalize(requestUtil.getEnvironmentName())};
        		modelAndView.getModelMap().addAttribute(ModelConstants.ENV_NAME, coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO.getPropertyKey(), ScopeWebMessage.COMMON.getPropertyKey(), "header.env.name", params, locale));
         		
         		if(!modelAndView.getModelMap().containsAttribute(ModelConstants.SEO_DATA_VIEW_BEAN)){
         			SeoDataViewBean seoData = frontofficeViewBeanFactory.buildViewSeoData(requestData);
         			if(modelAndView.getModelMap().containsAttribute(ModelConstants.PAGE_TITLE)){
         				String pageTitle = (String) modelAndView.getModelMap().get(ModelConstants.PAGE_TITLE);
         				seoData.setMetaOgTitle(pageTitle);
         				seoData.setPageTitle(pageTitle);
         			}
            		modelAndView.getModelMap().put(ModelConstants.SEO_DATA_VIEW_BEAN, seoData);
         		}
         		
        		modelAndView.getModelMap().put(ModelConstants.URL_SUBMIT_QUICK_SEARCH, urlService.generateUrl(FoUrls.CATALOG_SEARCH, requestUtil.getRequestData(request)));
         		
        		String currentRequestUrl = requestUtil.getCurrentRequestUrl(request);
        		if(modelAndView.getModelMap().get(ModelConstants.ALTERNATE_LINKS_VIEW_BEAN) == null){
        			System.out.println("!!!!! THIS URL NEED AN alternate: " + currentRequestUrl);
        		}
//        		// Alternate Links
//        		if(modelAndView.getModelMap().get(ExtModelConstants.ALTERNATE_LINKS_VIEW_BEAN) == null){
//            		String currentRequestUrl = requestUtil.getCurrentRequestUrl(request);
//            		List<HeadLinkViewBean> alternateLinks = new ArrayList<HeadLinkViewBean>();
//            		if(currentRequestUrl.contains("article")){
//            			
//            		} else {
//            			// default
//                		for (MarketAreaViewBean marketAreaViewBean : allMarketAreaViewBeans) {
//                			for (LocalizationViewBean localizationViewBean : marketAreaViewBean.getLocalizations()) {
//                    			if(!marketAreaViewBean.getCode().equals(currentMarketArea.getCode())){
//                    				String splitUrl[] = currentRequestUrl.split("/");
//                    				String alternateUrl = "/" + marketAreaViewBean.getCode().toLowerCase() + "/" + localizationViewBean.getCode();
//                    				for (int i = 3; i < splitUrl.length; i++) {
//    									alternateUrl = alternateUrl + "/" + splitUrl[i];
//    								}
//                    				String hreflang = localizationViewBean.getLocaleCode().replace("_", "-");
//                    				if(marketAreaViewBean.getCode().equals("INT") && localizationViewBean.getCode().equals("en")){
//                    					hreflang = "x-default";
//                    				}
//                        			HeadLinkViewBean metaLink = new HeadLinkViewBean("alternate", hreflang, alternateUrl);
//                    				alternateLinks.add(metaLink);
//                    			}
//                			}
//        				}
//            		}
//            		if(!alternateLinks.isEmpty()){
//                		modelAndView.getModelMap().addAttribute("alternateLinks", alternateLinks);
//            		}
//        		}
        		
            }

        } catch (Exception e) {
            logger.error("Inject common datas failed", e);
        }

    }

}