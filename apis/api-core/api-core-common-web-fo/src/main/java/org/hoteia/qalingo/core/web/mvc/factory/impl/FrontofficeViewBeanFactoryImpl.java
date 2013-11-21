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

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
import org.hoteia.qalingo.core.solr.response.ProductResponseBean;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
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

    // SEARCH

    /**
     * 
     */
    public SearchViewBean buildSearchViewBean(final RequestData requestData) throws Exception {
        final Localization localization = requestData.getLocalization();
        final Locale locale = localization.getLocale();

        final SearchViewBean search = new SearchViewBean();
        search.setTextLabel(getSpecificMessage(ScopeWebMessage.SEARCH, "form.label.text", locale));

        return search;
    }

    /**
     * 
     */
    public List<SearchProductItemViewBean> buildSearchProductItemViewBeans(final RequestData requestData, final ProductResponseBean productResponseBean) throws Exception {
        final List<SearchProductItemViewBean> searchProductItems = new ArrayList<SearchProductItemViewBean>();
        List<ProductSkuSolr> productSkus = productResponseBean.getProductSolrList();
        for (Iterator<ProductSkuSolr> iterator = productSkus.iterator(); iterator.hasNext();) {
            ProductSkuSolr productSkuSolr = (ProductSkuSolr) iterator.next();
            searchProductItems.add(buildSearchProductItemViewBean(requestData, productSkuSolr));
        }
        return searchProductItems;
    }

    /**
     * 
     */
    public SearchProductItemViewBean buildSearchProductItemViewBean(final RequestData requestData, final ProductSkuSolr productSkuSolr) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Localization localization = requestData.getLocalization();
        final Retailer retailer = requestData.getRetailer();
        final String localeCode = localization.getCode();

        final String productSkuCode = productSkuSolr.getCode();
        final ProductSku productSku = productSkuService.getProductSkuByCode(marketArea.getId(), retailer.getId(), productSkuCode);
        final ProductMarketing productMarketing = productMarketingService.getProductMarketingByCode(marketArea.getId(), retailer.getId(), productSku.getProductMarketing().getCode());
        final CatalogCategoryVirtual productCategory = productCategoryService.getDefaultVirtualCatalogCategoryByProductMarketing(marketArea.getId(), retailer.getId(), productMarketing);

        final String productName = productMarketing.getCode();
        final String categoryName = productCategory.getI18nName(localeCode);
        final String productSkuName = productMarketing.getDefaultProductSku().getI18nName(localeCode);

        final SearchProductItemViewBean searchProductItemViewBean = new SearchProductItemViewBean();
        searchProductItemViewBean.setName(categoryName + " " + productName + " " + productSkuName);
        searchProductItemViewBean.setDescription(productMarketing.getDescription());
        searchProductItemViewBean.setCode(productSkuCode);

        searchProductItemViewBean.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, productCategory, productMarketing, productSku));

        Map<String, String> getParams = new HashMap<String, String>();
        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, productSku.getCode());

        searchProductItemViewBean.setAddToCartUrl(urlService.generateUrl(FoUrls.CART_ADD_ITEM, requestData, getParams));

        return searchProductItemViewBean;
    }

    /**
     * 
     */
    public List<SearchFacetViewBean> buildSearchFacetViewBeans(final RequestData requestData, final ProductResponseBean productResponseBean) throws Exception {
        final List<SearchFacetViewBean> searchFacetViewBeans = new ArrayList<SearchFacetViewBean>();
        List<FacetField> productFacetFields = productResponseBean.getProductSolrFacetFieldList();
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