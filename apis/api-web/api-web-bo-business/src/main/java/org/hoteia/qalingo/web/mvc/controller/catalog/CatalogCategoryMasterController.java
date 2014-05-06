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
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
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
@Controller("catalogCategoryMasterController")
public class CatalogCategoryMasterController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CatalogService catalogService;
	
	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
    protected List<SpecificFetchMode> categoryMasterFetchPlans = new ArrayList<SpecificFetchMode>();
    
	public CatalogCategoryMasterController() {
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategories.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.parentCatalogCategory.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.attributes.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategoryType.getName()));
    }
    
	@RequestMapping(value = BoUrls.MASTER_CATEGORY_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView catalogCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

		final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode(), new FetchPlan(categoryMasterFetchPlans));

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
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        
		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);

        if(StringUtils.isNotEmpty(catalogCategoryCode)){
            // EDIT MODE
            final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode(), new FetchPlan(categoryMasterFetchPlans));
            
            Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATEGORY_EDIT.getKey(), params);
            
            initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
            
        } else {
            // ADD/CREATE MODE
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.MASTER_CATEGORY_ADD.getKey(), null);
        }
        
		modelAndView.addObject("categories", buildCategories(requestData, catalogCategoryCode));

        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData));

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.MASTER_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitMasterCatalogCategoryForm(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm,
												  BindingResult result, ModelMap modelMap) throws Exception {
		
		// TODO : Denis : refactoring, clean
		
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MASTER_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        final Locale locale = requestData.getLocale();
		
		final String catalogCategoryId = catalogCategoryForm.getId();
		final String parentCatalogCategoryId = catalogCategoryForm.getDefaultParentCategoryId();

		if(StringUtils.isNotEmpty(catalogCategoryId)){
			final String catalogCategoryCode = catalogCategoryForm.getCode();
			final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode(), new FetchPlan(categoryMasterFetchPlans));

		     // SANITY CHECK 
	        if(catalogCategory == null){
	            final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
	            return new ModelAndView(new RedirectView(url));
	        }
	        
			// UPDATE CATEORY
			if (result.hasErrors()) {
				if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryMasterFetchPlans));
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
				
				if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
					// CHIELD CATEGORY
					final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryMasterFetchPlans));
					initProductMasterCategoryModelAndView(request, modelAndView, parentCatalogCategory);
					return modelAndView;
					
				} else {
					// ROOT CATEGORY
					initProductMasterCategoryModelAndView(request, modelAndView, catalogCategory);
					return modelAndView;
				}
				
			}

		} else {
		    CatalogCategoryMaster parentCatalogCategory = null;
		    if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
	            parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryMasterFetchPlans));
		    }

			// CREATE A NEW CATEORY
			if (result.hasErrors()) {
				if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
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
				CatalogCategoryMaster savedCatalogCategory = webBackofficeService.createCatalogCategory(requestData, currentMarketArea, currentLocalization, parentCatalogCategory, catalogCategoryMaster, catalogCategoryForm);
				addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
				
				final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, savedCatalogCategory);
				return new ModelAndView(new RedirectView(urlRedirect));
				
			} catch (UniqueConstraintCodeCategoryException e) {
				addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.add.error.message", locale));
				if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
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
	
    /**
     * 
     */
    protected List<CatalogCategoryMaster> buildCategories(final RequestData requestData, final String catalogCategoryCode) throws Exception {
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        List<CatalogCategoryMaster> categories = catalogCategoryService.findAllMasterCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode(), new FetchPlan(categoryMasterFetchPlans));
        if(StringUtils.isNotEmpty(catalogCategoryCode)){
            for (Iterator<CatalogCategoryMaster> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryMaster CatalogCategoryMaster = (CatalogCategoryMaster) iterator.next();
                if(CatalogCategoryMaster.getCode().equalsIgnoreCase(catalogCategoryCode)){
                    iterator.remove();
                }
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
        return categories;
    }
    
	/**
     * 
     */
	protected void initProductMasterCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryMaster catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanMasterCatalogCategory(requestUtil.getRequestData(request), catalogCategory, new FetchPlan(categoryMasterFetchPlans), null, null);
		modelAndView.addObject(ModelConstants.CATALOG_CATEGORY_VIEW_BEAN, catalogCategoryViewBean);
	}
	
    /**
     * 
     */
    @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) 
    protected CatalogCategoryForm initCatalogCategoryForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        if(StringUtils.isNotEmpty(catalogCategoryCode)){
            final CatalogCategoryMaster catalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getMasterCatalogCode(), new FetchPlan(categoryMasterFetchPlans));
            backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, null, catalogCategory);
        }
        final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PARENT_CATALOG_CATEGORY_CODE);
        if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
            final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getMasterCatalogCode(), new FetchPlan(categoryMasterFetchPlans));
            return backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, parentCatalogCategory, null);
        }
        return backofficeFormFactory.buildCatalogMasterCategoryForm(requestData, null, null);
    }
	
}