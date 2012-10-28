/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, List<Localization> localizations) throws Exception;
	
	LocalizationViewBean buildLocalizationViewBean(HttpServletRequest request, Localization localization) throws Exception;

	LegacyViewBean buildLegacyViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, Localization localization) throws Exception;

	QuickSearchViewBean buildQuickSearchViewBean(HttpServletRequest request, Localization localization) throws Exception;
}
