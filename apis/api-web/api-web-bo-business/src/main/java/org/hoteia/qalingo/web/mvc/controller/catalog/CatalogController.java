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
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    private CatalogPojoService catalogPojoService;
	   
	@Autowired
	protected CatalogService catalogService;
	
	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping(value = BoUrls.MASTER_CATALOG_URL, method = RequestMethod.GET)
	public ModelAndView manageMasterCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATALOG.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		final String pageKey = BoUrls.MASTER_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

	    modelAndView.addObject("catalogType", "master");

//		final CatalogMaster catalog = catalogService.getMasterCatalogById(currentMarketArea.getCatalog().getCatalogMaster().getId());
//		List<CatalogCategoryMaster> rootCatalogCategories = catalogCategoryService.findRootMasterCatalogCategoriesByCatalogCode(catalog.getCode(), FetchPlanGraphCategory.masterCategoriesWithoutProductsAndAssetsFetchPlan());
//		CatalogViewBean catalogViewBean = backofficeViewBeanFactory.buildViewBeanCatalog(requestData, catalog, new ArrayList<AbstractCatalogCategory>(rootCatalogCategories));
//		modelAndView.addObject(ModelConstants.CATALOG_VIEW_BEAN, catalogViewBean);

//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            // TODO : Denis : temporary hack - need a good recursive fetch with criteria
//            for (Iterator<CatalogCategoryMaster> iterator = rootCatalogCategories.iterator(); iterator.hasNext();) {
//                CatalogCategoryMaster catalogCategoryMaster = (CatalogCategoryMaster) iterator.next();
//                Set<CatalogCategoryMaster> catalogCategoriesReload = new HashSet<CatalogCategoryMaster>();
//                for (Iterator<CatalogCategoryMaster> iteratorSubCategories = catalogCategoryMaster.getCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
//                    CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) iteratorSubCategories.next();
//                    CatalogCategoryMaster reloadedCategory = catalogCategoryService.getMasterCatalogCategoryById(catalogCategory.getId(), FetchPlanGraphCategory.masterCategoriesWithoutProductsAndAssetsFetchPlan());
//                    catalogCategoriesReload.add(reloadedCategory);
//                }
//                catalogCategoryMaster.setCatalogCategories(catalogCategoriesReload);
//            }
//            catalog.setCatalogCategories(new HashSet<CatalogCategoryMaster>(rootCatalogCategories));
//            CatalogPojo catalogPojo = (CatalogPojo) catalogPojoService.getMasterCatalog(catalog);
//            String catalogJson = mapper.writeValueAsString(catalogPojo);
//            modelAndView.addObject("catalogJson", catalogJson);
//            
//        } catch (JsonGenerationException e) {
//            logger.error(e.getMessage());
//        } catch (JsonMappingException e) {
//            logger.error(e.getMessage());
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATALOG_URL, method = RequestMethod.GET)
	public ModelAndView manageVirtualCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATALOG.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
		
		final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

        modelAndView.addObject("catalogType", "virtual");

//        CatalogVirtual catalog = catalogService.getVirtualCatalogbyMarketAreaId(currentMarketArea.getId());
//        List<CatalogCategoryVirtual> rootCatalogCategories = catalogCategoryService.findRootVirtualCatalogCategoriesByCatalogCode(catalog.getCode(),
//                FetchPlanGraphCategory.virtualCategoriesWithoutProductsAndAssetsFetchPlan());
//        CatalogViewBean catalogViewBean = backofficeViewBeanFactory.buildViewBeanCatalog(requestData, catalog, new ArrayList<AbstractCatalogCategory>(rootCatalogCategories));
//        modelAndView.addObject(ModelConstants.CATALOG_VIEW_BEAN, catalogViewBean);
		
