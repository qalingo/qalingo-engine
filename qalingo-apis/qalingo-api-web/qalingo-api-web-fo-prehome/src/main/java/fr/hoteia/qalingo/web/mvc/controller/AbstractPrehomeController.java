/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.hoteia.qalingo.core.ModelConstants;
import fr.hoteia.qalingo.core.web.mvc.factory.ViewBeanFactory;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.viewbean.LegalTermsViewBean;

/**
 * 
 * <p>
 * <a href="AbstractPrehomeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractPrehomeController extends AbstractFrontofficeQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected ViewBeanFactory viewBeanFactory;
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initCommon(final HttpServletRequest request, final Model model) throws Exception {
		model.addAttribute(ModelConstants.COMMON_VIEW_BEAN, viewBeanFactory.buildCommonViewBean(requestUtil.getRequestData(request)));
	}
	
	/**
	 * 
	 */
	@ModelAttribute
	protected void initLegalTerms(final HttpServletRequest request, final Model model) throws Exception {
		LegalTermsViewBean legalTermsViewBean = viewBeanFactory.buildLegalTermsViewBean(requestUtil.getRequestData(request));
		model.addAttribute(ModelConstants.LEGAl_TERMS_VIEW_BEAN, legalTermsViewBean);
	}
	
}