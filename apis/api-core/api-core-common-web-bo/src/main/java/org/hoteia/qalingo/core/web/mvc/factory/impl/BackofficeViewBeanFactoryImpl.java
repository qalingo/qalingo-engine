/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.factory.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.PaymentGatewayAttribute;
import org.hoteia.qalingo.core.domain.ProductAssociationLink;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserConnectionLog;
import org.hoteia.qalingo.core.domain.UserGroup;
import org.hoteia.qalingo.core.domain.UserPermission;
import org.hoteia.qalingo.core.domain.UserRole;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeCommonMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeReferenceDataMessage;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.mvc.factory.BackofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.mvc.viewbean.AssetViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.BatchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.BrandViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogCategoryViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CatalogViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CommonViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.DeliveryMethodViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingValueViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.EngineSettingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.GlobalSearchViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LegalTermsViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.LocalizationViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketAreaViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MarketViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.OrderViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.PaymentGatewayViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductAssociationLinkViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductMarketingViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductSkuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RetailerViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.RuleViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.SecurityViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserConnectionLogValueBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserViewBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service("backofficeViewBeanFactory")
public class BackofficeViewBeanFactoryImpl extends ViewBeanFactoryImpl implements BackofficeViewBeanFactory {

    @Autowired
    protected BackofficeUrlService backofficeUrlService;

