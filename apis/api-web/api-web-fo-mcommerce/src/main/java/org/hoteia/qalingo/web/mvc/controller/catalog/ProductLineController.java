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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSkuPrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogBreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SeoDataViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("productLineController")
public class ProductLineController extends AbstractMCommerceController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected ProductService productService;

    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();

    public ProductLineController() {
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
        
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName() + "." + CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.assets.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategoryProductSkuRels.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategoryProductSkuRels.getName() + "." + CatalogCategoryVirtualProductSkuRel_.pk.getName() +  "." + org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuPk_.productSku.getName()));
    }
    
	@RequestMapping(FoUrls.CATEGORY_AS_LINE_URL)
	public ModelAndView productLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CATEGORY_AS_LINE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Cart currentCart = requestData.getCart();
        
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
		
		if(catalogCategory != null){
    		final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanVirtualCatalogCategory(requestUtil.getRequestData(request), catalogCategory);
    
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
    		
    		loadRecentProducts(requestData, model);
            
            Object[] params = { catalogCategoryViewBean.getI18nName() };
            overrideDefaultMainContentTitle(request, modelAndView, FoUrls.CATEGORY_AS_LINE.getKey(), params);
            
            model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData, catalogCategory));

    		return modelAndView;
	    }
        final String urlRedirect = urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData, CatalogCategoryVirtual catalogCategory) {
        final Localization localization = requestData.getMarketAreaLocalization();
        final String localizationCode = localization.getCode();
        final Locale locale = requestData.getLocale();
        String i18Name = "";
        if(catalogCategory.getParentCatalogCategory() != null){
            i18Name = catalogCategory.getParentCatalogCategory().getI18nName(localizationCode);
        }
        i18Name = i18Name + " - " + catalogCategory.getI18nName(localizationCode);
        Object[] params = { i18Name };

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, "product_line", params, locale));

        List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
        MenuViewBean menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

//        menu = new MenuViewBean();
//        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "xxxxx", locale));
//        menu.setUrl(urlService.generateUrl(FoUrls.xxxx, requestData));
//        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "product_line", params, locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CATEGORY_AS_LINE, requestData, catalogCategory));
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
        List<ProductBrand> productBrands = productService.findAllProductBrandsEnabled();
        List<ProductBrandViewBean> productBrandViewBeans = frontofficeViewBeanFactory.buildListViewBeanProductBrand(requestUtil.getRequestData(request), productBrands);
        return productBrandViewBeans;
    }
    
    @ModelAttribute(ModelConstants.SEO_DATA_VIEW_BEAN)
    protected SeoDataViewBean initSeo(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_CATEGORY_CODE) final String categoryCode) throws Exception {
        SeoDataViewBean seoDataViewBean = super.initSeo(request, model);
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
        final CatalogCategoryViewBean catalogCategoryViewBean = frontofficeViewBeanFactory.buildViewBeanVirtualCatalogCategory(requestUtil.getRequestData(request), catalogCategory);

        // SEO
        String metaOgTitle = catalogCategoryViewBean.getI18nName();
        seoDataViewBean.setMetaOgTitle(metaOgTitle);
        String metaOgDescription = catalogCategoryViewBean.getI18nDescription();
        seoDataViewBean.setMetaOgDescription(metaOgDescription);
        String metaOgImage = catalogCategoryViewBean.getAssetAbsoluteWebPath("PACKSHOT");
        if(StringUtils.isNotEmpty(metaOgImage)){
            seoDataViewBean.setMetaOgImage(urlService.buildAbsoluteUrl(requestData, metaOgImage));
        }
        
        return seoDataViewBean;
    }
    
}