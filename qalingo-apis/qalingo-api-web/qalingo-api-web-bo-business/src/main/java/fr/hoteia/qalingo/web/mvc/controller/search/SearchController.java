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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.BoPageConstants;
import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import fr.hoteia.qalingo.web.mvc.viewbean.GlobalSearchViewBean;

/**
 * 
 */
@Controller("searchController")
public class SearchController extends AbstractBusinessBackofficeController {

	@RequestMapping(value = "/global-search.html*", method = RequestMethod.POST)
	public ModelAndView search(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.SEARCH_VELOCITY_PAGE);

		final String contentText = getSpecificMessage(ScopeWebMessage.SEARCH, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		String searchText = request.getParameter("search-text");
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Shippings";
		
		PagedListHolder<GlobalSearchViewBean> globalSearchViewBeanPagedListHolder = new PagedListHolder<GlobalSearchViewBean>();
		
        if(StringUtils.isEmpty(page)){
        	globalSearchViewBeanPagedListHolder = initList(request, sessionKey, searchText);
    		
        } else {
        	globalSearchViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (globalSearchViewBeanPagedListHolder == null) { 
	        	globalSearchViewBeanPagedListHolder = initList(request, sessionKey, searchText);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = globalSearchViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		globalSearchViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		globalSearchViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGE_URL, url);
		modelAndView.addObject(Constants.PAGE_PAGED_LIST_HOLDER, globalSearchViewBeanPagedListHolder);
		
        return modelAndView;
	}
	

	private PagedListHolder<GlobalSearchViewBean> initList(final HttpServletRequest request, String sessionKey, String searchText) throws Exception{
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		PagedListHolder<GlobalSearchViewBean> globalSearchViewBeanPagedListHolder = new PagedListHolder<GlobalSearchViewBean>();
		
		final List<GlobalSearchViewBean> globalSearchViewBeans = viewBeanFactory.buildGlobalSearchViewBean(request, currentMarketArea, currentLocalization, currentRetailer, searchText);

		globalSearchViewBeanPagedListHolder = new PagedListHolder<GlobalSearchViewBean>(globalSearchViewBeans);
		globalSearchViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, globalSearchViewBeanPagedListHolder);
        
        return globalSearchViewBeanPagedListHolder;
	}
    
}