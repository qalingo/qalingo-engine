/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("contactController")
public class ContactController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = FoUrls.CONTACT_URL, method = RequestMethod.GET)
	public ModelAndView displayContactForm(final HttpServletRequest request, Model model, @ModelAttribute(ModelConstants.CONTACT_FORM) ContactForm contactForm) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CONTACT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));
		
        modelAndView.addObject("withMap", true);
		
        overrideDefaultMainContentTitle(request, modelAndView, FoUrls.CONTACT.getKey());

        model.addAttribute(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));

        return modelAndView;
	}

	@RequestMapping(value = FoUrls.CONTACT_URL, method = RequestMethod.POST)
	public ModelAndView submitContact(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.CONTACT_FORM) ContactForm contactForm,
								      BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "contact/contact-success");

        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();

		if (result.hasErrors()) {
			return displayContactForm(request, model, contactForm);
		}

		modelAndView.addObject(ModelConstants.URL_BACK, urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request)));

		try {
			webManagementService.buildAndSaveContactMail(requestUtil.getRequestData(request), contactForm);
	        
        } catch (Exception e) {
        	logger.error("Can't send contact email!", e);
        	return displayContactForm(request, model, contactForm);
        }

        addSuccessMessage(request, getCommonMessage(ScopeCommonMessage.CONTACT, "form_success_message", locale));

        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.CONTACT.getKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.CONTACT.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.CONTACT.getKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.CONTACT, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        return breadcrumbViewBean;
    }
    
	/**
	 * 
	 */
    @ModelAttribute(ModelConstants.CONTACT_FORM)
	protected ContactForm getContactForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
    	return formFactory.buildContactForm(requestData);
	}
    
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
		return getCountries(requestData);
    }
    
}