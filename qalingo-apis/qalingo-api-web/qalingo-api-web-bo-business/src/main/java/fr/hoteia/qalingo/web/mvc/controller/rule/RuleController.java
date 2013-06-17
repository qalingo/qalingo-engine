/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.rule;

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
import fr.hoteia.qalingo.core.domain.AbstractRuleReferential;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.i18n.BoMessageKey;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.RuleReferentialService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import fr.hoteia.qalingo.web.mvc.form.RuleForm;
import fr.hoteia.qalingo.web.mvc.viewbean.RuleViewBean;

/**
 * 
 */
@Controller
public class RuleController extends AbstractBusinessBackofficeController {

	@Autowired
	private RuleReferentialService ruleReferentialService;
	
	@RequestMapping(value = "/rule-list.html*", method = RequestMethod.GET)
	public ModelAndView rule(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.RULE_LIST_VELOCITY_PAGE);
		
		final String contentText = getSpecificMessage(ScopeWebMessage.RULE, BoMessageKey.MAIN_CONTENT_TEXT, getCurrentLocale(request));
		modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
		
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGE_PARAMETER);
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
		modelAndView.addObject(Constants.PAGE_URL, url);
		modelAndView.addObject(Constants.PAGE_PAGED_LIST_HOLDER, ruleViewBeanPagedListHolder);
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/rule-details.html*", method = RequestMethod.GET)
	public ModelAndView ruleDetails(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.RULE_DETAILS_VELOCITY_PAGE);

		final String currentRuleCode = request.getParameter(Constants.REQUEST_PARAM_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);
		
		if(rule != null){
			initRuleDetailsPage(request, response, modelAndView, rule);
		} else {
			final String url = requestUtil.getLastRequestUrl(request);
			return new ModelAndView(new RedirectView(url));
		}
		
        return modelAndView;
	}
	
	@RequestMapping(value = "/rule-edit.html*", method = RequestMethod.GET)
	public ModelAndView ruleEdit(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.RULE_FORM_VELOCITY_PAGE);
		
		// "rule.edit";

		final String currentRuleCode = request.getParameter(Constants.REQUEST_PARAM_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);

		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		modelAndView.addObject(Constants.RULE_VIEW_BEAN, viewBeanFactory.buildRuleViewBean(request, currentLocalization, rule));
		modelAndView.addObject(Constants.RULE_FORM, formFactory.buildRuleForm(request, rule));
		return modelAndView;
	}
	
	@RequestMapping(value = "/rule-edit.html*", method = RequestMethod.POST)
	public ModelAndView ruleEdit(final HttpServletRequest request, final HttpServletResponse response, @Valid RuleForm ruleForm,
								BindingResult result, ModelMap modelMap) throws Exception {

		// "rule.edit";
		final String currentRuleCode = request.getParameter(Constants.REQUEST_PARAM_RULE_CODE);
		final AbstractRuleReferential rule = ruleReferentialService.getRuleReferentialByCode(currentRuleCode);
		
		if (result.hasErrors()) {
			ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoPageConstants.RULE_FORM_VELOCITY_PAGE);
			final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
			modelAndView.addObject(Constants.RULE_VIEW_BEAN, viewBeanFactory.buildRuleViewBean(request, currentLocalization, rule));
			modelAndView.addObject(Constants.RULE_FORM, formFactory.buildRuleForm(request, rule));
			return modelAndView;
		}
		
		// UPDATE RULE
//		webBackofficeService.updateRule(rule, ruleForm);
		
		final String urlRedirect = backofficeUrlService.buildRuleDetailsUrl(currentRuleCode);
        return new ModelAndView(new RedirectView(urlRedirect));
	}

	private PagedListHolder<RuleViewBean> initList(final HttpServletRequest request, String sessionKey) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		PagedListHolder<RuleViewBean> ruleViewBeanPagedListHolder = new PagedListHolder<RuleViewBean>();
		
		final List<RuleViewBean> ruleViewBeans = new ArrayList<RuleViewBean>();

		final List<AbstractRuleReferential> rules = ruleReferentialService.findRuleReferentials();
		for (Iterator<AbstractRuleReferential> iterator = rules.iterator(); iterator.hasNext();) {
			AbstractRuleReferential rule = (AbstractRuleReferential) iterator.next();
			ruleViewBeans.add(viewBeanFactory.buildRuleViewBean(request, currentLocalization, rule));
		}
		ruleViewBeanPagedListHolder = new PagedListHolder<RuleViewBean>(ruleViewBeans);
		ruleViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, ruleViewBeanPagedListHolder); 
        
        return ruleViewBeanPagedListHolder;
	}
    
	protected void initRuleDetailsPage(final HttpServletRequest request, final HttpServletResponse response, 
											final ModelAndViewThemeDevice modelAndView, final AbstractRuleReferential rule) throws Exception {
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		// "rule.details";

		modelAndView.addObject(Constants.RULE_VIEW_BEAN, viewBeanFactory.buildRuleViewBean(request, currentLocalization, rule));
	}
}