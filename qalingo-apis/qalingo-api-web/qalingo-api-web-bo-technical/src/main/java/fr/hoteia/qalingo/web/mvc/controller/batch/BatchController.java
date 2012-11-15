/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.common.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.enumtype.BatchProcessObjectType;
import fr.hoteia.qalingo.core.common.service.BatchProcessObjectService;
import fr.hoteia.qalingo.core.common.service.EngineSettingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LinkMenuViewBean;

/**
 * 
 */
@Controller
public class BatchController extends AbstractQalingoController {

	@Autowired
	protected BatchProcessObjectService batchProcessObjectService;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	@RequestMapping("/search-batch.html*")
	public ModelAndView searchBatch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");

		final String titleKeyPrefixSufix = "search";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		final String contentTest = coreMessageSource.getMessage("home.content.text", null, getCurrentLocale(request));
		modelAndView.addObject("contentTest", contentTest);
		
		formFactory.buildBatchQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch.html*", method = RequestMethod.GET)
	public ModelAndView batch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return batchCustomer(request, response);
	}
	
	@RequestMapping(value = "/batch-customer.html*", method = RequestMethod.GET)
	public ModelAndView batchCustomer(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "batch.customer.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Customer.getPropertyKey());
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CRM).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-order.html*", method = RequestMethod.GET)
	public ModelAndView batchOrder(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "batch.order.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Order.getPropertyKey());
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CRM).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-email.html*", method = RequestMethod.GET)
	public ModelAndView batchEmail(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "batch.email.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Email.getPropertyKey());
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_NOTIFICATION).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-cms.html*", method = RequestMethod.GET)
	public ModelAndView batchCms(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "batch.cms.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Cms.getPropertyKey());
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CMS).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-stock.html*", method = RequestMethod.GET)
	public ModelAndView batchStock(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "batch/batch-list");
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		final String titleKeyPrefixSufix = "batch.stock.list";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Stock.getPropertyKey());
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_ERP).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	protected void initPaginationResult(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Localization currentLocalization, final List<BatchProcessObject> batchProcessObjects) throws Exception{
		String url = requestUtil.getCurrentRequestUrl(request);
		
		String sessionKey = "PagedListHolder_Search_List_Product_" + request.getSession().getId();
        String page = request.getParameter(Constants.PAGE_PARAMETER);
		PagedListHolder<BatchViewBean> userViewBeanPagedListHolder;

        if(StringUtils.isEmpty(page)){
        	userViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, batchProcessObjects, new PagedListHolder<BatchViewBean>());
        } else {
	        userViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (userViewBeanPagedListHolder == null) { 
	        	userViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, batchProcessObjects, userViewBeanPagedListHolder);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = userViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		userViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
		        	userViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGE_URL, url);
		modelAndView.addObject(Constants.PAGE_PAGED_LIST_HOLDER, userViewBeanPagedListHolder);
		
		formFactory.buildBatchQuickSearchForm(request, modelAndView);	
	}
	
	protected PagedListHolder<BatchViewBean> initList(final HttpServletRequest request, final String sessionKey, final Localization currentLocalization, 
			final List<BatchProcessObject> batchProcessObjects, PagedListHolder<BatchViewBean> userViewBeanPagedListHolder) throws Exception{
		List<BatchViewBean> batchViewBeans = viewBeanFactory.buildBatchViewBeans(request, currentLocalization, batchProcessObjects);
		userViewBeanPagedListHolder = new PagedListHolder<BatchViewBean>(batchViewBeans);
		userViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE); 
        request.getSession().setAttribute(sessionKey, userViewBeanPagedListHolder);
        return userViewBeanPagedListHolder;
	}
	
	protected void initLinks(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Locale locale){
		List<LinkMenuViewBean> batchLinks = new ArrayList<LinkMenuViewBean>();

		LinkMenuViewBean linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.customer.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildBatchCustomerUrl(request));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.order.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildBatchOrderUrl(request));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.email.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildBatchEmailUrl(request));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.cms.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildBatchCmsUrl(request));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.stock.list", null, locale));
		linkMenuViewBean.setUrl(backofficeUrlService.buildBatchStockUrl(request));
		batchLinks.add(linkMenuViewBean);
		
		modelAndView.addObject("links", batchLinks);
	}
    
}
