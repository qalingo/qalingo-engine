/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.enginesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LinkMenuViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;

/**
 * 
 */
@Controller("engineSettingController")
public class EngineSettingController extends AbstractTechnicalBackofficeController {

	@RequestMapping(value = BoUrls.ENGINE_SETTING_LIST_URL, method = RequestMethod.GET)
	public ModelAndView engineSettingList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_LIST.getVelocityPage());
		
		final Locale locale = requestUtil.getCurrentLocale(request);
		initLinks(request, modelAndView, locale, null);
		
		List<EngineSetting> engineSettings = engineSettingService.findEngineSettings();
		
		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		PagedListHolder<EngineSettingViewBean> engineSettingViewBeanPagedListHolder;

        if(StringUtils.isEmpty(page)){
        	engineSettingViewBeanPagedListHolder = initList(request, sessionKey, engineSettings, new PagedListHolder<EngineSettingViewBean>());
        } else {
	        engineSettingViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (engineSettingViewBeanPagedListHolder == null) { 
	        	engineSettingViewBeanPagedListHolder = initList(request, sessionKey, engineSettings, engineSettingViewBeanPagedListHolder);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = engineSettingViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		engineSettingViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
		        	engineSettingViewBeanPagedListHolder.previousPage(); 
				}
	        }
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, engineSettingViewBeanPagedListHolder);
		
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.ENGINE_SETTING_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView engineSettingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_DETAILS.getVelocityPage());
		modelAndView.addObject("pageContextPath", request.getContextPath());
		final String engineSettingId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_ID);
		modelAndView.addObject("engineSettingId",engineSettingId);
		if(StringUtils.isNotEmpty(engineSettingId)){
			EngineSetting engineSetting = engineSettingService.getEngineSettingById(engineSettingId);
			if(engineSetting != null){
				final Locale locale = requestUtil.getCurrentLocale(request);
				EngineSettingViewBean engineSettingViewBean =viewBeanFactory.buildEngineSettingViewBean(requestUtil.getRequestData(request), engineSetting);
				modelAndView.addObject("engineSetting", engineSettingViewBean);
                initLinks(request, modelAndView, locale, engineSetting);
			} else {
				final String url = requestUtil.getLastRequestUrl(request);
				return new ModelAndView(new RedirectView(url));
			}
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		modelAndView.addObject("addUrl",BoUrls.ENGINE_SETTING_VALUE_EDIT);
		modelAndView.addObject("urlBack",BoUrls.ENGINE_SETTING_LIST);
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingValueEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_VALUE_EDIT.getVelocityPage());
        
        final String engineSettingValueId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID);
        if(StringUtils.isNotEmpty(engineSettingValueId)){
            final EngineSettingValue engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);
            if(engineSettingValue != null){
                final Locale locale = requestUtil.getCurrentLocale(request);
                modelAndView.addObject("engineSetting", viewBeanFactory.buildEngineSettingViewBean(requestUtil.getRequestData(request), engineSettingValue.getEngineSetting()));
                formFactory.buildEngineSettingValueEditForm(request, modelAndView, engineSettingValue);
                initLinks(request, modelAndView, locale, engineSettingValue.getEngineSetting());
                return modelAndView;
            }
        } 

        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }

    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitEngineSettingValueEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid EngineSettingValueForm engineSettingValueForm,
                                BindingResult result, ModelMap modelMap) throws Exception {

        if (result.hasErrors()) {
            return engineSettingValueEdit(request, response, modelMap);
        }

        final String engineSettingValueId = engineSettingValueForm.getId();
 
        EngineSettingValue engineSettingValue = new EngineSettingValue();
        if(null == engineSettingValueId){
        	
        	webBackofficeService.updateEngineSettingValue(engineSettingValue, engineSettingValueForm);
        	
        }else{
            engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);

	        // UPDATE ENGINE SETTING VALUE
	        webBackofficeService.updateEngineSettingValue(engineSettingValue, engineSettingValueForm);
        }

        return new ModelAndView(new RedirectView(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), engineSettingValue)));
    }
    
    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_ADD_URL, method = RequestMethod.POST)
    public ModelAndView submitEngineSettingValueAdd(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final String engineSettingId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_ID);
        final EngineSetting engineSetting = engineSettingService.getEngineSettingById(engineSettingId);
 
        EngineSettingValueForm engineSettingValueForm = new EngineSettingValueForm();
        engineSettingValueForm.setContext(request.getParameter("context"));
        engineSettingValueForm.setValue(request.getParameter("value"));

        EngineSettingValue engineSettingValue = new EngineSettingValue();
        engineSettingValue.setContext(request.getParameter("context"));
        engineSettingValue.setValue(request.getParameter("value"));
        engineSettingValue.setId(null);
        engineSettingValue.setEngineSetting(engineSetting);
        if(null != engineSettingId){

           

	        // UPDATE ENGINE SETTING VALUE
	        webBackofficeService.updateEngineSettingValue(engineSettingValue, engineSettingValueForm);
        }

        return new ModelAndView(new RedirectView(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), engineSetting)));
        
       //////// return engineSettingDetails(request,response);
    }
     
    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_ADD_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingValueAdd(final HttpServletRequest request, final HttpServletResponse response, @RequestParam(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_ID) final String engineSettingId) throws Exception{
    	ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_VALUE_ADD.getVelocityPage());
    	modelAndView.addObject("pageContextPath", request.getContextPath());
    	
        if(StringUtils.isNotEmpty(engineSettingId)){
            final EngineSetting engineSetting = engineSettingService.getEngineSettingById(engineSettingId);
            if(engineSetting != null){
                final Locale locale = requestUtil.getCurrentLocale(request);
                modelAndView.addObject("engineSetting", engineSetting);
 
                return modelAndView;
            }
        } 
        return modelAndView;
    }
    
	protected PagedListHolder<EngineSettingViewBean> initList(final HttpServletRequest request, final String sessionKey, final List<EngineSetting> engineSettings,
			PagedListHolder<EngineSettingViewBean> engineSettingViewBeanPagedListHolder) throws Exception{
		List<EngineSettingViewBean> engineSettingViewBeans = viewBeanFactory.buildEngineSettingViewBeans(requestUtil.getRequestData(request), engineSettings);
		engineSettingViewBeanPagedListHolder = new PagedListHolder<EngineSettingViewBean>(engineSettingViewBeans);
		
		engineSettingViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
		String pageSize = engineSettingService.getEngineSettingValueByCode(EngineSettingService.ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST);
		if(StringUtils.isNotEmpty(pageSize)){
			engineSettingViewBeanPagedListHolder.setPageSize(Integer.parseInt(pageSize)); 
		}
		
        request.getSession().setAttribute(sessionKey, engineSettingViewBeanPagedListHolder);
        return engineSettingViewBeanPagedListHolder;
	}
	
	protected void initLinks(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Locale locale, final EngineSetting engineSetting) throws Exception{
		List<LinkMenuViewBean> customerLinks = new ArrayList<LinkMenuViewBean>();

		LinkMenuViewBean linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, "header_menu_engine_setting_list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestUtil.getRequestData(request)));
		customerLinks.add(linkMenuViewBean);

		if(engineSetting != null){
			linkMenuViewBean = new LinkMenuViewBean();
			linkMenuViewBean.setName(getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, "header_menu_engine_setting_details", locale));
			linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), engineSetting));
			customerLinks.add(linkMenuViewBean);
		}
		
		modelAndView.addObject("links", customerLinks);
	}
    
}