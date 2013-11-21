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
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
import org.hoteia.qalingo.core.solr.response.ProductResponseBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchViewBean;

public interface FrontofficeViewBeanFactory extends ViewBeanFactory {

	// SEARCH
	
	SearchViewBean buildSearchViewBean(RequestData requestData) throws Exception;

	List<SearchProductItemViewBean> buildSearchProductItemViewBeans(RequestData requestData, ProductResponseBean productResponseBean) throws Exception;
	
	SearchProductItemViewBean buildSearchProductItemViewBean(RequestData requestData, ProductSkuSolr productSolr) throws Exception;
	
	List<SearchFacetViewBean> buildSearchFacetViewBeans(RequestData requestData, ProductResponseBean productResponseBean) throws Exception;
	
	SearchFacetViewBean buildSearchFacetViewBean(RequestData requestData, FacetField facetField) throws Exception;

}