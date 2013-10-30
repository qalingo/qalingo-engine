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
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.enumtype.BatchProcessObjectType;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.service.BatchProcessObjectService;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.BatchValueForm;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LinkMenuViewBean;

/**
 * 
 */
@Controller("batchController")
public class BatchController extends AbstractTechnicalBackofficeController {

	@Autowired
	protected BatchProcessObjectService batchProcessObjectService;
	
	@Autowired
	protected EngineSettingService engineSettingService;
	
	@RequestMapping("/search-batch.html*")
	public ModelAndView searchBatch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());

		formFactory.buildBatchQuickSearchForm(request, modelAndView);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.BATCH_URL, method = RequestMethod.GET)
	public ModelAndView batchSuccessAndError(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjects();//batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Customer);
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CRM).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
		String tab =  request.getParameter(RequestConstants.REQUEST_PARAMETER_TAB);
		/**
		 * Save the user selected Tab page, when reset, still stuck in this tag page
		 * so that the user experience better. That is, when user can click 
		 * one time mouse to complete an action, do not let user more than a few 
		 * mouse clicks to complete the action
		 * */
		if(null == tab){
			tab = "0";
		}
		modelAndView.addObject("tab", tab);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.BATCH_URL, method = RequestMethod.POST)
	public ModelAndView batchReset(final HttpServletRequest request, final Model model, @Valid BatchValueForm batchValueForm,
			BindingResult result, ModelMap modelMap) throws Exception {

		final String id = batchValueForm.getId();
		
		final BatchProcessObject batchProcessObject = batchProcessObjectService.getBatchProcessObjectById(id);
		if (null != batchProcessObject) {
			batchProcessObject.setProcessedCount(0);
			// UPDATE PROCESSED_COUNT
			batchProcessObjectService.saveOrUpdateBatchProcessObject(batchProcessObject);

		}

        return batchSuccessAndError(request,model);
	}
	
	@RequestMapping(value = "/batch-customer.html*", method = RequestMethod.GET)
	public ModelAndView batchCustomer(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjects();//batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Customer);
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CRM).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-order.html*", method = RequestMethod.GET)
	public ModelAndView batchOrder(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Order);
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CRM).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-email.html*", method = RequestMethod.GET)
	public ModelAndView batchEmail(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Email);
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_NOTIFICATION).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-cms.html*", method = RequestMethod.GET)
	public ModelAndView batchCms(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Cms);
		initPaginationResult(request, modelAndView, currentLocalization, batchProcessObjects);
		initLinks(request, modelAndView, locale);
		
		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SPRING_BATCH_URL);
		String springBatchUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_CONTEXT_CMS).getValue();
		modelAndView.addObject("springBatchUrl", springBatchUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/batch-stock.html*", method = RequestMethod.GET)
	public ModelAndView batchStock(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.BATCH.getVelocityPage());
		
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Locale locale = currentLocalization.getLocale();
		
		List<BatchProcessObject> batchProcessObjects = batchProcessObjectService.findBatchProcessObjectsByTypeObject(BatchProcessObjectType.Stock);
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
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		PagedListHolder<BatchViewBean> batchViewBeanPagedListHolder;

        if(StringUtils.isEmpty(page)){
        	batchViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, batchProcessObjects, new PagedListHolder<BatchViewBean>());
        } else {
	        batchViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (batchViewBeanPagedListHolder == null) { 
	        	batchViewBeanPagedListHolder = initList(request, sessionKey, currentLocalization, batchProcessObjects, batchViewBeanPagedListHolder);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = batchViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		batchViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
		        	batchViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, batchViewBeanPagedListHolder);
		
		formFactory.buildBatchQuickSearchForm(request, modelAndView);	
	}
	
	protected PagedListHolder<BatchViewBean> initList(final HttpServletRequest request, final String sessionKey, final Localization currentLocalization, 
			final List<BatchProcessObject> batchProcessObjects, PagedListHolder<BatchViewBean> batchViewBeanPagedListHolder) throws Exception{
		List<BatchViewBean> batchViewBeans = viewBeanFactory.buildBatchViewBeans(requestUtil.getRequestData(request), batchProcessObjects);
		batchViewBeanPagedListHolder = new PagedListHolder<BatchViewBean>(batchViewBeans);

		batchViewBeanPagedListHolder.setPageSize(Constants.PAGINATION_DEFAULT_PAGE_SIZE); 
		String pageSize = engineSettingService.getEngineSettingValueByCode(EngineSettingService.ENGINE_SETTING_CODE_COUNT_ITEM_BY_PAGE, EngineSettingService.ENGINE_SETTING_CONTEXT_BO_TECHNICAL_ENGINE_SETTING_LIST);
		if(StringUtils.isNotEmpty(pageSize)){
			batchViewBeanPagedListHolder.setPageSize(Integer.parseInt(pageSize)); 
		}

        request.getSession().setAttribute(sessionKey, batchViewBeanPagedListHolder);
        return batchViewBeanPagedListHolder;
	}
	
	protected void initLinks(final HttpServletRequest request, final ModelAndViewThemeDevice modelAndView, final Locale locale) throws Exception{
		List<LinkMenuViewBean> batchLinks = new ArrayList<LinkMenuViewBean>();

		LinkMenuViewBean linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.customer.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH_CUSTOMER, requestUtil.getRequestData(request)));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.order.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH_ORDER, requestUtil.getRequestData(request)));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.email.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH_EMAIL, requestUtil.getRequestData(request)));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.cms.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH_CMS, requestUtil.getRequestData(request)));
		batchLinks.add(linkMenuViewBean);
		
		linkMenuViewBean = new LinkMenuViewBean();
		linkMenuViewBean.setName(coreMessageSource.getMessage("header.menu.batch.stock.list", locale));
		linkMenuViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH_STOCK, requestUtil.getRequestData(request)));
		batchLinks.add(linkMenuViewBean);
		
		modelAndView.addObject("links", batchLinks);
	}
    
}