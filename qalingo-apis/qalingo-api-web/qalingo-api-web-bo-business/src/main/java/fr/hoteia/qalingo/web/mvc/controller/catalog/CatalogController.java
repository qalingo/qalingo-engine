/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.catalog;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import fr.hoteia.qalingo.core.rest.pojo.CatalogJsonPojo;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;
import fr.hoteia.qalingo.core.service.ProductCatalogService;
import fr.hoteia.qalingo.core.service.ProductCategoryService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller
public class CatalogController extends AbstractQalingoController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected JsonFactory jsonFactory;

	@Autowired
	protected ProductCatalogService productCatalogService;
	
	@Autowired
	protected ProductCategoryService productCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = "/manage-master-catalog.html*", method = RequestMethod.GET)
	public ModelAndView manageMasterCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/catalog");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final CatalogMaster catalogMaster = currentMarketArea.getVirtualCatalog().getCatalogMaster();
		
		final String titleKeyPrefixSufix = "business.catalog";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initCatalogModelAndView(request, modelAndView, catalogMaster);
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, catalogMaster.getBusinessName());

		ObjectMapper mapper = new ObjectMapper();
		try {
			CatalogJsonPojo catalogJsonPojo = jsonFactory.buildJsonCatalog(catalogMaster);
			String catalog = mapper.writeValueAsString(catalogJsonPojo);
			modelAndView.addObject("catalogJson", catalog);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
        return modelAndView;
	}
	
	@RequestMapping(value = "/manage-virtual-catalog.html*", method = RequestMethod.GET)
	public ModelAndView manageVirtualCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/catalog");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final CatalogVirtual catalogVirtual = currentMarketArea.getVirtualCatalog();

		final String titleKeyPrefixSufix = "business.catalog";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initCatalogModelAndView(request, modelAndView, catalogVirtual);
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, catalogVirtual.getBusinessName());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			CatalogJsonPojo catalogJsonPojo = jsonFactory.buildJsonCatalog(catalogVirtual, currentMarketArea.getId());
			String catalog = mapper.writeValueAsString(catalogJsonPojo);
			modelAndView.addObject("catalogJson", catalog);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
        return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-master-category-details.html*", method = RequestMethod.GET)
	public ModelAndView productCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-details");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final String productCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);

		final ProductCategoryMaster productCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		final String titleKeyPrefixSufix = "business.product.category.details";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, productCategory.getBusinessName());
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-master-category-edit.html*", method = RequestMethod.GET)
	public ModelAndView editMasterProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final String productCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		final ProductCategoryMaster productCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		for (Iterator<ProductCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
			ProductCategoryMaster productCategoryMaster = (ProductCategoryMaster) iterator.next();
			if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
				iterator.remove();
			}
		}
		Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
			@Override
			public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		final String titleKeyPrefixSufix = "business.product.category.edit";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, productCategory));
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, productCategory.getBusinessName());

		return modelAndView;
	}

	@RequestMapping(value = "/add-master-product-category.html*", method = RequestMethod.GET)
	public ModelAndView addMasterProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final String parentProductCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		
		List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
			@Override
			public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		if(StringUtils.isNotEmpty(parentProductCategoryCode)){
			final ProductCategoryMaster parentProductCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
			if(parentProductCategory != null){
				// Child category: We have parent informations - we prepare the child category IHM
				final String titleKeyPrefixSufix = "business.product.category.add";
				initPage(request, response, modelAndView, titleKeyPrefixSufix);
				modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, parentProductCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, parentProductCategory, null));
				overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
				return modelAndView;
			}
		}

		// No parent informations - we prepare the root category IHM
		final String titleKeyPrefixSufix = "business.product.category.add";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request));
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");

		return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-master-category-form.html*", method = RequestMethod.POST)
	public ModelAndView masterProductCategoryForm(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductCategoryForm productCategoryForm,
												  BindingResult result, ModelMap modelMap) throws Exception {
		
		// TODO : Denis : refactoring, clean
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String productCategoryId = productCategoryForm.getId();
		final String parentProductCategoryCode = productCategoryForm.getDefaultParentCategoryCode();

		if(StringUtils.isNotEmpty(productCategoryId)){
			final String productCategoryCode = productCategoryForm.getCode();
			final ProductCategoryMaster productCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

			List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
			for (Iterator<ProductCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
				ProductCategoryMaster productCategoryMaster = (ProductCategoryMaster) iterator.next();
				if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
					iterator.remove();
				}
			}
			Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
				@Override
				public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
					if(o1 != null && o2 != null){
						return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
					}
					return 0;
				}
			});
			modelAndView.addObject("categories", categories);
			
			// UPDATE CATEORY
			if (result.hasErrors()) {
				final String titleKeyPrefixSufix = "business.product.category.edit";
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					final ProductCategoryMaster parentProductCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");
					return modelAndView;
				}
			}
			
			// UPDATE CATEGORY
			try {
//				ProductCategoryMaster productCategoryMaster = new ProductCategoryMaster();
				webBackofficeService.updateProductCategory(currentMarketArea, currentRetailer, currentLocalization, productCategory, productCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.product.category.edit.success.message", null, locale));
				
				final String urlRedirect = backofficeUrlService.buildProductMasterCategoryDetailsUrl(productCategoryCode);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.product.category.edit.error.message", null, locale));
				
				final String titleKeyPrefixSufix = "business.product.category.add";
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					final ProductCategoryMaster parentProductCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");
					return modelAndView;
				}
				
			}

		} else {
			final ProductCategoryMaster parentProductCategory = productCategoryService.getMasterProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);

			List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
			Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
				@Override
				public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
					if(o1 != null && o2 != null){
						return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
					}
					return 0;
				}
			});
			modelAndView.addObject("categories", categories);
			
			// CREATE A NEW CATEORY
			if (result.hasErrors()) {
				final String titleKeyPrefixSufix = "business.product.category.add";
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");
					return modelAndView;
				}
			}
			
			// SAVE CATEGORY
			try {
				ProductCategoryMaster productCategoryMaster = new ProductCategoryMaster();
				webBackofficeService.createProductCategory(currentMarketArea, currentLocalization, parentProductCategory, productCategoryMaster, productCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.product.category.add.success.message", null, locale));
				
				final String productCategoryCode = productCategoryForm.getCode();
				final String urlRedirect = backofficeUrlService.buildProductMasterCategoryDetailsUrl(productCategoryCode);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.product.category.add.error.message", null, locale));
				
				final String titleKeyPrefixSufix = "business.product.category.add";
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initPage(request, response, modelAndView, titleKeyPrefixSufix);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");
					return modelAndView;
				}
				
			}
		}
	}
	
	@RequestMapping(value = "/catalog-virtual-category-details.html*", method = RequestMethod.GET)
	public ModelAndView productVirtualCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-details");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final String productCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);

		final ProductCategoryVirtual productCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		final String titleKeyPrefixSufix = "business.product.category.details";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, productCategory.getBusinessName());
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-virtual-category-edit.html*", method = RequestMethod.GET)
	public ModelAndView editVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final String productCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		final ProductCategoryVirtual productCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		for (Iterator<ProductCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
			ProductCategoryMaster productCategoryMaster = (ProductCategoryMaster) iterator.next();
			if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
				iterator.remove();
			}
		}
		Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
			@Override
			public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		final String titleKeyPrefixSufix = "business.product.category.edit";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, null, productCategory));
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, productCategory.getBusinessName());

		return modelAndView;
	}

	@RequestMapping(value = "/add-virtual-product-category.html*", method = RequestMethod.GET)
	public ModelAndView addVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final String parentProductCategoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		
		List<ProductCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		Collections.sort(categories, new Comparator<ProductCategoryMaster>() {
			@Override
			public int compare(ProductCategoryMaster o1, ProductCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		if(StringUtils.isNotEmpty(parentProductCategoryCode)){
			final ProductCategoryVirtual parentProductCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
			if(parentProductCategory != null){
				final String titleKeyPrefixSufix = "business.product.category.add";
				initPage(request, response, modelAndView, titleKeyPrefixSufix);
				modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, parentProductCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, parentProductCategory, null));
				overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, parentProductCategory.getBusinessName());
				return modelAndView;
			}
		}

		final String titleKeyPrefixSufix = "business.product.category.add";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request));
		overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, "Root");
		return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-virtual-category-form.html*", method = RequestMethod.POST)
	public ModelAndView editVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductCategoryForm productCategoryForm,
												   BindingResult result, ModelMap modelMap) throws Exception {

		final String titleKeyPrefixSufix = "business.product.category.edit";
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		
		final String productCategoryCode = productCategoryForm.getCode();

		if(StringUtils.isNotEmpty(productCategoryCode)){
			if (result.hasErrors()) {
				ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-category-form");
				final ProductCategoryVirtual productCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);
				initPage(request, response, modelAndView, titleKeyPrefixSufix);
				modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, null, productCategory));
				overrideSpecificSeo(request, modelAndView, titleKeyPrefixSufix, productCategory.getBusinessName());
				return modelAndView;
			}

			// SANITY CHECK
			final ProductCategoryVirtual productCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);
			if(productCategory != null){
				// UPDATE PRODUCT MARKETING
				webBackofficeService.updateProductCategory(currentMarketArea, currentRetailer, currentLocalization, productCategory, productCategoryForm);
				
			} else {
				// CREATE PRODUCT MARKETING
				webBackofficeService.createProductCategory(currentMarketArea, currentLocalization, productCategory, productCategoryForm);

			}
		}
		
		final String urlRedirect = backofficeUrlService.buildManageMasterCatalogUrl();
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
	 * 
	 */
	protected void overrideSpecificSeo(final HttpServletRequest request, final ModelAndView modelAndView, final String titleKeyPrefixSufix, String productName) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();

		String pageTitleKey = "header.title." + titleKeyPrefixSufix;
		String appName = (String) modelAndView.getModelMap().get(Constants.APP_NAME);
		Object[] params = {productName};
		String headerTitle = coreMessageSource.getMessage(pageTitleKey, params, locale);
        modelAndView.addObject("seoPageTitle", appName + " - " +  headerTitle);
        modelAndView.addObject("mainContentTitle", headerTitle);
	}
    
}
