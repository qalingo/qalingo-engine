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
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.exception.UniqueNewsletterSubscriptionException;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.web.mvc.form.FollowUsForm;
import org.hoteia.qalingo.core.web.mvc.form.NewsletterQuickRegistrationForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
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
@Controller("followUsController")
public class FollowUsController extends AbstractMCommerceController {

	@Autowired
    protected MarketService marketService;
	
	@RequestMapping(value = FoUrls.FOLLOW_US_URL, method = RequestMethod.GET)
	public ModelAndView displayFollowUs(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.FOLLOW_US_FORM) FollowUsForm followUsForm) throws Exception{
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FOLLOW_US.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        overrideDefaultPageTitle(request, modelAndView, FoUrls.FOLLOW_US.getKey());

        modelAndView.addObject(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.FOLLOW_US_URL, method = RequestMethod.POST)
	public ModelAndView followUs(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.FOLLOW_US_FORM) FollowUsForm followUsForm,
								BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FOLLOW_US_SUCCESS_VELOCITY_PAGE);

		if (result.hasErrors()) {
			return displayFollowUs(request, model, followUsForm);
		}

		try {
			webManagementService.saveNewsletterSubscriptionAndSendEmail(requestUtil.getRequestData(request), followUsForm.getEmail());
	        
        } catch (UniqueNewsletterSubscriptionException e) {
			final String forgottenPasswordUrl = urlService.generateUrl(FoUrls.FORGOTTEN_PASSWORD, requestUtil.getRequestData(request));
			final Object[] objects = {forgottenPasswordUrl};
			result.rejectValue("email", "fo.follow_us.form_fail_unique_email_message", objects,"This email customer account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
			displayFollowUs(request, model, followUsForm);
			
        } catch (Exception e) {
	        displayFollowUs(request, model, followUsForm);
        }
		
        return modelAndView;
	}
	
	
	@RequestMapping(value = FoUrls.NEWSLETTER_REGISTER_URL, method = RequestMethod.GET)
	public ModelAndView newsletterRegister(final HttpServletRequest request, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FOLLOW_US_SUCCESS_VELOCITY_PAGE);

		try {
			String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL);
			webManagementService.saveNewsletterSubscriptionAndSendEmail(requestUtil.getRequestData(request), email);
	        
        } catch (Exception e) {
	        displayFollowUs(request, model, new FollowUsForm());
        }
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.NEWSLETTER_REGISTER_URL, method = RequestMethod.POST)
	public ModelAndView newsletterRegister(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.NEWSLETTER_QUICK_REGISTRATION_FORM) NewsletterQuickRegistrationForm newsletterQuickRegistrationForm,
								BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.FOLLOW_US_SUCCESS_VELOCITY_PAGE);

		try {
			webManagementService.saveNewsletterSubscriptionAndSendEmail(requestUtil.getRequestData(request), newsletterQuickRegistrationForm.getEmail());
	        
        } catch (Exception e) {
	        displayFollowUs(request, model, new FollowUsForm());
        }
		
        return modelAndView;
	}
	
	@RequestMapping(value = FoUrls.NEWSLETTER_UNREGISTER_URL, method = RequestMethod.GET)
	public ModelAndView newsletterUnRegister(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.NEWSLETTER_QUICK_REGISTRATION_FORM) NewsletterQuickRegistrationForm newsletterQuickRegistrationForm,
								BindingResult result, Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.NEWSLETTER_UNREGISTER_VELOCITY_PAGE);

		try {
			String marketAreaCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE);
			String email = request.getParameter(RequestConstants.REQUEST_PARAMETER_NEWSLETTER_EMAIL);
			
			RequestData requestData = requestUtil.getRequestData(request);
			requestData.setMarketArea(marketService.getMarketAreaByCode(marketAreaCode));
			
			webManagementService.saveNewsletterUnsubscriptionAndSendEmail(requestData, email);
	        
        } catch (Exception e) {
	        displayFollowUs(request, model, new FollowUsForm());
        }
		
        return modelAndView;
	}
	
    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData) {
        final Locale locale = requestData.getLocale();

        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.FOLLOW_US.getMessageKey(), locale));

        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setKey(FoUrls.FOLLOW_US.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.FOLLOW_US.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.FOLLOW_US, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);

        return breadcrumbViewBean;
    }
	   
	/**
	 * 
	 */
    @ModelAttribute(ModelConstants.FOLLOW_US_FORM)
	protected FollowUsForm getFollowUsForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
    	return formFactory.buildFollowUsForm(requestData);
	}
	
}