/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RecentProductViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 */
@Controller("productLineController")
public class ProductLineController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService productCategoryService;
	@Autowired
	protected ProductService productService;
	@RequestMapping(FoUrls.CATEGORY_AS_LINE_URL)
	public ModelAndView productLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATEGORY_AS_LINE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        final Cart currentCart = requestUtil.getCurrentCart(request);
        
		final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), categoryCode);
		
		String seoPageMetaKeywords = coreMessageSource.getMessage("page.meta.keywords", locale);
        model.addAttribute("seoPageMetaKeywords", seoPageMetaKeywords);

		String seoPageMetaDescription = coreMessageSource.getMessage("page.meta.description", locale);
        model.addAttribute("seoPageMetaDescription", seoPageMetaDescription);

		String pageTitleKey = "header.title." + "";
		String seoPageTitle = coreMessageSource.getMessage("page.title.prefix", locale) + " - " + coreMessageSource.getMessage(pageTitleKey, locale);
        model.addAttribute("seoPageTitle", seoPageTitle);
        
		final CatalogCategoryViewBean productCategoryViewBean = frontofficeViewBeanFactory.buildCatalogCategoryViewBean(requestUtil.getRequestData(request), productCategory);

		String sortBy = request.getParameter("sortBy");
        String orderBy = request.getParameter("orderBy");
        String pageSizeParameter = request.getParameter("pageSize");
        String pageParameter = request.getParameter("page");
        
		int page = NumberUtils.toInt(pageParameter, 1);
	    int pageSize = NumberUtils.toInt(pageSizeParameter, 1);
		
		List<ProductMarketingViewBean> productMarketings = productCategoryViewBean.getProductMarketings();
		PagedListHolder<ProductMarketingViewBean> productList = new PagedListHolder<ProductMarketingViewBean>(productMarketings);
		productList.setPageSize(pageSize);
		productList.setPage(page-1);		
		productCategoryViewBean.setProductMarketings(productList.getPageList());

		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildCartViewBean(requestUtil.getRequestData(request), currentCart);
        modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
	
		final List<CatalogCategoryViewBean> catalogCategoryViewBeans = frontofficeViewBeanFactory.buildListRootCatalogCategories(requestUtil.getRequestData(request), currentMarketArea);
		model.addAttribute("catalogCategories", catalogCategoryViewBeans);
		
		final CatalogBreadcrumbViewBean catalogBreadcrumbViewBean = frontofficeViewBeanFactory.buildCatalogBreadCumViewBean(requestUtil.getRequestData(request) , productCategory);
		model.addAttribute("breadcrumb", catalogBreadcrumbViewBean);
		
		model.addAttribute(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, productCategoryViewBean);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", productList.getPageCount());
		
		final List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListProductBrands(requestUtil.getRequestData(request), productCategory);
		model.addAttribute("productBrandViewBeans", productBrandViewBeans);
		
		Cookie info=null;
        Cookie[] cookies = request.getCookies();
        Boolean found = false;
        if(cookies !=  null){
	        for(int i=0;i<cookies.length;i++)
	        {
	            info=cookies[i];
	            if(Constants.RECENT_PRODUCT_COOKIE_NAME.equals(info.getName()))
	            {
	                found = true;
	                break;
	            }
	        }
        }   
        List<String> listId = new ArrayList<String>();
        if(found){
        	if(!info.getValue().isEmpty()){
	        	String[] splits = info.getValue().split(" ");
	        	if(splits.length >= 3){
		        	for (int i = splits.length - 1; i >= splits.length - 3 ; i--) {
		        		listId.add(splits[i]);
		        	}
	        	} else {
	        		for (int i = splits.length - 1; i >= 0 ; i--) {
	        			listId.add(splits[i]);
					}
	        	}
        	}
        } 
        List<RecentProductViewBean> recentProductViewBeans = frontofficeViewBeanFactory.buildRecentProductViewBean(requestData, listId);
        model.addAttribute("recentProducts", recentProductViewBeans);
        
		return modelAndView;
	}
    
}