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
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.service.AbstractSolrService;
import org.hoteia.qalingo.core.solr.service.ProductMarketingSolrService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SearchFacetViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller("catalogSearchController")
public class CatalogSearchController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();;
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();

    public CatalogSearchController() {
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
    }
    
	@RequestMapping(value = FoUrls.CATALOG_SEARCH_URL, method = RequestMethod.GET)
	public ModelAndView search(final HttpServletRequest request, final Model model, @Valid SearchForm searchForm) throws Exception {
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATALOG_SEARCH.getVelocityPage());
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
		
		String sessionKeyPagedListHolder = "Search_ProductMarketing_PagedListHolder_" + request.getSession().getId();
        String sessionKeyFacet = "Search_ProductMarketing_Facet_" + request.getSession().getId();
        int page = searchForm.getPage() - 1;
        String mode = request.getParameter(Constants.PAGE_VIEW_MODE);
        String sortBy = searchForm.getSortBy();
        String order = searchForm.getOrder();
        int pageSize = searchForm.getPageSize();
        
        final List<String> facetFields = new ArrayList<String>();
        facetFields.add(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_CATEGORIES_CODE);
        facetFields.add(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_OPTION_DEFINITION_CODE);
        
		try {
            PagedListHolder<ProductMarketingViewBean> pagedListHolder;
		    if(searchForm.getPage() == 0){
	            ProductMarketingResponseBean productMarketingResponseBean = null;
	            if(searchForm.getPrice() != null){
	                if(searchForm.getCatalogCategoryList() != null){
	                    productMarketingResponseBean = productMarketingSolrService.searchProductMarketing(ProductMarketingResponseBean.PRODUCT_MARKETING_DEFAULT_SEARCH_FIELD, 
	                            searchForm.getText(), facetFields, searchForm.getPrice().getStartValue(), searchForm.getPrice().getEndValue(), 
	                            searchForm.getCatalogCategoryList());
	                } else {
	                    productMarketingResponseBean = productMarketingSolrService.searchProductMarketing(ProductMarketingResponseBean.PRODUCT_MARKETING_DEFAULT_SEARCH_FIELD, 
	                            searchForm.getText(), facetFields, searchForm.getPrice().getStartValue(), searchForm.getPrice().getEndValue());
	                }
	                
	            } else {
	                productMarketingResponseBean = productMarketingSolrService.searchProductMarketing(ProductMarketingResponseBean.PRODUCT_MARKETING_DEFAULT_SEARCH_FIELD, 
	                                                        searchForm.getText(), facetFields);
	            }
	            
	            pagedListHolder = initList(requestData, sessionKeyPagedListHolder, productMarketingResponseBean, pageSize, sortBy, order);
	            
	            // FACETS
                List<SearchFacetViewBean> facets = frontofficeViewBeanFactory.buildListViewBeanCatalogSearchFacet(requestData, productMarketingResponseBean);
                modelAndView.addObject(AbstractSolrService.SEARCH_FACET_FIELD_LIST, facets);
                request.getSession().setAttribute(sessionKeyFacet, facets);

		    } else {
		        pagedListHolder = (PagedListHolder<ProductMarketingViewBean>) request.getSession().getAttribute(sessionKeyPagedListHolder);
		        
		        // FACETS
                List<SearchFacetViewBean> facets = (List<SearchFacetViewBean>) request.getSession().getAttribute(sessionKeyFacet);
                modelAndView.addObject(AbstractSolrService.SEARCH_FACET_FIELD_LIST, facets);
		    }
			
	        int pageCurrent = pagedListHolder.getPage();
	        if (pageCurrent < page) { 
	        	for (int i = pageCurrent; i < page; i++) {
	        		pagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > page) { 
	        	for (int i = page; i < pageCurrent; i++) {
		        	pagedListHolder.previousPage(); 
				}
	        }
	        
			modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
			modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, pagedListHolder);
			modelAndView.addObject(Constants.SEARCH_TEXT, searchForm.getText());
			modelAndView.addObject(Constants.PAGINATION_PAGE_SIZE, pagedListHolder.getPageSize());
			modelAndView.addObject(Constants.PAGINATION_SORT_BY, sortBy);
			modelAndView.addObject(Constants.PAGINATION_ORDER, order);
			modelAndView.addObject(Constants.PRICE_RANGE_PARAMETER, searchForm.getPrice());
			modelAndView.addObject(Constants.CATALOG_CATEGORIES_PARAMETER, searchForm.getCategoriesFilter());
			
		} catch (Exception e) {
			logger.error("SOLR Error", e);
			return displaySearch(request, model);
		}
		
		loadRecentProducts(requestData, model);
        
        final Cart currentCart = requestData.getCart();
        final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
        modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.CATALOG_SEARCH.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        
        return modelAndView;
	}
	
    protected ModelAndView displaySearch(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATALOG_SEARCH.getVelocityPage());
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
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.CATALOG_SEARCH.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CATALOG_SEARCH.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CATALOG_SEARCH, requestData));
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
    
    // TODO : Temporary
    
    @Autowired
    public ProductService productService;

    @Autowired
    private CatalogCategoryService catalogCategoryService;
    
    @RequestMapping(value = "/**/search-load-catalog-index.html", method = RequestMethod.GET)
    public ModelAndView loadIndex(final HttpServletRequest request, final HttpServletResponse response, Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        final Retailer retailer = requestData.getMarketAreaRetailer();

        List<ProductMarketing> products = productService.findProductMarketings();
        for (Iterator<ProductMarketing> iteratorProductMarketing = products.iterator(); iteratorProductMarketing.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iteratorProductMarketing.next();
            for (Iterator<ProductSku> iteratorProductSku = productMarketing.getProductSkus().iterator(); iteratorProductSku.hasNext();) {
                ProductSku productSku = (ProductSku) iteratorProductSku.next();
                List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId()); 
                productMarketingSolrService.addOrUpdateProductMarketing(productMarketing, catalogCategories, marketArea, retailer);
            }
        }

        return new ModelAndView(new RedirectView(urlService.generateRedirectUrl(FoUrls.CATALOG_SEARCH, requestUtil.getRequestData(request))));
    }
	
}