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

import fr.hoteia.qalingo.core.BoPageConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.rest.pojo.CatalogJsonPojo;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;
import fr.hoteia.qalingo.core.service.CatalogCategoryService;
import fr.hoteia.qalingo.core.service.CatalogService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.ProductCategoryForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller
public class CatalogController extends AbstractBusinessBackofficeController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected JsonFactory jsonFactory;

	@Autowired
	protected CatalogService productCatalogService;
	
	@Autowired
	protected CatalogCategoryService productCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = "/manage-master-catalog.html*", method = RequestMethod.GET)
	public ModelAndView manageMasterCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Locale locale = requestUtil.getCurrentLocale(request);

		final CatalogMaster catalogMaster = currentMarketArea.getVirtualCatalog().getCatalogMaster();

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		modelAndViewFactory.initCatalogModelAndView(request, modelAndView, catalogMaster);

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
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		final CatalogVirtual catalogVirtual = currentMarketArea.getVirtualCatalog();

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initCatalogModelAndView(request, modelAndView, catalogVirtual);
		
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
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_DETAILS_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		final String productCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);

		final CatalogCategoryMaster productCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-master-category-edit.html*", method = RequestMethod.GET)
	public ModelAndView editMasterProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		final String productCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		final CatalogCategoryMaster productCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		for (Iterator<CatalogCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
			CatalogCategoryMaster productCategoryMaster = (CatalogCategoryMaster) iterator.next();
			if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
				iterator.remove();
			}
		}
		Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
			@Override
			public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, productCategory));

		return modelAndView;
	}

	@RequestMapping(value = "/add-master-catalog-category.html*", method = RequestMethod.GET)
	public ModelAndView addMasterProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);

		final String parentProductCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		
		List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
			@Override
			public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		if(StringUtils.isNotEmpty(parentProductCategoryCode)){
			final CatalogCategoryMaster parentProductCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
			if(parentProductCategory != null){
				// Child category: We have parent informations - we prepare the child category IHM
				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, parentProductCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request, parentProductCategory, null));
				return modelAndView;
			}
		}

		// No parent informations - we prepare the root category IHM
		
		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request));

		return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-master-category-form.html*", method = RequestMethod.POST)
	public ModelAndView masterProductCategoryForm(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductCategoryForm productCategoryForm,
												  BindingResult result, ModelMap modelMap) throws Exception {
		
		// TODO : Denis : refactoring, clean
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String productCategoryId = productCategoryForm.getId();
		final String parentProductCategoryCode = productCategoryForm.getDefaultParentCategoryCode();

		if(StringUtils.isNotEmpty(productCategoryId)){
			final String productCategoryCode = productCategoryForm.getCode();
			final CatalogCategoryMaster productCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

			List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
			for (Iterator<CatalogCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
				CatalogCategoryMaster productCategoryMaster = (CatalogCategoryMaster) iterator.next();
				if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
					iterator.remove();
				}
			}
			Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
				@Override
				public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
					if(o1 != null && o2 != null){
						return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
					}
					return 0;
				}
			});
			modelAndView.addObject("categories", categories);
			
			// UPDATE CATEORY
			if (result.hasErrors()) {
				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentProductCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					return modelAndView;
				}
			}
			
			// UPDATE CATEGORY
			try {
//				ProductCategoryMaster productCategoryMaster = new ProductCategoryMaster();
				webBackofficeService.updateProductCategory(currentMarketArea, currentRetailer, currentLocalization, productCategory, productCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.buildProductMasterCategoryDetailsUrl(productCategoryCode);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.error.message",  locale));

				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentProductCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, productCategory);
					return modelAndView;
				}
				
			}

		} else {
			final CatalogCategoryMaster parentProductCategory = productCategoryService.getMasterCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);

			List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
			Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
				@Override
				public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
					if(o1 != null && o2 != null){
						return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
					}
					return 0;
				}
			});
			modelAndView.addObject("categories", categories);
			
			// CREATE A NEW CATEORY
			if (result.hasErrors()) {
				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
				}
			}
			
			// SAVE CATEGORY
			try {
				CatalogCategoryMaster productCategoryMaster = new CatalogCategoryMaster();
				webBackofficeService.createProductCategory(currentMarketArea, currentLocalization, parentProductCategory, productCategoryMaster, productCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
				
				final String productCategoryCode = productCategoryForm.getCode();
				final String urlRedirect = backofficeUrlService.buildProductMasterCategoryDetailsUrl(productCategoryCode);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.add.error.message", locale));
				
				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentProductCategoryCode)){
					// CHIELD CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					modelAndViewFactory.initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
				}
				
			}
		}
	}
	
	@RequestMapping(value = "/catalog-virtual-category-details.html*", method = RequestMethod.GET)
	public ModelAndView productVirtualCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_DETAILS_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Locale locale = requestUtil.getCurrentLocale(request);

		final String productCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);

		final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-virtual-category-edit.html*", method = RequestMethod.GET)
	public ModelAndView editVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);

		final String productCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);

		List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		for (Iterator<CatalogCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
			CatalogCategoryMaster productCategoryMaster = (CatalogCategoryMaster) iterator.next();
			if(productCategoryMaster.getCode().equalsIgnoreCase(productCategoryCode)){
				iterator.remove();
			}
		}
		Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
			@Override
			public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
		modelAndView.addObject("productCategoryForm", formFactory.buildCatalogCategoryForm(request, productCategory));

		return modelAndView;
	}

	@RequestMapping(value = "/add-virtual-catalog-category.html*", method = RequestMethod.GET)
	public ModelAndView addVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);

		final String parentProductCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		
		List<CatalogCategoryMaster> categories = productCategoryService.findMasterCategoriesByMarketIdAndRetailerId(currentMarketArea.getId(), currentRetailer.getId());
		Collections.sort(categories, new Comparator<CatalogCategoryMaster>() {
			@Override
			public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
		if(StringUtils.isNotEmpty(parentProductCategoryCode)){
			final CatalogCategoryVirtual parentProductCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), parentProductCategoryCode);
			if(parentProductCategory != null){
				final String pageKey = BoPageConstants.CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, parentProductCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildCatalogCategoryForm(request, parentProductCategory, null));
				return modelAndView;
			}
		}

		final String pageKey = "catalog";
		final String title = getSpecificMessage(ScopeWebMessage.CATALOG_CATEGORY, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("productCategoryForm", formFactory.buildProductCategoryForm(request));
		return modelAndView;
	}
	
	@RequestMapping(value = "/catalog-virtual-category-form.html*", method = RequestMethod.POST)
	public ModelAndView editVirtualProductCategory(final HttpServletRequest request, final HttpServletResponse response, @Valid ProductCategoryForm productCategoryForm,
												   BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.CATALOG_CATEGORY_FORM_VELOCITY_PAGE);
		
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = requestUtil.getCurrentLocale(request);
		
		final String productCategoryCode = productCategoryForm.getCode();

		final String pageKey = BoPageConstants.CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		if(StringUtils.isNotEmpty(productCategoryCode)){
			if (result.hasErrors()) {
				final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);
				modelAndViewFactory.initProductVirtualCategoryModelAndView(request, modelAndView, productCategory);
				modelAndView.addObject("productCategoryForm", formFactory.buildCatalogCategoryForm(request, null, productCategory));
				return modelAndView;
			}

			// SANITY CHECK
			final CatalogCategoryVirtual productCategory = productCategoryService.getVirtualCatalogCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), productCategoryCode);
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
	
}