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
public class BoTechnicalViewBeanFactory extends BackofficeViewBeanFactory {

    /**
     * 
     */
    @Override
    public List<MenuViewBean> buildListViewBeanMenu(RequestData requestData) throws Exception {
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

        List<String> engineSettingUrls = new ArrayList<String>();
        engineSettingUrls.add(BoUrls.ENGINE_SETTING_LIST.getUrlWithoutWildcard());
        engineSettingUrls.add(BoUrls.ENGINE_SETTING_DETAILS.getUrlWithoutWildcard());
        engineSettingUrls.add(BoUrls.ENGINE_SETTING_EDIT.getUrlWithoutWildcard());
        engineSettingUrls.add(BoUrls.ENGINE_SETTING_VALUE_EDIT.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, engineSettingUrls));
        menu.setCssIcon("fa fa-cogs");
		menu.setName("Engine Setting");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_LIST, requestData));
		menuViewBeans.add(menu);
		
        List<String> cacheUrls = new ArrayList<String>();
        cacheUrls.add(BoUrls.CACHE.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, cacheUrls));
		menu.setCssIcon("fa fa-bars");
		menu.setName("Cache");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.CACHE, requestData));
		menuViewBeans.add(menu);

        List<String> batchUrls = new ArrayList<String>();
        batchUrls.add(BoUrls.BATCH.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, batchUrls));
		menu.setCssIcon("fa fa-random");
		menu.setName("Batch");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.BATCH, requestData));
		menuViewBeans.add(menu);
		
        List<String> paymentGatewayUrls = new ArrayList<String>();
        paymentGatewayUrls.add(BoUrls.PAYMENT_GATEWAY_LIST.getUrlWithoutWildcard());
        paymentGatewayUrls.add(BoUrls.PAYMENT_GATEWAY_DETAILS.getUrlWithoutWildcard());
        paymentGatewayUrls.add(BoUrls.PAYMENT_GATEWAY_EDIT.getUrlWithoutWildcard());

        menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, paymentGatewayUrls));
        menu.setCssIcon("fa fa-money");
        menu.setName("Payment Gateway");
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_LIST, requestData));
        menuViewBeans.add(menu);

        List<String> userUrls = new ArrayList<String>();
        userUrls.add(BoUrls.USER_LIST.getUrlWithoutWildcard());
        userUrls.add(BoUrls.USER_DETAILS.getUrlWithoutWildcard());
        userUrls.add(BoUrls.USER_EDIT.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, userUrls));
		menu.setCssIcon("fa fa-group");
		menu.setName("User");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.USER_LIST, requestData));
		menuViewBeans.add(menu);
		
        List<String> referenceDataUrls = new ArrayList<String>();
        referenceDataUrls.add(BoUrls.REFERENCE_DATAS.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, referenceDataUrls));
		menu.setCssIcon("fa fa-book");
		menu.setName("References Datas");
		menu.setUrl(backofficeUrlService.generateUrl(BoUrls.REFERENCE_DATAS, requestData));
		menuViewBeans.add(menu);
		
        List<String> monitoringUrls = new ArrayList<String>();
        monitoringUrls.add(BoUrls.MONITORING.getUrlWithoutWildcard());
        
		menu = new MenuViewBean();
        menu.setActive(menuIsActive(currentUrl, monitoringUrls));
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