/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.common.domain.AbstractPaymentGateway;
import fr.hoteia.qalingo.core.common.domain.BatchProcessObject;
import fr.hoteia.qalingo.core.common.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.common.domain.EngineSetting;
import fr.hoteia.qalingo.core.common.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.User;
import fr.hoteia.qalingo.web.mvc.viewbean.BatchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CommonViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.CurrencyReferentialViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingDetailsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingValueEditViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.EngineSettingViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.FooterMenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LegacyViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.LocalizationViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.MenuViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.PaymentGatewayViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.QuickSearchViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.SecurityViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserDetailsViewBean;
import fr.hoteia.qalingo.web.mvc.viewbean.UserEditViewBean;

public interface ViewBeanFactory {

	CommonViewBean buildCommonViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	List<MenuViewBean> buildMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	List<FooterMenuViewBean> buildFooterMenuViewBeans(HttpServletRequest request, Localization localization) throws Exception;
	
	List<LocalizationViewBean> buildLocalizationViewBeans(HttpServletRequest request, List<Localization> localizations) throws Exception;
	
	LocalizationViewBean buildLocalizationViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	LegacyViewBean buildLegacyViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	SecurityViewBean buildSecurityViewBean(HttpServletRequest request, Localization localization) throws Exception;

	QuickSearchViewBean buildQuickSearchViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	List<EngineSettingViewBean> buildEngineSettingViewBeans(HttpServletRequest request, List<EngineSetting> engineSettings) throws Exception;
	
	EngineSettingViewBean buildEngineSettingViewBean(HttpServletRequest request, EngineSetting engineSetting) throws Exception;
	
	EngineSettingDetailsViewBean buildEngineSettingDetailsViewBean(HttpServletRequest request, Localization localization) throws Exception;
	
	EngineSettingValueEditViewBean buildEngineSettingValueEditViewBean(HttpServletRequest request, Localization localization, EngineSettingValue engineSettingValue) throws Exception;

	List<UserDetailsViewBean> buildUserViewBeans(HttpServletRequest request, Localization localization, List<User> users) throws Exception;
	
	UserDetailsViewBean buildUserViewBean(HttpServletRequest request, Localization localization, User user) throws Exception;

	UserEditViewBean buildUserEditViewBean(HttpServletRequest request, Localization localization, User user) throws Exception;

	List<BatchViewBean> buildBatchViewBeans(HttpServletRequest request, Localization localization, List<BatchProcessObject> batchProcessObjects) throws Exception;
	
	BatchViewBean buildBatchViewBean(HttpServletRequest request, Localization localization, BatchProcessObject batchProcessObject) throws Exception;
	
	List<CurrencyReferentialViewBean> buildCurrencyReferentialViewBeans(HttpServletRequest request, List<CurrencyReferential> currencyReferentials) throws Exception;
	
	CurrencyReferentialViewBean buildCurrencyReferentialViewBean(HttpServletRequest request, CurrencyReferential currencyReferential) throws Exception;
	
	List<PaymentGatewayViewBean> buildPaymentGatewayViewBeans(HttpServletRequest request, List<AbstractPaymentGateway> paymentGateways) throws Exception;
	
	PaymentGatewayViewBean buildPaymentGatewayViewBean(HttpServletRequest request, AbstractPaymentGateway paymentGateway) throws Exception;
}
