/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.enginesetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.view.RedirectView;
import fr.hoteia.qalingo.core.web.util.impl.RequestUtilImpl;
import fr.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LinkMenuViewBean;

/**
 * 
 */
@Controller("engineSettingController")
public class EngineSettingController extends AbstractTechnicalBackofficeController {

	@RequestMapping("/search-engine-setting.html*")
	public ModelAndView searchEngineSetting(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING.getVelocityPage());

		final String contentText = getSpecificMessage(ScopeWebMessage.ENGINE_SETTING, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.ENGINE_SETTING_URL, method = RequestMethod.GET)
	public ModelAndView engineSettingList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.ENGINE_SETTING.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		initLinks(request, modelAndView, locale, null);
		
		List<EngineSetting> engineSettings = engineSettingService.findEngineSettings();
		
		String url = RequestUtilImpl.getEffectiveURL(requestUtil.getCurrentRequestUrl(request));
		//get correct url for page, modified by daniel yao 2013-10-22
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
	
	@RequestMapping(value = "/engine-setting-details.html*", method = RequestMethod.GET)
	public ModelAndView engineSettingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-details");

		final String engineSettingId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID);
		if(StringUtils.isNotEmpty(engineSettingId)){
			EngineSetting engineSetting = engineSettingService.getEngineSettingById(engineSettingId);
			if(engineSetting != null){
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
				final Locale locale = currentLocalization.getLocale();

				initLinks(request, modelAndView, locale, engineSetting);
				
				modelAndView.addObject("engineSettingDetails", viewBeanFactory.buildEngineSettingDetailsViewBean(requestUtil.getRequestData(request)));
				
				modelAndView.addObject("engineSetting", viewBeanFactory.buildEngineSettingViewBean(requestUtil.getRequestData(request), engineSetting));
			} else {
				final String url = requestUtil.getLastRequestUrl(request);
				return new ModelAndView(new RedirectView(url));
			}
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/engine-setting-value-edit.html*", method = RequestMethod.GET)
	public ModelAndView engineSettingValueEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-value-edit");
		
		final String engineSettingValueId = request.getParameter(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID);
		if(StringUtils.isNotEmpty(engineSettingValueId)){
			final EngineSettingValue engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);
			if(engineSettingValue != null){
				modelAndView.addObject("engineSettingValueEdit", viewBeanFactory.buildEngineSettingValueEditViewBean(requestUtil.getRequestData(request), engineSettingValue));

				formFactory.buildEngineSettingValueEditForm(request, modelAndView, engineSettingValue);
				return modelAndView;
			}
		}

		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	@RequestMapping(value = "/engine-setting-value-edit.html*", method = RequestMethod.POST)
	public ModelAndView submitEngineSettingValueEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid EngineSettingValueForm engineSettingValueForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		final String engineSettingValueId = engineSettingValueForm.getId();
		final EngineSettingValue engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);
		if (result.hasErrors()) {
			return engineSettingValueEdit(request, response, modelMap);
		}
		
		// UPDATE ENGINE SETTING VALUE
		webBackofficeService.updateEngineSettingValue(engineSettingValue, engineSettingValueForm);

		final String engineSettingId = engineSettingValue.getEngineSetting().getId().toString();
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID, engineSettingId);
        return new ModelAndView(new RedirectView(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), urlParams)));
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
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.engine.setting.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING, requestUtil.getRequestData(request)));
		customerLinks.add(linkMenuViewBean);

		if(engineSetting != null){
			linkMenuViewBean = new LinkMenuViewBean();
			linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.engine.setting.details", locale));
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID, engineSetting.getId().toString());
			linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestUtil.getRequestData(request), urlParams));
			customerLinks.add(linkMenuViewBean);
		}
		
		modelAndView.addObject("links", customerLinks);
	}
    
}