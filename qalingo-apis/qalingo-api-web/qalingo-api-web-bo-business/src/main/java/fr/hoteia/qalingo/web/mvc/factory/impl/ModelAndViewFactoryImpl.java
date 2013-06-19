/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;
import fr.hoteia.qalingo.core.service.LocalizationService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.service.StoreService;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.factory.FormFactory;
import fr.hoteia.qalingo.web.mvc.factory.ModelAndViewFactory;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogCategoryViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CatalogViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductMarketingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductSkuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

/**
 * 
 */
@Service("modelAndViewFactory")
public class ModelAndViewFactoryImpl implements ModelAndViewFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CoreMessageSource coreMessageSource;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected UrlService urlService;
	
	@Autowired
    protected StoreService storeService;
	
	@Autowired
    protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected LocalizationService localizationService;
	
	@Autowired
	protected CatalogCategoryService productCategoryService;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	@Autowired
    protected FormFactory formFactory;
	
	/**
     * 
     */
	public void initCatalogModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogMaster catalogMaster) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		List<CatalogCategoryMaster> productCategories = productCategoryService.findRootCatalogCategories();

		CatalogViewBean catalogViewBean = viewBeanFactory.buildMasterCatalogViewBean(request, currentMarketArea, currentLocalization, catalogMaster, productCategories);
		
		modelAndView.addObject(Constants.CATALOG_VIEW_BEAN, catalogViewBean);
	}
	
	/**
     * 
     */
	public void initCatalogModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogVirtual catalogVirtual) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		
		List<CatalogCategoryVirtual> productCategories = productCategoryService.findRootCatalogCategories(currentMarketArea.getId(), currentRetailer.getId());

		CatalogViewBean catalogViewBean = viewBeanFactory.buildVirtualCatalogViewBean(request, currentMarketArea, currentLocalization, catalogVirtual, productCategories);
		
		modelAndView.addObject(Constants.CATALOG_VIEW_BEAN, catalogViewBean);
	}
	
	/**
     * 
     */
	public void initProductMasterCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryMaster productCategory) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		CatalogCategoryViewBean productCategoryViewBean = viewBeanFactory.buildMasterProductCategoryViewBean(request, currentMarketArea, currentLocalization, productCategory, true);
		
		modelAndView.addObject(Constants.CATALOG_CATEGORY_VIEW_BEAN, productCategoryViewBean);
	}
	
	/**
     * 
     */
	public void initProductVirtualCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryVirtual productCategory) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		CatalogCategoryViewBean productCategoryViewBean = viewBeanFactory.buildVirtualProductCategoryViewBean(request, currentMarketArea, currentLocalization, productCategory, true);
		
		modelAndView.addObject(Constants.CATALOG_CATEGORY_VIEW_BEAN, productCategoryViewBean);
	}
	
	/**
     * 
     */
	public void initProductMarketingModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductMarketing productMarketing) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductMarketingViewBean productMarketingViewBean = viewBeanFactory.buildProductMarketingViewBean(request, currentMarketArea, currentLocalization, productMarketing, true);
		
		modelAndView.addObject(Constants.PRODUCT_MARKETING_VIEW_BEAN, productMarketingViewBean);
	}
	
	/**
     * 
     */
	public void initProductSkuModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final ProductSku productSku) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Localization currentLocalization = requestUtil.getCurrentMarketLocalization(request);
		
		ProductSkuViewBean productSkuViewBean = viewBeanFactory.buildProductSkuViewBean(request, currentMarketArea, currentLocalization, productSku);
		
		modelAndView.addObject(Constants.PRODUCT_SKU_VIEW_BEAN, productSkuViewBean);
	}

}