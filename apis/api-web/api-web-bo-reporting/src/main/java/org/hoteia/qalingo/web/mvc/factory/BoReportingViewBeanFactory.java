/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;

/**
 * 
 */
public class BoReportingViewBeanFactory extends BackofficeViewBeanFactory {

	/**
     * 
     */
	@Override
	public List<MenuViewBean> buildListViewBeanMenu(final RequestData requestData) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = localization.getLocale();
        final String currentUrl = requestUtil.getCurrentRequestUrl(request);
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
        List<String> homeUrls = new ArrayList<String>();
        homeUrls.add(BoUrls.HOME.getUrlWithoutWildcard());
        
		MenuViewBean menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, homeUrls));
		menu.setCssIcon("fa fa-home");
		menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
		menuViewBeans.add(menu);

        List<String> catalogUrls = new ArrayList<String>();
        catalogUrls.add(BoUrls.CATALOG.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, catalogUrls));
		menu.setCssIcon("fa fa-sitemap");
		menu.setName("Catalog stats");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.CATALOG, requestData));
		menuViewBeans.add(menu);
		
        List<String> ruletUrls = new ArrayList<String>();
        ruletUrls.add(BoUrls.RULE_LIST.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, ruletUrls));
		menu.setCssIcon("fa fa-money");
		menu.setName("Promotion stats");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.RULE_LIST, requestData));
		menuViewBeans.add(menu);

        List<String> deliveryMethodsUrls = new ArrayList<String>();
        deliveryMethodsUrls.add(BoUrls.DELIVERY_METHOD_LIST.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, deliveryMethodsUrls));
		menu.setCssIcon("fa fa-truck");
		menu.setName("Shipping stats");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_LIST, requestData));
		menuViewBeans.add(menu);
		
        List<String> orderUrls = new ArrayList<String>();
        orderUrls.add(BoUrls.ORDER_LIST.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, orderUrls));
		menu.setCssIcon("fa fa-shopping-cart");
		menu.setName("Orders stats");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.ORDER_LIST, requestData));
		menuViewBeans.add(menu);
		
        List<String> customerUrls = new ArrayList<String>();
        customerUrls.add(BoUrls.CUSTOMER_LIST.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, customerUrls));
		menu.setCssIcon("fa fa-group");
		menu.setName("Customers stats");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.CUSTOMER_LIST, requestData));
		menuViewBeans.add(menu);
		
        List<String> reportingUrls = new ArrayList<String>();
        reportingUrls.add(BoUrls.REPORTING.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, reportingUrls));
		menu.setCssIcon("fa fa-bar-chart-o");
		menu.setName("Reporting");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.REPORTING, requestData));
		menuViewBeans.add(menu);
		
//		menu = new MenuViewBean();
//		menu.setCssIcon("fa fa-paper-clip");
//		menu.setName("FAQ");
//		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.FAQ, requestData));
//		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
}