/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.shipping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import fr.hoteia.qalingo.core.BoPageConstants;
import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Shipping;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.ShippingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.ShippingForm;
import fr.hoteia.qalingo.web.mvc.viewbean.ShippingViewBean;

/**
 * 
 */
@Controller("shippingController")
public class ShippingController extends AbstractBusinessBackofficeController {

	@Autowired
	private ShippingService shippingService;
	
	@RequestMapping(value = "/shipping-list.html*", method = RequestMethod.GET)
	public ModelAndView shipping(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.SHIPPING_LIST_VELOCITY_PAGE);
		
		final String contentText = getSpecificMessage(ScopeWebMessage.SHIPPING, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Shippings";
		
		PagedListHolder<ShippingViewBean> shippingViewBeanPagedListHolder = new PagedListHolder<ShippingViewBean>();
		
        if(StringUtils.isEmpty(page)){
        	shippingViewBeanPagedListHolder = initList(request, sessionKey);
    		
        } else {
        	shippingViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (shippingViewBeanPagedListHolder == null) { 
	        	shippingViewBeanPagedListHolder = initList(request, sessionKey);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = shippingViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		shippingViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		shippingViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, shippingViewBeanPagedListHolder);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/shipping-details.html*", method = RequestMethod.GET)
	public ModelAndView shippingDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.SHIPPING_DETAILS_VELOCITY_PAGE);

		final String currentShippingCode = request.getParameter(RequestConstants.REQUEST_PARAM_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingById(currentShippingCode);
		
		if(shipping != null){
			initShippingDetailsPage(request, response, modelAndView, shipping);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/shipping-edit.html*", method = RequestMethod.GET)
	public ModelAndView shippingEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.SHIPPING_FORM_VELOCITY_PAGE);
		
		// "shipping.edit";

		final String currentShippingId = request.getParameter(RequestConstants.REQUEST_PARAM_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingByCode(currentShippingId);
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		modelAndView.addObject(Constants.SHIPPING_VIEW_BEAN, viewBeanFactory.buildShippingViewBean(request, currentLocalization, shipping));
		modelAndView.addObject(Constants.SHIPPING_FORM, formFactory.buildShippingForm(request, shipping));
		return modelAndView;
	}
	
	@RequestMapping(value = "/shipping-edit.html*", method = RequestMethod.POST)
	public ModelAndView shippingEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid ShippingForm shippingForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		// "shipping.edit";
		
		final String currentShippingId = request.getParameter(RequestConstants.REQUEST_PARAM_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingByCode(currentShippingId);
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.SHIPPING_FORM_VELOCITY_PAGE);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			modelAndView.addObject(Constants.SHIPPING_VIEW_BEAN, viewBeanFactory.buildShippingViewBean(request, currentLocalization, shipping));
			modelAndView.addObject(Constants.SHIPPING_FORM, formFactory.buildShippingForm(request, shipping));
			return modelAndView;
		}
		
		// UPDATE SHIPPING
//		webBackofficeService.updateShipping(shipping, shippingForm);
		
		final String urlRedirect = backofficeUrlService.buildShippingDetailsUrl(currentShippingId);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<ShippingViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		PagedListHolder<ShippingViewBean> shippingViewBeanPagedListHolder = new PagedListHolder<ShippingViewBean>();
		
		final List<ShippingViewBean> shippingViewBeans = new ArrayList<ShippingViewBean>();

		final List<Shipping> shippings = shippingService.findShippings();
		for (Iterator<Shipping> iterator = shippings.iterator(); iterator.hasNext();) {
			Shipping shipping = (Shipping) iterator.next();
			shippingViewBeans.add(viewBeanFactory.buildShippingViewBean(request, currentLocalization, shipping));
		}
		shippingViewBeanPagedListHolder = new PagedListHolder<ShippingViewBean>(shippingViewBeans);
		shippingViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, shippingViewBeanPagedListHolder);
        
        return shippingViewBeanPagedListHolder;
	}
    
	protected void initShippingDetailsPage(final HttpServletRequest request, final HttpServletResponse response, final ModelAndViewThemeDevice modelAndView, final Shipping user) throws Exception{
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		// "shipping.details";
		modelAndView.addObject(Constants.SHIPPING_VIEW_BEAN, viewBeanFactory.buildShippingViewBean(request, currentLocalization, user));
	}
}