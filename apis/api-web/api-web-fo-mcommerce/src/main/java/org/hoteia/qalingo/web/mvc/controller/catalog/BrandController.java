/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrand_;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSkuPrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("brandController")
public class BrandController extends AbstractMCommerceController {

	@Autowired
	protected ProductService productService;
	
    protected List<SpecificFetchMode> productBrandFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();

    public BrandController() {
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.assets.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
    }
    
    @RequestMapping(FoUrls.BRAND_ALL_URL)
    public ModelAndView allBrand(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.BRAND_ALL.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.BRAND_ALL.getKey());

        final List<ProductBrand> productBrands = productService.findAllProductBrands(new FetchPlan(productBrandFetchPlans));
        final List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductBrand(requestData, productBrands);
        model.addAttribute(ModelConstants.PRODUCT_BRANDS_VIEW_BEAN, productBrandViewBeans);
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "all_brand", locale));
        
        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "all_brand", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_ALL, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        breadcrumbViewBean.setMenus(menuViewBeans);
        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, breadcrumbViewBean);
        
        return modelAndView;
    }
    
	@RequestMapping(FoUrls.BRAND_DETAILS_URL)
	public ModelAndView brandDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.BRAND_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		final ProductBrand productBrand = productService.getProductBrandByCode(brandCode, new FetchPlan(productBrandFetchPlans));
		final ProductBrandViewBean productBrandViewBean = frontofficeViewBeanFactory.buildViewBeanProductBrand(requestUtil.getRequestData(request), productBrand);
		model.addAttribute(ModelConstants.PRODUCT_BRAND_VIEW_BEAN, productBrandViewBean);
		
		List<ProductMarketing> productMarketings = productService.findProductMarketingsByBrandCode(brandCode, new FetchPlan(productMarketingFetchPlans));
		List<ProductMarketingViewBean> productMarketingViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductMarketing(requestData, productMarketings);
        model.addAttribute(ModelConstants.PRODUCT_MARKETINGS_VIEW_BEAN, productMarketingViewBeans);

        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.BRAND_DETAILS.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData, productBrand));

        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData, ProductBrand productBrand) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        Object[] params = { productBrand.getI18nName(localizationCode) };

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "brand_details", params, locale));

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "all_brand", locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_ALL, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "brand_details", params, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
        menu.setActive(true);
        menuViewBeans.add(menu);

        breadcrumbViewBean.setMenus(menuViewBeans);
        return breadcrumbViewBean;
    }
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.PRODUCT_BRANDS_VIEW_BEAN)
    protected List<ProductBrandViewBean> brandList(final HttpServletRequest request, final Model model) throws Exception {
        List<ProductBrand> productBrands = productService.findAllProductBrands();
        List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductBrand(requestUtil.getRequestData(request), productBrands);
        return productBrandViewBeans;
    }

}