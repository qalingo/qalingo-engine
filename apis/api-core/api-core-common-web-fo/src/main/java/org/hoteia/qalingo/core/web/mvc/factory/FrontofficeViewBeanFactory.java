/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tag;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.FoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.response.ProductSkuResponseBean;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RecentProductViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchStoreItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorCityFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorCountryFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetValueBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 */
@Service("frontofficeViewBeanFactory")
@Transactional
public class FrontofficeViewBeanFactory extends ViewBeanFactory {

    /**
     * 
     */
    @Override
    public CommonViewBean buildViewBeanCommon(final RequestData requestData) throws Exception {
        final CommonViewBean commonViewBean = super.buildViewBeanCommon(requestData);

        // NO CACHE FOR THIS PART

        final String currentThemeResourcePrefixPath = requestUtil.getCurrentThemeResourcePrefixPath(requestData);
        commonViewBean.setThemeResourcePrefixPath(currentThemeResourcePrefixPath);

        commonViewBean.setHomeUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        commonViewBean.setLoginUrl(urlService.generateUrl(FoUrls.LOGIN, requestData));
        commonViewBean.setForgottenPasswordUrl(urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestData));
        commonViewBean.setLogoutUrl(urlService.generateUrl(FoUrls.LOGOUT, requestData));
        commonViewBean.setCreateAccountUrl(urlService.generateUrl(FoUrls.CUSTOMER_CREATE_ACCOUNT, requestData));
        commonViewBean.setCheckoutCreateAccountUrl(urlService.generateUrl(FoUrls.CART_CREATE_ACCOUNT, requestData));
        commonViewBean.setCustomerDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
        commonViewBean.setPersonalDetailsUrl(urlService.generateUrl(FoUrls.PERSONAL_DETAILS, requestData));
        commonViewBean.setContactUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));

        commonViewBean.setContextJsonUrl(urlService.generateUrl(FoUrls.CONTEXT, requestData));

        return commonViewBean;
    }
    
    @Override
    public SeoDataViewBean buildViewSeoData(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Locale locale = requestData.getLocale();

        final SeoDataViewBean seoDataViewBean = super.buildViewSeoData(requestData);
        seoDataViewBean.setCurrentUrl(urlService.buildAbsoluteUrl(requestData, requestUtil.getCurrentRequestUrl(request)));
        
        // TODO : canonical urls
        
        String pageTitle = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_PAGE_TITLE_SITE_NAME, locale);
        seoDataViewBean.setPageTitle(pageTitle);
        String metaAuthor = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_AUTHOR, locale);
        seoDataViewBean.setMetaAuthor(metaAuthor);
        String metaKeywords = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_KEYWORDS, locale);
        seoDataViewBean.setMetaKeywords(metaKeywords);
        String metaDescription = getCommonMessage(ScopeCommonMessage.SEO, FoMessageKey.SEO_META_DESCRIPTION, locale);
        seoDataViewBean.setMetaDescription(metaDescription);

        String metaOgTitle = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_TITLE, locale);
        seoDataViewBean.setMetaOgTitle(metaOgTitle);
        String metaOgDescription = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_DESCRIPTION, locale);
        seoDataViewBean.setMetaOgDescription(metaOgDescription);
        String metaOgImage = getSpecificMessage(ScopeWebMessage.SEO, FoMessageKey.PAGE_META_OG_IMAGE, locale);
        seoDataViewBean.setMetaOgImage(metaOgImage);
        
        return seoDataViewBean;
    }
    
    /**
     * 
     */
    public List<MenuViewBean> buildListViewBeanHeaderNav(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = localization.getLocale();

        final String currentUrl = requestUtil.getCurrentRequestUrl(request);

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        int ordering = 1;
        
        MenuViewBean menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBean.setActive(currentUrl.contains(FoUrls.HOME.getUrlPatternKey()));
        menuViewBean.setOrdering(ordering);
        menuViewBeans.add(menuViewBean);
        
        // Set active menu
        for (MenuViewBean menuCheck : menuViewBeans) {
            menuCheck.setActive(false);
            if (currentUrl.contains(menuCheck.getUrl())) {
                menuCheck.setActive(true);
                for (MenuViewBean subMenu : menuCheck.getSubMenus()) {
                    subMenu.setActive(false);
                    if (currentUrl.contains(subMenu.getUrl())) {
                        subMenu.setActive(true);
                    }
                }
            }
        }

        return menuViewBeans;
    }
   
    /**
     * 
     */
    public List<MenuViewBean> buildListViewBeanHeaderMenu(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = localization.getLocale();

        final String currentUrl = requestUtil.getCurrentRequestUrl(request);

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        int ordering = 1;
        
        MenuViewBean menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBean.setActive(currentUrl.contains(FoUrls.HOME.getUrlPatternKey()));
        menuViewBean.setOrdering(ordering++);
        menuViewBeans.add(menuViewBean);

        CatalogVirtual catalogVirtual = catalogService.getVirtualCatalogbyMarketAreaId(marketArea.getId());
        List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();;
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        if (catalogVirtual != null) {
            final List<CatalogCategoryVirtual> catalogCategories = catalogVirtual.getSortedRootCatalogCategories();
            if (catalogCategories != null) {
                for (final CatalogCategoryVirtual catalogCategory : catalogCategories) {
                    final CatalogCategoryVirtual catalogCategoryReloaded = catalogCategoryService.getVirtualCatalogCategoryById(catalogCategory.getId(), new FetchPlan(categoryVirtualFetchPlans));

                    menuViewBean = new MenuViewBean();
                    final String seoCatalogCategoryName = catalogCategoryReloaded.getI18nName(localizationCode);
                    menuViewBean.setName(seoCatalogCategoryName);
                    menuViewBean.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategoryReloaded));
                    menuViewBean.setOrdering(ordering++);
                    menuViewBean.setCatalog(true);

                    List<CatalogCategoryVirtual> subCatalogCategories = catalogCategoryReloaded.getSortedChildCatalogCategories();
                    if (subCatalogCategories != null) {
                        List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();
                        for (final CatalogCategoryVirtual subCatalogCategory : subCatalogCategories) {
                            final CatalogCategoryVirtual subCatalogCategoryReloaded = catalogCategoryService.getVirtualCatalogCategoryById(subCatalogCategory.getId(), new FetchPlan(categoryVirtualFetchPlans));
                            final MenuViewBean subMenu = new MenuViewBean();
                            final String seoSubCatalogCategoryName = catalogCategoryReloaded.getI18nName(localizationCode) + " " + subCatalogCategoryReloaded.getI18nName(localizationCode);
                            subMenu.setName(seoSubCatalogCategoryName);
                            subMenu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, subCatalogCategoryReloaded));
                            subMenu.setCatalog(true);
                            subMenus.add(subMenu);
                        }
                        menuViewBean.setSubMenus(subMenus);
                    }
                    menuViewBeans.add(menuViewBean);
                }
            }
        }

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "our_company", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.OUR_COMPANY, requestData));
        menuViewBean.setActive(currentUrl.contains(FoUrls.OUR_COMPANY.getUrlPatternKey()));
        menuViewBean.setOrdering(ordering++);
        menuViewBeans.add(menuViewBean);
        
        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBean.setActive(currentUrl.contains(FoUrls.STORE_LOCATION.getUrlPatternKey()));
        menuViewBean.setOrdering(ordering);
        menuViewBeans.add(menuViewBean);

        // Set active menu
        for (MenuViewBean menuCheck : menuViewBeans) {
            menuCheck.setActive(false);
            if (currentUrl.contains(menuCheck.getUrl())) {
                menuCheck.setActive(true);
                for (MenuViewBean subMenu : menuCheck.getSubMenus()) {
                    subMenu.setActive(false);
                    if (currentUrl.contains(subMenu.getUrl())) {
                        subMenu.setActive(true);
                    }
                }
            }
        }

        return menuViewBeans;
    }
    
    /**
     * 
     */
    public List<MenuViewBean> buildListViewBeanFooterMenu(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();
        List<MenuViewBean> MenuViewBeans = new ArrayList<MenuViewBean>();

        String MENU_TYPE_CUSTOMER_CARE = "MENU_TYPE_CUSTOMER_CARE";
        String MENU_TYPE_OUR_COMPANY   = "MENU_TYPE_OUR_COMPANY";
        String MENU_TYPE_PRODUCT       = "MENU_TYPE_PRODUCT";
        String MENU_TYPE_MORE          = "MENU_TYPE_MORE";
        
        int ordering = 1;
        
        MenuViewBean menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "conditionsofuse", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_USE, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.FAQ, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "legal_terms", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.LEGAL_TERMS, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);
        
        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "contactus", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "followus", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering);
        MenuViewBeans.add(menuViewBean);

        return MenuViewBeans;
    }
    
    /**
     * 
     */
    public List<MenuViewBean> buildListViewBeanFooterNav(final RequestData requestData) throws Exception {
        final Locale locale = requestData.getLocale();
        List<MenuViewBean> MenuViewBeans = new ArrayList<MenuViewBean>();

        String MENU_TYPE_CUSTOMER_CARE = "MENU_TYPE_CUSTOMER_CARE";
        String MENU_TYPE_OUR_COMPANY   = "MENU_TYPE_OUR_COMPANY";
        String MENU_TYPE_PRODUCT       = "MENU_TYPE_PRODUCT";
        String MENU_TYPE_MORE          = "MENU_TYPE_MORE";
        
        int ordering = 1;
        
        MenuViewBean menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "conditionsofuse", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.CONDITIONS_OF_USE, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.FAQ, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "store_location", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBean.setType(MENU_TYPE_CUSTOMER_CARE);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "legal_terms", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.LEGAL_TERMS, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);
        
        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "contactus", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);

        menuViewBean = new MenuViewBean();
        menuViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "followus", locale));
        menuViewBean.setUrl(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));
        menuViewBean.setType(MENU_TYPE_OUR_COMPANY);
        menuViewBean.setOrdering(ordering++);
        MenuViewBeans.add(menuViewBean);
        
        return MenuViewBeans;
    }
    
    // SEARCH

    /**
     * 
     */
    public List<SearchFacetViewBean> buildListViewBeanCatalogSearchFacet(final RequestData requestData, final ProductMarketingResponseBean productMarketingResponseBean) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
        final List<FacetField> facetFields = productMarketingResponseBean.getProductMarketingSolrFacetFieldList();
        for (FacetField facetField : facetFields) {
            if (ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_CATEGORIE_CODES.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }

            } else if (ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_OPTION_DEFINITION_CODES.equalsIgnoreCase(facetField.getName())) {

                // GROUP SKU OPTIONS BY TYPE
                Map<String, List<SearchFacetValueBean>> skuOptionsByType = new HashMap<String, List<SearchFacetValueBean>>();
                for (Count value : facetField.getValues()) {
                    String skuOptionDefinitionCode = value.getName();
                    final ProductSkuOptionDefinition productSkuOptionDefinition = productService.getProductSkuOptionDefinitionByCode(skuOptionDefinitionCode);
                    if (productSkuOptionDefinition != null && productSkuOptionDefinition.getOptionDefinitionType() != null) {
                        String key = productSkuOptionDefinition.getOptionDefinitionType().getI18nName(localizationCode);
                        List<SearchFacetValueBean> skuOptions = skuOptionsByType.get(key);
                        if (skuOptions == null) {
                            skuOptions = new ArrayList<SearchFacetValueBean>();
                        }
                        SearchFacetValueBean valueBean = new SearchFacetValueBean(productSkuOptionDefinition.getCode(), productSkuOptionDefinition.getI18nName(localizationCode), productSkuOptionDefinition.getOptionDefinitionType().getCode(), value.getCount());
                        skuOptions.add(valueBean);
                        skuOptionsByType.put(key, skuOptions);
                    }
                }

                for (String key : skuOptionsByType.keySet()) {
                    final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();
                    searchFacetViewBean.setCode(facetField.getName());
                    searchFacetViewBean.setName(key);

                    List<SearchFacetValueBean> skuOptions = skuOptionsByType.get(key);
                    for (SearchFacetValueBean valueBean : skuOptions) {
                        searchFacetViewBean.addValue(valueBean);
                    }
                    if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                        searchFacetViewBeans.add(searchFacetViewBean);
                    }
                }

            } else if (ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_TAG_CODES.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }

            } else if (ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_PRODUCT_BRAND_CODE.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }
            }
        }
        return searchFacetViewBeans;
    }
    
    /**
     * 
     */
    public List<SearchFacetViewBean> buildListViewBeanCatalogSearchFacet(final RequestData requestData, final ProductSkuResponseBean productMarketingResponseBean) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        
        final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
        final List<FacetField> facetFields = productMarketingResponseBean.getProductSkuSolrFacetFieldList();
        for (FacetField facetField : facetFields) {
            if (ProductSkuResponseBean.PRODUCT_SKU_SEARCH_FIELD_CATEGORIE_CODES.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }

            } else if (ProductSkuResponseBean.PRODUCT_SKU_SEARCH_FIELD_OPTION_DEFINITION_CODES.equalsIgnoreCase(facetField.getName())) {

                // GROUP SKU OPTIONS BY TYPE
                Map<String, List<SearchFacetValueBean>> skuOptionsByType = new HashMap<String, List<SearchFacetValueBean>>();
                for (Count value : facetField.getValues()) {
                    String skuOptionDefinitionCode = value.getName();
                    final ProductSkuOptionDefinition productSkuOptionDefinition = productService.getProductSkuOptionDefinitionByCode(skuOptionDefinitionCode);
                    if (productSkuOptionDefinition != null && productSkuOptionDefinition.getOptionDefinitionType() != null) {
                        String key = productSkuOptionDefinition.getOptionDefinitionType().getI18nName(localizationCode);
                        List<SearchFacetValueBean> skuOptions = skuOptionsByType.get(key);
                        if (skuOptions == null) {
                            skuOptions = new ArrayList<SearchFacetValueBean>();
                        }
                        SearchFacetValueBean valueBean = new SearchFacetValueBean(productSkuOptionDefinition.getCode(), productSkuOptionDefinition.getI18nName(localizationCode), productSkuOptionDefinition.getOptionDefinitionType().getCode(), value.getCount());
                        skuOptions.add(valueBean);
                        skuOptionsByType.put(key, skuOptions);
                    }
                }

                for (String key : skuOptionsByType.keySet()) {
                    final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();
                    searchFacetViewBean.setCode(facetField.getName());
                    searchFacetViewBean.setName(key);

                    List<SearchFacetValueBean> skuOptions = skuOptionsByType.get(key);
                    for (SearchFacetValueBean valueBean : skuOptions) {
                        searchFacetViewBean.addValue(valueBean);
                    }
                    if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                        searchFacetViewBeans.add(searchFacetViewBean);
                    }
                }

            } else if (ProductSkuResponseBean.PRODUCT_SKU_SEARCH_FIELD_TAG_CODES.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }

            } else if (ProductSkuResponseBean.PRODUCT_SKU_SEARCH_FIELD_PRODUCT_BRAND_CODE.equalsIgnoreCase(facetField.getName())) {
                SearchFacetViewBean searchFacetViewBean = buildViewBeanCatalogSearchFacet(requestData, facetField);
                if (searchFacetViewBean.getValues() != null && !searchFacetViewBean.getValues().isEmpty()) {
                    searchFacetViewBeans.add(searchFacetViewBean);
                }
            }
        }
        return searchFacetViewBeans;
    }
    
    public void handleSelectedFacet(List<SearchFacetViewBean> facets, List<String> selectedFilterFacets) {
		for(SearchFacetViewBean searchFacet : facets){
			for(SearchFacetValueBean searchFacetValue : searchFacet.getValues()){
				if(selectedFilterFacets.contains(searchFacetValue.getCode())){
					searchFacetValue.setSelected(true);
				} else {
				    searchFacetValue.setSelected(false);
				}
			}
		}
	}

    /**
     * 
     */
    public SearchFacetViewBean buildViewBeanCatalogSearchFacet(final RequestData requestData, final FacetField facetField) throws Exception {
        final Locale locale = requestData.getLocale();
        final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();
        searchFacetViewBean.setCode(facetField.getName());
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final CatalogVirtual catalog = requestData.getMarketArea().getCatalog();
        
        if(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_CATEGORIE_CODES.equalsIgnoreCase(facetField.getName())){
            List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName() + "." + CatalogCategoryVirtual_.categoryMaster.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
            categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.attributes.getName()));

        	searchFacetViewBean.setName(getSpecificMessage(ScopeWebMessage.FACET_FIELD, facetField.getName().toLowerCase(), locale));
            for (Count value : facetField.getValues()) {
                String specificCatalogCategoryCode = value.getName();
                if (specificCatalogCategoryCode.contains(catalog.getCode())) {
                    String categoryCode = specificCatalogCategoryCode.replace(catalog.getCode() + "_", "");
                    final CatalogCategoryVirtual catalogCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, catalog.getCode(), new FetchPlan(categoryVirtualFetchPlans));
                    if (catalogCategoryVirtual != null) {
                        SearchFacetValueBean valueBean = new SearchFacetValueBean(catalogCategoryVirtual.getCode(), catalogCategoryVirtual.getI18nName(localizationCode), value.getCount());
                        searchFacetViewBean.addValue(valueBean);
                    }
                }
            }
            
        } else if(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_OPTION_DEFINITION_CODES.equalsIgnoreCase(facetField.getName())){

            // PARENT METHODE
            
        } else if(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_TAG_CODES.equalsIgnoreCase(facetField.getName())){
            searchFacetViewBean.setName(getSpecificMessage(ScopeWebMessage.FACET_FIELD, facetField.getName().toLowerCase(), locale));
            List<SearchFacetValueBean> values = new ArrayList<SearchFacetValueBean>();
            for (Count value : facetField.getValues()) {
                String tagCode = value.getName();
                final Tag tag = referentialDataService.getTagByCode(tagCode);
                if (tagCode != null) {
                    SearchFacetValueBean valueBean = new SearchFacetValueBean(tag.getCode(), tag.getI18nName(localizationCode), value.getCount());
                    searchFacetViewBean.addValue(valueBean);
                }
            }
        } else if(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_PRODUCT_BRAND_CODE.equalsIgnoreCase(facetField.getName())){
            searchFacetViewBean.setName(getSpecificMessage(ScopeWebMessage.FACET_FIELD, facetField.getName().toLowerCase(), locale));
            List<SearchFacetValueBean> values = new ArrayList<SearchFacetValueBean>();
            for (Count value : facetField.getValues()) {
                String productBrandCode = value.getName();
                final ProductBrand productBrand = productService.getProductBrandByCode(productBrandCode);
                if (productBrand != null) {
                    SearchFacetValueBean valueBean = new SearchFacetValueBean(productBrand.getCode(), productBrand.getI18nName(localizationCode), value.getCount());
                    searchFacetViewBean.addValue(valueBean);
                }
            }
        }
        
        return searchFacetViewBean;
    }
    
    /**
     * 
     */
    public SearchStoreItemViewBean buildViewBeanSearchStoreItem(final RequestData requestData, final StoreSolr storeSolr) throws Exception {

        final String storeCode = storeSolr.getCode();
        final Store store = retailerService.getStoreByCode(storeCode);
        
        final SearchStoreItemViewBean searchItemViewBean = new SearchStoreItemViewBean();
        searchItemViewBean.setName(storeSolr.getName());
        searchItemViewBean.setCode(storeCode);

        searchItemViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.STORE_DETAILS, requestData, store));

        return searchItemViewBean;
    }
    
    /**
     * 
     */
    public List<SearchFacetViewBean> buildListViewBeanStoreSearchFacet(final RequestData requestData, final StoreResponseBean storeResponseBean) throws Exception {
        final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
        List<FacetField> facetFields = storeResponseBean.getStoreSolrFacetFieldList();
        for (FacetField facetField : facetFields) {
            searchFacetViewBeans.add(buildViewBeanStoreSearchFacet(requestData, facetField));
        }
        return searchFacetViewBeans;
    }

    /**
     * 
     */
    public SearchFacetViewBean buildViewBeanStoreSearchFacet(final RequestData requestData, final FacetField facetField) throws Exception {
        final Locale locale = requestData.getLocale();
        final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();

        // TODO : Denis : facet like country ? city ? online/corner etc
        if(StoreResponseBean.STORE_CITY_FACET_FIELD.equalsIgnoreCase(facetField.getName())){
        	searchFacetViewBean.setName(getSpecificMessage(ScopeWebMessage.FACET_FIELD, facetField.getName().toLowerCase(), locale));
            List<SearchFacetValueBean> values = new ArrayList<SearchFacetValueBean>();
            for (Count count : facetField.getValues()) {
                SearchFacetValueBean valueBean = new SearchFacetValueBean(count.getName(), count.getName(), count.getCount());
                values.add(valueBean);
            }
            Collections.sort(values, new Comparator<SearchFacetValueBean>() {
                @Override
                public int compare(SearchFacetValueBean o1, SearchFacetValueBean o2) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
            });
            searchFacetViewBean.setValues(values);
            searchFacetViewBean.setCode(StoreResponseBean.STORE_CITY_FACET_FIELD_CODE);
        }
        
        if(StoreResponseBean.STORE_COUNTRY_FACET_FIELD.equalsIgnoreCase(facetField.getName())){
        	searchFacetViewBean.setName(getSpecificMessage(ScopeWebMessage.FACET_FIELD, facetField.getName().toLowerCase(), locale));
            List<SearchFacetValueBean> values = new ArrayList<SearchFacetValueBean>();
            for (Count count : facetField.getValues()) {
                SearchFacetValueBean valueBean = new SearchFacetValueBean(count.getName(), count.getName(), count.getCount());
                values.add(valueBean);
            }
            Collections.sort(values, new Comparator<SearchFacetValueBean>() {
				@Override
				public int compare(SearchFacetValueBean o1, SearchFacetValueBean o2) {
					return o1.getLabel().compareTo(o2.getLabel());
				}
			});
            searchFacetViewBean.setValues(values);
            searchFacetViewBean.setCode(StoreResponseBean.STORE_COUNTRY_FACET_FIELD_CODE);
        }
        return searchFacetViewBean;
    }
    
    public List<CatalogCategoryViewBean> buildListViewBeanRootCatalogCategory(final RequestData requestData, final List<CatalogCategoryVirtual> catalogCategories, final FetchPlan categoryFetchPlan, final FetchPlan productFetchPlan, final FetchPlan skuFetchPlan) throws Exception {
        final List<CatalogCategoryViewBean> catalogCategoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (CatalogCategoryVirtual catalogCategoryVirtual : catalogCategories) {
            CatalogCategoryViewBean catalogCategoryViewBean = buildViewBeanVirtualCatalogCategory(requestData, catalogCategoryVirtual);
            catalogCategoryViewBeans.add(catalogCategoryViewBean);
        }
        return catalogCategoryViewBeans;
    }

    public List<ProductBrandViewBean> buildListViewBeanProductBrand(final RequestData requestData, final List<ProductBrand> productBrands) throws Exception {
        final List<ProductBrandViewBean> productBrandViewBeans = new ArrayList<ProductBrandViewBean>();
        for (ProductBrand productBrand : productBrands) {
            ProductBrandViewBean productBrandViewBean = buildViewBeanProductBrand(requestData, productBrand);
            productBrandViewBeans.add(productBrandViewBean);
        }
        return productBrandViewBeans;
    }
    
    public List<RecentProductViewBean> buildListViewBeanRecentProduct(final RequestData requestData, final List<String> cookieProductValues, FetchPlan categoryVirtualFetchPlans, FetchPlan productMarketingFetchPlans, FetchPlan productSkuFetchPlans) throws Exception {
    	final List<RecentProductViewBean> recentProductViewBeans = new ArrayList<RecentProductViewBean>(); 
    	final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
    	for (String cookieProductValue : cookieProductValues) {
    	    String virtualCatalogCode = requestUtil.decodeRecentProductCookieVirtualCatalogCode(cookieProductValue);
            String virtualCategoryCode = requestUtil.decodeRecentProductCookieVirtualCategoryCode(cookieProductValue);
            String productMarketingCode = requestUtil.decodeRecentProductCookieProductMarketingCode(cookieProductValue);
            String productSkuCode = requestUtil.decodeRecentProductCookieProductSkuCode(cookieProductValue);
    		RecentProductViewBean recentProductViewBean = new RecentProductViewBean();
            final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSkuCode, productSkuFetchPlans);
            if(reloadedProductSku != null){
                final ProductMarketing productMarketing = productService.getProductMarketingByCode(productMarketingCode, productMarketingFetchPlans);
                final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(virtualCategoryCode, virtualCatalogCode, categoryVirtualFetchPlans);
                if(productMarketing.getId() != null){
                    recentProductViewBean.setId(productMarketing.getId().toString());
                }
                recentProductViewBean.setCode(productMarketing.getCode());
                recentProductViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));   
                recentProductViewBean.setI18nName(productMarketing.getI18nName(localizationCode));
                
                List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(reloadedProductSku.getId()); 
                
                if(catalogCategories.contains(catalogCategory)
                        && catalogCategory.getCatalog().getCode().equals(virtualCatalogCode)){
                    recentProductViewBeans.add(recentProductViewBean);
                }
            }
    	}
    	return recentProductViewBeans;
    }
    
    /**
     * 
     */
    public CatalogBreadcrumbViewBean buildViewBeanCatalogBreadcrumb(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
    	 final Localization localization =  requestData.getMarketAreaLocalization();
    	 final String localizationCode = localization.getCode();
    	 final CatalogBreadcrumbViewBean catalogBreadCumViewBean = new CatalogBreadcrumbViewBean();
    	 catalogBreadCumViewBean.setRoot(catalogCategory.isRoot());
    	 catalogBreadCumViewBean.setCode(catalogCategory.getCode());
         catalogBreadCumViewBean.setName(catalogCategory.getI18nName(localizationCode));
		
		 if (catalogCategory.isRoot()) {
			catalogBreadCumViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategory));
		 } else {
			catalogBreadCumViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
		 }
		 final CatalogCategoryVirtual parentCatalogCategoryVirtual = catalogCategory.getParentCatalogCategory();
		 if(!catalogCategory.isRoot() && parentCatalogCategoryVirtual != null){
			 final CatalogCategoryVirtual pareCatalogCategoryVirtualReload = catalogCategoryService.getVirtualCatalogCategoryById(parentCatalogCategoryVirtual.getId());
			catalogBreadCumViewBean.setDefaultParentCategory(buildViewBeanCatalogBreadcrumb(requestData, pareCatalogCategoryVirtualReload));
		 }

    	return catalogBreadCumViewBean;
    }
    
    public StoreLocatorFilterBean buildFilterBeanStoreLocator(final List<StoreViewBean> stores, final Locale locale) throws Exception {
        final StoreLocatorFilterBean filter = new StoreLocatorFilterBean();

        final Map<String, StoreLocatorCountryFilterBean> countryFilterMap = new HashMap<String, StoreLocatorCountryFilterBean>();
        final Map<String, StoreLocatorCityFilterBean> cityFilterMap = new HashMap<String, StoreLocatorCityFilterBean>();

        for (StoreViewBean store : stores) {
            String country = store.getCountry();
            String city = store.getCity();
            StoreLocatorCountryFilterBean countryFilter;
            StoreLocatorCityFilterBean cityFilter;

            if (countryFilterMap.containsKey(country)) {
                countryFilter = countryFilterMap.get(country);
            } else {
                countryFilter = new StoreLocatorCountryFilterBean();
                countryFilter.setCode(country);
                String countryLabel = referentialDataService.getCountryByLocale(country, locale);
                countryFilter.setName(countryLabel);
                filter.addCountry(countryFilter);
                countryFilterMap.put(country, countryFilter);
            }

            if (cityFilterMap.containsKey(city)) {
                cityFilter = cityFilterMap.get(city);
            } else {
                cityFilter = new StoreLocatorCityFilterBean();
                cityFilter.setCode(handleCityCode(country));
                cityFilter.setName(city);
                countryFilter.addCity(cityFilter);
                cityFilterMap.put(city, cityFilter);
            }
            cityFilter.addStore(store);
        }
        filter.sortCountries();
        return filter;
    }
    
    protected String handleCityCode(String cityName) throws Exception {
        if (StringUtils.isNotEmpty(cityName)) {
            cityName = cityName.replaceAll(" ", "-");
            cityName = cityName.replaceAll("_", "-");
            cityName = cityName.replaceAll("[àáâãäå]", "a");
            cityName = cityName.replaceAll("[ç]", "c");
            cityName = cityName.replaceAll("[èéêë]", "e");
            cityName = cityName.replaceAll("[ìíîï]", "i");
            cityName = cityName.replaceAll("[ðòóôõö]", "o");
            cityName = cityName.replaceAll("[ùúûü]", "u");
            cityName = cityName.replaceAll("[ýÿ]", "y");
            cityName = cityName.toLowerCase().trim();
        }
        return cityName;
    }

}