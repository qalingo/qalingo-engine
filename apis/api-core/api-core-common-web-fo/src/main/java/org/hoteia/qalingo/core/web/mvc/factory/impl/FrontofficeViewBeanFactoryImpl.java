/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchViewBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 */
@Service("frontofficeViewBeanFactory")
@Transactional
public class FrontofficeViewBeanFactoryImpl extends ViewBeanFactoryImpl implements FrontofficeViewBeanFactory {

    /**
     * 
     */
    @Override
    public List<MenuViewBean> buildMenuViewBeans(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();

        final Locale locale = localization.getLocale();
        final String localeCode = localization.getCode();

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        CatalogVirtual catalogVirtual = catalogService.getCatalogVirtual(marketArea.getId());
        if (catalogVirtual != null) {
            final List<CatalogCategoryVirtual> catalogCategories = catalogVirtual.getCatalogCategories(marketArea.getId());
            if (catalogCategories != null) {
                for (Iterator<CatalogCategoryVirtual> iteratorCatalogCategory = catalogCategories.iterator(); iteratorCatalogCategory.hasNext();) {
                    final CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) iteratorCatalogCategory.next();
                    final CatalogCategoryVirtual catalogCategoryReloaded = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategory.getCode());
                    
                    menu = new MenuViewBean();
                    final String seoCatalogCategoryName = catalogCategoryReloaded.getI18nName(localeCode);
                    menu.setName(seoCatalogCategoryName);
                    menu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategoryReloaded));

                    List<CatalogCategoryVirtual> subCatalogCategories = catalogCategoryReloaded.getCatalogCategories(marketArea.getId());
                    if (subCatalogCategories != null) {
                        List<MenuViewBean> subMenus = new ArrayList<MenuViewBean>();
                        for (Iterator<CatalogCategoryVirtual> iteratorSubCatalogCategory = subCatalogCategories.iterator(); iteratorSubCatalogCategory.hasNext();) {
                            final CatalogCategoryVirtual subCatalogCategory = (CatalogCategoryVirtual) iteratorSubCatalogCategory.next();
                            final CatalogCategoryVirtual subCatalogCategoryReloaded = catalogCategoryService.getVirtualCatalogCategoryByCode(subCatalogCategory.getCode());
                            final MenuViewBean subMenu = new MenuViewBean();
                            final String seoSubCatalogCategoryName = catalogCategoryReloaded.getI18nName(localeCode) + " " + subCatalogCategoryReloaded.getI18nName(localeCode);
                            subMenu.setName(seoSubCatalogCategoryName);
                            subMenu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, subCatalogCategory));
                            subMenus.add(subMenu);
                        }
                        menu.setSubMenus(subMenus);
                    }
                    menuViewBeans.add(menu);
                }
            }
        }

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "our_company", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.OUR_COMPANY, requestData));
        menuViewBeans.add(menu);

        // Set active menu
        String currentUrl = requestUtil.getLastRequestUrl(request);
        for (Iterator<MenuViewBean> iteratorMenu = menuViewBeans.iterator(); iteratorMenu.hasNext();) {
            MenuViewBean menuCheck = (MenuViewBean) iteratorMenu.next();
            menuCheck.setActive(false);
            if (currentUrl != null && currentUrl.contains(menuCheck.getUrl())) {
                menuCheck.setActive(true);
                for (Iterator<MenuViewBean> iteratorSubMenu = menuCheck.getSubMenus().iterator(); iteratorSubMenu.hasNext();) {
                    MenuViewBean subMenu = (MenuViewBean) iteratorSubMenu.next();
                    subMenu.setActive(false);
                    if (currentUrl != null && currentUrl.contains(subMenu.getUrl())) {
                        subMenu.setActive(true);
                    }
                }
            }
        }

        return menuViewBeans;
    }
    
    // SEARCH

    /**
     * 
     */
    public SearchViewBean buildSearchViewBean(final RequestData requestData) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = localization.getLocale();

        final SearchViewBean search = new SearchViewBean();
        search.setTextLabel(getSpecificMessage(ScopeWebMessage.SEARCH, "form.label.text", locale));

        return search;
    }

    /**
     * 
     */
    public List<SearchProductItemViewBean> buildSearchProductItemViewBeans(final RequestData requestData, final ProductMarketingResponseBean productMarketingResponseBean) throws Exception {
        final List<SearchProductItemViewBean> searchProductItems = new ArrayList<SearchProductItemViewBean>();
        List<ProductMarketingSolr> productMarketings = productMarketingResponseBean.getProductMarketingSolrList();
        for (Iterator<ProductMarketingSolr> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketingSolr productMarketingSolr = (ProductMarketingSolr) iterator.next();
            searchProductItems.add(buildSearchProductItemViewBean(requestData, productMarketingSolr));
        }
        return searchProductItems;
    }

    /**
     * 
     */
    public SearchProductItemViewBean buildSearchProductItemViewBean(final RequestData requestData, final ProductMarketingSolr productMarketingSolr) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Retailer retailer = requestData.getMarketAreaRetailer();
        final String localeCode = localization.getCode();

        final String productSkuCode = productMarketingSolr.getCode();
        final ProductSku productSku = productSkuService.getProductSkuByCode(marketArea.getId(), retailer.getId(), productSkuCode);
        final ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(marketArea.getId(), retailer.getId(), productSku.getProductMarketing().getCode());
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing);

        final String productName = productMarketing.getCode();
        final String categoryName = catalogCategory.getI18nName(localeCode);
        final String productSkuName = productMarketing.getDefaultProductSku().getI18nName(localeCode);

        final SearchProductItemViewBean searchProductItemViewBean = new SearchProductItemViewBean();
        searchProductItemViewBean.setName(categoryName + " " + productName + " " + productSkuName);
        searchProductItemViewBean.setDescription(productMarketing.getDescription());
        searchProductItemViewBean.setCode(productSkuCode);

        searchProductItemViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productSku));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, productSku.getCode());

        searchProductItemViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, getParams));

        return searchProductItemViewBean;
    }

    /**
     * 
     */
    public List<SearchFacetViewBean> buildSearchFacetViewBeans(final RequestData requestData, final ProductMarketingResponseBean productMarketingResponseBean) throws Exception {
        final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
        List<FacetField> productFacetFields = productMarketingResponseBean.getProductMarketingSolrFacetFieldList();
        for (Iterator<FacetField> iterator = productFacetFields.iterator(); iterator.hasNext();) {
            FacetField facetField = (FacetField) iterator.next();
            searchFacetViewBeans.add(buildSearchFacetViewBean(requestData, facetField));
        }
        return searchFacetViewBeans;
    }

    /**
     * 
     */
    public SearchFacetViewBean buildSearchFacetViewBean(final RequestData requestData, final FacetField facetField) throws Exception {
        final SearchFacetViewBean searchFacetViewBean = new SearchFacetViewBean();
        searchFacetViewBean.setName(facetField.getName());
        List<String> values = new ArrayList<String>();
        for (Iterator<Count> iterator = facetField.getValues().iterator(); iterator.hasNext();) {
            Count count = (Count) iterator.next();
            values.add(count.getName() + "(" + count.getCount() + ")");
        }
        searchFacetViewBean.setValues(values);
        return searchFacetViewBean;
    }

}