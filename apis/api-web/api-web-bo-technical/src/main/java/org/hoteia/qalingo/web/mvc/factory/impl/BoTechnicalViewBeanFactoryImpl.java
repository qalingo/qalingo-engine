/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.factory.impl.BackofficeViewBeanFactoryImpl;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;

/**
 * 
 */
public class BoTechnicalViewBeanFactoryImpl extends BackofficeViewBeanFactoryImpl {

    /**
     * 
     */
    @Override
    public List<MenuViewBean> buildListViewBeanMenu(RequestData requestData) throws Exception {
		final Localization localization = requestData.getMarketAreaLocalization();
		final Locale locale = localization.getLocale();
		
		final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();
		
		MenuViewBean menu = new MenuViewBean();
		menu.setCssClass("active");
		menu.setCssIcon("fa fa-home");
		menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "home", locale));
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-cogs");
		menu.setName("Engine Setting");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-bars");
		menu.setName("Cache");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.CACHE, requestData));
		menuViewBeans.add(menu);

		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-random");
		menu.setName("Batch");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH, requestData));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-group");
		menu.setName("Users");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.USER_LIST, requestData));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-book");
		menu.setName("References Datas");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.REFERENCE_DATAS, requestData));
		menuViewBeans.add(menu);
		
		menu = new MenuViewBean();
		menu.setCssIcon("fa fa-bar-chart-o");
		menu.setName("Monitoring");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.MONITORING, requestData));
		menuViewBeans.add(menu);
		
//		menu = new MenuViewBean();
//		menu.setCssIcon("fa fa-paper-clip");
//		menu.setName("FAQ");
//		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.FAQ, requestData));
//		menuViewBeans.add(menu);
		
		return menuViewBeans;
	}
	
}