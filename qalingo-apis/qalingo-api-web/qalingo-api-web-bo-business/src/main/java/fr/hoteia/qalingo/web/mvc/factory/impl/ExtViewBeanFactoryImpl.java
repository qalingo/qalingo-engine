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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.web.mvc.factory.impl.ViewBeanFactoryImpl;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;

/**
 * 
 */
public class ExtViewBeanFactoryImpl extends ViewBeanFactoryImpl {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * @throws Exception 
	 * 
	 */
	public List<MenuViewBean> buildMenuViewBeans(final HttpServletRequest request, final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		final String currentUrl = requestUtil.getCurrentRequestUrl(request);
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		MenuViewBean menu = new MenuViewBean();
		if(currentUrl.contains("home")){
			menu.setCssClass("active");
		}
		menu.setCssIcon("icon-home");
		menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
		menu.setUrl(backofficeUrlService.buildHomeUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		if(currentUrl.contains("catalog")){
			menu.setCssClass("dropdown active");
		} else {
			menu.setCssClass("dropdown");
		}
		menu.setCssIcon("icon-sitemap");
		menu.setName("Catalog");
		menuViewBeans.add(menu);
		
		MenuViewBean subMenu = new MenuViewBean();
		subMenu.setName("Manage Master Catalog");
		subMenu.setUrl(backofficeUrlService.buildManageMasterCatalogUrl());
		menu.getSubMenus().add(subMenu);
		
		subMenu = new MenuViewBean();
		subMenu.setName("Manage Virtual Catalog");
		subMenu.setUrl(backofficeUrlService.buildManageVirtualCatalogUrl());
		menu.getSubMenus().add(subMenu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-money");
		menu.setName("Promotion");
		menu.setUrl(backofficeUrlService.buildRuleListUrl());
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("icon-truck");
		menu.setName("Shipping");
		menu.setUrl(backofficeUrlService.buildShippingListUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-shopping-cart");
		menu.setName("Orders");
		menu.setUrl(backofficeUrlService.buildOrderListUrl());
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("icon-group");
		menu.setName("Customers");
		menu.setUrl(backofficeUrlService.buildCustomerListUrl());
		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
}