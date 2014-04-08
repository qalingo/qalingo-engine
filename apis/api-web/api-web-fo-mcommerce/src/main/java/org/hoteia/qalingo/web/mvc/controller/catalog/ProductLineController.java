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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
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
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected ProductService productService;
	
	@RequestMapping(FoUrls.CATEGORY_AS_LINE_URL)
	public ModelAndView productLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATEGORY_AS_LINE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Cart currentCart = requestData.getCart();
        
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
		
		final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogCategory(requestUtil.getRequestData(request), catalogCategory);

		String sortBy = request.getParameter("sortBy");
        String orderBy = request.getParameter("orderBy");
        String pageSizeParameter = request.getParameter("pageSize");
        String pageParameter = request.getParameter("page");
        String mode = request.getParameter("mode");
        
		int page = NumberUtils.toInt(pageParameter, 1) - 1;
	    int pageSize = NumberUtils.toInt(pageSizeParameter, 9);
		
		List<ProductMarketingViewBean> productMarketings = catalogCategoryViewBean.getProductMarketings();
		PagedListHolder<ProductMarketingViewBean> productList = new PagedListHolder<ProductMarketingViewBean>(productMarketings);
		productList.setPageSize(pageSize);
		productList.setPage(page);
		
		int pageCurrent = productList.getPage();
        if (pageCurrent < page) { 
        	for (int i = pageCurrent; i < page; i++) {
        		productList.nextPage(); 
			}
        } else if (pageCurrent > page) { 
        	for (int i = page; i < pageCurrent; i++) {
        		productList.previousPage(); 
			}
        }
		
		catalogCategoryViewBean.setProductMarketings(productList.getPageList());

		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildViewBeanCart(requestUtil.getRequestData(request), currentCart);
        modelAndView.addObject(ModelConstants.CART_VIEW_BEAN, cartViewBean);
	
		final CatalogBreadcrumbViewBean catalogBreadcrumbViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogBreadcrumb(requestUtil.getRequestData(request) , catalogCategory);
		model.addAttribute(ModelConstants.CATALOG_BREADCRUMB_VIEW_BEAN, catalogBreadcrumbViewBean);
		
		model.addAttribute(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("currentPage", page);
		model.addAttribute("mode",mode);
		model.addAttribute("totalPage", productList.getPageCount());
		
		final List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductBrand(requestUtil.getRequestData(request), catalogCategory);
		model.addAttribute(ModelConstants.PRODUCT_BRANDS_VIEW_BEAN, productBrandViewBeans);
		
		final List<String> listProductSkuCodes = requestUtil.getRecentProductSkuCodesFromCookie(request);
        List<RecentProductViewBean> recentProductViewBeans = frontofficeViewBeanFactory.buildListViewBeanRecentProduct(requestData, listProductSkuCodes);
        model.addAttribute(ModelConstants.RECENT_PPRODUCT_MARKETING_VIEW_BEAN, recentProductViewBeans);
        
        // SEO
        model.addAttribute(ModelConstants.PAGE_META_OG_TITLE, catalogCategoryViewBean.getI18nName() );
        
        model.addAttribute(ModelConstants.PAGE_META_OG_DESCRIPTION, catalogCategoryViewBean.getI18nDescription());
        
        model.addAttribute(ModelConstants.PAGE_META_OG_IMAGE, urlService.buildAbsoluteUrl(requestData, catalogCategoryViewBean.getCarouselImage()));

        Object[] params = { catalogCategoryViewBean.getI18nName() };
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CATEGORY_AS_LINE.getKey(), params);
        
		return modelAndView;
	}
    
}