package org.hoteia.qalingo.core.web.mvc.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.domain.bean.GeolocData;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.FoNavigatorGeolocationPojo;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.FoMessagePojo;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 */
@Controller("commonAjaxController")
public class CommonAjaxController extends AbstractFrontofficeQalingoController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected GeolocService geolocService;
	
    @RequestMapping(value = FoUrls.NAVIGATOR_GEOLOCATION_AJAX_URL, method = RequestMethod.POST)
    @ResponseBody
    public FoNavigatorGeolocationPojo navigatorGeolocation(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final Locale locale = requestData.getLocale();
        final String latitude = request.getParameter("latitude");
        final String longitude = request.getParameter("longitude");

        final FoNavigatorGeolocationPojo navigatorGeolocation = new FoNavigatorGeolocationPojo();
        navigatorGeolocation.setLatitude(latitude);
        navigatorGeolocation.setLongitude(longitude);
        
        try {
            EngineEcoSession engineEcoSession = requestUtil.handleGeolocLatitudeLongitude(requestData, latitude, longitude);
            GeolocData geolocData = engineEcoSession.getGeolocData();
            navigatorGeolocation.setCity(geolocData.getCity().getName());
            navigatorGeolocation.setCountry(geolocData.getCountry().getName());
            FoMessagePojo successMessage = new FoMessagePojo();
            successMessage.setId("success-navigator-geolocation");
            Object[] messageParams = { latitude, longitude };
            successMessage.setMessage(getSpecificMessage("GEOLOCATION", "navigator_geolocation_success_message", messageParams, locale));
            navigatorGeolocation.getSuccessMessages().add(successMessage);
            return navigatorGeolocation;
            
        } catch (Exception e) {
            logger.error("", e);
            FoMessagePojo errorMessage = new FoMessagePojo();
            errorMessage.setId("error-navigator-geolocation");
            Object[] messageParams = { latitude, longitude };
            errorMessage.setMessage(getSpecificMessage("GEOLOCATION", "navigator_geolocation_error_message", messageParams, locale));
            navigatorGeolocation.getErrorMessages().add(errorMessage);
            navigatorGeolocation.setStatus(false);
            return navigatorGeolocation;
        }
    }
    
}