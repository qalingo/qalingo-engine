/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductMarketingSolrService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchProductItemViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("searchController")
public class SearchController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public ProductService productMarketingService;
	
	@Autowired
	public ProductMarketingSolrService productMarketingSolrService;

//	Commented by Tri.Nguyen: mapping the search method instead. Not using POST method for the searching.
//	@RequestMapping(value = FoUrls.SEARCH_URL, method = RequestMethod.GET)
	public ModelAndView displaySearch(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.SEARCH.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		final SearchViewBean search = frontofficeViewBeanFactory.buildSearchViewBean(requestData);
		modelAndView.addObject("search", search);
		
		modelAndView.addObject("searchForm", formFactory.buildSearchForm(requestData));

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.SEARCH_URL, method = RequestMethod.GET)
	public ModelAndView search(final HttpServletRequest request, final HttpServletResponse response, @Valid SearchForm searchForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.SEARCH.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		if (result.hasErrors()) {
			return displaySearch(request, response, modelMap);
		}

		final SearchViewBean search = frontofficeViewBeanFactory.buildSearchViewBean(requestData);
		modelAndView.addObject("search", search);
		
		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKey = "PagedListHolder_Search_List_ProductMarketing_" + request.getSession().getId();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        
        String mode = request.getParameter(Constants.PAGE_VIEW_MODE);
        String sortBy = request.getParameter(Constants.PAGINATION_SORT_BY);
        String order = request.getParameter(Constants.PAGINATION_ORDER);
		
        //TODO: Tri: how use session list
        
		try {
			ProductMarketingResponseBean productMarketingResponseBean = null;
			if(StringUtils.isEmpty(searchForm.getText())){
				productMarketingResponseBean = productMarketingSolrService.searchProductMarketing();
			}else{
				productMarketingResponseBean = productMarketingSolrService.searchProductMarketing("businessname", searchForm.getText(), "businessname");
			}
			
			modelAndView.addObject(Constants.SEARCH_FACET_FIELD_LIST, frontofficeViewBeanFactory.buildSearchFacetViewBeans(requestData, productMarketingResponseBean));
	        
			PagedListHolder<SearchProductItemViewBean> productsViewBeanPagedListHolder;

	        if(StringUtils.isEmpty(page)){
	        	productsViewBeanPagedListHolder = initList(request, sessionKey, productMarketingResponseBean, new PagedListHolder<SearchProductItemViewBean>());
	        } else {
		        productsViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey);
		        if (productsViewBeanPagedListHolder == null) { 
		        	productsViewBeanPagedListHolder = initList(request, sessionKey, productMarketingResponseBean, productsViewBeanPagedListHolder);
		        }
	        }
	        
	        int pageTarget = NumberUtils.toInt(page, 1) - 1;
	        int pageCurrent = productsViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		productsViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
		        	productsViewBeanPagedListHolder.previousPage(); 
				}
	        }
	        
			modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
			modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, productsViewBeanPagedListHolder);
			modelAndView.addObject(Constants.SEARCH_TEXT, searchForm.getText());
			modelAndView.addObject(Constants.PAGINATION_PAGE_SIZE, productsViewBeanPagedListHolder.getPageSize());
			modelAndView.addObject(Constants.PAGINATION_SORT_BY, sortBy);
			modelAndView.addObject(Constants.PAGINATION_ORDER, order);
			modelAndView.addObject("searchResultCatalogCategories", initCatalogCategoryList(productsViewBeanPagedListHolder));
			
		} catch (Exception e) {
			logger.error("SOLR Error", e);
			return displaySearch(request, response, modelMap);
		}
		
        return modelAndView;
	}
	
    @Autowired
    public ProductService productService;

    @RequestMapping(value = "/**/search-load-index.html", method = RequestMethod.GET)
    public ModelAndView loadIndex(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);

        List<ProductMarketing> products = productService.findProductMarketingsByCatalogCategoryCode(requestData.getMarketArea().getId(), "cate301");
        for (Iterator<ProductMarketing> iterator = products.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            productMarketingSolrService.addOrUpdateProductMarketing(productMarketing);
        }

        products = productService.findProductMarketingsByCatalogCategoryCode(requestData.getMarketArea().getId(), "cate302");
        for (Iterator<ProductMarketing> iterator = products.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            productMarketingSolrService.addOrUpdateProductMarketing(productMarketing);
        }

        return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.SEARCH, requestUtil.getRequestData(request))));
    }
    
    private List<CatalogCategoryViewBean> initCatalogCategoryList(PagedListHolder<SearchProductItemViewBean> productsViewBeanPagedListHolder){
    	List<SearchProductItemViewBean> searchProductItems = productsViewBeanPagedListHolder.getPageList();
    	List<CatalogCategoryViewBean> catalogs = new ArrayList<CatalogCategoryViewBean>();
    	Map<String, Object> catalogMap = new HashMap<String, Object>();
    	for (SearchProductItemViewBean searchProductItemViewBean : searchProductItems) {
			if(!catalogMap.containsKey(searchProductItemViewBean.getCategoryCode())){
				CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();
				catalogCategoryViewBean.setCode(searchProductItemViewBean.getCategoryCode());
				catalogCategoryViewBean.setName(searchProductItemViewBean.getCategoryName());
				catalogs.add(catalogCategoryViewBean);
				catalogMap.put(searchProductItemViewBean.getCategoryCode(), Boolean.TRUE);
			}
		}
    	
    	return catalogs;
    }
	
	private PagedListHolder<SearchProductItemViewBean> initList(final HttpServletRequest request, final String sessionKey, final ProductMarketingResponseBean productMarketingResponseBean,
			PagedListHolder<SearchProductItemViewBean> productsViewBeanPagedListHolder) throws Exception{
		String pageSize = request.getParameter(Constants.PAGINATION_PAGE_SIZE);
		String sortBy = request.getParameter(Constants.PAGINATION_SORT_BY);
        String order = request.getParameter(Constants.PAGINATION_ORDER);
		List<SearchProductItemViewBean> searchProductItems = frontofficeViewBeanFactory.buildSearchProductItemViewBeans(requestUtil.getRequestData(request), productMarketingResponseBean);
		productsViewBeanPagedListHolder = new PagedListHolder<SearchProductItemViewBean>(searchProductItems);
		productsViewBeanPagedListHolder.setPageSize(NumberUtils.toInt(pageSize, Constants.PAGINATION_DEFAULT_PAGE_SIZE));
		productsViewBeanPagedListHolder.setSort(new MutableSortDefinition(sortBy, true, Constants.PAGE_ORDER_ASC.equalsIgnoreCase(order)));
		productsViewBeanPagedListHolder.resort();
        request.getSession().setAttribute(sessionKey, searchProductItems);
        return productsViewBeanPagedListHolder;
	}
	
}