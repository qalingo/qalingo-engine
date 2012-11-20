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
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LinkMenuViewBean;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 */
@Controller
public class EngineSettingController extends AbstractQalingoController {

	@Autowired
	protected WebBackofficeService webBackofficeService;
	
	@RequestMapping("/search-engine-setting.html*")
	public ModelAndView searchEngineSetting(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-list");

		final String titleKeyPrefixSufix = "search";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		final String contentTest = coreMessageSource.getMessage("home.content.text", null, getCurrentLocale(request));
		modelAndView.addObject("contentTest", contentTest);
		
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/engine-settings.html*", method = RequestMethod.GET)
	public ModelAndView engineSettingList(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "engine.setting.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		initLinks(request, modelAndView, locale, null);
		
		List<EngineSetting> engineSettings = engineSettingService.findEngineSettings();
		
		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
        String page = request.getParameter(Constants.PAGE_PARAMETER);
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
		modelAndView.addObject(Constants.PAGE_URL, url);
		modelAndView.addObject(Constants.PAGE_PAGED_LIST_HOLDER, engineSettingViewBeanPagedListHolder);
		
		formFactory.buildEngineSettingQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/engine-setting-details.html*", method = RequestMethod.GET)
	public ModelAndView engineSettingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-details");

		final String engineSettingId = request.getParameter(Constants.REQUEST_PARAM_ENGINE_SETTING_ID);
		if(StringUtils.isNotEmpty(engineSettingId)){
			EngineSetting engineSetting = engineSettingService.getEngineSettingById(engineSettingId);
			if(engineSetting != null){
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
				final Locale locale = currentLocalization.getLocale();
				final String titleKeyPrefixSufix = "engine.setting.details";

				initPage(request, response, modelAndView, titleKeyPrefixSufix);
				initLinks(request, modelAndView, locale, engineSetting);
				
				modelAndView.addObject("engineSettingDetails", viewBeanFactory.buildEngineSettingDetailsViewBean(request, currentLocalization));
				
				modelAndView.addObject("engineSetting", viewBeanFactory.buildEngineSettingViewBean(request, engineSetting));
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
		
		final String titleKeyPrefixSufix = "engine.setting.value.edit";

		final String engineSettingValueId = request.getParameter(Constants.REQUEST_PARAM_ENGINE_SETTING_VALUE_ID);
		if(StringUtils.isNotEmpty(engineSettingValueId)){
			final EngineSettingValue engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);
			if(engineSettingValue != null){
				final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
				
				modelAndView.addObject("engineSettingValueEdit", viewBeanFactory.buildEngineSettingValueEditViewBean(request, currentLocalization, engineSettingValue));

				formFactory.buildEngineSettingValueEditForm(request, modelAndView, engineSettingValue);
				initPage(request, response, modelAndView, titleKeyPrefixSufix);
				return modelAndView;
			}
		}

		final String urlRedirect = backofficeUrlService.buildEngineSettingListUrl(request);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	@RequestMapping(value = "/engine-setting-value-edit.html*", method = RequestMethod.POST)
	public ModelAndView engineSettingValueEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid EngineSettingValueForm engineSettingValueForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		final String titleKeyPrefixSufix = "engine.setting.value.edit";
		final String engineSettingValueId = engineSettingValueForm.getId();
		final EngineSettingValue engineSettingValue = engineSettingService.getEngineSettingValueById(engineSettingValueId);
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "engine-setting/engine-setting-value-edit");
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			modelAndView.addObject("engineSettingValueEdit", viewBeanFactory.buildEngineSettingValueEditViewBean(request, currentLocalization, engineSettingValue));
			formFactory.buildEngineSettingValueEditForm(request, modelAndView, engineSettingValue);
			initPage(request, response, modelAndView, titleKeyPrefixSufix);
			return modelAndView;
		}
		
		// UPDATE ENGINE SETTING VALUE
		webBackofficeService.updateEngineSettingValue(engineSettingValue, engineSettingValueForm);

		final String engineSettingId = engineSettingValue.getEngineSetting().getId().toString();
		final String urlRedirect = backofficeUrlService.buildEngineSettingDetailsUrl(request, engineSettingId);
        return new ModelAndView(new RedirectView(urlRedirect));
	}
	
	protected PagedListHolder<EngineSettingViewBean> initList(final HttpServletRequest request, final String sessionKey, final List<EngineSetting> engineSettings,
			PagedListHolder<EngineSettingViewBean> engineSettingViewBeanPagedListHolder) throws Exception{
		List<EngineSettingViewBean> engineSettingViewBeans = viewBeanFactory.buildEngineSettingViewBeans(request, engineSettings);
		engineSettingViewBeanPagedListHolder = new PagedListHolder<EngineSettingViewBean>(engineSettingViewBeans);
		
		engineSettingViewBeanPagedListHolder.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		String pageSize = engineSettingService.getEngineSettingValueByCode(EngineSettingService.ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST);
		if(StringUtils.isNotEmpty(pageSize)){
			engineSettingViewBeanPagedListHolder.setPageSize(Integer.parseInt(pageSize)); 
		}
		
        request.getSession().setAttribute(sessionKey, engineSettingViewBeanPagedListHolder);
        return engineSettingViewBeanPagedListHolder;
	}
	
	protected void initLinks(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Locale locale, final EngineSetting engineSetting){
		List<LinkMenuViewBean> customerLinks = new ArrayList<LinkMenuViewBean>();

		LinkMenuViewBean linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.engine.setting.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildEngineSettingListUrl(request));
		customerLinks.add(linkMenuViewBean);

		if(engineSetting != null){
			linkMenuViewBean = new LinkMenuViewBean();
			linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.engine.setting.details", null, locale));
			linkMenuViewBean.setUrl(backofficeUrlService.buildEngineSettingDetailsUrl(request, engineSetting.getId().toString()));
			customerLinks.add(linkMenuViewBean);
		}
		
		modelAndView.addObject("links", customerLinks);
	}
    
}
