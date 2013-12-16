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

import org.apache.solr.client.solrj.response.FacetField;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
//import org.hoteia.qalingo.core.web.mvc.viewbean.CategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchViewBean;

public interface FrontofficeViewBeanFactory extends ViewBeanFactory {

	// SEARCH
	
	SearchViewBean buildSearchViewBean(RequestData requestData) throws Exception;

	List<SearchProductItemViewBean> buildSearchProductItemViewBeans(RequestData requestData, ProductMarketingResponseBean productMarketingResponseBean) throws Exception;
	
	SearchProductItemViewBean buildSearchProductItemViewBean(RequestData requestData, ProductMarketingSolr productMarketingSolr) throws Exception;
	
	List<SearchFacetViewBean> buildSearchFacetViewBeans(RequestData requestData, ProductMarketingResponseBean productMarketingResponseBean) throws Exception;
	
	SearchFacetViewBean buildSearchFacetViewBean(RequestData requestData, FacetField facetField) throws Exception;
	
/*	List<CategoryViewBean> buildVirtualCategoryViewBeans(RequestData requestData, List<CatalogCategoryVirtual> categories, boolean fullPopulate) throws Exception;
	
	CategoryViewBean buildVirtualCategoryViewBean(RequestData requestData, CatalogCategoryVirtual categories, boolean fullPopulate) throws Exception;
*/
}