    /**
     * 
     */
    @Override
    public CommonViewBean buildCommonViewBean(final RequestData requestData) throws Exception {
        final CommonViewBean commonViewBean = super.buildCommonViewBean(requestData);

        commonViewBean.setHomeUrl(backofficeUrlService.generateUrl(BoUrls.HOME, requestData));
        commonViewBean.setLoginUrl(backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData));
        commonViewBean.setForgottenPasswordUrl(backofficeUrlService.generateUrl(BoUrls.FORGOTTEN_PASSWORD, requestData));
        commonViewBean.setLogoutUrl(backofficeUrlService.generateUrl(BoUrls.LOGOUT, requestData));
        commonViewBean.setPersonalDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestData));

        commonViewBean.setContextJsonUrl(backofficeUrlService.generateUrl(BoUrls.CONTEXT, requestData));

        return commonViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    public List<MenuViewBean> buildMorePageMenuViewBeans(final RequestData requestData) throws Exception {
        final User currentUser = requestData.getUser();
        final Locale locale = requestData.getLocale();
        
        final List<MenuViewBean> menuViewBeans = new ArrayList<MenuViewBean>();

        // TODO : denis : move this part in the global list menu java/vm

        MenuViewBean menu = new MenuViewBean();
        menu.setCssIcon("fa fa-paper-clip");
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, "faq", locale));
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.FAQ, requestData));
        menuViewBeans.add(menu);

        menu = new MenuViewBean();
        menu.setCssIcon("fa fa-user");
        menu.setName(getSpecificMessage(ScopeWebMessage.COMMON, "header_link_my_account", locale));
        menu.setUrl(backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestData, currentUser));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setCssIcon("fa fa-lock");
        if(currentUser != null){
            menu.setName(getSpecificMessage(ScopeWebMessage.AUTH, "header_title_logout", locale));
            menu.setUrl(backofficeUrlService.generateUrl(BoUrls.LOGOUT, requestData));
        } else {
            menu.setName(getSpecificMessage(ScopeWebMessage.AUTH, "header_title_login", locale));
            menu.setUrl(backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData));
        }
        menuViewBeans.add(menu);
        
        return menuViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<MarketViewBean> buildMarketViewBeansByMarketPlace(final RequestData requestData, final MarketPlace marketPlace, final List<Market> markets) throws Exception {
        List<MarketViewBean> marketViewBeans = new ArrayList<MarketViewBean>();
        for (Iterator<Market> iteratorMarket = markets.iterator(); iteratorMarket.hasNext();) {
            final Market marketNavigation = (Market) iteratorMarket.next();
            marketViewBeans.add(buildMarketViewBean(requestData, marketNavigation));
        }
        return marketViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<MarketAreaViewBean> buildMarketAreaViewBeansByMarket(final RequestData requestData, final Market market, final List<MarketArea> marketAreas) throws Exception {
        List<MarketAreaViewBean> marketAreaViewBeans = new ArrayList<MarketAreaViewBean>();
        for (Iterator<MarketArea> iteratorMarketArea = marketAreas.iterator(); iteratorMarketArea.hasNext();) {
            final MarketArea marketArea = (MarketArea) iteratorMarketArea.next();
            marketAreaViewBeans.add(buildMarketAreaViewBean(requestData, marketArea));
        }
        return marketAreaViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public LocalizationViewBean buildLocalizationViewBeanByMarketArea(final RequestData requestData, final Localization localization) throws Exception {
        final Locale locale = localization.getLocale();
        final String localeCodeNavigation = localization.getCode();

        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaLocalization(localization);

        final LocalizationViewBean localizationViewBean = new LocalizationViewBean();

        if (StringUtils.isNotEmpty(localeCodeNavigation) && localeCodeNavigation.length() == 2) {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation.toLowerCase(), locale));
        } else {
            localizationViewBean.setName(getReferenceData(ScopeReferenceDataMessage.LANGUAGE, localeCodeNavigation, locale));
        }

        localizationViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        return localizationViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<LocalizationViewBean> buildLocalizationViewBeansByMarketArea(final RequestData requestData, final List<Localization> localizations) throws Exception {
        final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        if (localizations != null) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                localizationViewBeans.add(buildLocalizationViewBeanByMarketArea(requestData, localization));
            }
        }
        return localizationViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<LocalizationViewBean> buildLocalizationViewBeans(final RequestData requestData, final List<Localization> localizations) throws Exception {
        final List<LocalizationViewBean> localizationViewBeans = new ArrayList<LocalizationViewBean>();
        if (localizations != null) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                localizationViewBeans.add(buildLocalizationViewBean(requestData, localization));
            }
        }
        return localizationViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<RetailerViewBean> buildRetailerViewBeans(final RequestData requestData) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final List<Retailer> retailers = new ArrayList<Retailer>(currentMarketArea.getRetailers());
        List<RetailerViewBean> retailerViewBeans = new ArrayList<RetailerViewBean>();
        for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
            final Retailer retailerIt = (Retailer) iterator.next();
            retailerViewBeans.add(buildRetailerViewBean(requestData, retailerIt));
        }
        return retailerViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public RetailerViewBean buildRetailerViewBean(final RequestData requestData, final Retailer retailer) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = localization.getLocale();

        final RetailerViewBean retailerViewBean = new RetailerViewBean();

        // CLONE THE CURRENT REQUEST DATE TO BUILD THE CHANGE CONTEXT URL (MENU)
        RequestData requestDataChangecontext = new RequestData();
        BeanUtils.copyProperties(requestData, requestDataChangecontext);
        requestDataChangecontext.setMarketAreaRetailer(retailer);
        retailerViewBean.setChangeContextUrl(backofficeUrlService.buildChangeContextUrl(requestDataChangecontext));

        retailerViewBean.setId(retailer.getId());
        retailerViewBean.setVersion(retailer.getVersion());
        retailerViewBean.setCode(retailer.getCode());
        retailerViewBean.setName(retailer.getName());
        retailerViewBean.setDescription(retailer.getDescription());

        if (retailer.getAddresses() != null) {
            RetailerAddress defaultAddress = retailer.getDefaultAddress();
            if (defaultAddress != null) {
                retailerViewBean.getDefaultAddress().setAddress1(defaultAddress.getAddress1());
                retailerViewBean.getDefaultAddress().setAddress2(defaultAddress.getAddress2());
                retailerViewBean.getDefaultAddress().setAddressAdditionalInformation(defaultAddress.getAddressAdditionalInformation());
                retailerViewBean.getDefaultAddress().setPostalCode(defaultAddress.getPostalCode());
                retailerViewBean.getDefaultAddress().setCity(defaultAddress.getCity());
                retailerViewBean.getDefaultAddress().setStateCode(defaultAddress.getStateCode());
                retailerViewBean.getDefaultAddress().setStateLabel(defaultAddress.getStateCode());
                retailerViewBean.getDefaultAddress().setAreaCode(defaultAddress.getAreaCode());
                retailerViewBean.getDefaultAddress().setAreaLabel(defaultAddress.getAreaCode());
                retailerViewBean.getDefaultAddress().setCountryCode(defaultAddress.getCountryCode());

                String countryLabel = referentialDataService.getCountryByLocale(defaultAddress.getCountryCode(), locale);
                retailerViewBean.getDefaultAddress().setCountryLabel(countryLabel);

                retailerViewBean.getDefaultAddress().setLongitude(defaultAddress.getLongitude());
                retailerViewBean.getDefaultAddress().setLatitude(defaultAddress.getLatitude());

                retailerViewBean.getDefaultAddress().setPhone(defaultAddress.getPhone());
                retailerViewBean.getDefaultAddress().setMobile(defaultAddress.getMobile());
                retailerViewBean.getDefaultAddress().setFax(defaultAddress.getFax());
                retailerViewBean.getDefaultAddress().setEmail(defaultAddress.getEmail());
                String websiteUrl = defaultAddress.getWebsite();
                if (StringUtils.isNotEmpty(websiteUrl) && !websiteUrl.contains("http")) {
                    websiteUrl = "http://" + websiteUrl;
                }
                retailerViewBean.getDefaultAddress().setWebsite(websiteUrl);
            }
        }

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = retailer.getDateCreate();
        if (createdDate != null) {
            retailerViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            retailerViewBean.setCreatedDate(Constants.NOT_AVAILABLE);
        }
        Date updatedDate = retailer.getDateUpdate();
        if (updatedDate != null) {
            retailerViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            retailerViewBean.setUpdatedDate(Constants.NOT_AVAILABLE);
        }

        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE, retailer.getCode());
        String detailsUrl = backofficeUrlService.generateUrl(BoUrls.RETAILER_DETAILS, requestData, urlParams);
        String editUrl = backofficeUrlService.generateUrl(BoUrls.RETAILER_EDIT, requestData, urlParams);

        retailerViewBean.setDetailsUrl(detailsUrl);
        retailerViewBean.setEditUrl(editUrl);

        return retailerViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogViewBean buildMasterCatalogViewBean(final RequestData requestData, final CatalogMaster catalogMaster, final List<CatalogCategoryMaster> catalogCategories) throws Exception {
        final CatalogViewBean catalogViewBean = new CatalogViewBean();
        catalogViewBean.setBusinessName(catalogMaster.getBusinessName());
        catalogViewBean.setCode(catalogMaster.getCode());

        if (catalogCategories != null) {
            catalogViewBean.setCategories(buildMasterCatalogCategoryViewBeans(requestData, catalogCategories, true));
        }

        catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData));

        return catalogViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogViewBean buildVirtualCatalogViewBean(final RequestData requestData, final CatalogVirtual catalogVirtual, final List<CatalogCategoryVirtual> catalogCategories) throws Exception {
        final CatalogViewBean catalogViewBean = new CatalogViewBean();
        catalogViewBean.setBusinessName(catalogVirtual.getBusinessName());
        catalogViewBean.setCode(catalogVirtual.getCode());

        if (catalogCategories != null) {
            catalogViewBean.setCategories(buildVirtualCatalogCategoryViewBeans(requestData, catalogCategories, true));
        }

        catalogViewBean.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_ADD, requestData));

        return catalogViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<CatalogCategoryViewBean> buildMasterCatalogCategoryViewBeans(final RequestData requestData, final List<CatalogCategoryMaster> catalogCategories, boolean fullPopulate) throws Exception {
        List<CatalogCategoryViewBean> categoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryMaster> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            final CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) iterator.next();

            // TODO : Denis : fetch optim - cache : we reload entity to fetch the defaultParentCatalogCategory
            CatalogCategoryMaster reloadedCategory = catalogCategoryService.getMasterCatalogCategoryById(catalogCategory.getId());

            categoryViewBeans.add(buildMasterCatalogCategoryViewBean(requestData, reloadedCategory, fullPopulate));
        }
        return categoryViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public List<CatalogCategoryViewBean> buildVirtualCatalogCategoryViewBeans(final RequestData requestData, final List<CatalogCategoryVirtual> catalogCategories, boolean fullPopulate)
            throws Exception {
        List<CatalogCategoryViewBean> categoryViewBeans = new ArrayList<CatalogCategoryViewBean>();
        for (Iterator<CatalogCategoryVirtual> iterator = catalogCategories.iterator(); iterator.hasNext();) {
            final CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) iterator.next();
            
         // TODO : Denis : fetch optim - cache : we reload entity to fetch the defaultParentCatalogCategory
            CatalogCategoryVirtual reloadedCategory = catalogCategoryService.getVirtualCatalogCategoryById(catalogCategory.getId());

            categoryViewBeans.add(buildVirtualCatalogCategoryViewBean(requestData, reloadedCategory, fullPopulate));
        }
        return categoryViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogCategoryViewBean buildMasterCatalogCategoryViewBean(final RequestData requestData, final CatalogCategoryMaster catalogCategory, boolean fullPopulate) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();

        if (catalogCategory != null) {
            final String categoryCode = catalogCategory.getCode();

            catalogCategoryViewBean.setName(catalogCategory.getBusinessName());
            catalogCategoryViewBean.setCode(categoryCode);
            catalogCategoryViewBean.setDescription(catalogCategory.getDescription());

            if (catalogCategory.getDefaultParentCatalogCategory() != null) {
             // TODO : Denis : fetch optim - cache : we reload entity to fetch the defaultParentCatalogCategory
                CatalogCategoryMaster defaultParentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(catalogCategory.getDefaultParentCatalogCategory().getId());
                catalogCategoryViewBean.setDefaultParentCategory(buildMasterCatalogCategoryViewBean(requestData, defaultParentCatalogCategory, false));
            }

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date createdDate = catalogCategory.getDateCreate();
            if (createdDate != null) {
                catalogCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
            } else {
                catalogCategoryViewBean.setCreatedDate(Constants.NOT_AVAILABLE);
            }
            Date updatedDate = catalogCategory.getDateUpdate();
            if (updatedDate != null) {
                catalogCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
            } else {
                catalogCategoryViewBean.setUpdatedDate(Constants.NOT_AVAILABLE);
            }

            if (fullPopulate) {
                if (catalogCategory.getCatalogCategories() != null) {
                    catalogCategoryViewBean.setSubCategories(buildMasterCatalogCategoryViewBeans(requestData, new ArrayList<CatalogCategoryMaster>(catalogCategory.getCatalogCategories()), fullPopulate));
                }

                List<CatalogCategoryMasterAttribute> globalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes();
                for (Iterator<CatalogCategoryMasterAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    catalogCategoryViewBean.getGlobalAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
                }

                List<CatalogCategoryMasterAttribute> marketAreaAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId());
                for (Iterator<CatalogCategoryMasterAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iterator.next();
                    catalogCategoryViewBean.getMarketAreaAttributes().put(catalogCategoryMasterAttribute.getAttributeDefinition().getCode(), catalogCategoryMasterAttribute.getValueAsString());
                }

                List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(requestData, catalogCategory, new ArrayList<ProductMarketing>(catalogCategory.getProductMarketings()), true);
                catalogCategoryViewBean.setProductMarketings(productMarketingViewBeans);

                int countProduct = catalogCategory.getProductMarketings().size();
                for (Iterator<CatalogCategoryViewBean> iterator = catalogCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryViewBean subCategoryViewBean = (CatalogCategoryViewBean) iterator.next();
                    countProduct = countProduct + subCategoryViewBean.getCountProduct();
                }
                catalogCategoryViewBean.setCountProduct(countProduct);

                List<Asset> assets = catalogCategory.getAssetsIsGlobal();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    catalogCategoryViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
                }
            }

            catalogCategoryViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, catalogCategory));
            catalogCategoryViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_EDIT, requestData, catalogCategory));
        }

        return catalogCategoryViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public CatalogCategoryViewBean buildVirtualCatalogCategoryViewBean(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, boolean fullPopulate) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final CatalogCategoryViewBean catalogCategoryViewBean = new CatalogCategoryViewBean();

        if (catalogCategory != null) {
            final String categoryCode = catalogCategory.getCode();

            catalogCategoryViewBean.setName(catalogCategory.getBusinessName());
            catalogCategoryViewBean.setCode(categoryCode);
            catalogCategoryViewBean.setDescription(catalogCategory.getDescription());

            if (catalogCategory.getDefaultParentCatalogCategory() != null) {
                // TODO : Denis : fetch optim - cache : we reload entity to fetch the defaultParentCatalogCategory
                CatalogCategoryVirtual defaultParentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(catalogCategory.getDefaultParentCatalogCategory().getId());
                catalogCategoryViewBean.setDefaultParentCategory(buildVirtualCatalogCategoryViewBean(requestData, defaultParentCatalogCategory, false));
            }

            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date createdDate = catalogCategory.getDateCreate();
            if (createdDate != null) {
                catalogCategoryViewBean.setCreatedDate(dateFormat.format(createdDate));
            } else {
                catalogCategoryViewBean.setCreatedDate(Constants.NOT_AVAILABLE);
            }
            Date updatedDate = catalogCategory.getDateUpdate();
            if (updatedDate != null) {
                catalogCategoryViewBean.setUpdatedDate(dateFormat.format(updatedDate));
            } else {
                catalogCategoryViewBean.setUpdatedDate(Constants.NOT_AVAILABLE);
            }

            if (fullPopulate) {
                if (catalogCategory.getCatalogCategories() != null) {
                    catalogCategoryViewBean.setSubCategories(buildVirtualCatalogCategoryViewBeans(requestData, new ArrayList<CatalogCategoryVirtual>(catalogCategory.getCatalogCategories()), fullPopulate));
                }

                List<CatalogCategoryVirtualAttribute> globalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes();
                for (Iterator<CatalogCategoryVirtualAttribute> iterator = globalAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                    catalogCategoryViewBean.getGlobalAttributes().put(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode(), catalogCategoryVirtualAttribute.getValueAsString());
                }

                List<CatalogCategoryVirtualAttribute> marketAreaAttributes = catalogCategory.getCatalogCategoryMarketAreaAttributes(currentMarketArea.getId());
                for (Iterator<CatalogCategoryVirtualAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iterator.next();
                    catalogCategoryViewBean.getMarketAreaAttributes().put(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode(), catalogCategoryVirtualAttribute.getValueAsString());
                }

                List<ProductMarketingViewBean> productMarketingViewBeans = buildProductMarketingViewBeans(requestData, catalogCategory, new ArrayList<ProductMarketing>(catalogCategory.getProductMarketings()), true);
                catalogCategoryViewBean.setProductMarketings(productMarketingViewBeans);

                int countProduct = catalogCategory.getProductMarketings().size();
                for (Iterator<CatalogCategoryViewBean> iterator = catalogCategoryViewBean.getSubCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryViewBean subCategoryViewBean = (CatalogCategoryViewBean) iterator.next();
                    countProduct = countProduct + subCategoryViewBean.getCountProduct();
                }
                catalogCategoryViewBean.setCountProduct(countProduct);

                List<Asset> assets = catalogCategory.getAssetsIsGlobal();
                for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                    Asset asset = (Asset) iterator.next();
                    catalogCategoryViewBean.getAssets().add(buildAssetViewBean(requestData, asset));
                }
            }

            catalogCategoryViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, catalogCategory));
            catalogCategoryViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_EDIT, requestData, catalogCategory));
        }

        return catalogCategoryViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<ProductMarketingViewBean> buildProductMarketingViewBeans(final RequestData requestData, final CatalogCategoryMaster catalogCategory, final List<ProductMarketing> productMarketings, boolean withDependency) throws Exception {
        List<ProductMarketingViewBean> products = new ArrayList<ProductMarketingViewBean>();
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            
            // TODO : Denis : fetch optim - cache
            ProductMarketing reloadedProductMarketing = (ProductMarketing) productService.getProductMarketingById(productMarketing.getId());
            
            products.add(buildProductMarketingViewBean(requestData, catalogCategory, reloadedProductMarketing));
//            products.add(buildProductMarketingViewBean(requestData, reloadedProductMarketing, withDependency));
        }
        return products;
    }

    /**
     * @throws Exception
     * 
     */
    public List<ProductMarketingViewBean> buildProductMarketingViewBeans(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final List<ProductMarketing> productMarketings, boolean withDependency) throws Exception {
        List<ProductMarketingViewBean> products = new ArrayList<ProductMarketingViewBean>();
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            
            // TODO : Denis : fetch optim - cache
            ProductMarketing reloadedProductMarketing = (ProductMarketing) productService.getProductMarketingById(productMarketing.getId());
            
            products.add(buildProductMarketingViewBean(requestData, catalogCategory, reloadedProductMarketing));
//            products.add(buildProductMarketingViewBean(requestData, reloadedProductMarketing, withDependency));
        }
        return products;
    }
    
    public ProductMarketingViewBean buildProductMarketingViewBean(final RequestData requestData, final ProductMarketing productMarketing) throws Exception {
        final CatalogCategoryMaster catalogCategory = productMarketing.getDefaultCatalogCategory().getCategoryMaster();
        final ProductMarketingViewBean productMarketingViewBean = super.buildProductMarketingViewBean(requestData, catalogCategory, productMarketing);
        return productMarketingViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    @Override
    public ProductMarketingViewBean buildProductMarketingViewBean(final RequestData requestData, final CatalogCategoryMaster catalogCategory, final ProductMarketing productMarketing) throws Exception {
        final ProductMarketingViewBean productMarketingViewBean = super.buildProductMarketingViewBean(requestData, catalogCategory, productMarketing);

        productMarketingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));
        productMarketingViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_EDIT, requestData, productMarketing));

        return productMarketingViewBean;
    }
    
    /**
     * @throws Exception
     * 
     */
    @Override
    public ProductMarketingViewBean buildProductMarketingViewBean(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, final ProductMarketing productMarketing) throws Exception {
        final ProductMarketingViewBean productMarketingViewBean = super.buildProductMarketingViewBean(requestData, catalogCategory, productMarketing);

        productMarketingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));
        productMarketingViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_EDIT, requestData, productMarketing));

        return productMarketingViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public ProductAssociationLinkViewBean buildProductAssociationLinkViewBean(final RequestData requestData, final ProductAssociationLink productAssociationLink) throws Exception {
        final ProductAssociationLinkViewBean productAssociationLinkViewBean = new ProductAssociationLinkViewBean();

        productAssociationLinkViewBean.setOrderItem(productAssociationLink.getRankPosition());
        productAssociationLinkViewBean.setName(productAssociationLink.getProductSku().getProductMarketing().getBusinessName());
        productAssociationLinkViewBean.setType(productAssociationLink.getType().name());
        productAssociationLinkViewBean.setDescription(productAssociationLink.getType().name());
        productAssociationLinkViewBean.setProductDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productAssociationLink.getProductSku().getProductMarketing()));

        return productAssociationLinkViewBean;
    }

    @Override
    public ProductSkuViewBean buildProductSkuViewBean(final RequestData requestData, final ProductSku productSku) throws Exception {
        final ProductMarketing productMarketing = productSku.getProductMarketing();
        final CatalogCategoryMaster catalogCategory = productMarketing.getDefaultCatalogCategory().getCategoryMaster();
        final ProductSkuViewBean productSkuViewBean = super.buildProductSkuViewBean(requestData, catalogCategory, productMarketing, productSku);
        return productSkuViewBean;
    }

    /**
     * @throws Exception
     * 
     */
     @Override
     public ProductSkuViewBean buildProductSkuViewBean(final RequestData requestData, final CatalogCategoryVirtual catalogCategory, 
                                                      final ProductMarketing productMarketing, final ProductSku productSku) throws Exception {
        final ProductSkuViewBean productSkuViewBean = super.buildProductSkuViewBean(requestData, catalogCategory, productMarketing, productSku);

        productSkuViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));
        productSkuViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_EDIT, requestData, productSku));

        return productSkuViewBean;
    }

    /**
     * 
     */
    public BrandViewBean buildBrandViewBean(final RequestData requestData, final ProductBrand productBrand) throws Exception {
        final BrandViewBean brandViewBean = new BrandViewBean();

        brandViewBean.setBusinessName(productBrand.getName());
        brandViewBean.setCode(productBrand.getCode());
        // brandViewBean.setBrandDetailsUrl(brandDetailsUrl);
        // brandViewBean.setBrandLineDetailsUrl(brandLineDetailsUrl);

        return brandViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public AssetViewBean buildAssetViewBean(final RequestData requestData, final Asset asset) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final String assetCode = asset.getCode();

        AssetViewBean assetViewBean = new AssetViewBean();

        assetViewBean.setName(asset.getName());
        assetViewBean.setCode(assetCode);
        assetViewBean.setDescription(asset.getDescription());
        assetViewBean.setPath(asset.getPath());
        if (asset.getScope() != null) {
            assetViewBean.setScope(asset.getScope().getPropertyKey());
        }
        if (asset.getType() != null) {
            assetViewBean.setType(asset.getType().getPropertyKey());
        }
        if (asset.getSize() != null) {
            assetViewBean.setSize(asset.getSize().getPropertyKey());
        }
        assetViewBean.setFileSize("" + asset.getFileSize());
        assetViewBean.setIsDefault("" + asset.isDefault());

        assetViewBean.setAbsoluteWebPath(requestUtil.getProductMarketingImageWebPath(request, asset));

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date createdDate = asset.getDateCreate();
        if (createdDate != null) {
            assetViewBean.setCreatedDate(dateFormat.format(createdDate));
        } else {
            assetViewBean.setCreatedDate(Constants.NOT_AVAILABLE);
        }
        Date updatedDate = asset.getDateUpdate();
        if (updatedDate != null) {
            assetViewBean.setUpdatedDate(dateFormat.format(updatedDate));
        } else {
            assetViewBean.setUpdatedDate(Constants.NOT_AVAILABLE);
        }

        assetViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ASSET_DETAILS, requestData, asset));
        assetViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.ASSET_EDIT, requestData, asset));

        return assetViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public LegalTermsViewBean buildLegalTermsViewBean(final RequestData requestData) throws Exception {
        final Localization localization = requestData.getMarketAreaLocalization();
        final Locale locale = localization.getLocale();

        final LegalTermsViewBean legalTerms = new LegalTermsViewBean();

        legalTerms.setWarning(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "warning", locale));
        legalTerms.setCopyright(getCommonMessage(ScopeCommonMessage.LEGAL_TERMS, "copyright", locale));

        return legalTerms;
    }

    /**
     * @throws Exception
     * 
     */
    public SecurityViewBean buildSecurityViewBean(final RequestData requestData) throws Exception {
        final SecurityViewBean security = new SecurityViewBean();
        security.setLoginUrl(backofficeUrlService.generateUrl(BoUrls.LOGIN, requestData));
        security.setSubmitLoginUrl(backofficeUrlService.buildSpringSecurityCheckUrl(requestData));
        security.setForgottenPasswordUrl(backofficeUrlService.generateUrl(BoUrls.FORGOTTEN_PASSWORD, requestData));
        return security;
    }

    /**
     * @throws Exception
     * 
     */
    public UserViewBean buildUserViewBean(final RequestData requestData, final User user) throws Exception {
        final HttpServletRequest request = requestData.getRequest();
        final UserViewBean userViewBean = new UserViewBean();
        userViewBean.setId(user.getId());
        userViewBean.setLogin(user.getLogin());
        userViewBean.setFirstname(user.getFirstname());
        userViewBean.setLastname(user.getLastname());
        userViewBean.setEmail(user.getEmail());
        userViewBean.setPassword(user.getPassword());
        userViewBean.setActive(user.isActive());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (user.getDateCreate() != null) {
            userViewBean.setDateCreate(dateFormat.format(user.getDateCreate()));
        } else {
            userViewBean.setDateCreate(Constants.NOT_AVAILABLE);
        }
        if (user.getDateUpdate() != null) {
            userViewBean.setDateUpdate(dateFormat.format(user.getDateUpdate()));
        } else {
            userViewBean.setDateUpdate(Constants.NOT_AVAILABLE);
        }

        final Set<UserGroup> userGroups = user.getUserGroups();
        for (Iterator<UserGroup> iteratorUserGroup = userGroups.iterator(); iteratorUserGroup.hasNext();) {
            UserGroup userGroup = (UserGroup) iteratorUserGroup.next();
            String keyUserGroup = userGroup.getCode();
            String valueUserGroup = userGroup.getName();
            userViewBean.getUserGroups().put(keyUserGroup, valueUserGroup);

            final Set<UserRole> userRoles = userGroup.getGroupRoles();
            for (Iterator<UserRole> iteratorUserRole = userRoles.iterator(); iteratorUserRole.hasNext();) {
                UserRole userRole = (UserRole) iteratorUserRole.next();
                String keyUserRole = userRole.getCode() + " (" + userGroup.getCode() + ")";
                String valueUserRole = userRole.getName();
                userViewBean.getUserRoles().put(keyUserRole, valueUserRole);

                final Set<UserPermission> rolePermissions = userRole.getRolePermissions();
                for (Iterator<UserPermission> iteratorUserPermission = rolePermissions.iterator(); iteratorUserPermission.hasNext();) {
                    UserPermission userPermission = (UserPermission) iteratorUserPermission.next();
                    String keyUserPermission = userPermission.getCode() + " (" + userRole.getCode() + ")";
                    String valueUserPermission = userPermission.getName();
                    userViewBean.getUserPermissions().put(keyUserPermission, valueUserPermission);
                }
            }
        }

        final Set<UserConnectionLog> connectionLogs = user.getConnectionLogs();
        for (Iterator<UserConnectionLog> iteratorUserConnectionLog = connectionLogs.iterator(); iteratorUserConnectionLog.hasNext();) {
            UserConnectionLog userConnectionLog = (UserConnectionLog) iteratorUserConnectionLog.next();
            UserConnectionLogValueBean userConnectionLogValueBean = new UserConnectionLogValueBean();
            userConnectionLogValueBean.setDate(dateFormat.format(userConnectionLog.getLoginDate()));
            userConnectionLogValueBean.setHost(Constants.NOT_AVAILABLE);
            if (StringUtils.isNotEmpty(userConnectionLog.getHost())) {
                userConnectionLogValueBean.setHost(userConnectionLog.getHost());
            }
            userConnectionLogValueBean.setPublicAddress(Constants.NOT_AVAILABLE);
            if (StringUtils.isNotEmpty(userConnectionLog.getPublicAddress())) {
                userConnectionLogValueBean.setPublicAddress(userConnectionLog.getPublicAddress());
            }
            userConnectionLogValueBean.setPrivateAddress(Constants.NOT_AVAILABLE);
            if (StringUtils.isNotEmpty(userConnectionLog.getPrivateAddress())) {
                userConnectionLogValueBean.setPublicAddress(userConnectionLog.getPrivateAddress());
            }
            userViewBean.getUserConnectionLogs().add(userConnectionLogValueBean);
        }

        final List<String> excludedPatterns = new ArrayList<String>();
        excludedPatterns.add("form");
        userViewBean.setBackUrl(requestUtil.getLastRequestUrl(request, excludedPatterns));

        userViewBean.setUserDetailsUrl(backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestData, user));
        userViewBean.setUserEditUrl(backofficeUrlService.generateUrl(BoUrls.USER_EDIT, requestData, user));

        userViewBean.setPersonalDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestData, user));
        userViewBean.setPersonalEditUrl(backofficeUrlService.generateUrl(BoUrls.PERSONAL_EDIT, requestData, user));

        return userViewBean;
    }

    /**
     * 
     */
    public List<UserViewBean> buildUserViewBeans(final RequestData requestData, final List<User> users) throws Exception {
        final List<UserViewBean> userViewBeans = new ArrayList<UserViewBean>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            userViewBeans.add(buildUserViewBean(requestData, user));
        }
        return userViewBeans;
    }

    /**
     * @throws Exception
     * 
     */
    @Override
    public DeliveryMethodViewBean buildDeliveryMethodViewBean(final RequestData requestData, final DeliveryMethod deliveryMethod) throws Exception {
        final DeliveryMethodViewBean deliveryMethodViewBean = super.buildDeliveryMethodViewBean(requestData, deliveryMethod);

        deliveryMethodViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_DETAILS, requestData, deliveryMethod));
        deliveryMethodViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.DELIVERY_METHOD_EDIT, requestData, deliveryMethod));

        return deliveryMethodViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    @Override
    public OrderViewBean buildOrderViewBean(final RequestData requestData, final OrderCustomer order) throws Exception {
        OrderViewBean orderViewBean = super.buildOrderViewBean(requestData, order);

        orderViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ORDER_DETAILS, requestData, order));
        orderViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ORDER_DETAILS, requestData, order));

        return orderViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public RuleViewBean buildRuleViewBean(final RequestData requestData, final AbstractRuleReferential rule) throws Exception {
        RuleViewBean ruleViewBean = new RuleViewBean();
        ruleViewBean.setId(rule.getId());

        ruleViewBean.setVersion(rule.getVersion());
        ruleViewBean.setName(rule.getName());
        ruleViewBean.setDescription(rule.getDescription());
        ruleViewBean.setSalience(rule.getSalience());

        DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
        if (rule.getDateCreate() != null) {
            ruleViewBean.setDateCreate(dateFormat.format(rule.getDateCreate()));
        } else {
            ruleViewBean.setDateCreate(Constants.NOT_AVAILABLE);
        }
        if (rule.getDateUpdate() != null) {
            ruleViewBean.setDateUpdate(dateFormat.format(rule.getDateUpdate()));
        } else {
            ruleViewBean.setDateUpdate(Constants.NOT_AVAILABLE);
        }

        ruleViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.RULE_DETAILS, requestData, rule));
        ruleViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.RULE_EDIT, requestData, rule));

        return ruleViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    @Override
    public CustomerViewBean buildCustomerViewBean(final RequestData requestData, final Customer customer) throws Exception {
        CustomerViewBean customerViewBean = super.buildCustomerViewBean(requestData, customer);

        customerViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.CUSTOMER_DETAILS, requestData, customer));
        customerViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.CUSTOMER_EDIT, requestData, customer));

        return customerViewBean;
    }

    /**
     * @throws Exception
     * 
     */
    public List<GlobalSearchViewBean> buildGlobalSearchViewBean(final RequestData requestData, final String searchText) throws Exception {
        final MarketArea currentMarketArea = requestData.getMarketArea();

        final List<GlobalSearchViewBean> globalSearchViewBeans = new ArrayList<GlobalSearchViewBean>();

        final List<ProductMarketing> productMarketings = productService.findProductMarketings(currentMarketArea.getId(), searchText);
        for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();

            final GlobalSearchViewBean globalSearchViewBean = new GlobalSearchViewBean();
            globalSearchViewBean.setValue(productMarketing.getBusinessName() + " : " + productMarketing.getCode());
            globalSearchViewBean.setType("ProductMarketing");
            globalSearchViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, productMarketing));

            globalSearchViewBeans.add(globalSearchViewBean);
        }

        final List<ProductSku> productSkus = productService.findProductSkus(currentMarketArea.getId(), searchText);
        for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
            ProductSku productSku = (ProductSku) iterator.next();

            final GlobalSearchViewBean globalSearchViewBean = new GlobalSearchViewBean();
            globalSearchViewBean.setValue(productSku.getBusinessName() + " : " + productSku.getCode());
            globalSearchViewBean.setType("ProductSku");
            globalSearchViewBean.setUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, productSku));

            globalSearchViewBeans.add(globalSearchViewBean);
        }

        return globalSearchViewBeans;
    }

    /**
     * 
     */
    public List<EngineSettingViewBean> buildEngineSettingViewBeans(final RequestData requestData, final List<EngineSetting> engineSettings) throws Exception {
        final List<EngineSettingViewBean> engineSettingViewBeans = new ArrayList<EngineSettingViewBean>();
        for (Iterator<EngineSetting> iterator = engineSettings.iterator(); iterator.hasNext();) {
            EngineSetting engineSetting = (EngineSetting) iterator.next();
            engineSettingViewBeans.add(buildEngineSettingViewBean(requestData, engineSetting));
        }
        return engineSettingViewBeans;
    }

    /**
     * 
     */
    public EngineSettingViewBean buildEngineSettingViewBean(final RequestData requestData, final EngineSetting engineSetting) throws Exception {
        final EngineSettingViewBean engineSettingViewBean = new EngineSettingViewBean();
        engineSettingViewBean.setName(engineSetting.getName());
        engineSettingViewBean.setCode(engineSetting.getCode());
        engineSettingViewBean.setDescription(engineSetting.getDescription());
        if(StringUtils.isNotEmpty(engineSetting.getDefaultValue())){
            engineSettingViewBean.setDefaultValue(engineSetting.getDefaultValue());
        } else {
            engineSettingViewBean.setDefaultValue(Constants.NOT_AVAILABLE);
        }
        
        engineSettingViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_DETAILS, requestData, engineSetting));

        Set<EngineSettingValue> engineSettingValues = engineSetting.getEngineSettingValues();
        if (engineSettingValues != null) {
            for (Iterator<EngineSettingValue> iterator = engineSettingValues.iterator(); iterator.hasNext();) {
                EngineSettingValue engineSettingValue = (EngineSettingValue) iterator.next();
                engineSettingViewBean.getEngineSettingValues().add(buildEngineSettingValueViewBean(requestData, engineSettingValue));
            }
        }
        return engineSettingViewBean;
    }

    /**
     * 
     */
    public EngineSettingValueViewBean buildEngineSettingValueViewBean(final RequestData requestData, final EngineSettingValue engineSettingValue) throws Exception {
        final EngineSettingValueViewBean engineSettingValueViewBean = new EngineSettingValueViewBean();
        engineSettingValueViewBean.setContext(engineSettingValue.getContext());
        engineSettingValueViewBean.setValue(engineSettingValue.getValue());
        engineSettingValueViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.ENGINE_SETTING_VALUE_EDIT, requestData, engineSettingValue));
        return engineSettingValueViewBean;
    }

    /**
     * 
     */
    public List<BatchViewBean> buildBatchViewBeans(final RequestData requestData, final List<BatchProcessObject> batchProcessObjects) throws Exception {
        final List<BatchViewBean> batchViewBeans = new ArrayList<BatchViewBean>();
        for (Iterator<BatchProcessObject> iterator = batchProcessObjects.iterator(); iterator.hasNext();) {
            BatchProcessObject batchProcessObject = (BatchProcessObject) iterator.next();
            batchViewBeans.add(buildBatchViewBean(requestData, batchProcessObject));
        }
        return batchViewBeans;
    }

    /**
     * 
     */
    public BatchViewBean buildBatchViewBean(final RequestData requestData, final BatchProcessObject batchProcessObject) throws Exception {
        final BatchViewBean batchViewBean = new BatchViewBean();
        batchViewBean.setId(batchProcessObject.getId());
        batchViewBean.setStatus(batchProcessObject.getStatus());
        batchViewBean.setTypeObject(batchProcessObject.getTypeObject().getPropertyKey());
        batchViewBean.setProcessedCount(batchProcessObject.getProcessedCount());
        return batchViewBean;
    }

    /**
     * 
     */
    public List<PaymentGatewayViewBean> buildPaymentGatewayViewBeans(final RequestData requestData, final List<AbstractPaymentGateway> paymentGateways) throws Exception {
        final List<PaymentGatewayViewBean> paymentGatewayViewBeans = new ArrayList<PaymentGatewayViewBean>();
        if (paymentGateways != null) {
            for (Iterator<AbstractPaymentGateway> iterator = paymentGateways.iterator(); iterator.hasNext();) {
                AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) iterator.next();
                paymentGatewayViewBeans.add(buildPaymentGatewayViewBean(requestData, paymentGateway));
            }
        }
        return paymentGatewayViewBeans;
    }

    /**
     * 
     */
    public PaymentGatewayViewBean buildPaymentGatewayViewBean(final RequestData requestData, final AbstractPaymentGateway paymentGateway) throws Exception {
        final PaymentGatewayViewBean paymentGatewayViewBean = new PaymentGatewayViewBean();
        if (paymentGateway != null) {
            paymentGatewayViewBean.setName(paymentGateway.getName());
            paymentGatewayViewBean.setDescription(paymentGateway.getDescription());
            paymentGatewayViewBean.setCode(paymentGateway.getCode());

            Set<PaymentGatewayAttribute> paymentGatewayAttributes = paymentGateway.getPaymentGatewayAttributes();
            for (Iterator<PaymentGatewayAttribute> iterator = paymentGatewayAttributes.iterator(); iterator.hasNext();) {
                PaymentGatewayAttribute paymentGatewayAttribute = (PaymentGatewayAttribute) iterator.next();
                paymentGatewayViewBean.getPaymentGatewayAttributes().put(paymentGatewayAttribute.getAttributeDefinition().getCode(), paymentGatewayAttribute.getValueAsString());
            }
            
            DateFormat dateFormat = requestUtil.getFormatDate(requestData, DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date dateCreate = paymentGateway.getDateCreate();
            if (dateCreate != null) {
                paymentGatewayViewBean.setDateCreate(dateFormat.format(dateCreate));
            } else {
                paymentGatewayViewBean.setDateCreate(Constants.NOT_AVAILABLE);
            }

            Date dateUpdate = paymentGateway.getDateUpdate();
            if (dateUpdate != null) {
                paymentGatewayViewBean.setDateUpdate(dateFormat.format(dateUpdate));
            } else {
                paymentGatewayViewBean.setDateUpdate(Constants.NOT_AVAILABLE);
            }

            paymentGatewayViewBean.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_DETAILS, requestData, paymentGateway));
            paymentGatewayViewBean.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PAYMENT_GATEWAY_EDIT, requestData, paymentGateway));

        }
        return paymentGatewayViewBean;
    }

    protected String getSpecificMessage(ScopeWebMessage scope, String key, Locale locale) {
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, locale);
    }

    protected String getSpecificMessage(ScopeWebMessage scope, String key, Object[] params, Locale locale) {
        return coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.BO, scope, key, params, locale);
    }

}