/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.core.solr.service.ProductSolrService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import fr.hoteia.qalingo.web.mvc.form.SearchForm;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchProductItemViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SearchViewBean;

/**
 * 
 */
@Controller("searchController")
public class SearchController extends AbstractMCommerceController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public ProductMarketingService productMarketingService;
	
	@Autowired
	public ProductSolrService productSolrService;

	@RequestMapping(value = FoUrls.SEARCH_URL, method = RequestMethod.GET)
	public ModelAndView displaySearch(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.SEARCH.getVelocityPage());
		
		final SearchViewBean search = viewBeanFactory.buildSearchViewBean(requestUtil.getRequestData(request));
		modelAndView.addObject("search", search);
		
		modelAndView.addObject("searchForm", formFactory.buildSearchForm(request));

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.SEARCH_URL, method = RequestMethod.POST)
	public ModelAndView search(final HttpServletRequest request, final HttpServletResponse response, @Valid SearchForm searchForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.SEARCH.getVelocityPage());

		if (result.hasErrors()) {
			return displaySearch(request, response, modelMap);
		}

		final SearchViewBean search = viewBeanFactory.buildSearchViewBean(requestUtil.getRequestData(request));
		modelAndView.addObject("search", search);
		
		try {
			ProductResponseBean productResponseBean = productSolrService.searchProduct();
			modelAndView.addObject(Constants.SEARCH_FACET_FIELD_LIST, viewBeanFactory.buildSearchFacetViewBeans(requestUtil.getRequestData(request), productResponseBean));
			
			String url = requestUtil.getCurrentRequestUrl(request);
			
			String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
	        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
			PagedListHolder<SearchProductItemViewBean> accountsViewBeanPagedListHolder;

	        if(StringUtils.isEmpty(page)){
	        	accountsViewBeanPagedListHolder = initList(request, sessionKey, productResponseBean, new PagedListHolder<SearchProductItemViewBean>());
	        } else {
	        	
		        accountsViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
		        if (accountsViewBeanPagedListHolder == null) { 
		        	accountsViewBeanPagedListHolder = initList(request, sessionKey, productResponseBean, accountsViewBeanPagedListHolder);
		        }
		        int pageTarget = new Integer(page).intValue() - 1;
		        int pageCurrent = accountsViewBeanPagedListHolder.getPage();
		        if (pageCurrent < pageTarget) { 
		        	for (int i = pageCurrent; i < pageTarget; i++) {
		        		accountsViewBeanPagedListHolder.nextPage(); 
					}
		        } else if (pageCurrent > pageTarget) { 
		        	for (int i = pageTarget; i < pageCurrent; i++) {
			        	accountsViewBeanPagedListHolder.previousPage(); 
					}
		        } 
	        }
			modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
			modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, accountsViewBeanPagedListHolder);
			
		} catch (Exception e) {
			LOG.error("SOLR Error", e);
		}
		
        return modelAndView;
	}
	
	private PagedListHolder<SearchProductItemViewBean> initList(final HttpServletRequest request, final String sessionKey, final ProductResponseBean productResponseBean,
			PagedListHolder<SearchProductItemViewBean> accountsViewBeanPagedListHolder) throws Exception{
		List<SearchProductItemViewBean> searchProductItems = viewBeanFactory.buildSearchProductItemViewBeans(requestUtil.getRequestData(request), productResponseBean);
		accountsViewBeanPagedListHolder = new PagedListHolder<SearchProductItemViewBean>(searchProductItems);
		accountsViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
        request.getSession().setAttribute(sessionKey, searchProductItems);
        return accountsViewBeanPagedListHolder;
	}
	
}