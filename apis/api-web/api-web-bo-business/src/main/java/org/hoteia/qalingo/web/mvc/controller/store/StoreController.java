package org.hoteia.qalingo.web.mvc.controller.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.retailer.FetchPlanGraphRetailer;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.web.mvc.form.StoreForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.StoreViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.ValueBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("storeController")
public class StoreController extends AbstractBusinessBackofficeController{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RetailerService retailerService;
	
	@RequestMapping(value = BoUrls.STORE_LIST_URL, method = RequestMethod.GET)
	public ModelAndView storeList(final HttpServletRequest request, final Model model, @RequestParam(value = RequestConstants.REQUEST_PARAMETER_RETAILER_CODE) String retailerCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.STORE_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
		
        // SANITY CHECK
        if (StringUtils.isEmpty(retailerCode)) {
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.RETAILER_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }

        final Retailer retailer = retailerService.getRetailerByCode(retailerCode);

        displayList(request, model, requestData, null);

        Object[] params = {retailer.getName() + " (" + retailer.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.STORE_LIST.getKey() + ".by.retailer", params);

        model.addAttribute(ModelConstants.URL_ADD, backofficeUrlService.generateUrl(BoUrls.STORE_ADD, requestData, retailer));
        model.addAttribute(ModelConstants.URL_RETAILER_VIEW, backofficeUrlService.generateUrl(BoUrls.RETAILER_DETAILS, requestData, retailer));

        return modelAndView;
	}
	
    @RequestMapping(value = BoUrls.STORE_LIST_URL, method = RequestMethod.POST)
    public ModelAndView searchRetailerList(final HttpServletRequest request, final Model model) throws Exception {
        //TODO: implement
        return null;
    }
    
	@RequestMapping(value = BoUrls.STORE_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView storeDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.STORE_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);

