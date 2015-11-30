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
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrand_;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.form.SearchForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
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
@Controller("brandLineController")
public class BrandLineController extends AbstractMCommerceController {

	@Autowired
	protected ProductService productService;
	
    protected List<SpecificFetchMode> productBrandFetchPlans = new ArrayList<SpecificFetchMode>();

    public BrandLineController() {
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.attributes.getName()));
        productBrandFetchPlans.add(new SpecificFetchMode(ProductBrand_.assets.getName()));
    }
    
	@RequestMapping(FoUrls.BRAND_LINE_URL)
	public ModelAndView brandLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode, final SearchForm searchForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.BRAND_LINE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        
		final ProductBrand productBrand = productService.getProductBrandByCode(brandCode, new FetchPlan(productBrandFetchPlans));
		if(productBrand != null
                && productBrand.isEnabled()){
	        List<ProductMarketing>  productMarketings = productService.findProductMarketingsByBrandId(currentMarketArea.getId(), productBrand.getId());
	        final ProductBrandViewBean productBrandViewBean = frontofficeViewBeanFactory.buildViewBeanProductBrand(requestUtil.getRequestData(request), productBrand, productMarketings);
	        model.addAttribute(ModelConstants.PRODUCT_BRAND_VIEW_BEAN, productBrandViewBean);
	        
	        overrideDefaultPageTitle(request, modelAndView, FoUrls.BRAND_LINE.getKey());

	        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData, productBrand));
	        
	        return modelAndView;
		}
        final String urlRedirect = urlService.generateRedirectUrl(FoUrls.BRAND_ALL, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
    
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData, final ProductBrand productBrand){
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        Object[] params = { productBrand.getI18nName(localizationCode) };
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.BRAND_LINE.getKey(), params, locale));

        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.BRAND_ALL.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_ALL, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.BRAND_DETAILS.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_DETAILS, requestData, productBrand));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.BRAND_LINE.getKey(), params, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.BRAND_LINE, requestData, productBrand));
        menu.setActive(true);
        menuViewBeans.add(menu);

        return breadcrumbViewBean;
    }
    
}