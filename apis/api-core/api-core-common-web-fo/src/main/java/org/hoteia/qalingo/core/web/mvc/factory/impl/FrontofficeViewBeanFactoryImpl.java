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
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RecentProductViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorCityFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorCountryFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
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
        final String localeCode = localization.getCode();

        final String productSkuCode = productMarketingSolr.getCode();
        final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
        final ProductMarketing productMarketing = productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing.getCode());

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
    
    public List<CatalogCategoryViewBean> buildListRootCatalogCategories(RequestData requestData, MarketArea marketArea) throws Exception {
    	final List<CatalogCategoryVirtual> categoryVirtuals = catalogCategoryService.findRootVirtualCatalogCategories(marketArea.getId());
    	
    	final List<CatalogCategoryViewBean> catalogCategoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
    	
    	for (CatalogCategoryVirtual catalogCategoryVirtual : categoryVirtuals) {
    		CatalogCategoryViewBean catalogCategoryViewBean = buildCatalogCategoryViewBean(requestData, catalogCategoryVirtual);
    		catalogCategoryViewBeans.add(catalogCategoryViewBean);
		}
    	
    	return catalogCategoryViewBeans;
    }
    
    public List<ProductBrandViewBean> buildListProductBrands(final RequestData requestData, final CatalogCategoryVirtual catalogCategoryVirtual) throws Exception {
    	final List<ProductBrandViewBean> productBrandViewBeans = new ArrayList<ProductBrandViewBean>();
    	
    	List<ProductBrand> productBrands = productService.findProductBrandsByCatalogCategoryCode(catalogCategoryVirtual.getCode());
    	for (ProductBrand productBrand : productBrands) {
    		ProductBrandViewBean productBrandViewBean = buildProductBrandViewBean(requestData, productBrand);
    		productBrandViewBeans.add(productBrandViewBean);
		}
    	return productBrandViewBeans;
    }
    
    @Override
    public List<RecentProductViewBean> buildRecentProductViewBean(
    		final RequestData requestData,
    		final List<String> listCode) throws Exception {
    	final List<RecentProductViewBean> recentProductViewBeans = new ArrayList<RecentProductViewBean>(); 
    	final Localization localization = requestData.getMarketAreaLocalization();
        final String localeCode = localization.getCode();
    	final MarketArea marketArea = requestData.getMarketArea();
    	for (String value : listCode) {
    		RecentProductViewBean recentProductViewBean = new RecentProductViewBean();
    		ProductMarketing productMarketing = productService.getProductMarketingById(value);
    		CatalogCategoryVirtual catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), productMarketing.getCode());
        	recentProductViewBean.setId(productMarketing.getId());
    		recentProductViewBean.setCode(value);
    		recentProductViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, catalogCategory, productMarketing, productMarketing.getDefaultProductSku()));	
        	recentProductViewBean.setI18nName(productMarketing.getI18nName(localeCode));
        	recentProductViewBeans.add(recentProductViewBean);
    	}
    	return recentProductViewBeans;
    }
    
    /**
     * 
     */
    public CatalogBreadcrumbViewBean buildCatalogBreadcrumbViewBean(final RequestData requestData, final CatalogCategoryVirtual catalogCategory) throws Exception {
    	 final Localization localization =  requestData.getMarketAreaLocalization();
    	 final String localizationCode = localization.getCode();
    	 final MarketArea currentMarketArea = requestData.getMarketArea();
    	 final CatalogBreadcrumbViewBean catalogBreadCumViewBean = new CatalogBreadcrumbViewBean();
    	 catalogBreadCumViewBean.setRoot(catalogCategory.isRoot());
    	 catalogBreadCumViewBean.setName(catalogCategory.getI18nName(localizationCode));
		
		 if (catalogCategory.isRoot()) {
			catalogBreadCumViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_AXE, requestData, catalogCategory));
		 } else {
			catalogBreadCumViewBean.setDetailsUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
		 }
		 final CatalogCategoryVirtual parentCatalogCategoryVirtual = catalogCategory.getDefaultParentCatalogCategory();
		 if(!catalogCategory.isRoot() && parentCatalogCategoryVirtual != null){
			 final CatalogCategoryVirtual pareCatalogCategoryVirtualReload = catalogCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), parentCatalogCategoryVirtual.getCode());
			catalogBreadCumViewBean.setDefaultParentCategory(buildCatalogBreadcrumbViewBean(requestData,pareCatalogCategoryVirtualReload));
		 }

    	return catalogBreadCumViewBean;
    }
    
    @Override
    public StoreLocatorFilterBean buildStoreLocatorFilterBean(
    		StoreLocatorViewBean storeLocatorViewBean) {
    	
    	List<StoreViewBean> stores = storeLocatorViewBean.getStores();
    	
    	StoreLocatorFilterBean filter = new StoreLocatorFilterBean();
    	
    	Map<String, StoreLocatorCountryFilterBean> countryFilterMap = new HashMap<String, StoreLocatorCountryFilterBean>();
    	Map<String, StoreLocatorCityFilterBean> cityFilterMap = new HashMap<String, StoreLocatorCityFilterBean>();
    	
    	for (StoreViewBean store : stores) {
			String country = store.getCountry();
			String city = store.getCity();
			StoreLocatorCountryFilterBean countryFilter = null;
			StoreLocatorCityFilterBean cityFilter = null;
			
			if(countryFilterMap.containsKey(country)){
				countryFilter = countryFilterMap.get(country);
			}else{
				countryFilter = new StoreLocatorCountryFilterBean();
				countryFilter.setCode(country);
				filter.addCountry(countryFilter);
				//TODO: set i18nName
				countryFilterMap.put(country, countryFilter);
			}
			
			if(cityFilterMap.containsKey(city)){
				cityFilter = cityFilterMap.get(city);
			}else{
				cityFilter = new StoreLocatorCityFilterBean();
				cityFilter.setName(city);
				countryFilter.addCity(cityFilter);
				//TODO: set code?
				cityFilterMap.put(city, cityFilter);
			}
			
			cityFilter.addStore(store);
		}
    	
    	return filter;
    }

}