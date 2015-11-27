/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerMarketArea;
import org.hoteia.qalingo.core.domain.CustomerWishlist;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.hoteia.qalingo.core.fetchplan.customer.FetchPlanGraphCustomer;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;

/**
 * 
 */
@Controller("customerWishListController")
public class CustomerWishListController extends AbstractCustomerController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;
	   
    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();

    public CustomerWishListController() {
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName() + "." + ProductMarketing_.productBrand.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuStorePrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
    }
    
	@RequestMapping(FoUrls.PERSONAL_WISHLIST_URL)
	public ModelAndView customerWishList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.PERSONAL_WISHLIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
		final Customer customer = requestData.getCustomer();
		
        final Customer reloadedCustomer = customerService.getCustomerById(customer.getId(), FetchPlanGraphCustomer.fullCustomerFetchPlan());
		
        final List<ProductSkuViewBean> productSkuViewBeans = new ArrayList<ProductSkuViewBean>();
        final CustomerMarketArea customerMarketArea = customer.getCurrentCustomerMarketArea(marketArea.getId());
        if (customerMarketArea != null) {
            final Set<CustomerWishlist> customerWishlists = customerMarketArea.getWishlistProducts();
            if (Hibernate.isInitialized(customerWishlists) && customerWishlists != null) {
                for (Iterator<CustomerWishlist> iterator = customerWishlists.iterator(); iterator.hasNext();) {
                    final CustomerWishlist customerWishlist = (CustomerWishlist) iterator.next();
                    final ProductSku productSku = productService.getProductSkuByCode(customerWishlist.getProductSkuCode(), new FetchPlan(productSkuFetchPlans));
                    final ProductMarketing productMarketing =  productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                    CatalogCategoryVirtual catalogCategory = null;
                    if(StringUtils.isNotEmpty(customerWishlist.getCatalogCategoryCode()) && "undefined".equalsIgnoreCase(customerWishlist.getCatalogCategoryCode())){
                        catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(customerWishlist.getCatalogCategoryCode(), marketArea.getCatalog().getCode());
                    } 
                    if(catalogCategory == null){
                        catalogCategory = catalogCategoryService.getDefaultVirtualCatalogCategoryByProductSkuId(productSku.getId());
                    }
                    productSkuViewBeans.add(frontofficeViewBeanFactory.buildViewBeanProductSku(requestData, catalogCategory, productMarketing, productSku));
                }
            }
        }
        
		model.addAttribute(ModelConstants.PRODUCT_SKUS_VIEW_BEAN, productSkuViewBeans);

        Object[] params = { customer.getLastname(), customer.getFirstname() };
        overrideDefaultPageTitle(request, modelAndView, FoUrls.PERSONAL_WISHLIST.getKey(), params);

        return modelAndView;
	}

	@RequestMapping(FoUrls.WISHLIST_REMOVE_ITEM_URL)
	public ModelAndView removeFromWishlist(final HttpServletRequest request, final Model model) throws Exception {
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final RequestData requestData = requestUtil.getRequestData(request);
		webManagementService.removeProductSkuFromWishlist(requestData, skuCode);

		final String url = urlService.generateRedirectUrl(FoUrls.PERSONAL_WISHLIST, requestData);
        return new ModelAndView(new RedirectView(url));
	}
	
	@RequestMapping(FoUrls.WISHLIST_ADD_PRODUCT_URL)
	public ModelAndView AddToWishlist(final HttpServletRequest request, final Model model) throws Exception {
        final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final String skuCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE);
		final RequestData requestData = requestUtil.getRequestData(request);
		try {
			webManagementService.addProductSkuToWishlist(requestData, catalogCategoryCode, skuCode);
			
		} catch (Exception e) {
			logger.error("Error with the wishlist, skuCode:" + skuCode, e);
		}
		
		final String url = urlService.generateRedirectUrl(FoUrls.PERSONAL_WISHLIST, requestData);
        return new ModelAndView(new RedirectView(url));
	}

}