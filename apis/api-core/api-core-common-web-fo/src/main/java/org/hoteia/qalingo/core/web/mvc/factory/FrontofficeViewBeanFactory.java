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

import org.apache.solr.client.solrj.response.FacetField;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RecentProductViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchStoreItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;

public interface FrontofficeViewBeanFactory extends ViewBeanFactory {

    // SEARCH

//    SearchViewBean buildViewBeanSearch(RequestData requestData) throws Exception;

    List<SearchProductItemViewBean> buildListViewBeanSearchProductItem(RequestData requestData, ProductMarketingResponseBean productMarketingResponseBean) throws Exception;

    SearchProductItemViewBean buildViewBeanSearchProductItem(RequestData requestData, ProductMarketingSolr productMarketingSolr) throws Exception;

    List<SearchFacetViewBean> buildListViewBeanCatalogSearchFacet(RequestData requestData, ProductMarketingResponseBean productMarketingResponseBean) throws Exception;

    SearchFacetViewBean buildViewBeanCatalogSearchFacet(RequestData requestData, FacetField facetField) throws Exception;

    List<SearchStoreItemViewBean> buildListViewBeanSearchStoreItem(RequestData requestData, StoreResponseBean storeResponseBean) throws Exception;

    SearchStoreItemViewBean buildViewBeanSearchStoreItem(RequestData requestData, StoreSolr storeSolr) throws Exception;

    List<SearchFacetViewBean> buildListViewBeanStoreSearchFacet(RequestData requestData, StoreResponseBean storeResponseBean) throws Exception;

    SearchFacetViewBean buildViewBeanStoreSearchFacet(RequestData requestData, FacetField facetField) throws Exception;

    List<CatalogCategoryViewBean> buildListViewBeanRootCatalogCategory(RequestData requestData, List<CatalogCategoryVirtual> categoryVirtuals) throws Exception;

    List<ProductBrandViewBean> buildListViewBeanProductBrand(RequestData requestData, CatalogCategoryVirtual catalogCategoryVirtual) throws Exception;

    List<RecentProductViewBean> buildListViewBeanRecentProduct(RequestData requestData, List<String> listId) throws Exception;

    CatalogBreadcrumbViewBean buildViewBeanCatalogBreadcrumb(RequestData requestData, CatalogCategoryVirtual productCategory) throws Exception;

    StoreLocatorFilterBean buildFilterBeanStoreLocator(List<StoreViewBean> stores, Locale locale) throws Exception;
    
}