//        ObjectMapper mapper = new ObjectMapper();
//        try {
////            // TODO : Denis : temporary hack - need a good recursive fetch with criteria
////            Set<CatalogCategoryVirtual> rootCatalogCategoriesReload = new HashSet<CatalogCategoryVirtual>();
////            for (Iterator<CatalogCategoryVirtual> iterator = rootCatalogCategories.iterator(); iterator.hasNext();) {
////                CatalogCategoryVirtual rootCatalogCategoryVirtual = (CatalogCategoryVirtual) iterator.next();
////                Set<CatalogCategoryVirtual> subCatalogCategoriesReload = new HashSet<CatalogCategoryVirtual>();
////                for (Iterator<CatalogCategoryVirtual> iteratorSubCategories = rootCatalogCategoryVirtual.getChildCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
////                    CatalogCategoryVirtual subCatalogCategory = (CatalogCategoryVirtual) iteratorSubCategories.next();
////                    CatalogCategoryVirtual subCatalogCategoryReloaded = catalogCategoryService.getVirtualCatalogCategoryById(subCatalogCategory.getId(), FetchPlanGraphCategory.virtualCategoriesWithoutProductsAndAssetsFetchPlan());
////                    subCatalogCategoriesReload.add(subCatalogCategoryReloaded);
////                }
////                rootCatalogCategoryVirtual.setChildCatalogCategories(subCatalogCategoriesReload);
////                rootCatalogCategoriesReload.add(rootCatalogCategoryVirtual);
////            }
////            catalogVirtual.setChildCatalogCategories(new HashSet<CatalogCategoryVirtual>(rootCatalogCategoriesReload));
//            
//            CatalogPojo catalogPojo = (CatalogPojo) catalogPojoService.getVirtualCatalog(catalog);
//            String catalogJson = mapper.writeValueAsString(catalogPojo);
//            modelAndView.addObject("catalogJson", catalogJson);
//            
//        } catch (JsonGenerationException e) {
//            logger.error(e.getMessage(), e);
//        } catch (JsonMappingException e) {
//            logger.error(e.getMessage(), e);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView catalogCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

		final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode);

		final String pageKey = BoUrls.MASTER_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView editMasterCatalogCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode);

		List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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

		final String pageKey = BoUrls.MASTER_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
		modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData, catalogCategory));

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.MASTER_CATEGORY_ADD_URL, method = RequestMethod.GET)
	public ModelAndView addMasterCatalogCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_ADD.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();

		final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		
		List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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
		
		if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
			final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode);
			if(parentCatalogCategory != null){
				// Child category: We have parent informations - we prepare the child category IHM
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
				modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData, parentCatalogCategory, null));
				return modelAndView;
			}
		}

		// No parent informations - we prepare the root category IHM
		
		final String pageKey = BoUrls.MASTER_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductMasterCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData));

		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView masterCatalogCategoryForm(final HttpServletRequest request, final HttpServletResponse response, @Valid CatalogCategoryForm catalogCategoryForm,
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
			final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode);

			List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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
			
			// UPDATE CATEORY
			if (result.hasErrors()) {
				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode);
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
				webBackofficeService.updateCatalogCategory(currentMarketArea, currentRetailer, currentLocalization, catalogCategory, catalogCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestUtil.getRequestData(request), catalogCategory);;
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.error.message",  locale));

				final String pageKey = BoUrls.MASTER_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode);
					initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
					return modelAndView;
				}
				
			}

		} else {
			final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode);

			List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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
				webBackofficeService.createCatalogCategory(currentMarketArea, currentLocalization, parentCatalogCategory, catalogCategoryMaster, catalogCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestUtil.getRequestData(request), catalogCategoryMaster);
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
		final Locale locale = requestData.getLocale();

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode);

		final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView editVirtualCatalogCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode);

		List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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

		final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
		modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData, catalogCategory));

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_ADD_URL, method = RequestMethod.GET)
	public ModelAndView addVirtualCatalogCategory(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_ADD.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();

		final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		
		List<CatalogCategoryMaster> categories = catalogCategoryService.findMasterCategories();
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
		
		if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
			final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode);
			if(parentCatalogCategory != null){
				final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
				final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
				overrideSeoTitle(request, modelAndView, title);
				
				initProductVirtualCategoryModelAndView(request, modelAndView, parentCatalogCategory);
				modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData, parentCatalogCategory, null));
				return modelAndView;
			}
		}

		final String pageKey = "catalog";
		final String title = getSpecificMessage(ScopeWebMessage.CATALOG_CATEGORY, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);
		
		initProductVirtualCategoryModelAndView(request, modelAndView, null);
		modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData));
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView editVirtualCatalogCategory(final HttpServletRequest request, final HttpServletResponse response, @Valid CatalogCategoryForm catalogCategoryForm,
												   BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
		final Locale locale = requestData.getLocale();
		
		final String catalogCategoryCode = catalogCategoryForm.getCode();

		final String pageKey = BoUrls.VIRTUAL_CATALOG_KEY;
		final String title = getSpecificMessage(ScopeWebMessage.SEO, getMessageTitleKey(pageKey), locale);
		overrideSeoTitle(request, modelAndView, title);

		if(StringUtils.isNotEmpty(catalogCategoryCode)){
			if (result.hasErrors()) {
				final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode);
				initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
				modelAndView.addObject("catalogCategoryForm", backofficeFormFactory.buildCatalogCategoryForm(requestData, null, catalogCategory));
				return modelAndView;
			}

			// SANITY CHECK
			final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode);
			if(catalogCategory != null){
				// UPDATE PRODUCT MARKETING
				webBackofficeService.updateCatalogCategory(currentMarketArea, currentRetailer, currentLocalization, catalogCategory, catalogCategoryForm);
				
			} else {
				// CREATE PRODUCT MARKETING
				webBackofficeService.createCatalogCategory(currentMarketArea, currentLocalization, catalogCategory, catalogCategoryForm);
			}
		}
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	/**
     * 
     */
	protected void initProductMasterCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryMaster catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanCatalogCategory(requestUtil.getRequestData(request), catalogCategory, true, false);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
	}
	
	/**
     * 
     */
	protected void initProductVirtualCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryVirtual catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanCatalogCategory(requestUtil.getRequestData(request), catalogCategory, true, false);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
	}
	
}