/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.mvc.factory.impl.ViewBeanFactoryImpl;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;

/**
 * 
 */
public class ExtViewBeanFactoryImpl extends ViewBeanFactoryImpl {

	/**
     * 
     */
	@Override
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		MenuViewBean menu = new MenuViewBean();
		menu.setCssClass("active");
		menu.setCssIcon("icon-home");
		menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
		menu.setUrl(backofficeUrlService.buildHomeUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-sitemap");
		menu.setName("Catalog stats");
		menu.setUrl(backofficeUrlService.buildCatalogStatsUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-money");
		menu.setName("Promotion stats");
		menu.setUrl(backofficeUrlService.buildPromotionStatsUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-truck");
		menu.setName("Shipping stats");
		menu.setUrl(backofficeUrlService.buildShippingStatsUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-shopping-cart");
		menu.setName("Orders stats");
		menu.setUrl(backofficeUrlService.buildOrderStatsUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-group");
		menu.setName("Customers stats");
		menu.setUrl(backofficeUrlService.buildCustomerStatsUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-bar-chart");
		menu.setName("Reporting");
		menu.setUrl(backofficeUrlService.buildReportingUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-paper-clip");
		menu.setName("FAQ");
		menu.setUrl(backofficeUrlService.buildFaqUrl());
		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
}