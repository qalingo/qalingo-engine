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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("catalogController")
public class CatalogController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CatalogService catalogService;
	
	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = BoUrls.MASTER_CATALOG_URL, method = RequestMethod.GET)
	public ModelAndView manageMasterCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATALOG.getVelocityPage());

	    modelAndView.addObject("catalogType", "master");

        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATALOG.getKey(), null);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATALOG_URL, method = RequestMethod.GET)
	public ModelAndView manageVirtualCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATALOG.getVelocityPage());
		
        modelAndView.addObject("catalogType", "virtual");

        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATALOG.getKey(), null);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView catalogCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

		final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode());

		// SANITY CHECK 
		if(catalogCategory == null){
            final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
            return new ModelAndView(new RedirectView(url));
		}
		
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

		modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData));

		initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
		
        Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATEGORY_DETAILS.getKey(), params);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView editMasterCatalogCategory(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode());

	      // SANITY CHECK 
        if(catalogCategory == null){
            final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
            return new ModelAndView(new RedirectView(url));
        }
        
		List<CatalogCategoryMaster> categories = catalogCategoryService.findAllMasterCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
		for (Iterator<CatalogCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
			CatalogCategoryMaster catalogCategoryMaster = (CatalogCategoryMaster) iterator.next();
			if(catalogCategoryMaster.getCode().equalsIgnoreCase(catalogCategoryCode)){
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

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData));

		initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, null, catalogCategory));

        Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATEGORY_EDIT.getKey(), params);

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.MASTER_CATEGORY_ADD_URL, method = RequestMethod.GET)
	public ModelAndView addMasterCatalogCategory(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_ADD.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();

		final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		
		List<CatalogCategoryMaster> categories = catalogCategoryService.findAllMasterCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
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
		
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

		if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
			final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getMasterCatalogCode());
			if(parentCatalogCategory != null){
				// Child category: We have parent informations - we prepare the child category IHM
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
				modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, parentCatalogCategory, null));
				return modelAndView;
			}
		}

		// No parent informations - we prepare the root category IHM
		
        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData));

        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATEGORY_ADD.getKey(), null);

		initProductMasterCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, null, null));

		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView masterCatalogCategoryForm(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm,
												  BindingResult result, ModelMap modelMap) throws Exception {
		
		// TODO : Denis : refactoring, clean
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
		
		final String catalogCategoryId = catalogCategoryForm.getId();
		final String parentCatalogCategoryCode = catalogCategoryForm.getDefaultParentCategoryCode();

		if(StringUtils.isNotEmpty(catalogCategoryId)){
			final String catalogCategoryCode = catalogCategoryForm.getCode();
			final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode());

		     // SANITY CHECK 
	        if(catalogCategory == null){
	            final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
	            return new ModelAndView(new RedirectView(url));
	        }
	        
			// UPDATE CATEORY
			if (result.hasErrors()) {
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getMasterCatalogCode());
					initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
					return modelAndView;
				}
			}
			
			// UPDATE CATEGORY
			try {
//				CatalogCategoryMaster catalogCategoryMaster = new CatalogCategoryMaster();
				webBackofficeService.updateCatalogCategory(requestData, currentMarketArea, currentRetailer, currentLocalization, catalogCategory, catalogCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, catalogCategory);;
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.error.message",  locale));

				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getMasterCatalogCode());
					initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
					return modelAndView;
				}
				
			}

		} else {
			final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getMasterCatalogCode());

			// CREATE A NEW CATEORY
			if (result.hasErrors()) {
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
				}
			}
			
			// SAVE CATEGORY
			try {
				CatalogCategoryMaster catalogCategoryMaster = new CatalogCategoryMaster();
				webBackofficeService.createCatalogCategory(requestData, currentMarketArea, currentLocalization, parentCatalogCategory, catalogCategoryMaster, catalogCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, catalogCategoryMaster);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.add.error.message", locale));
				
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, null);
					return modelAndView;
				}
				
			}
		}
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productVirtualCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());

	    // SANITY CHECK 
        if(catalogCategory == null){
            final String url = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData);
            return new ModelAndView(new RedirectView(url));
        }
        
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

        Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATEGORY_DETAILS.getKey(), params);
        
		initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView editVirtualCatalogCategory(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());

        // SANITY CHECK 
        if(catalogCategory == null){
            final String url = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData);
            return new ModelAndView(new RedirectView(url));
        }
        
		List<CatalogCategoryVirtual> categories = catalogCategoryService.findAllVirtualCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
		for (Iterator<CatalogCategoryVirtual> iterator = categories.iterator(); iterator.hasNext();) {
		    CatalogCategoryVirtual catalogCategoryVirtual = (CatalogCategoryVirtual) iterator.next();
			if(catalogCategoryVirtual.getCode().equalsIgnoreCase(catalogCategoryCode)){
				iterator.remove();
			}
		}
		Collections.sort(categories, new Comparator<CatalogCategoryVirtual>() {
			@Override
			public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

        Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATEGORY_EDIT.getKey(), params);
        
		initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, null, catalogCategory));

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_ADD_URL, method = RequestMethod.GET)
	public ModelAndView addVirtualCatalogCategory(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_ADD.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();

		final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		
        List<CatalogCategoryVirtual> categories = catalogCategoryService.findAllVirtualCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
		Collections.sort(categories, new Comparator<CatalogCategoryVirtual>() {
			@Override
			public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
				if(o1 != null && o2 != null){
					return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));	
				}
				return 0;
			}
		});
		modelAndView.addObject("categories", categories);
		
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

		if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
			final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
			if(parentCatalogCategory != null){
				final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				initProductVirtualCategoryModelAndView(request, modelAndView, parentCatalogCategory);
				modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, parentCatalogCategory, null));
				return modelAndView;
			}
		}

		final String pageKey = "catalog";
		final String title = getSpecificMessage(ScopeWebMessage.CATALOG_CATEGORY, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATEGORY_ADD.getKey(), null);

		initProductVirtualCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_FORM, backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, null, null));
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView editVirtualCatalogCategory(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm,
												   BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
		final Locale locale = requestData.getLocale();
		
        final String catalogCategoryId = catalogCategoryForm.getId();
        final String parentCatalogCategoryCode = catalogCategoryForm.getDefaultParentCategoryCode();

        if(StringUtils.isNotEmpty(catalogCategoryId)){
            final String catalogCategoryCode = catalogCategoryForm.getCode();
            final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());

             // SANITY CHECK 
            if(catalogCategory == null){
                final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
                return new ModelAndView(new RedirectView(url));
            }
            
            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, catalogCategory));

            // UPDATE CATEORY
            if (result.hasErrors()) {
                final String pageKey = BoUrls.MASTER_CATALOG_KEY;
                final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
                overrideSeoTitle(request, modelAndView, title);
                
                if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
                    // CHIELD CATEGORY
                    final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
                    initProductVirtualCategoryModelAndView(request, modelAndView, parentCatalogCategory);
                    return modelAndView;
                    
                } else {
                    // ROOT CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
                    return modelAndView;
                }
            }
            
            // UPDATE CATEGORY
            try {
//              CatalogCategoryVirtual catalogCategoryVirtual = new CatalogCategoryVirtual();
                webBackofficeService.updateCatalogCategory(requestData, currentMarketArea, currentRetailer, currentLocalization, catalogCategory, catalogCategoryForm);
                addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.success.message", locale));
                
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, catalogCategory);;
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } catch (UniqueConstraintCodeCategoryException e) {
                addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.error.message",  locale));

                final String pageKey = BoUrls.MASTER_CATALOG_KEY;
                final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
                overrideSeoTitle(request, modelAndView, title);
                
                if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
                    // CHIELD CATEGORY
                    final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
                    initProductVirtualCategoryModelAndView(request, modelAndView, parentCatalogCategory);
                    return modelAndView;
                    
                } else {
                    // ROOT CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
                    return modelAndView;
                }
                
            }

        } else {
            final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());

            List<CatalogCategoryVirtual> categories = catalogCategoryService.findAllVirtualCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
            Collections.sort(categories, new Comparator<CatalogCategoryVirtual>() {
                @Override
                public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
                    if(o1 != null && o2 != null){
                        return o1.getI18nName(currentLocalization.getCode()).compareTo(o2.getI18nName(currentLocalization.getCode()));  
                    }
                    return 0;
                }
            });
            modelAndView.addObject("categories", categories);
            
            // CREATE A NEW CATEORY
            if (result.hasErrors()) {
                final String pageKey = BoUrls.MASTER_CATALOG_KEY;
                final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
                overrideSeoTitle(request, modelAndView, title);
                
                if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
                    // CHIELD CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, null);
                    return modelAndView;
                    
                } else {
                    // ROOT CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, null);
                    return modelAndView;
                }
            }
            
            // SAVE CATEGORY
            try {
                CatalogCategoryVirtual catalogCategoryVirtual = new CatalogCategoryVirtual();
//                webBackofficeService.createCatalogCategory(currentMarketArea, currentLocalization, parentCatalogCategory, catalogCategoryVirtual, catalogCategoryForm);
                addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
                
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, catalogCategoryVirtual);
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } catch (UniqueConstraintCodeCategoryException e) {
                addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.add.error.message", locale));
                
                final String pageKey = BoUrls.MASTER_CATALOG_KEY;
                final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
                overrideSeoTitle(request, modelAndView, title);
                
                if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
                    // CHIELD CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, null);
                    return modelAndView;
                    
                } else {
                    // ROOT CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, null);
                    return modelAndView;
                }
                
            }
        }
	}
	
	/**
     * 
     */
	protected void initProductMasterCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryMaster catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanMasterCatalogCategory(requestUtil.getRequestData(request), catalogCategory, true, false);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
	}
	
	/**
     * 
     */
	protected void initProductVirtualCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryVirtual catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanVirtualCatalogCategory(requestUtil.getRequestData(request), catalogCategory, true, false);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
	}
	
}