        final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_STORE_CODE);

        // SANITY CHECK
        if (StringUtils.isEmpty(storeCode)) {
            final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.STORE_LIST, requestData);
            return new ModelAndView(new RedirectView(urlRedirect));
        }

        final Store retailer = retailerService.getStoreByCode(storeCode);

        // SANITY CHECK
        if (retailer != null) {
            modelAndView.addObject(ModelConstants.STORE_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanStore(requestData, retailer));
        } else {
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }

        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.STORE_LIST, requestData));
        model.addAttribute(ModelConstants.URL_RETAILER_VIEW, backofficeUrlService.generateUrl(BoUrls.RETAILER_DETAILS, requestData, retailer));

        Object[] params = { retailer.getName() + " (" + retailer.getCode() + ")" };
        initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.STORE_DETAILS.getKey(), params);

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.STORE_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView storeEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.STORE_FORM) StoreForm storeForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.STORE_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_STORE_CODE);
        if(StringUtils.isNotEmpty(storeCode)){
            // EDIT MODE
            final Store store = retailerService.getStoreByCode(storeCode, FetchPlanGraphRetailer.fullStoreFetchPlan());

            // SANITY CHECK
            if (store != null) {
                modelAndView.addObject(ModelConstants.STORE_VIEW_BEAN, backofficeViewBeanFactory.buildViewBeanStore(requestData, store));
            } else {
                final String url = requestUtil.getLastRequestUrl(request);
                return new ModelAndView(new RedirectView(url));
            }
            
            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.STORE_DETAILS, requestData, store));

            Object[] params = { store.getName() + " (" + store.getCode() + ")" };
            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.STORE_DETAILS.getKey(), params);

        } else {
            // ADD MODE

            initPageTitleAndMainContentTitle(request, modelAndView, BoUrls.STORE_ADD.getKey(), null);

            model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.STORE_LIST, requestData));
        }

        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.STORE_EDIT_URL, method = RequestMethod.POST)
	public ModelAndView submitRetailerEdit(final HttpServletRequest request, final Model model, @Valid @ModelAttribute(ModelConstants.STORE_FORM) StoreForm storeForm,
								BindingResult result, ModelMap modelMap) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        
        if (result.hasErrors()) {
            return storeEdit(request, model, storeForm);
        }
        
        Store store = null;
        if(StringUtils.isNotEmpty(storeForm.getId())){
            store = retailerService.getStoreById(storeForm.getId());
        }

        final String retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE);
        Retailer retailer = null;
        if(StringUtils.isNotEmpty(retailerCode)){
            retailer = retailerService.getRetailerByCode(retailerCode, FetchPlanGraphRetailer.fullStoreFetchPlan());
        }

        try {
            // CREATE OR UPDATE RETAILER
            webBackofficeService.createOrUpdateStore(retailer, store, storeForm);
            
            if(store == null){
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.STORE, "create_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.STORE_LIST, requestUtil.getRequestData(request));
                return new ModelAndView(new RedirectView(urlRedirect));
                
            } else {
                addSuccessMessage(request, getSpecificMessage(ScopeWebMessage.STORE, "update_success_message", locale));
                final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.STORE_DETAILS, requestUtil.getRequestData(request), store);
                return new ModelAndView(new RedirectView(urlRedirect));
            }
            
        } catch (Exception e) {
            addMessageError(result, null, "code", "code", getSpecificMessage(ScopeWebMessage.RETAILER, "create_or_update_message", locale));
            logger.error("Can't save or update Retailer:" + storeForm.getId() + "/" + storeForm.getCode(), e);
            return storeEdit(request, model, storeForm);
        }
	}
	
	/**
	 * 
	 */
    @ModelAttribute(ModelConstants.STORE_FORM)
	protected StoreForm initStoreForm(final HttpServletRequest request, final Model model) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		final String storeCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_STORE_CODE);
		if(StringUtils.isNotEmpty(storeCode)){
	        final Store store = retailerService.getStoreByCode(storeCode, FetchPlanGraphRetailer.fullStoreFetchPlan());
	        return backofficeFormFactory.buildStoreForm(requestData, store);
		}
    	return backofficeFormFactory.buildStoreForm(requestData, null);
	}
    
    @ModelAttribute(ModelConstants.COUNTRIES)
    public List<ValueBean> getCountries(HttpServletRequest request) throws Exception {
		List<ValueBean> countriesValues = new ArrayList<ValueBean>();
		try {
	        final RequestData requestData = requestUtil.getRequestData(request);
	        final Locale locale = requestData.getLocale();
	        
			final Map<String, String> countries = referentialDataService.getCountriesByLocale(locale);
			if(countries != null){
				Set<String> countriesKey = countries.keySet();
				for (Iterator<String> iterator = countriesKey.iterator(); iterator.hasNext();) {
					final String countryKey = (String) iterator.next();
					countriesValues.add(new ValueBean(countryKey.replace(Constants.COUNTRY_MESSAGE_PREFIX, ""), countries.get(countryKey)));
				}
				Collections.sort(countriesValues, new Comparator<ValueBean>() {
					@Override
					public int compare(ValueBean o1, ValueBean o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return countriesValues;
    }
    
    @ModelAttribute(ModelConstants.RETAILERS_VIEW_BEAN)
    public List<ValueBean> getRetailers(HttpServletRequest request) throws Exception {
    	List<ValueBean> retailerValues = new ArrayList<ValueBean>();
    	try {
    		List<Retailer> retailers = retailerService.findAllRetailers();
	        if(retailers != null){
		        for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
		        	Retailer retailerIt = (Retailer) iterator.next();
		            final String retailerId = retailerIt.getId().toString();
		            retailerValues.add(new ValueBean(retailerId, retailerIt.getName()));
		        }
		        Collections.sort(retailerValues, new Comparator<ValueBean>() {
					@Override
					public int compare(ValueBean o1, ValueBean o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
	        }
		} catch (Exception e) {
			logger.error("", e);
		}
    	
    	return retailerValues;
    }
	
	private void displayList(final HttpServletRequest request, final Model model, final RequestData requestData,  List<Store> stores) throws Exception{
		String url = request.getRequestURI();
		String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
		String sessionKey = "PagedListHolder_Stores";

		PagedListHolder<StoreViewBean> storeViewBeanPagedListHolder = new PagedListHolder<StoreViewBean>();

       	if(stores == null){       	
            final String retailerCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_RETAILER_CODE);
    		stores = retailerService.findStoresByRetailerCode(retailerCode, FetchPlanGraphRetailer.fullStoreFetchPlan());
    	}
       	
        if(StringUtils.isEmpty(page)){
        	storeViewBeanPagedListHolder = initList(request, sessionKey, requestData, stores);
    		
        } else {
        	storeViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(sessionKey); 
	        if (storeViewBeanPagedListHolder == null) { 
	        	storeViewBeanPagedListHolder = initList(request, sessionKey, requestData, stores);
	        }
	        int pageTarget = new Integer(page).intValue() - 1;
	        int pageCurrent = storeViewBeanPagedListHolder.getPage();
	        if (pageCurrent < pageTarget) { 
	        	for (int i = pageCurrent; i < pageTarget; i++) {
	        		storeViewBeanPagedListHolder.nextPage(); 
				}
	        } else if (pageCurrent > pageTarget) { 
	        	for (int i = pageTarget; i < pageCurrent; i++) {
	        		storeViewBeanPagedListHolder.previousPage(); 
				}
	        } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, storeViewBeanPagedListHolder);
	}
	
	private PagedListHolder<StoreViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData, final List<Store> stores) throws Exception {
		PagedListHolder<StoreViewBean> storeViewBeanPagedListHolder = new PagedListHolder<StoreViewBean>();
		
		final List<StoreViewBean> storeViewBeans = new ArrayList<StoreViewBean>();
		for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
			Store storeIt = (Store) iterator.next();
			storeViewBeans.add(backofficeViewBeanFactory.buildViewBeanStore(requestData, storeIt));
		}
		storeViewBeanPagedListHolder = new PagedListHolder<StoreViewBean>(storeViewBeans);
		storeViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, storeViewBeanPagedListHolder); 
        
        return storeViewBeanPagedListHolder;
	}
}
