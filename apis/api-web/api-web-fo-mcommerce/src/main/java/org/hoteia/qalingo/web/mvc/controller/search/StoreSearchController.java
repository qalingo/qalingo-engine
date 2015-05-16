/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;
import org.hoteia.qalingo.core.solr.service.AbstractSolrService;
import org.hoteia.qalingo.core.solr.service.StoreSolrService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("storeSearchController")
public class StoreSearchController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public ProductService productMarketingService;
	
    @Autowired
    public RetailerService retailerService;
    
	@Autowired
	public StoreSolrService storeSolrService;

    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();;

    public StoreSearchController() {
        storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }
    
	@RequestMapping(value = FoUrls.STORE_SEARCH_URL, method = RequestMethod.GET)
	public ModelAndView search(final HttpServletRequest request, final Model model, @Valid SearchForm searchForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_SEARCH.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        // SANITY CHECK
        List<String> evictValues = new ArrayList<String>();
        evictValues.add("*");
        if (StringUtils.isNotEmpty(searchForm.getText())
                && evictValues.contains(searchForm.getText())) {
            return displaySearch(request, model);
        }

        if (StringUtils.isEmpty(searchForm.getText())
		        && searchForm.getPage() == 0) {
			return displaySearch(request, model);
		}

		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKeyPagedListHolder = "Search_Store_PagedListHolder_" + request.getSession().getId();
        String sessionKeyFacet = "Search_Store_Facet_" + request.getSession().getId();
        
        int page = 0;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            // NO NEED
        }
        
        String mode = request.getParameter(Constants.PAGE_VIEW_MODE);
        String cities = request.getParameter("cities");
        String countries = request.getParameter("countries");
        
        List<String> cityList = new ArrayList<String>();
        if (StringUtils.isEmpty(cities)) {
        	cityList = null;
		} else{
			String[] arr = cities.split(",");
			cityList = Arrays.asList(arr);
		}       
        List<String> countryList = new ArrayList<String>();
        if (StringUtils.isEmpty(countries)) {
        	countryList = null;
		} else{
			String[] arr = countries.split(",");
			countryList = Arrays.asList(arr);
		}
        
        int pageSize = searchForm.getPageSize();
        if(pageSize == 0){
            pageSize = 16;
        }
        String sortBy = searchForm.getSortBy();
        String order = searchForm.getOrder();
		
		try {
            PagedListHolder<StoreViewBean> pagedListHolder;
            if(page == 0 || request.getSession().getAttribute(sessionKeyPagedListHolder) == null){
                StoreResponseBean storeResponseBean = null;

                List<String> facetFields = new ArrayList<String>();
                facetFields.add(StoreResponseBean.STORE_DEFAULT_FACET_FIELD);
                facetFields.add(StoreResponseBean.STORE_SECOND_FACET_FIELD);

                String query = getSearchQuery(searchForm.getText());

	            storeResponseBean = storeSolrService.searchStore(query, facetFields, cityList,countryList);
//	            StoreResponseBean storeResponBeanNonFilter = storeSolrService.searchStore(, , facetFields);
	            pagedListHolder = initList(requestData, sessionKeyPagedListHolder, storeResponseBean, pageSize, sortBy, order);
	            
	            // FACETS
//                List<SearchFacetViewBean> facets = frontofficeViewBeanFactory.buildListViewBeanStoreSearchFacet(requestData, storeResponBeanNonFilter);
                List<SearchFacetViewBean> facets = frontofficeViewBeanFactory.buildListViewBeanStoreSearchFacet(requestData, storeResponseBean);
	            modelAndView.addObject(AbstractSolrService.SEARCH_FACET_FIELD_LIST, facets);
	            request.getSession().setAttribute(sessionKeyFacet, facets);
			    
			} else {
			    pagedListHolder = (PagedListHolder<StoreViewBean>) request.getSession().getAttribute(sessionKeyPagedListHolder);
			    
			    // FACETS
                List<SearchFacetViewBean> facets = (List<SearchFacetViewBean>) request.getSession().getAttribute(sessionKeyFacet);
                modelAndView.addObject(AbstractSolrService.SEARCH_FACET_FIELD_LIST, facets);
			}
	        
			int pageListPosition = page - 1;
	        int pageCurrent = pagedListHolder.getPage();
	        if (pageCurrent < pageListPosition) { 
	        	for (int i = pageCurrent; i < pageListPosition; i++) {
	        	    pagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageListPosition) { 
	        	for (int i = pageListPosition; i < pageCurrent; i++) {
	        	    pagedListHolder.previousPage(); 
				}
	        }
	        
			modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
			modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, pagedListHolder);
			modelAndView.addObject(Constants.SEARCH_TEXT, searchForm.getText());
			modelAndView.addObject(Constants.PAGINATION_PAGE_SIZE, pagedListHolder.getPageSize());
			modelAndView.addObject(Constants.PAGINATION_SORT_BY, sortBy);
			modelAndView.addObject(Constants.PAGINATION_ORDER, order);
			modelAndView.addObject(Constants.STORE_CITY_PARAMETER, cities);
			modelAndView.addObject(Constants.STORE_COUNTRY_PARAMETER, countries);
			
		} catch (Exception e) {
			logger.error("SOLR Error", e);
			return displaySearch(request, model);
		}
		
		loadRecentProducts(requestData, model);
        
        final Cart currentCart = requestData.getCart();
        final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
        modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.STORE_SEARCH.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        
        modelAndView.addObject("storeSearchUrl", urlService.generateUrl(FoUrls.STORE_SEARCH, requestData));

        return modelAndView;
	}

	protected String getSearchQuery(String text) {
        return StoreResponseBean.STORE_DEFAULT_SEARCH_FIELD + ":" + text + " AND active:true";
    }

    protected ModelAndView displaySearch(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_SEARCH.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

        modelAndView.addObject(ModelConstants.SEARCH_FORM, formFactory.buildSearchForm(requestData));

        loadRecentProducts(requestData, model);

        final Cart currentCart = requestData.getCart();
        final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
        modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);

        return modelAndView;
    }
    
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData){
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.STORE_LOCATION.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.STORE_LOCATION.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.STORE_LOCATION.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.STORE_SEARCH.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.STORE_SEARCH.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.STORE_SEARCH, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        return breadcrumbViewBean;
    }
    
    /**
     * 
     */
    @ModelAttribute("searchForm")
    protected SearchForm getSearchForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        return formFactory.buildSearchForm(requestData);
    }
	
	private PagedListHolder<StoreViewBean> initList(final RequestData requestData, final String sessionKeyPagedListHolder, final StoreResponseBean storeResponseBean,
			                                        int pageSize, String sortBy, String order) throws Exception {
	    final HttpServletRequest request = requestData.getRequest();
        
        final List<StoreViewBean> storeViewBeans = new ArrayList<StoreViewBean>();
        List<StoreSolr> searchtItems = storeResponseBean.getStoreSolrList();
        for (Iterator<StoreSolr> iterator = searchtItems.iterator(); iterator.hasNext();) {
            StoreSolr storeSolr = (StoreSolr) iterator.next();
            Store store = retailerService.getStoreByCode(storeSolr.getCode(), new FetchPlan(storeFetchPlans));
            if(store != null){
                StoreViewBean storeViewBean = frontofficeViewBeanFactory.buildViewBeanStore(requestData, store);
                storeViewBeans.add(storeViewBean);
            } else {
                // STORE DOESN'T EXIST ANYMORE : CLEAN INDEX
                storeSolrService.removeStore(storeSolr);
            }
        }
        
        PagedListHolder<StoreViewBean> pagedListHolder = new PagedListHolder<StoreViewBean>(storeViewBeans);
        pagedListHolder.setPageSize(pageSize);
        if(StringUtils.isNotEmpty(sortBy)
                && StringUtils.isNotEmpty(order)){
            pagedListHolder.setSort(new MutableSortDefinition(sortBy, true, Constants.PAGE_ORDER_ASC.equalsIgnoreCase(order)));
            pagedListHolder.resort();
        }
        request.getSession().setAttribute(sessionKeyPagedListHolder, pagedListHolder);
        return pagedListHolder;
	}
    
}