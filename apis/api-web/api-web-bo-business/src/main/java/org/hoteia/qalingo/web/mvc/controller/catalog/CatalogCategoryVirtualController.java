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
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
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
@Controller("catalogCategoryVirtualController")
public class CatalogCategoryVirtualController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected CatalogService catalogService;
	
	@Autowired
	protected CatalogCategoryService catalogCategoryService;
	
	@Autowired
	protected WebBackofficeService webBackofficeService;
	
    protected List<SpecificFetchMode> categoryMasterFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();;

    public CatalogCategoryVirtualController() {
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName() + "." + CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.assets.getName()));
        
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategories.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.parentCatalogCategory.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.attributes.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategoryType.getName()));
    }
    
	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView productVirtualCategoryDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));

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

		final String catalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
		
        if(StringUtils.isNotEmpty(catalogCategoryCode)){
            // EDIT MODE
            final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
            
            Object[] params = {catalogCategory.getName() + " (" + catalogCategory.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATEGORY_EDIT.getKey(), params);
            
            initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
            
        } else {
            // ADD/CREATE MODE
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.VIRTUAL_CATEGORY_ADD.getKey(), null);
        }
        
        modelAndView.addObject("masterCategories", buildAllMasterCategories(requestData));
        modelAndView.addObject("categories", buildCategories(requestData, catalogCategoryCode));
        
        modelAndView.addObject("availableGlobaleAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryGlobalAttributeDefinitions()));
        modelAndView.addObject("availableMarketAreaAttributeDefinitions", backofficeViewBeanFactory.buildListViewBeanAttributeDefinition(requestData, attributeService.findCatalogCategoryMarketAreaAttributeDefinitions()));

        modelAndView.addObject(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATALOG, requestData));

		return modelAndView;
	}

	@RequestMapping(value = BoUrls.VIRTUAL_CATEGORY_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitVirtualCatalogCategoryForm(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.CATALOG_CATEGORY_FORM) CatalogCategoryForm catalogCategoryForm,
												   BindingResult result, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.VIRTUAL_CATEGORY_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final Retailer currentRetailer = requestData.getMarketAreaRetailer();
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
		final Locale locale = requestData.getLocale();
		
        final String catalogCategoryId = catalogCategoryForm.getId();
        final String parentCatalogCategoryId = catalogCategoryForm.getDefaultParentCategoryId();

        if(StringUtils.isNotEmpty(catalogCategoryId)){
            final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(catalogCategoryId, new FetchPlan(categoryVirtualFetchPlans));

             // SANITY CHECK 
            if(catalogCategory == null){
                final String url = backofficeUrlService.generateUrl(BoUrls.MASTER_CATALOG, requestData);
                return new ModelAndView(new RedirectView(url));
            }
            
            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, catalogCategory));

            // UPDATE CATEORY
            if (result.hasErrors()) {
                if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
                    // CHIELD CATEGORY
                    final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryVirtualFetchPlans));
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
                CatalogCategoryVirtual catalogCategoryVirtual = webBackofficeService.updateCatalogCategory(requestData, currentMarketArea, currentRetailer, currentLocalization, catalogCategory, catalogCategoryForm);
                addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.success.message", locale));
                
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, catalogCategoryVirtual);
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } catch (UniqueConstraintCodeCategoryException e) {
                addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.edit.error.message",  locale));

                if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
                    // CHIELD CATEGORY
                    final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryVirtualFetchPlans));
                    initProductVirtualCategoryModelAndView(request, modelAndView, parentCatalogCategory);
                    return modelAndView;
                    
                } else {
                    // ROOT CATEGORY
                    initProductVirtualCategoryModelAndView(request, modelAndView, catalogCategory);
                    return modelAndView;
                }
                
            }

        } else {
            CatalogCategoryVirtual parentCatalogCategory = null;
            if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
                parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(parentCatalogCategoryId, new FetchPlan(categoryVirtualFetchPlans));
            }

            // CREATE A NEW CATEORY
            if (result.hasErrors()) {
                if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
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
                CatalogCategoryVirtual savedCatalogCategory = webBackofficeService.createCatalogCategory(requestData, currentMarketArea, currentLocalization, parentCatalogCategory, catalogCategoryVirtual, catalogCategoryForm);
                addSuccessMessage(request, coreMessageSource.getMessage("business.catalog.category.add.success.message", locale));
                
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, savedCatalogCategory);
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } catch (UniqueConstraintCodeCategoryException e) {
                addErrorMessage(request, coreMessageSource.getMessage("business.catalog.category.add.error.message", locale));
                
                if(StringUtils.isNotEmpty(parentCatalogCategoryId)){
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
    protected List<CatalogCategoryMaster> buildAllMasterCategories(final RequestData requestData) throws Exception {
        final Localization currentLocalization = requestData.getMarketAreaLocalization();
        List<CatalogCategoryMaster> categories = catalogCategoryService.findAllMasterCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode(), new FetchPlan(categoryMasterFetchPlans));
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
	protected List<CatalogCategoryVirtual> buildCategories(final RequestData requestData, final String catalogCategoryCode) throws Exception {
	    final Localization currentLocalization = requestData.getMarketAreaLocalization();
        List<CatalogCategoryVirtual> categories = catalogCategoryService.findAllVirtualCatalogCategoriesByCatalogCode(requestData.getVirtualCatalogCode());
        if(StringUtils.isNotEmpty(catalogCategoryCode)){
            for (Iterator<CatalogCategoryVirtual> iterator = categories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual catalogCategoryVirtual = (CatalogCategoryVirtual) iterator.next();
                if(catalogCategoryVirtual.getCode().equalsIgnoreCase(catalogCategoryCode)){
                    iterator.remove();
                }
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
        return categories;
	}
	
	/**
     * 
     */
	protected void initProductVirtualCategoryModelAndView(final HttpServletRequest request, final ModelAndView modelAndView, final CatalogCategoryVirtual catalogCategory) throws Exception {
		CatalogCategoryViewBean catalogCategoryViewBean = backofficeViewBeanFactory.buildViewBeanVirtualCatalogCategory(requestUtil.getRequestData(request), catalogCategory, new FetchPlan(categoryVirtualFetchPlans), null, null);
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
            final CatalogCategoryVirtual catalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
            return backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, null, catalogCategory);
        }
        final String parentCatalogCategoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_PARENT_CATALOG_CATEGORY_CODE);
        if(StringUtils.isNotEmpty(parentCatalogCategoryCode)){
            final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(parentCatalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), new FetchPlan(categoryVirtualFetchPlans));
            return backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, parentCatalogCategory, null);
        }
        return backofficeFormFactory.buildCatalogVirtualCategoryForm(requestData, null, null);
    }
    
}