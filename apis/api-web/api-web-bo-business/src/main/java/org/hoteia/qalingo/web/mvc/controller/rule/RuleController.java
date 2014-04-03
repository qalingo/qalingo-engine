/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RuleReferentialService;
import org.hoteia.qalingo.core.web.mvc.form.RuleForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.RuleViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("ruleController")
public class RuleController extends AbstractBusinessBackofficeController {

	@Autowired
	private RuleReferentialService ruleReferentialService;

	@RequestMapping(value = BoUrls.RULE_LIST_URL, method = RequestMethod.GET)
	public ModelAndView ruleList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RULE_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Rules";
		
		PagedListHolder<RuleViewBean> ruleViewBeanPagedListHolder = new PagedListHolder<RuleViewBean>();
		
        if(StringUtils.isEmpty(page)){
        	ruleViewBeanPagedListHolder = initList(request, sessionKey);
    		
        } else {
        	ruleViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (ruleViewBeanPagedListHolder == null) { 
	        	ruleViewBeanPagedListHolder = initList(request, sessionKey);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = ruleViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		ruleViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		ruleViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
		modelAndView.addObject(Constants.PAGINATION_PAGE_URL, url);
		modelAndView.addObject(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, ruleViewBeanPagedListHolder);
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RULE_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView ruleDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RULE_DETAILS.getVelocityPage());

		final String currentRuleCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);
		
		if(rule != null){
			initRuleDetailsPage(requestUtil.getRequestData(request), model, modelAndView, rule);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RULE_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView ruleEdit(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.RULE_EDIT.getVelocityPage());
		final RequestData requestData = requestUtil.getRequestData(request);
		final String currentRuleCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);

		modelAndView.addObject(ModelConstants.RULE_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanRule(requestData, rule));
		modelAndView.addObject(ModelConstants.RULE_FORM, backofficeFormFactory.buildRuleForm(requestData, rule));
		return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.RULE_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitRuleEdit(final HttpServletRequest request, final Model model, @Valid RuleForm ruleForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		final String currentRuleCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);
		
		if (result.hasErrors()) {
			return ruleEdit(request, model);
		}
		
		// UPDATE RULE
//		webBackofficeService.updateRule(rule, ruleForm);
		
		final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.RULE_DETAILS, requestUtil.getRequestData(request), rule);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<RuleViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception {
		PagedListHolder<RuleViewBean> ruleViewBeanPagedListHolder = new PagedListHolder<RuleViewBean>();
		
		final List<RuleViewBean> ruleViewBeans = new ArrayList<RuleViewBean>();

		final List<AbstractRuleReferential> rules = ruleReferentialService.findRuleReferentials();
		for (Iterator<AbstractRuleReferential> iterator = rules.iterator(); iterator.hasNext();) {
			AbstractRuleReferential rule = (AbstractRuleReferential) iterator.next();
			ruleViewBeans.add(backofficeViewBeanFactory.buildViewBeanRule(requestUtil.getRequestData(request), rule));
		}
		ruleViewBeanPagedListHolder = new PagedListHolder<RuleViewBean>(ruleViewBeans);
		ruleViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, ruleViewBeanPagedListHolder); 
        
        return ruleViewBeanPagedListHolder;
	}
    
	protected void initRuleDetailsPage(final RequestData requestData, final Model model, 
											final ModelAndViewThemeDevice modelAndView, final AbstractRuleReferential rule) throws Exception {
		modelAndView.addObject(ModelConstants.RULE_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanRule(requestData, rule));
	}

}