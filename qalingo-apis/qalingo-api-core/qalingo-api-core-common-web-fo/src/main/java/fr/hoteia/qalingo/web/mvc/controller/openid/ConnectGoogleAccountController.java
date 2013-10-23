package fr.hoteia.qalingo.web.mvc.controller.openid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.pojo.RequestData;
import fr.hoteia.qalingo.core.service.openid.Association;
import fr.hoteia.qalingo.core.service.openid.Endpoint;
import fr.hoteia.qalingo.core.service.openid.OpenProvider;
import fr.hoteia.qalingo.core.service.openid.Utils;

/**
 * 
 */
@Controller("connectGoogleAccountController")
public class ConnectGoogleAccountController extends AbstractOpenIdFrontofficeController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@RequestMapping("/connect-openid-google-account.html*")
	public ModelAndView connectGoogle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
				openIdService.setRealm(urlService.buildDomainePathUrl(requestData));
				String openIdCallBackURL = urlService.buildOpenIdCallBackUrl(requestData);
				openIdService.setReturnTo(urlService.buildAbsoluteUrl(requestData, openIdCallBackURL));
	        
				Endpoint endpoint = openIdService.lookupEndpoint(OpenProvider.GOOGLE_ACCOUNT.getPropertyKey().toLowerCase());
	            Association association = openIdService.lookupAssociation(endpoint);
	            request.getSession().setAttribute(Utils.ATTR_MAC, association.getRawMacKey());
	            request.getSession().setAttribute(Utils.ATTR_ALIAS, endpoint.getAlias());
	            String url = openIdService.getAuthenticationUrl(endpoint, association);
	            response.sendRedirect(url);                

			} catch (Exception e) {
				LOG.error("Connect With " + OpenProvider.GOOGLE_ACCOUNT.getPropertyKey() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}
		
		return null;
	}
	
}