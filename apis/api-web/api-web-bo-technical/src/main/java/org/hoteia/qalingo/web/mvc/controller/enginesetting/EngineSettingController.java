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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.springframework.beans.support.PagedListHolder;
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
@Controller("engineSettingController")
public class EngineSettingController extends AbstractTechnicalBackofficeController {

    public static final String SESSION_KEY = "PagedListHolder_EngineSettings";

    @RequestMapping(value = BoUrls.ENGINE_SETTING_LIST_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        
        displayList(request, model, requestData);
        
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.ENGINE_SETTING_LIST.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.ENGINE_SETTING_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String engineSettingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_CODE);
        
        // SANITY CHECK
        if(StringUtils.isEmpty(engineSettingCode)){
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }
        
        final EngineSetting engineSetting = engineSettingService.getEngineSettingByCode(engineSettingCode);

        // SANITY CHECK
        if(engineSetting != null){
            modelAndView.addObject(ModelConstants.ENGINE_SETTING_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanEngineSetting(requestData, engineSetting));
        } else {
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData));
        
        Object[] params = {engineSetting.getName() + " (" + engineSetting.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.ENGINE_SETTING_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.ENGINE_SETTING_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.ENGINE_SETTING_FORM) EngineSettingForm engineSettingForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String engineSettingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_CODE);
        if(StringUtils.isNotEmpty(engineSettingCode)){
            // EDIT MODE
            final EngineSetting engineSetting = engineSettingService.getEngineSettingByCode(engineSettingCode);

            EngineSettingViewBean engineSettingViewBean = backofficeViewBeanFactory.buildViewBeanEngineSetting(requestData, engineSetting);
            request.setAttribute(ModelConstants.ENGINE_SETTING_VIEW_BEAN, engineSettingViewBean);

            Object[] params = {engineSetting.getName() + " (" + engineSetting.getCode() + ")"};
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.ENGINE_SETTING_EDIT.getKey(), params);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestData, engineSetting));
        } else {
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.ENGINE_SETTING_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitEngineSettingEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.ENGINE_SETTING_FORM) EngineSettingForm engineSettingForm,
                                BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return engineSettingEdit(request, model, engineSettingForm);
        }
        
        EngineSetting engineSetting = null;
        if(StringUtils.isNotEmpty(engineSettingForm.getId())){
            engineSetting = engineSettingService.getEngineSettingById(engineSettingForm.getId());
        }

        try {
            // CREATE OR UPDATE ENGINE SETTING
            webBackofficeService.createOrUpdateEngineSetting(engineSetting, engineSettingForm);
            
            if (engineSetting == null) {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData);
                return new ModelAndView(new RedirectView(urlRedirect));
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestData, engineSetting);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, "create_or_update_message", locale));
            logger.error("Can't save or update EngineSetting:" + engineSettingForm.getId() + "/" + engineSettingForm.getCode(), e);
            return engineSettingEdit(request, model, engineSettingForm);
        }
    }

    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView engineSettingValueEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.ENGINE_SETTING_VALUE_FORM) EngineSettingValueForm engineSettingValueForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING_VALUE_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String engineSettingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_CODE);
        final String engineSettingValueContext = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_CONTEXT);
        if(StringUtils.isNotEmpty(engineSettingCode)
                && StringUtils.isNotEmpty(engineSettingValueContext)){
            final EngineSetting engineSetting = engineSettingService.getEngineSettingByCode(engineSettingCode);
            if(engineSetting != null){
                EngineSettingValue engineSettingValue = engineSetting.getEngineSettingValue(engineSettingValueContext);
                if(engineSettingValue != null){
                    modelAndView.addObject("engineSetting", backofficeViewBeanFactory.buildViewBeanEngineSetting(requestData, engineSetting));
                    modelAndView.addObject("engineSettingValue", backofficeViewBeanFactory.buildViewBeanEngineSettingValue(requestData, engineSettingValue));
                    modelAndView.addObject("engineSettingValueForm", backofficeFormFactory.buildEngineSettingValueForm(requestData, engineSettingValue));

                    model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestData, engineSetting));

                    Object[] params = {engineSetting.getName() + " (" + engineSetting.getCode() + ")"};
                    initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.ENGINE_SETTING_EDIT.getKey(), params);

                    return modelAndView;
                } else {
                    final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), engineSetting);
                    return new ModelAndView(new RedirectView(urlRedirect));
                }
            }
        } 
        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }

    @RequestMapping(value = BoUrls.ENGINE_SETTING_VALUE_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitEngineSettingEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.ENGINE_SETTING_VALUE_FORM) EngineSettingValueForm engineSettingValueForm,
            BindingResult result, ModelMap modelMap) throws Exception {

        if (result.hasErrors()) {
            return engineSettingValueEdit(request, model, engineSettingValueForm);
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
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.ENGINE_SETTING_FORM)
    protected EngineSettingForm initEngineSettingForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String engineSettingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_CODE);
        if(StringUtils.isNotEmpty(engineSettingCode)){
            final EngineSetting engineSettingEdit = engineSettingService.getEngineSettingByCode(engineSettingCode);
            return backofficeFormFactory.buildEngineSettingForm(requestData, engineSettingEdit);
        }
        
        return backofficeFormFactory.buildEngineSettingForm(requestData, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        
        PagedListHolder<EngineSettingViewBean> engineSettingViewBeanPagedListHolder = new PagedListHolder<EngineSettingViewBean>();

        if(StringUtils.isEmpty(page)){
            engineSettingViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData);
            
        } else {
            engineSettingViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(SESSION_KEY); 
            if (engineSettingViewBeanPagedListHolder == null) { 
                engineSettingViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData);
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
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, engineSettingViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<EngineSettingViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
        List<EngineSetting> engineSettings = engineSettingService.findEngineSettings();

        PagedListHolder<EngineSettingViewBean> engineSettingViewBeanPagedListHolder = new PagedListHolder<EngineSettingViewBean>();
        
        final List<EngineSettingViewBean> engineSettingViewBeans = new ArrayList<EngineSettingViewBean>();
        for (Iterator<EngineSetting> iterator = engineSettings.iterator(); iterator.hasNext();) {
            EngineSetting engineSettingIt = (EngineSetting) iterator.next();
            engineSettingViewBeans.add(backofficeViewBeanFactory.buildViewBeanEngineSetting(requestData, engineSettingIt));
        }
        engineSettingViewBeanPagedListHolder = new PagedListHolder<EngineSettingViewBean>(orderEngineSettingViewList(engineSettingViewBeans));
        engineSettingViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, engineSettingViewBeanPagedListHolder); 
        
        return engineSettingViewBeanPagedListHolder;
    }
    
    protected List<EngineSettingViewBean> orderEngineSettingViewList(final List<EngineSettingViewBean> engineSettingViewBeans) {
        if (engineSettingViewBeans != null) {
            List<EngineSettingViewBean> sortedObjects = new LinkedList<EngineSettingViewBean>(engineSettingViewBeans);
                Collections.sort(sortedObjects, new Comparator<EngineSettingViewBean>() {
                    @Override
                    public int compare(EngineSettingViewBean o1, EngineSettingViewBean o2) {
                        if (o1 != null && o2 != null) {
                            return o1.getCode().compareTo(o2.getCode());
                        }
                        return 0;
                    }
                });
            return sortedObjects;
        }
        return null;
    }
    
}