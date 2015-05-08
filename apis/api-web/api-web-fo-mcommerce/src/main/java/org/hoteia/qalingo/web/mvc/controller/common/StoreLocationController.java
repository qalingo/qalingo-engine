/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.domain.bean.GeolocData;
import org.hoteia.qalingo.core.domain.bean.GeolocatedStore;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.viewbean.BreadcrumbViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.MenuViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorCountryFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreLocatorFilterBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
@Controller("storeLocationController")
public class StoreLocationController extends AbstractMCommerceController {

	@Autowired
    protected RetailerService retailerService;
	
    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();;

	public StoreLocationController() {
	    storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }
	
	@RequestMapping(FoUrls.STORE_LOCATION_URL)
	public ModelAndView storeLocation(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.STORE_LOCATION.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        final GeolocData geolocData = requestData.getGeolocData();
        
        ObjectMapper mapper = new ObjectMapper();
        List<StoreLocatorCountryFilterBean> countries = new ArrayList<StoreLocatorCountryFilterBean>();
        if(geolocData != null){
            String distance = getDistance();
            List<Store> stores = new ArrayList<Store>();
            final List<GeolocatedStore> geolocatedStores = retailerService.findStoresByGeolocAndCountry(marketArea.getGeolocCountryCode(), geolocData.getLatitude(), geolocData.getLongitude(), distance, 100);
            if(geolocatedStores != null){
                for (Iterator<GeolocatedStore> iterator = geolocatedStores.iterator(); iterator.hasNext();) {
                    GeolocatedStore geolocatedStore = (GeolocatedStore) iterator.next();
                    Store store = retailerService.getStoreById(geolocatedStore.getId(), new FetchPlan(storeFetchPlans));
                    stores.add(store);
                }
            } else {
                // TRY FIRST LONG DISTANCE
                Integer newDistance = Integer.parseInt(distance) * 2;
                geolocatedStores = retailerService.findStoresByGeolocAndCountry(marketArea.getGeolocCountryCode(), geolocData.getLatitude(), geolocData.getLongitude(), newDistance.toString(), 100);
                if(geolocatedStores != null){
                    for (Iterator<GeolocatedStore> iterator = geolocatedStores.iterator(); iterator.hasNext();) {
                        GeolocatedStore geolocatedStore = (GeolocatedStore) iterator.next();
                        Store store = retailerService.getStoreById(geolocatedStore.getId(), new FetchPlan(storeFetchPlans));
                        stores.add(store);
                    }
                } else {
                    // TRY SECOND LONG DISTANCE
                    newDistance = newDistance * 2;
                    geolocatedStores = retailerService.findStoresByGeolocAndCountry(marketArea.getGeolocCountryCode(), geolocData.getLatitude(), geolocData.getLongitude(), newDistance.toString(), 100);
                    if(geolocatedStores != null){
                        for (Iterator<GeolocatedStore> iterator = geolocatedStores.iterator(); iterator.hasNext();) {
                            GeolocatedStore geolocatedStore = (GeolocatedStore) iterator.next();
                            Store store = retailerService.getStoreById(geolocatedStore.getId(), new FetchPlan(storeFetchPlans));
                            stores.add(store);
                        }
                    } else {
                        // TODO : ERROR MESSAGE IN IHM
                    }
                }
            }
            if(stores != null){
                final List<StoreViewBean> storeViewBeans = frontofficeViewBeanFactory.buildListViewBeanStore(requestUtil.getRequestData(request), stores);
                modelAndView.addObject("stores", storeViewBeans);

                final StoreLocatorFilterBean storeFilter = frontofficeViewBeanFactory.buildFilterBeanStoreLocator(storeViewBeans, locale);
                countries = storeFilter.getCountries();
            }
            
            modelAndView.addObject("storeSearchUrl", urlService.generateUrl(FoUrls.STORE_SEARCH, requestData));
            
            overrideDefaultMainContentTitle(request, modelAndView, FoUrls.STORE_LOCATION.getKey());

            modelAndView.addObject(ModelConstants.BREADCRUMB_VIEW_BEAN, buildBreadcrumbViewBean(requestData));
        }

        try {
            modelAndView.addObject("jsonStores", mapper.writeValueAsString(countries));
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
        }
        
        return modelAndView;
	}
	
	protected String getDistance() {
        return "50";
    }

    protected BreadcrumbViewBean buildBreadcrumbViewBean(final RequestData requestData){
        final Locale locale = requestData.getLocale();
        
        // BREADCRUMB
        BreadcrumbViewBean breadcrumbViewBean = new BreadcrumbViewBean();
        breadcrumbViewBean.setName(getSpecificMessage(ScopeWebMessage.HEADER_TITLE, FoUrls.STORE_LOCATION.getMessageKey(), locale));
        
        List<MenuViewBean> menuViewBeans = breadcrumbViewBean.getMenus();
        MenuViewBean menu = new MenuViewBean();
        menu.setKey(FoUrls.HOME.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.HOME.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.HOME, requestData));
        menuViewBeans.add(menu);
        
        menu = new MenuViewBean();
        menu.setKey(FoUrls.STORE_LOCATION.getKey());
        menu.setName(getSpecificMessage(ScopeWebMessage.HEADER_MENU, FoUrls.STORE_LOCATION.getMessageKey(), locale));
        menu.setUrl(urlService.generateUrl(FoUrls.STORE_LOCATION, requestData));
        menu.setActive(true);
        menuViewBeans.add(menu);
        
        breadcrumbViewBean.setMenus(menuViewBeans);
        return breadcrumbViewBean;
	}
 
}