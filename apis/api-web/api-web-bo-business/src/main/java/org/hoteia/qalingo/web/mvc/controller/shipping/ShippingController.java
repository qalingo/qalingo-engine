/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.shipping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Shipping;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ShippingService;
import org.hoteia.qalingo.core.web.mvc.form.ShippingForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.ShippingViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("shippingController")
public class ShippingController extends AbstractBusinessBackofficeController {

	@Autowired
	private ShippingService shippingService;

	@RequestMapping(value = BoUrls.SHIPPING_LIST_URL, method = RequestMethod.GET)
	public ModelAndView shippingList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.SHIPPING_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
		final String contentText = getSpecificMessage(ScopeWebMessage.SHIPPING, BoMessageKey.MAIN_CONTENT_TEXT, locale);
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
	
	@RequestMapping(value = BoUrls.SHIPPING_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView shippingDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.SHIPPING_DETAILS.getVelocityPage());

		final String currentShippingCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingById(currentShippingCode);
		
		if(shipping != null){
			initShippingDetailsPage(requestUtil.getRequestData(request), model, modelAndView, shipping);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.SHIPPING_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView shippingEdit(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.SHIPPING_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String currentShippingId = request.getParameter(RequestConstants.REQUEST_PARAMETER_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingByCode(currentShippingId);
		
		modelAndView.addObject(ModelConstants.SHIPPING_VIEW_BEAN, backofficeViewBeanFactory.buildShippingViewBean(requestData, shipping));
		modelAndView.addObject(ModelConstants.SHIPPING_FORM, backofficeFormFactory.buildShippingForm(requestData, shipping));
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.SHIPPING_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitShippingEdit(final HttpServletRequest request, final Model model, @Valid ShippingForm shippingForm,
								BindingResult result) throws Exception {
		
		final String currentShippingId = request.getParameter(RequestConstants.REQUEST_PARAMETER_SHIPPING_CODE);
		final Shipping shipping = shippingService.getShippingByCode(currentShippingId);
		
		if (result.hasErrors()) {
			return shippingEdit(request, model);
		}
		
		// UPDATE SHIPPING
//		webBackofficeService.updateShipping(shipping, shippingForm);
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.SHIPPING_DETAILS, requestUtil.getRequestData(request), shipping);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<ShippingViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception{
		PagedListHolder<ShippingViewBean> shippingViewBeanPagedListHolder = new PagedListHolder<ShippingViewBean>();
		
		final List<ShippingViewBean> shippingViewBeans = new ArrayList<ShippingViewBean>();

		final List<Shipping> shippings = shippingService.findShippings();
		for (Iterator<Shipping> iterator = shippings.iterator(); iterator.hasNext();) {
			Shipping shipping = (Shipping) iterator.next();
			shippingViewBeans.add(backofficeViewBeanFactory.buildShippingViewBean(requestUtil.getRequestData(request), shipping));
		}
		shippingViewBeanPagedListHolder = new PagedListHolder<ShippingViewBean>(shippingViewBeans);
		shippingViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, shippingViewBeanPagedListHolder);
        
        return shippingViewBeanPagedListHolder;
	}
    
	protected void initShippingDetailsPage(final RequestData requestData, final Model model, final ModelAndViewThemeDevice modelAndView, final Shipping user) throws Exception{
		modelAndView.addObject(ModelConstants.SHIPPING_VIEW_BEAN, backofficeViewBeanFactory.buildShippingViewBean(requestData, user));
	}

}