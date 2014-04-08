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

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productAxeController")
public class ProductAxeController extends AbstractMCommerceController {

	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@RequestMapping(FoUrls.CATEGORY_AS_AXE_URL)
	public ModelAndView productAxe(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATEGORY_AS_AXE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());

        final List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductBrand(requestUtil.getRequestData(request), catalogCategory);
        model.addAttribute(ModelConstants.PRODUCT_BRANDS_VIEW_BEAN, productBrandViewBeans);

        final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanCatalogCategory(requestUtil.getRequestData(request), catalogCategory);
        model.addAttribute(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);

        // SEO
        model.addAttribute(ModelConstants.PAGE_META_OG_TITLE, catalogCategoryViewBean.getI18nName() );
        
        model.addAttribute(ModelConstants.PAGE_META_OG_DESCRIPTION, catalogCategoryViewBean.getI18nDescription());
        
        model.addAttribute(ModelConstants.PAGE_META_OG_IMAGE, urlService.buildAbsoluteUrl(requestData, catalogCategoryViewBean.getCarouselImage()));

        Object[] params = { catalogCategoryViewBean.getI18nName() };
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.CATEGORY_AS_AXE.getKey(), params);

        return modelAndView;
    }
    
}